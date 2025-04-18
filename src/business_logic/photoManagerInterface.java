package business_logic;

import data.Photo;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface photoManagerInterface {
    abstract void uploadPhoto(Photo photo);
    abstract void addTagToPhoto(String photoId, String tag);
    abstract Set<Photo> searchByTag(String tag);
    abstract Set<Photo> searchByLocation(String locationName);
    abstract Set<Photo> searchByDate(Date date);
    abstract Set<Photo> searchByDateRange(Date from, Date to);
    abstract Set<Photo> searchByGeoRange(double minLat, double maxLat, double minLon, double maxLon);
    abstract List<Photo> weightedSearch(String tag, String location, Date date);
}
