package com.segfault.android.breadcrumbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    EditText mPhoneText;
    Button mEnterButton;
    Button mFirstButton;
    Button mSecondButton;
    Button mThirdButton;
    List<String> numList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        numList = new ArrayList<String>();

        mFirstButton = (Button) findViewById(R.id.firstButton);
        mFirstButton.setText("None");
        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numList.remove(mFirstButton.getText().toString());
                mFirstButton.setText("None");
                mFirstButton.setVisibility(View.INVISIBLE);
            }
        });
        mSecondButton = (Button) findViewById(R.id.secondButton);
        mSecondButton.setText("None");
        mSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numList.remove(mSecondButton.getText().toString());
                mSecondButton.setText("None");
                mSecondButton.setVisibility(View.INVISIBLE);
            }
        });
        mThirdButton = (Button) findViewById(R.id.thirdButton);
        mThirdButton.setText("None");
        mThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numList.remove(mThirdButton.getText().toString());
                mThirdButton.setText("None");
                mThirdButton.setVisibility(View.INVISIBLE);
            }
        });

        mPhoneText = (EditText) findViewById(R.id.phoneNum);
        mEnterButton = (Button) findViewById(R.id.enterButton);
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mPhoneText.getText().toString();
                if ((text.matches("[0-9]+")) & (text.length() <= 15) & (text.length() >= 10)) {
                    mPhoneText.setText("");
                    if (numList.size() < 3) {
                        if (!numList.contains(text)) {
                            numList.add(text);
                            if (mFirstButton.getText() == "None") {
                                mFirstButton.setText(text);
                                mFirstButton.setVisibility(View.VISIBLE);
                            } else if (mSecondButton.getText() == "None") {
                                mSecondButton.setText(text);
                                mSecondButton.setVisibility(View.VISIBLE);
                            } else {//numList.size() == 2
                                mThirdButton.setText(text);
                                mThirdButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            };
        });
    }
}