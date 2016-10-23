package com.example.dasha.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DetailsActivity_1 extends AppCompatActivity {

    private String id;
    private Profile profile;
    private TextView tvMail;
    private TextView tvName;
    private TextView tvDate;
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        tvMail = (TextView) findViewById(R.id.tv_mail);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        profile = Profile.getInstance();

        if(getIntent().getStringExtra(Constants.PROFILE_ID)!=null){
            saveID(getIntent().getStringExtra(Constants.PROFILE_ID));
        }

        id = loadId();

        if(!profile.getName().equals(Constants.EMPTY_STR)||id!=Constants.EMPTY_STR){
            if(profile.getName().equals(Constants.EMPTY_STR)) {
                loadFromDB(id, profile);
            }else {
                tvMail.setText(profile.getEmail());
                tvName.setText(profile.getName());
                tvDate.setText(profile.getDate());
                tvLocation.setText(profile.getLocation());

            }
        }
    }

    public String loadId(){
        SharedPreferences sPref;
        sPref = getPreferences(MODE_PRIVATE);
        String id = sPref.getString(Constants.PROFILE_ID, Constants.EMPTY_STR);
        return id;
    }

    void saveID(String id) {
        SharedPreferences sPref;
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constants.PROFILE_ID, id);
        ed.commit();
    }

    public void editProfile(View view){
        Intent intent = new Intent(getApplicationContext(), EditProfileActivity_2.class);
        startActivity(intent);
        finish();
    }

    public void loadFromDB(String id, final Profile profile) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.TABLE_NAME);
        Toast.makeText(getApplicationContext(),Constants.DOWNLOAD,Toast.LENGTH_SHORT).show();
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject arg0, ParseException arg1) {
                if (arg1 == null) {
                    profile.update(arg0.getString(Constants.EMAIL), arg0.getString(Constants.NAME), arg0.getString(Constants.DATE), arg0.getString(Constants.LOCATION));
                    tvMail.setText(profile.getEmail());
                    tvName.setText(profile.getName());
                    tvDate.setText(profile.getDate());
                    tvLocation.setText(profile.getLocation());
                }
            }

        });
    }

}

