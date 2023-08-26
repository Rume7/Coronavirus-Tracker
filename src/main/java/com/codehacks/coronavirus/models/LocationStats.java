package com.codehacks.coronavirus.models;

import java.util.Objects;

public class LocationStats {

    private String state;
    private String country;
    private String latitude;
    private String longitude;
    private long latestStats;

    public LocationStats() {}

    public LocationStats(String state, String country, String latitude, String longitude, long latestStats) {
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latestStats = latestStats;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public long getLatestStats() {
        return latestStats;
    }

    public void setLatestStats(long latestStats) {
        this.latestStats = latestStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationStats that = (LocationStats) o;
        return country.equals(that.country) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, latitude, longitude);
    }

    @Override
    public String toString() {
        return "LocationStats{state=" + state + ", country=" + country +
                ", latitude=" + latitude + ", longitude=" + longitude + ", latestStats=" + latestStats + '}';
    }
}
