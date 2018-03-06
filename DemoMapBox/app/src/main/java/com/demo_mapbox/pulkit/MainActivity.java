package com.demo_mapbox.pulkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demo_mapbox.pulkit.activities.BuildingsActivity;
import com.demo_mapbox.pulkit.activities.DrawNavLineActivity;
import com.demo_mapbox.pulkit.activities.ShowMapCustomInfActivity;
import com.demo_mapbox.pulkit.activities.TrafficActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_show_custom_info, btn_draw_line, btn_traffic, btn_building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        init();

    }

    private void findIds() {

        btn_show_custom_info = findViewById(R.id.btn_show_custom_info);
        btn_draw_line = findViewById(R.id.btn_draw_line);
        btn_traffic = findViewById(R.id.btn_traffic);
        btn_building = findViewById(R.id.btn_building);
    }

    private void init() {

        btn_show_custom_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ShowMapCustomInfActivity.class);
                startActivity(intent);

            }
        });

        btn_draw_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DrawNavLineActivity.class);
                startActivity(intent);
            }
        });

        btn_traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TrafficActivity.class);
                startActivity(intent);
            }
        });

        btn_building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BuildingsActivity.class);
                startActivity(intent);
            }
        });


    }


}
