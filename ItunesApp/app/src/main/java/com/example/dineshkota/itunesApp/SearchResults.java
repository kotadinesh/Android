package com.example.dineshkota.itunesApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class SearchResults extends AppCompatActivity {
    ListView lv;
    CustomTrackAdapter customTrackAdapter;
    List<Track> tracks;
    Button bfav;
    ImageButton star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results2);


        tracks= (List<Track>) getIntent().getSerializableExtra("TRACKARRAY");

        lv = (ListView) findViewById(R.id.lv);
        customTrackAdapter=new CustomTrackAdapter(getApplicationContext(),tracks);
        lv.setAdapter(customTrackAdapter);
        Log.d("lvsize",""+lv.getAdapter().getCount());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inten = new Intent(SearchResults.this,MusicDetails.class);

                inten.putExtra("POSITIONARRAY",tracks.get(position));
                startActivity(inten);
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
