package com.example.dineshkota.itunesApp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dineshkota on 10/7/17.
 */

public class TrackUtil {
    static public class TrackJSONParser{
        static ArrayList<Track> parseTrack(String in) throws JSONException {
            ArrayList<Track> trackList = new ArrayList<Track>();

            JSONObject root = new JSONObject(in);

            JSONObject resultsJSONObject =root.getJSONObject("results");
            JSONObject trackmatchesJSONObject = resultsJSONObject.getJSONObject("trackmatches");
            JSONArray trackJSONArray = trackmatchesJSONObject.getJSONArray("track");
            //JSONObject trackJSONObject = trackmatchesJSONObject.getJSONObject("track");
            //JSONArray trackImgJSONObject = trackJSONObject.getJSONArray("image");

            for(int i=0;i<trackJSONArray.length();i++){
                JSONObject trackObject = trackJSONArray.getJSONObject(i);
                Track track = new Track();
                track.setName(trackObject.getString("name"));
                track.setArtist(trackObject.getString("artist"));
                track.setTrackurl(trackObject.getString("url"));
                JSONArray trackImgJson = trackObject.getJSONArray("image");
                JSONObject smallImgurl = trackImgJson.getJSONObject(0);
                JSONObject largeImgurl = trackImgJson.getJSONObject(2);
                track.setSimgurl(smallImgurl.get("#text").toString());
                track.setLimgurl(largeImgurl.get("#text").toString());
                trackList.add(track);
            }


            return trackList;
        }
    }
    static public class TrackSimilarJsonParser{
        static ArrayList<Track> parseTrack(String in) throws JSONException {
            ArrayList<Track> trackList = new ArrayList<Track>();
            JSONObject root = new JSONObject(in);
            JSONObject resultsJSONObject =root.getJSONObject("similartracks");
            JSONArray trackJSONArray = resultsJSONObject.getJSONArray("track");
            for(int i=0;i<trackJSONArray.length();i++){
                JSONObject trackObject = trackJSONArray.getJSONObject(i);
                Track track = new Track();
                track.setName(trackObject.getString("name"));
                JSONObject trackArtist = trackObject.getJSONObject("artist");
                track.setArtist(trackArtist.getString("name"));
                track.setTrackurl(trackObject.getString("url"));
                JSONArray trackImgJson = trackObject.getJSONArray("image");
                JSONObject smallImgurl = trackImgJson.getJSONObject(0);
                JSONObject largeImgurl = trackImgJson.getJSONObject(2);
                track.setSimgurl(smallImgurl.get("#text").toString());
                track.setLimgurl(largeImgurl.get("#text").toString());
                trackList.add(track);
            }
            Log.d("inJSON",""+trackList.size());
            return trackList;
        }

    }
}
