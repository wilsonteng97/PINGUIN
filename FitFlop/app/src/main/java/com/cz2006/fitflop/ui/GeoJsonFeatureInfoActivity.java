package com.cz2006.fitflop.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.cz2006.fitflop.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GeoJsonFeatureInfoActivity extends AppCompatActivity {

    String GymName, Description, StreetName, BuildingName, BlockNumber, FloorNumber, UnitNumber, PostalCode;
    TextView name, description, street, building, block, floor, unit, postal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // FIXME: Pass in the data from GeoJson here instead of hardcoding
        GymName = "OMG Yoga";
        Description = "Operating Hours: Monday & Friday: 10.00am - 6.00pm";
        StreetName = "Jurong Gateway Road";
        BuildingName = "-";
        BlockNumber = "134";
        FloorNumber = "4";
        UnitNumber = "309";
        PostalCode = "600134";

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


