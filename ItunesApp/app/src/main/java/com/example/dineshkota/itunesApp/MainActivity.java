package com.example.dineshkota.itunesApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetMusicAsync.Idata {
    Button search;
    EditText searchText;
    String xmlurl;
    String apikey = "9335aaafdda8b9eeecbdf58a4146c790";
    public static ArrayList<Track> trackArrayList;
    List<Track> favlist;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.favouriteList);
        favlist=(List<Track>) CustomTrackAdapter.getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey");
        if(favlist == null)
            favlist = new ArrayList<>();
        CustomTrackAdapter customTrackAdapter = new CustomTrackAdapter(MainActivity.this,favlist);
        listView.setAdapter(customTrackAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(MainActivity.this,MusicDetails.class);

                inten.putExtra("POSITIONARRAY",favlist.get(position));
                startActivity(inten);
            }
        });
        search = (Button) findViewById(R.id.buttonSearch);
        searchText = (EditText) findViewById(R.id.editSearchMusic);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams("GET"," http://ws.audioscrobbler.com/2.0/?method=track.search&");
                params.addParams("track",searchText.getText().toString());
                params.addParams("api_key",apikey);
                xmlurl = params.getEncodedUrl()+"&format=json&limit=20";
                new GetMusicAsync(MainActivity.this).execute(xmlurl);
             }
        });
    }

    @Override
    public void setupdata(ArrayList<Track> t) {
        if(t!=null){
            Intent intent = new Intent(MainActivity.this,SearchResults.class);
            for(Track track : t){

                for(Track f : favlist){
                    if(f.equals(track)){
                        track.setGold(true);
                    }
                }
            }
            intent.putExtra("TRACKARRAY",t);
            startActivity(intent);
        }
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
