package data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Photo {

    private String id,fileName,locationName;
    private  double latitude,longtitude;
    private Set<String> tags;
    private Date uploadDate;
    public Photo(String id,String fileName, String locationName, Date uploadDate, double latitude, double longtitude) {
        this.id = id;
        this.fileName = fileName;
        this.locationName = locationName;
        this.uploadDate = uploadDate;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.tags = new HashSet<>(); // initialize the tags set
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }


    public void addTag(String tag) {
        tags.add(tag);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", locationName='" + locationName + '\'' +
                ", uploadDate=" + uploadDate +
                ", tags=" + tags +
                '}';
    }
}
