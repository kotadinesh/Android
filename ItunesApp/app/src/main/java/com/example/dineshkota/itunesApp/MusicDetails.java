package com.example.dineshkota.itunesApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MusicDetails extends AppCompatActivity implements GetSimilarAsync.Idata1 {
    TextView name,artist,url;
    ImageView img;
    String apikey = "9335aaafdda8b9eeecbdf58a4146c790";
    List<Track> similarT;
    ListView lv;
    CustomTrackAdapter customTrackAdapter;
    List<Track> tracks;
    ArrayList<Track> favlist;
    Track track;
    String changeurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_details);

        track = (Track) getIntent().getSerializableExtra("POSITIONARRAY");
        name = (TextView)findViewById(R.id.textdName);
        artist=(TextView)findViewById(R.id.textdArtist);
        url=(TextView)findViewById(R.id.textdUrl);
        img = (ImageView) findViewById(R.id.imagedView);

        Picasso.with(this).load(track.getLimgurl()).into(img);
        name.setText("Name: "+track.getName());
        artist.setText("Artist: "+track.getArtist());
        url.setText("Url: "+track.getTrackurl());
        favlist = (ArrayList<Track>) CustomTrackAdapter.getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey");

        String similarurl;
        similarurl= " http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist="+track.getArtist().trim()+
                "&track="+track.getName().trim()+"&api_key="+apikey+"&format=json&limit=10";
        new GetSimilarAsync(MusicDetails.this).execute(similarurl);
    }
    @Override
    public void setupsimilardata(ArrayList<Track> t) {
        for(Track track : t){
            for(Track f : favlist){
                if(f.equals(track)){
                    track.setGold(true);
                }

            }
        }
        tracks=t;
        lv = (ListView) findViewById(R.id.similarList);
        customTrackAdapter=new CustomTrackAdapter(getApplicationContext(),tracks);
        lv.setAdapter(customTrackAdapter);
        lv.setItemsCanFocus(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MusicDetails.this,MusicDetails.class);
                i.putExtra("POSITIONARRAY", tracks.get(position));
                MusicDetails.this.startActivity(i);

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.Quit:
                this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
