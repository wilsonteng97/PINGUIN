package com.cz2006.fitflop.ui.screens;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cz2006.fitflop.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;

import static com.cz2006.fitflop.R.layout.activity_maps;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private String apiPlacesKey = "AIzaSyDWMCbxmtQumcTuacPtZR4EW81RmEcnF0k"; //store this value in string resource file?
    PlacesClient placesClient;
    private LatLng searched = new LatLng(1.27274, 103.602552); //default marker

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_maps, container, false);

        final SupportMapFragment myMAPF = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        myMAPF.getMapAsync(this);

        return view;
    }

    public void onMapReady(GoogleMap googleMap) {
        // Display Singapore
        LatLngBounds Singapore = new LatLngBounds(new LatLng(1.27274, 103.602552), new LatLng(1.441715, 104.039828));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Singapore.getCenter(), 10));

        searchPlaces(googleMap);

//        try {
//            KmlLayer layer = new KmlLayer(googleMap, R.raw.gyms_sg_kml, getActivity().getApplicationContext());
//            layer.addLayerToMap();
//        } catch (XmlPullParserException e) {
//            Log.e(TAG,""+e.toString());
//        } catch (IOException e) {
//            Log.e(TAG,""+e.toString());
//        }

        try {
            GeoJsonLayer layer = new GeoJsonLayer(googleMap, R.raw.gyms_sg_geojson, getActivity().getApplicationContext());
            layer.addLayerToMap();
        } catch (IOException e) {
            Log.e(TAG,""+e.toString());
        } catch (JSONException e) {
            Log.e(TAG,""+e.toString());
        }


    }

    public void searchPlaces(final GoogleMap googleMap){
        if (!Places.isInitialized()){
            Places.initialize(getActivity().getApplicationContext(), apiPlacesKey);
        }
        placesClient = Places.createClient(getContext());
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                searched = place.getLatLng();
                Log.i(TAG, "onPlaceSelected: "+searched.latitude+"\n"+searched.longitude);
                // FIXME: Add marker to searched location
                googleMap.addMarker(new MarkerOptions().position(searched).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(searched, 30);
                googleMap.animateCamera(yourLocation);
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
    }

//    LinearLayout dynamicContent, bottomNavBar;
//    private GoogleMap mMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.base_activity);
//
//        dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
//        bottomNavBar = (LinearLayout) findViewById(R.id.bottomNavBar);
//        View wizard = getLayoutInflater().inflate(activity_maps, null);
//        dynamicContent.addView(wizard);
//
//        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
//        RadioButton rb=(RadioButton)findViewById(R.id.Home);
//
//        rb.setCompoundDrawablesWithIntrinsicBounds( 0,R.drawable.ic_home_black_selected_24dp, 0,0);
//        rb.setTextColor(Color.parseColor("#3F51B5"));
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    public void onMapReady(GoogleMap googleMap) {
//        // Add a marker in Sydney, Australia,
//        // and move the map's camera to the same location.
//        //LatLng sydney = new LatLng(-33.852, 151.211);
//        //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        // Display Singapore :D
//        LatLngBounds Singapore = new LatLngBounds(new LatLng(1.27274, 103.602552), new LatLng(1.441715, 104.039828));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Singapore.getCenter(), 10));
//    }
}
