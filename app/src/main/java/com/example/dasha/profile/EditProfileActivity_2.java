package com.example.dasha.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class EditProfileActivity_2 extends AppCompatActivity {
    private String id;
    private Profile profile;
    SharedPreferences sPref;

    private EditText etMail;
    private EditText etName;
    private EditText etDate;
    private EditText etLocation;

    private String email;
    private String name;
    private String location;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        etMail = (EditText) findViewById(R.id.et_mail);
        etName = (EditText) findViewById(R.id.et_name);
        etDate = (EditText) findViewById(R.id.et_date);
        etLocation = (EditText) findViewById(R.id.et_location);

        id = loadId();
        profile = Profile.getInstance();

        etMail.setText(profile.getEmail());
        etName.setText(profile.getName());
        etDate.setText(profile.getDate());
        etLocation.setText(profile.getLocation());

    }


    public String loadId(){
        sPref = getPreferences(MODE_PRIVATE);
        String id = sPref.getString(Constants.PROFILE_ID, Constants.EMPTY_STR);
        return id;
    }

    public void saveProfile(View view){
        if(etMail.getText().toString().trim().length()==Constants.ZERO){
            etMail.setError(Constants.EMAIL_ERROR);
            return;
        }else if(etName.getText().toString().trim().length()==Constants.ZERO){
            etName.setError(Constants.NAME_ERROR);
            return;
        }else if(etDate.getText().toString().trim().length()==Constants.ZERO){
            etDate.setError(Constants.DATE_ERROR);
            return;
        }else if(etLocation.toString().trim().length()==Constants.ZERO) {
            etLocation.setError(Constants.LOCATION_ERROR);
            return;
        }
        else{
            email = etMail.getText().toString();
            name = etName.getText().toString();
            date = etDate.getText().toString();
            location = etLocation.getText().toString();
            profile.update(email, name, date, location);
            if (id != Constants.EMPTY_STR) {
                updateInDB(id, email, name, date, location);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity_1.class);
                startActivity(intent);
                finish();
            }else {
                saveInDB(email, name, date, location);
             }

        }
    }

    void saveID(String id) {
        SharedPreferences sPref;
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constants.PROFILE_ID, id);
        ed.commit();
        Intent intent = new Intent(getApplicationContext(), DetailsActivity_1.class);
        intent.putExtra(Constants.PROFILE_ID, id);
        startActivity(intent);
        finish();
    }


    public void saveInDB(String email, String name, String date, String location) {
        final ParseObject po = new ParseObject(Constants.TABLE_NAME);
        po.put(Constants.EMAIL, email);
        po.put(Constants.NAME, name);
        po.put(Constants.DATE, date);
        po.put(Constants.LOCATION, location);
        po.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                saveID(po.getObjectId());
            }
        });
    }

    public void updateInDB(String id, String email, String name, String date, String location) {
        ParseObject po = ParseObject.createWithoutData(Constants.TABLE_NAME, id);
        po.put(Constants.EMAIL, email);
        po.put(Constants.NAME, name);
        po.put(Constants.DATE, date);
        po.put(Constants.LOCATION, location);
        po.saveInBackground();
    }


}
