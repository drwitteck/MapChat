package edu.temple.mapchat;

public class Partners {
    private String username, latitude, longitude;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Partners (String username, String latitude, String longitude) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
