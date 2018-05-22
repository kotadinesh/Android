package com.example.dineshkota.itunesApp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dineshkota on 10/14/17.
 */

public class GetSimilarAsync extends AsyncTask<String, Void, ArrayList<Track>> {
    Context c;
    Idata1 getMusicActivity;
    public GetSimilarAsync(Idata1 getMusicActivity){this.getMusicActivity = getMusicActivity;}
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

            return TrackUtil.TrackSimilarJsonParser.parseTrack(stringBuilder.toString());
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

       Log.d("size"," "+tracks.size());
        getMusicActivity.setupsimilardata(tracks);




    }
    static public interface Idata1{
        public void setupsimilardata(ArrayList<Track> t);
    }
}
