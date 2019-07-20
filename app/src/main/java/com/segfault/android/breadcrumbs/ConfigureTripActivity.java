package com.segfault.android.breadcrumbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigureTripActivity extends AppCompatActivity {

    private Button mSetRoute, mStartTripButton;
    private CheckBox mAuthoritiesCheckBox;
    private Button mContactButton;
    EditText mEdit;
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

        mStartTripButton = (Button) findViewById(R.id.start_trip);
        this.config = new PreferencesConfig();
        this.mAuthoritiesCheckBox = (CheckBox) findViewById(R.id.authoritiesCheck);
        this.mContactButton = (Button) findViewById(R.id.contactBtn);
        mAuthoritiesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                config.setAuthoritiesBool(checked);
            }
        });
        mContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigureTripActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
        mEdit   = (EditText) findViewById(R.id.timer);
        mStartTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference current_db = FirebaseDatabase.getInstance().getReference();
                current_db = current_db.child("travelerRoutes").child("test_user").child("emergency_timer");
                current_db.setValue(mEdit.getText().toString()+":00:00");
            }
        });
    }
    public PreferencesConfig config;
}
