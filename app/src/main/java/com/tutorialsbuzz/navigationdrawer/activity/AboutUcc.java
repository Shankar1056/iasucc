package com.tutorialsbuzz.navigationdrawer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tutorialsbuzz.navigationdrawer.activity.common.ClsGeneral;
import com.ucc.application.R;


/**
 * Created by igcs-27 on 3/3/16.
 */
    public class AboutUcc extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout  mail;
    ImageView refresh;
    private TextView actionbar_title;
    Toolbar toolbar;
    LinearLayout about_ucc, contact_us;
    GoogleMap map;
    TextView address;
    MarkerOptions marker;
    private  TextView call,call2,call3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            String headerlanguage = ClsGeneral.getPreferences(AboutUcc.this, "choosedlanguage");
            ClsGeneral.updatelanguage(headerlanguage);
            setContentView(R.layout.about_ucc);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        about_ucc = (LinearLayout) findViewById(R.id.about_ucc);
        contact_us = (LinearLayout) findViewById(R.id.contact_us);
        call = (TextView) findViewById(R.id.call);
        call2 = (TextView) findViewById(R.id.call2);
        call3 = (TextView) findViewById(R.id.call3);
        mail = (LinearLayout) findViewById(R.id.mail);
        address=(TextView)findViewById(R.id.address);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        actionbar_title.setText(R.string.about_ucc);
        refresh = (ImageView) findViewById(R.id.refresh);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getExtras().getString("token").equalsIgnoreCase("about")) {
            actionbar_title.setText(R.string.about_ucc);
            contact_us.setVisibility(View.GONE);


        }
        refresh.setVisibility(View.GONE);
        if (getIntent().getExtras().getString("token").equalsIgnoreCase("contactus")) {
            about_ucc.setVisibility(View.GONE);
            actionbar_title.setText(R.string.contact_ucc);
            try {
                initilizeMap();

            } catch (Exception e) {
                e.printStackTrace();
            }


            call.setOnClickListener(this);
            call2.setOnClickListener(this);
            call3.setOnClickListener(this);
            mail.setOnClickListener(this);
            address.setOnClickListener(this);
        }
    }
    private void initilizeMap() {
        if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();



             marker = new MarkerOptions().position(new LatLng(12.962817, 77.537894)).title("Universal Coaching Centre ");
            map.addMarker(marker);

            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(12.962817, 77.537894)).zoom(12).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
          //  map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (map == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
      String headerlanguage = ClsGeneral.getPreferences(AboutUcc.this, "choosedlanguage");
        ClsGeneral.updatelanguage(headerlanguage);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call:
try {

    Intent intent = new Intent(Intent.ACTION_DIAL);
   // intent.setPackage("com.android.server.telecom");
    intent.setData(Uri.parse("tel:9686664985"));
    startActivity(intent);
}
catch (Exception e){
    Toast.makeText(AboutUcc.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
   // Toast.makeText(AboutUcc.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
}

                break;
            case R.id.call2:
                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                  //  intent.setPackage("com.android.server.telecom");
                    intent.setData(Uri.parse("tel:08023397437"));
                    startActivity(intent);
                }
                catch (Exception e){
                     Toast.makeText(AboutUcc.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.call3:
                try {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                   // intent.setPackage("com.android.server.telecom");
                    intent.setData(Uri.parse("tel:08023396409"));
                    startActivity(intent);
                }
                catch (Exception e){
                     Toast.makeText(AboutUcc.this, "Phone facility not available on your device", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.mail:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","universalhelpdesk@yahoo.in", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.address:
                Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(12.962817, 77.537894)).title("Universal Coaching Centre "));
                marker.showInfoWindow();
                break;
            case R.id.refresh:
                if (getIntent().getExtras().getString("token").equalsIgnoreCase("about")) {

                    contact_us.setVisibility(View.GONE);


                }

                if (getIntent().getExtras().getString("token").equalsIgnoreCase("contactus")) {
                    about_ucc.setVisibility(View.GONE);

                    try {
                     //   initilizeMap();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;


        }

    }
}


