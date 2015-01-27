package com.locationdevs.locationsaverapp;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapActivity extends Activity implements OnMapReadyCallback{

    MapFragment mapFragment;;
    int i=0;
    double longitude=0,latitude=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        AddMarkers(map);
    }

    void AddMarkers(GoogleMap map){
        InputStream inputStream = null;
        try {
            inputStream = openFileInput("SavedData.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while(true){
                i++;
                String title = new String("Position" + i);
                try {
                    String[] recieveString= bufferedReader.readLine().split(" ");
                    if(recieveString[0].equals(null)|recieveString[1].equals(null)){
                        break;
                    }
                    latitude = Double.parseDouble(recieveString[0]);
                    longitude = Double.parseDouble(recieveString[1]);
                    map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(title));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}