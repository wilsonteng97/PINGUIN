package com.cz2006.fitflop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.ui.screens.MapsActivity;
import com.cz2006.fitflop.ui.screens.NotificationsActivity;
import com.cz2006.fitflop.ui.screens.ProfileActivity;
import com.cz2006.fitflop.ui.screens.SettingsActivity;
import com.cz2006.fitflop.ui.screens.StarredActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import static com.cz2006.fitflop.util.InAppNotifications.toastNotification;

public class BaseActivity extends AppCompatActivity {


//    RadioGroup radioGroup1;
//    RadioButton screen;
    MeowBottomNavigation meo;
    Fragment selected_fragment = null;
    private static final int ID_STARRED         = 1;
    private static final int ID_NOTIFICATIONS   = 2;
    private static final int ID_HOME            = 3;
    private static final int ID_PROFILE         = 4;
    private static final int ID_SETTINGS        = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        meo = (MeowBottomNavigation)findViewById(R.id.bottomNavBar);
        meo.add(new MeowBottomNavigation.Model(1, R.drawable.ic_star_black_24dp));
        meo.add(new MeowBottomNavigation.Model(2, R.drawable.ic_message_black_24dp));
        meo.add(new MeowBottomNavigation.Model(3, R.drawable.ic_home_black_24dp));
        meo.add(new MeowBottomNavigation.Model(4, R.drawable.ic_profile_black_24dp));
        meo.add(new MeowBottomNavigation.Model(5, R.drawable.ic_settings_black_24dp));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapsActivity()).commit();

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                toastNotification(BaseActivity.this, "Clicked item" + item.getId()).show();
            }
        });

        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                switch (item.getId()) {
                    case ID_STARRED:
                        selected_fragment = new StarredActivity();
                        break;
                    case ID_NOTIFICATIONS:
                        selected_fragment = new NotificationsActivity();
                        break;
                    case ID_HOME:
                        selected_fragment = new MapsActivity();
                        break;
                    case ID_PROFILE:
                        selected_fragment = new ProfileActivity();
                        break;
                    case ID_SETTINGS:
                        selected_fragment = new SettingsActivity();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();
            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // Do Nothing
            }
        });



//        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
//        screen = (RadioButton)findViewById(R.id.screen);
//        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId)
//            {
//                Intent in;
//                Log.i("matching", "matching inside1 bro" + checkedId);
//                switch (checkedId)
//                {
//                    case R.id.Starred:
//                        Log.i("matching", "matching inside1 matching" +  checkedId);
//                        in=new Intent(getBaseContext(), StarredActivity.class);
//                        startActivity(in);
//                        overridePendingTransition(0, 0);
//                        break;
//                    case R.id.Notifications:
//                        Log.i("matching", "matching inside1 watchlistAdapter" + checkedId);
//
//                        in = new Intent(getBaseContext(), NotificationsActivity.class);
//                        startActivity(in);
//                        overridePendingTransition(0, 0);
//
//                        break;
//                    case R.id.Home:
//                        Log.i("matching", "matching inside1 rate" + checkedId);
//
//                        in = new Intent(getBaseContext(), MapsActivity.class);
//                        startActivity(in);
//                        overridePendingTransition(0, 0);
//                        break;
//                    case R.id.Profile:
//                        Log.i("matching", "matching inside1 listing" + checkedId);
//                        in = new Intent(getBaseContext(), ProfileActivity.class);
//                        startActivity(in);
//                        overridePendingTransition(0, 0);
//                        break;
//                    case R.id.Settings:
//                        Log.i("matching", "matching inside1 deals" + checkedId);
//                        in = new Intent(getBaseContext(), SettingsActivity.class);
//                        startActivity(in);
//                        overridePendingTransition(0, 0);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }
}