package com.cz2006.fitflop.ui;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.User;
import com.cz2006.fitflop.model.UserLocation;
import com.cz2006.fitflop.services.GoogleLocationService;
import com.cz2006.fitflop.ui.screens.MapsFragment;
import com.cz2006.fitflop.ui.screens.NotificationsFragment;
import com.cz2006.fitflop.ui.screens.ProfileFragment;
import com.cz2006.fitflop.ui.screens.SettingsFragment;
import com.cz2006.fitflop.ui.screens.StarredFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import es.dmoral.toasty.Toasty;

import static com.cz2006.fitflop.Constants.ERROR_DIALOG_REQUEST;
import static com.cz2006.fitflop.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.cz2006.fitflop.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class BaseActivity extends AppCompatActivity {
//    RadioGroup radioGroup1;
//    RadioButton screen;
    private static final String TAG = "BaseActivity";


    // DataBase
    private FirebaseFirestore mDb;

    // Location Related Vars
    private UserLocation mUserLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;

    //widgets
    private ProgressBar mProgressBar;

    // Btm Nav Bar
    MeowBottomNavigation meo;
    Fragment selected_fragment = null;
    Fragment fragment_starred = null;
    Fragment fragment_notifications = null;
    Fragment fragment_home = null;
    Fragment fragment_profile = null;
    Fragment fragment_settings = null;

    private static final int ID_STARRED         = 1;
    private static final int ID_NOTIFICATIONS   = 2;
    private static final int ID_HOME            = 3;
    private static final int ID_PROFILE         = 4;
    private static final int ID_SETTINGS        = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        // Get permission to use Location Services
        mDb = FirebaseFirestore.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(BaseActivity.this);

        // Init Btm Nav Bar
        meo = (MeowBottomNavigation) findViewById(R.id.bottomNavBar);
        meo.add(new MeowBottomNavigation.Model(1, R.drawable.ic_star_black_24dp));
        meo.add(new MeowBottomNavigation.Model(2, R.drawable.ic_message_black_24dp));
        meo.add(new MeowBottomNavigation.Model(3, R.drawable.ic_home_black_24dp));
        meo.add(new MeowBottomNavigation.Model(4, R.drawable.ic_profile_black_24dp));
        meo.add(new MeowBottomNavigation.Model(5, R.drawable.ic_settings_black_24dp));

        if(getIntent().hasExtra("Notification_Fragment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationsFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsFragment()).commit();
        }

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toasty.info(BaseActivity.this, "Clicked item" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                    switch (item.getId()) {
                        case ID_STARRED:
                            if (fragment_starred==null) {
                                selected_fragment = new StarredFragment(); fragment_starred = selected_fragment;
                            }
                            else selected_fragment = fragment_starred;
                            break;
                        case ID_NOTIFICATIONS:
                            if (fragment_notifications==null) {
                                selected_fragment = new NotificationsFragment(); fragment_notifications = selected_fragment;
                            }
                            else selected_fragment = fragment_notifications;
                            break;
                        case ID_HOME:
                            if (fragment_home==null) {
                                selected_fragment = new MapsFragment(); fragment_home = selected_fragment; Log.e(TAG, "DIE");
                            }
                            else {
                                selected_fragment = fragment_home; Log.e(TAG, "NODIE");
                            }
                            break;
                        case ID_PROFILE:
                            if (fragment_profile==null) {
                                selected_fragment = new ProfileFragment(); fragment_profile = selected_fragment;
                            }
                            else selected_fragment = fragment_profile;
                            break;
                        case ID_SETTINGS:
                            if (fragment_settings==null) {
                                selected_fragment = new SettingsFragment(); fragment_settings = selected_fragment;
                            }
                            else selected_fragment = fragment_settings;
                            break;
                    }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();
            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case ID_STARRED:
                        if (fragment_starred==null) {
                            selected_fragment = new StarredFragment(); fragment_starred = selected_fragment;
                        }
                        else selected_fragment = fragment_starred;
                        break;
                    case ID_NOTIFICATIONS:
                        if (fragment_notifications==null) {
                            selected_fragment = new NotificationsFragment(); fragment_notifications = selected_fragment;
                        }
                        else selected_fragment = fragment_notifications;
                        break;
                    case ID_HOME:
                        if (fragment_home==null) {
                            selected_fragment = new MapsFragment(); fragment_home = selected_fragment;
                        }
                        else selected_fragment = fragment_home;
                        break;
                    case ID_PROFILE:
                        if (fragment_profile==null) {
                            selected_fragment = new ProfileFragment(); fragment_profile = selected_fragment;
                        }
                        else selected_fragment = fragment_profile;
                        break;
                    case ID_SETTINGS:
                        if (fragment_settings==null) {
                            selected_fragment = new SettingsFragment(); fragment_settings = selected_fragment;
                        }
                        else selected_fragment = fragment_settings;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                getUserDetails();
            }
            else{
                getLocationPermission();
            }
        }
    }

    private boolean checkMapServices(){
        if(isGoogleServicesOK()){
            if(isGPSEnabled()){
                return true;
            }
        }
        return false;
    }

    public boolean isGoogleServicesOK(){
        /** Checks whether device is able to use Google Services */
        Log.d(TAG, "isGoogleServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(BaseActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isGoogleServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occurred but we can resolve it
            Log.d(TAG, "isGoogleServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(BaseActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toasty.warning(BaseActivity.this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean isGPSEnabled(){
        /** Checks whether device has GPS enabled */
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getUserDetails(){
        if(mUserLocation == null){
            mUserLocation = new UserLocation();
            DocumentReference userRef = mDb.collection(getString(R.string.collection_users))
                    .document(FirebaseAuth.getInstance().getUid());

            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: successfully set the user client.");
                        User user = task.getResult().toObject(User.class);
                        mUserLocation.setUser(user);
                        ((UserClient)(getApplicationContext())).setUser(user);
                        getLastKnownLocation();
                    }
                }
            });
        }
        else{
            getLastKnownLocation();
        }
    }

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: called.");


        if (ActivityCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
            @Override
            public void onComplete(@NonNull Task<android.location.Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    mUserLocation.setGeo_point(geoPoint);
                    mUserLocation.setTimestamp(null);
                    saveUserLocation();
                    startLocationService();
                }
            }
        });
    }

    private void saveUserLocation(){

        if(mUserLocation != null){
            DocumentReference locationRef = mDb
                    .collection(getString(R.string.collection_user_locations))
                    .document(FirebaseAuth.getInstance().getUid());

            locationRef.set(mUserLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "saveUserLocation: \ninserted user location into database." +
                                "\n latitude: " + mUserLocation.getGeo_point().getLatitude() +
                                "\n longitude: " + mUserLocation.getGeo_point().getLongitude());
                    }
                }
            });
        }
    }

    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(this, GoogleLocationService.class);
//        this.startService(serviceIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                BaseActivity.this.startForegroundService(serviceIntent);
            }else{
                startService(serviceIntent);
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.cz2006.fitflop.services.GoogleLocationService".equals(service.service.getClassName())) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.");
        return false;
    }

    private void getLocationPermission() {
        /**
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getUserDetails();
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    getUserDetails();
                }
                else{
                    getLocationPermission();
                }
            }
        }
    }

    // Supporting Methods
    private void buildAlertMessageNoGps() {
        /** Prompts user to enable GPS on phone */
        final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        // To find out whether user accepted or denied permissions
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}