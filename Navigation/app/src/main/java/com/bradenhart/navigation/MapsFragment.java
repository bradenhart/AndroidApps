package com.bradenhart.navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by bradenhart on 15/12/14.
 */
public class MapsFragment extends Fragment {

    private MapView mapView;
    private GoogleMap map;
    private GoogleMapOptions mapOptions = new GoogleMapOptions();
    private final LatLng LOCATION_DUNEDIN_CAMPUS = new LatLng(-45.864684,170.514423); // Otago University, Dunedin Campus coordinates
    private Button normalBtn, satelliteBtn, hybridBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LOCATION_DUNEDIN_CAMPUS, 16);
        map.animateCamera(cameraUpdate);

        // setting map options
        setMapOptions();
        //map.getUiSettings().setMapToolbarEnabled(false); what does this do?


        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // add a marker to the map at the center of Dunedin campus
        map.addMarker(new MarkerOptions()
                        .position(LOCATION_DUNEDIN_CAMPUS)
                        .title(getString(R.string.otago_uni_title))
                        .snippet(getString(R.string.otago_uni_snippet))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        );

        // Get map-type buttons
        getMapButtons(v);
        // Set map-type buttons
        setUpMapTypeButtons();

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void setMapOptions() {
        mapOptions.compassEnabled(true)
                .tiltGesturesEnabled(false);

        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    private void getMapButtons(View v) {
        normalBtn = (Button) v.findViewById(R.id.btn_normal);
        hybridBtn = (Button) v.findViewById(R.id.btn_hybrid);
        satelliteBtn = (Button) v.findViewById(R.id.btn_satellite);
    }

    private void setUpMapTypeButtons() {

        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.getMapType() != GoogleMap.MAP_TYPE_NORMAL) {
                    Toast.makeText(getActivity(), "Map type NORMAL requested", Toast.LENGTH_SHORT).show();
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        satelliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.getMapType() != GoogleMap.MAP_TYPE_SATELLITE) {
                    Toast.makeText(getActivity(), "Map type SATELLITE requested", Toast.LENGTH_SHORT).show();
                    // Satellite map type requested
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });

        hybridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.getMapType() != GoogleMap.MAP_TYPE_HYBRID) {
                    Toast.makeText(getActivity(), "Map type HYBRID requested", Toast.LENGTH_SHORT).show();
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });

    }


}
