package com.segfault.android.breadcrumbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfigureTripActivity extends AppCompatActivity {

    private Button mSetRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_trip);

        mSetRoute = (Button) findViewById(R.id.set_route);

        mSetRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigureTripActivity.this, SetRouteActivity.class);
                startActivity(intent);
            }
        });
    }
}
