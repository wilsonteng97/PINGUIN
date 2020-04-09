package com.cz2006.fitflop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.GeoJsonFeatureHashMapInfo;
import com.cz2006.fitflop.model.User;

import java.util.HashMap;

/**
 * GeoJsonFeatureInfoActivity displays the info of the input feature to the user.
 */
public class GeoJsonFeatureInfoActivity extends AppCompatActivity {

    String GymName, Description, StreetName, BuildingName, BlockNumber, FloorNumber, UnitNumber, PostalCode;
    TextView name, description, street, building, block, floor, unit, postal;
    Button back;
    ImageButton star, removeStar;
    String masterKey;
    HashMap<String, String> map;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras.containsKey("NAME")) {
            masterKey = intent.getStringExtra("NAME");
        }

        user = ((UserClient)(getApplicationContext())).getUser();
        if (user==null) {
            user = new User("TestEmail@mail.com", "3mTjQ1eGZEfLHrqNqka2cLk3Qui2", "TestEmail", "test_avatar", 1.75f, 65);
        }

        // FIXME: Pass in the data from GeoJson here instead of hardcoding
        GeoJsonFeatureHashMapInfo geoJsonInfo = ((UserClient) getApplicationContext()).getGeoJsonFeatureInfo();
        // TODO: Create a getMasterKey method in GeoJsonFeatureHashMapInfo, where the master key is stored upon double clicking the location
        map = geoJsonInfo.getInfo(masterKey);
        // TODO: then save the information about that particular location as a Hash Map (map) in this class


        // Initialise Views
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        street = findViewById(R.id.street);
        building = findViewById(R.id.building);
        block = findViewById(R.id.block);
        floor = findViewById(R.id.floor);
        unit = findViewById(R.id.unit);
        postal = findViewById(R.id.postal);

        inputInformationIntoLayout();

        // Buttons
        back = findViewById(R.id.backButton);
        star = findViewById(R.id.favorite_button_starred);
        removeStar = findViewById(R.id.favorite_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoJsonFeatureInfoActivity.super.onBackPressed();
            }
        });

        star.setVisibility(View.INVISIBLE);

        removeStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStar(GymName, PostalCode);
                star.setVisibility(View.VISIBLE);
                removeStar.setVisibility(View.INVISIBLE);
            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeStar(GymName);
                removeStar.setVisibility(View.VISIBLE);
                star.setVisibility(View.INVISIBLE);
            }
        });

        // Check if a location is starred or unstarred (to initialise star button view)
        boolean k = false;
        if (user.getStarredItems()==null) return;
        for (int i=0; i<user.getStarredItems().size(); i++){
            if (user.getStarredItems().get(i).getName().equals(GymName)){
                star.setVisibility(View.VISIBLE);
                removeStar.setVisibility(View.INVISIBLE);
                k = true;
            }
        }
        if(k==false){
            star.setVisibility(View.INVISIBLE);
            removeStar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
//        moveTaskToBack(true);
        finish();
    }

    public void inputInformationIntoLayout(){
        if(map.get("NAME")!=null){
            GymName = map.get("NAME");
            name.setText(GymName);
        }
        if(map.get("DESCRIPTION")!=null) {
            if(!map.get("DESCRIPTION").equals("")) {
                Description = map.get("DESCRIPTION");
                description.setText(Description);
            }
        }
        if(map.get("ADDRESSSTREETNAME")!=null){
            StreetName = map.get("ADDRESSSTREETNAME");
            street.setText(StreetName);
        }
        if(map.get("ADDRESSBUILDINGNAME")!=null){
            BuildingName = map.get("ADDRESSBUILDINGNAME");
            building.setText(BuildingName);
        }
        if(map.get("ADDRESSBLOCKHOUSENUMBER")!=null){
            BlockNumber = map.get("ADDRESSBLOCKHOUSENUMBER");
            block.setText(BlockNumber);
        }
        if(map.get("ADDRESSFLOORNUMBER")!=null){
            FloorNumber = map.get("ADDRESSFLOORNUMBER");
            floor.setText(FloorNumber);
        }
        if(map.get("ADDRESSUNITNUMBER")!=null){
            UnitNumber = map.get("ADDRESSUNITNUMBER");
            unit.setText(UnitNumber);
        }
        if(map.get("ADDRESSPOSTALCODE")!=null){
            PostalCode = map.get("ADDRESSPOSTALCODE");
            postal.setText(PostalCode);
        }

    }

    private void addStar(String gymName, String postalCode){
        user.addStarredItem(gymName, postalCode);
        ((UserClient) getApplicationContext()).setUser(user);
    }

    private void removeStar(String gymName){
        user.removeStarredItem(gymName);
        ((UserClient) getApplicationContext()).setUser(user);
    }


}


