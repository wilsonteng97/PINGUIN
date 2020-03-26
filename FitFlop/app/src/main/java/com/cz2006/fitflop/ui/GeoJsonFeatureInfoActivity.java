package com.cz2006.fitflop.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cz2006.fitflop.R;
import com.cz2006.fitflop.UserClient;
import com.cz2006.fitflop.model.GeoJsonFeatureHashMapInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GeoJsonFeatureInfoActivity extends AppCompatActivity {

    String GymName, Description, StreetName, BuildingName, BlockNumber, FloorNumber, UnitNumber, PostalCode;
    TextView name, description, street, building, block, floor, unit, postal;
    Button back, star;
    String masterKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // FIXME: Pass in the data from GeoJson here instead of hardcoding
        GeoJsonFeatureHashMapInfo geojsoninfo = ((UserClient) getApplicationContext()).getGeoJsonFeatureInfo();
        // TODO: Create a getMasterKey method in GeoJsonFeatureHashMapInfo, where the master key is stored upon double clicking the location
        // geojsoninfo.getMasterKey();
        // TODO: then save the information about that particular location as a Hash Map (map) in this class

        /*GymName = map.get("NAME").toString();
        Description = map.get("DESCRIPTION").toString();
        StreetName = map.get("ADDRESSSTREETNAME").toString();
        BuildingName = map.get("ADDRESSBUILDINGNAME").toString();
        BlockNumber = map.get("ADDRESSBLOCKHOUSENUMBER").toString();
        FloorNumber = map.get("ADDRESSFLOORNUMBER").toString();
        UnitNumber = map.get("ADDRESSUNITNUMBER").toString();
        PostalCode = map.get("ADDRESSPOSTALCODE").toString();*/

        // Initialise Views
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        street = findViewById(R.id.street);
        building = findViewById(R.id.building);
        block = findViewById(R.id.block);
        floor = findViewById(R.id.floor);
        unit = findViewById(R.id.unit);
        postal = findViewById(R.id.postal);

        // Buttons
        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        star = findViewById(R.id.starButton);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: if value in hash map is true, change Starred value to false (unstarring)
                //TODO: else if value in hash map is false, change Starred value to true (star)
            }
        });

        inputInformationIntoLayout();

    }

    public void inputInformationIntoLayout(){
        name.setText(GymName);
        description.setText(Description);
        street.setText(StreetName);
        building.setText(BuildingName);
        block.setText(BlockNumber);
        floor.setText(FloorNumber);
        unit.setText(UnitNumber);
        postal.setText(PostalCode);
    }


}


