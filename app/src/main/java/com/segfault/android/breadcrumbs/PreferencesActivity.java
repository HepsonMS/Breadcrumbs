package com.segfault.android.breadcrumbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class PreferencesActivity extends AppCompatActivity {

    private CheckBox mAuthoritiesCheckBox;
    private Button mContactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
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
                Intent intent = new Intent(PreferencesActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    public PreferencesConfig config;

}


