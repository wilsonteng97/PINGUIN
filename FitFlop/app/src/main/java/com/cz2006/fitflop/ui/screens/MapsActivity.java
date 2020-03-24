package com.cz2006.fitflop.ui.screens;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cz2006.fitflop.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPointStyle;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

import static com.cz2006.fitflop.R.layout.activity_maps;

public class MapsActivity extends Fragment implements OnMapReadyCallback, LocationListener {

    private static final String TAG = "MapsActivity";
    private String apiPlacesKey; // Instantiated in OnCreate
    PlacesClient placesClient;
    private LatLng searched = new LatLng(1.27274, 103.602552); //default marker

    private static GoogleMap googleMap;
    private LocationManager mLocationManager;

    private static final double EARTH_RADIUS = 6371000; //meters

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(activity_maps, container, false);
        apiPlacesKey = getActivity().getApplicationContext().getResources().getString(R.string.google_places_api_key);
        final SupportMapFragment myMAPF = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        myMAPF.getMapAsync(this);

        return view;
    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLngBounds Singapore = new LatLngBounds(new LatLng(1.27274, 103.602552), new LatLng(1.441715, 104.039828));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Singapore.getCenter(), 10));
        searchPlaces(googleMap);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
        if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, String.valueOf(location.getLatitude()));
        Log.i(TAG, String.valueOf(location.getLongitude()));

        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(userLocation)).setTitle("Current Location");
        try {
            GeoJsonLayer layer = new GeoJsonLayer(googleMap, R.raw.gyms_sg_geojson, getActivity().getApplicationContext());
            GeoJsonLayer near_layer = get_features_near_current_location(googleMap, layer, location); //change bk to 10k
            near_layer.addLayerToMap();
            //Change icon
            addColorsToMarkers(layer);
        } catch (IOException e) {
            Log.e(TAG, "" + e.toString());
        } catch (JSONException e) {
            Log.e(TAG, "" + e.toString());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "Provider " + provider + " has now status: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "Provider " + provider + " is enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "Provider " + provider + " is disabled");
    }

    private HashMap<String, String> parse_html_table(String html_table) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Document doc = Jsoup.parse(html_table);
        Elements tableElements = doc.select("table");
        Elements tableRowElements = tableElements.select(":not(thead) tr");

        try {
            for (int i = 0; i < tableRowElements.size(); i++) {
                Element row = tableRowElements.get(i);
                Elements item = row.select("td");
                Elements header = row.select("th");
//                for (int j = 0; j < rowItems.size(); j++) {
//                    arrayList.add(rowItems.get(j).text());
//                }
//                Log.d(TAG, arrayList.toString());
                hashMap.put(header.text(), item.text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Map.Entry<String, String>> setOfEntries = hashMap.entrySet();

        // get the iterator from entry set
        Iterator<Map.Entry<String, String>> iterator = setOfEntries.iterator();

        // iterate over map
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String value = entry.getValue();

            if (value == null) {
                Log.d(TAG, "removing : " + entry);
                iterator.remove(); // always use remove() method of iterator
            }
        }

        return hashMap;
    }

    //to add color/change icon and change properties
    private void addColorsToMarkers(GeoJsonLayer layer) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
            BitmapDescriptor pointIcon = BitmapDescriptorFactory.fromResource(R.drawable.cdumbbelltwo);
            pointStyle.setIcon(pointIcon);
            String description = feature.getProperty("Description");
            HashMap<String, String> hashmap = parse_html_table(description);
            for (String key : hashmap.keySet()) {
                if(key.equals("NAME")){
                    String value = hashmap.get("NAME");
                    feature.setProperty(key,value);
                    pointStyle.setTitle(feature.getProperty("NAME"));
                }
            }
            feature.setPointStyle(pointStyle);
        }
    }

    private LatLng getLatLngFromGeoJsonFeature(GeoJsonFeature feature) {
        String[] coord_str = feature.getGeometry().getGeometryObject().toString().split(",");
        String lat = coord_str[0].substring(10);
        String lng = coord_str[1];
        lng = lng.substring(0, lng.length() - 1);
        LatLng latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        return latlng;
    }
    //update here, can just move the seekBar around if you find a better place to put
    private GeoJsonLayer get_features_near_current_location(GoogleMap googleMap, GeoJsonLayer layer, Location location) throws IOException, JSONException {
        LatLng current_user_location = new LatLng(location.getLatitude(), location.getLongitude());
        
        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekBar);
        int progress = seekBar.getProgress();
        double metres = progress * 1000;
        TextView progressShow = (TextView) getView().findViewById(R.id.textView);
        progressShow.setText("Progress: " + progress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        GeoJsonLayer new_layer = new GeoJsonLayer(googleMap, R.raw.empty_geojson, getActivity().getApplicationContext());
        if(new_layer!=null){
            new_layer.removeLayerFromMap();
        }
        for (GeoJsonFeature feature : layer.getFeatures()) {
            LatLng feature_location = getLatLngFromGeoJsonFeature(feature);
            if (is_feature_near(metres, feature_location, current_user_location)) {
                new_layer.addFeature(feature);
            }
            //added else to removeFeature but it doesnt work because it doesn't remove the map and re-add
            else{
                new_layer.removeFeature(feature);
            }
        }
        return new_layer;
    }

    private boolean is_feature_near(double metres, LatLng feature_location, LatLng current_user_location) {
        boolean isNear = false;
        double dist;

        dist = getDistance(metres, feature_location, current_user_location);
        if (dist < metres) {
            isNear = true;
        }
        return isNear;
    }

    private double getDistance(double metres, LatLng feature_location, LatLng current_user_location) {
        double dist = 0;
        if (metres <= 100) {
            dist = Math.abs(getUnaccurateDist(feature_location, current_user_location));
        } else {
            dist = Math.abs(getAccurateDist(feature_location, current_user_location));
        }
        return dist;
    }

    /**
     * Inaccurate for distances more than 100m but very fast. (Assumes the world is Flat)
     * Works by approximating the distance of a single latitude/longitude at the given latitude and
     *
     * @param latLng1
     * @param latLng2
     * @returns Pythagorean distance in metres
     */
    public double getUnaccurateDist(LatLng latLng1, LatLng latLng2) {
        double lat1 = latLng1.latitude;
        double lng1 = latLng1.longitude;
        double lat2 = latLng2.latitude;
        double lng2 = latLng2.longitude;

        double a = (lat1 - lat2) * distPerLat(lat1);
        double b = (lng1 - lng2) * distPerLng(lat1);
        return Math.sqrt(a * a + b * b);
    }

    private static double distPerLng(double lat) {
        return 0.0003121092 * Math.pow(lat, 4)
                + 0.0101182384 * Math.pow(lat, 3)
                - 17.2385140059 * lat * lat
                + 5.5485277537 * lat + 111301.967182595;
    }

    private static double distPerLat(double lat) {
        return -0.000000487305676 * Math.pow(lat, 4)
                - 0.0033668574 * Math.pow(lat, 3)
                + 0.4601181791 * lat * lat
                - 1.4558127346 * lat + 110579.25662316;
    }


    private double getAccurateDist(LatLng latLng1, LatLng latLng2) {
        double lat1 = latLng1.latitude;
        double lng1 = latLng1.longitude;
        double lat2 = latLng2.latitude;
        double lng2 = latLng2.longitude;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

//        double height = h1 - h2;
//        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
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
                // Add marker to searched location
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

