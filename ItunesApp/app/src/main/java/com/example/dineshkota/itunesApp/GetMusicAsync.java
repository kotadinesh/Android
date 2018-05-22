package com.example.dineshkota.itunesApp;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dineshkota on 10/7/17.
 */

public class GetMusicAsync extends AsyncTask<String, Void, ArrayList<Track>> {
    Context c;
    Idata getMusicActivity;



    public GetMusicAsync(Idata getMusicActivity){this.getMusicActivity = getMusicActivity;}
    @Override
    protected ArrayList<Track> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line ="";
            while ((line=bufferedReader.readLine())!=null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            return TrackUtil.TrackJSONParser.parseTrack(stringBuilder.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        super.onPostExecute(tracks);

//        Log.d("size"," "+tracks.size());
        getMusicActivity.setupdata(tracks);




    }
    static public interface Idata{
        public void setupdata(ArrayList<Track> t);
    }
}
