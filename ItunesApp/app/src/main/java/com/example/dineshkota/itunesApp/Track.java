package com.example.dineshkota.itunesApp;

import java.io.Serializable;

/**
 * Created by dineshkota on 10/7/17.
 */

public class Track implements Serializable {
    String name,simgurl,limgurl,artist,trackurl;
    boolean isGold;

    public boolean isGold() {
        return isGold;
    }

    public void setGold(boolean gold) {
        isGold = gold;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", simgurl='" + simgurl + '\'' +
                ", limgurl='" + limgurl + '\'' +
                ", artist='" + artist + '\'' +
                ", trackurl='" + trackurl + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimgurl() {
        return simgurl;
    }

    public void setSimgurl(String simgurl) {
        this.simgurl = simgurl;
    }

    public String getLimgurl() {
        return limgurl;
    }

    public void setLimgurl(String limgurl) {
        this.limgurl = limgurl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrackurl() {
        return trackurl;
    }

    public void setTrackurl(String trackurl) {
        this.trackurl = trackurl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (name != null ? !name.equals(track.name) : track.name != null) return false;
        if (simgurl != null ? !simgurl.equals(track.simgurl) : track.simgurl != null) return false;
        if (limgurl != null ? !limgurl.equals(track.limgurl) : track.limgurl != null) return false;
        if (artist != null ? !artist.equals(track.artist) : track.artist != null) return false;
        return trackurl != null ? trackurl.equals(track.trackurl) : track.trackurl == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (simgurl != null ? simgurl.hashCode() : 0);
        result = 31 * result + (limgurl != null ? limgurl.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (trackurl != null ? trackurl.hashCode() : 0);
        return result;
    }
}
