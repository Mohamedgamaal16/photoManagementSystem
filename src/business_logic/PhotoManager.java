package business_logic;

import data.Photo;

import java.util.*;

public class PhotoManager implements photoManagerInterface{

    //    هنا عملت لكل طريقه سيرش ماب عشان استخدم inverted index زي بشهمندس سعد مقال
    private Map<String, Photo> photosById = new HashMap<>();
    private Map<String, Set<Photo>> photosByTag = new HashMap<>();
    private Map<String, Set<Photo>> photosByLocation = new HashMap<>();
    private Map<Date, Set<Photo>> photosByDate = new TreeMap<>();

    @Override
    public void uploadPhoto(Photo photo) {

        photosById.put(photo.getId(), photo);

        Set<String> tags = photo.getTags();
        if (tags != null) {
            for (String tag : tags) {
                if (!photosByTag.containsKey(tag)) {
                    photosByTag.put(tag, new HashSet<Photo>());
                }
                photosByTag.get(tag).add(photo);
            }
        }


        String location = photo.getLocationName();
        if (location != null) {
            Set<Photo> photosAtLocation = photosByLocation.get(location);
            if (photosAtLocation == null) {
               photosAtLocation = new HashSet<Photo>();
                photosByLocation.put(location, photosAtLocation);
            }
            photosAtLocation.add(photo);
        }


        Date date = photo.getUploadDate();
        if (date != null) {
            Set<Photo> photosOnDate = photosByDate.get(date);
            if (photosOnDate == null) {
                photosOnDate = new HashSet<Photo>();
                photosByDate.put(date, photosOnDate);
            }
            photosOnDate.add(photo);
        }

    }

    @Override
    public void addTagToPhoto(String photoId, String tag) {
        Photo photo = photosById.get(photoId);
        if (photo != null) {
            photo.addTag(tag);

            Set<Photo> photosWithTag = photosByTag.get(tag);
            if (photosWithTag == null) {
                photosWithTag = new HashSet<Photo>();
                photosByTag.put(tag, photosWithTag);
            }
            photosWithTag.add(photo);
        }
    }

    @Override
    public Set<Photo> searchByTag(String tag) {
        Set<Photo> result = photosByTag.get(tag);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public Set<Photo> searchByLocation(String locationName) {
        Set<Photo> result = photosByLocation.get(locationName);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public Set<Photo> searchByDate(Date date) {
        Set<Photo> result = photosByDate.get(date);
        if (result == null) {
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public Set<Photo> searchByDateRange(Date from, Date to) {
        Set<Photo> result = new HashSet<Photo>();
        SortedMap<Date, Set<Photo>> subMap = ((TreeMap<Date, Set<Photo>>) photosByDate).subMap(from, to);
        for (Set<Photo> photoSet : subMap.values()) {
            result.addAll(photoSet);
        }
        return result;
    }


    @Override
    public Set<Photo> searchByGeoRange(double minLat, double maxLat, double minLon, double maxLon) {
        Set<Photo> result = new HashSet<Photo>();
        for (Photo photo : photosById.values()) {
            double lat = photo.getLatitude();
            double lon = photo.getLongtitude();
            if (lat >= minLat && lat <= maxLat && lon >= minLon && lon <= maxLon) {
                result.add(photo);
            }
        }
        return result;
    }

    @Override
    public List<Photo> weightedSearch(String tag, String location, Date date) {
        Map<Photo, Integer> scoreMap = new HashMap<Photo, Integer>();

        // Match tag
        Set<Photo> tagMatches = photosByTag.get(tag);
        if (tagMatches != null) {
            for (Photo photo : tagMatches) {
                Integer score = scoreMap.get(photo);
                if (score == null) score = 0;
                scoreMap.put(photo, score + 3);
            }
        }

        // Match location
        Set<Photo> locationMatches = photosByLocation.get(location);
        if (locationMatches != null) {
            for (Photo photo : locationMatches) {
                Integer score = scoreMap.get(photo);
                if (score == null) score = 0;
                scoreMap.put(photo, score + 2);
            }
        }

        // Match date
        Set<Photo> dateMatches = photosByDate.get(date);
        if (dateMatches != null) {
            for (Photo photo : dateMatches) {
                Integer score = scoreMap.get(photo);
                if (score == null) score = 0;
                scoreMap.put(photo, score + 1);
            }
        }

        // Sort by score (descending)
        List<Map.Entry<Photo, Integer>> scoredList = new ArrayList<Map.Entry<Photo, Integer>>(scoreMap.entrySet());
        Collections.sort(scoredList, new Comparator<Map.Entry<Photo, Integer>>() {
            public int compare(Map.Entry<Photo, Integer> o1, Map.Entry<Photo, Integer> o2) {
                return o2.getValue() - o1.getValue(); // descending
            }
        });

        // Return top results
        List<Photo> result = new ArrayList<Photo>();
        for (Map.Entry<Photo, Integer> entry : scoredList) {
            result.add(entry.getKey());
        }
        return result;
    }
}
