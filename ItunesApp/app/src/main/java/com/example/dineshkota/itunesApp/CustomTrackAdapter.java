package com.example.dineshkota.itunesApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




/**
 * Created by dineshkota on 10/14/17.
 */

public class CustomTrackAdapter extends BaseAdapter  {
    private Context mContext;
    private List<Track> mTrack;
    public static List<Track> fav;



    public CustomTrackAdapter(Context mContext, List<Track> mTrack) {
        this.mContext = mContext;
        this.mTrack = mTrack;
        fav= (List<Track>) getSavedObjectFromPreference(mContext, "mPreference", "mObjectKey");
        if(fav == null){
            fav = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mTrack.size();
    }

    @Override
    public Object getItem(int position) {
        return mTrack.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.roview,null);
        TextView tname = (TextView) v.findViewById(R.id.textrName);
        TextView tart=(TextView)v.findViewById(R.id.textrArtist);
        ImageView timg = (ImageView)v.findViewById(R.id.imageTrack);
        final ImageButton star= (ImageButton) v.findViewById(R.id.imageButton);

        //check if already in fav list and is gold
        if(mTrack.get(position).isGold()){
            star.setImageResource(R.drawable.star2);
            star.setTag("star2");

        }
        else {
            star.setImageResource(R.drawable.star1);
            star.setTag("star1");
        }
        tname.setText(mTrack.get(position).getName().trim());
        tart.setText(mTrack.get(position).getArtist().trim());
        Picasso.with(mContext).load(mTrack.get(position).getSimgurl()).into(timg);
        v.setTag(mTrack.get(position));

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mContext instanceof MainActivity){
                 mTrack.remove(position);
                    notifyDataSetChanged();
                    fav.remove(position);
                    saveObjectToSharedPreference(v.getContext(), "mPreference", "mObjectKey", fav);

                }
                else {
                    //Log.d("favsize",fav.size()+"");
                    if(fav.size()>19)
                        Toast.makeText(mContext,"Favourite List is full", Toast.LENGTH_SHORT).show();
                    else{
                        if (star.getTag().equals("star1")) {
                            star.setImageResource(R.drawable.star2);
                            star.setTag("star2");
                            mTrack.get(position).setGold(true);
                            fav.add(mTrack.get(position));
                            saveObjectToSharedPreference(v.getContext(), "mPreference", "mObjectKey", fav);
                            Toast.makeText(mContext,"Added to favourites", Toast.LENGTH_SHORT).show();
                        } else {
                            star.setImageResource(R.drawable.star1);
                            star.setTag("star1");
                            ArrayList<Track> temp = new ArrayList<Track>(fav);
                            for(Track f : temp){
                                if(mTrack.get(position).equals(f)){
                                    fav.remove(f);
                                }

                            }
                            saveObjectToSharedPreference(v.getContext(), "mPreference", "mObjectKey", fav);
                            Toast.makeText(mContext,"Removed from favourites", Toast.LENGTH_SHORT).show();

                            mTrack.get(position).setGold(false);

                            //fav = (List<Track>) getSavedObjectFromPreference(v.getContext(), "mPreference", "mObjectKey");
                        }
                    }


                }

//                Log.d("fav",""+fav.size());
            }
        });
        return v;
    }
    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, List<Track> object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }
    public static <GenericClass> List getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            //sharedPreferences.edit().clear().commit();
            final Gson gson = new Gson();
            ArrayList<Track> s = new ArrayList<>();
            if(sharedPreferences.getString(preferenceKey, "") == null)
                s =  new ArrayList<>();

            Track[] x = gson.fromJson(sharedPreferences.getString(preferenceKey, ""), Track[].class);
            if(x == null)
                s = new ArrayList();

            s = new ArrayList<Track>(Arrays.asList(x));

            return  s;

        }
        return null;
    }

}


