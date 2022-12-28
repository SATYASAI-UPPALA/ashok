package com.cdac.locationpurpose;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // below are the latitude and longitude
    // of 4 different locations.
    LatLng srkr = new LatLng(16.542688, 81.496886);
    LatLng tanuku = new LatLng(16.753369, 81.67791);
    LatLng undi = new LatLng(17, 81.463581);
    LatLng kaikaluru = new LatLng(16.556444, 81.211778);

    // two array list for our lat long and location Name;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;
    Context cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        cnt =this;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // initializing our array lists.
        latLngArrayList = new ArrayList<>();
        locationNameArraylist = new ArrayList<>();

        // on below line we are adding
        // data to our array list.
        latLngArrayList.add(srkr);
        locationNameArraylist.add("srkr");
        latLngArrayList.add(tanuku);
        locationNameArraylist.add("tanuku");
        latLngArrayList.add(undi);
        locationNameArraylist.add("undi");
        latLngArrayList.add(kaikaluru);
        locationNameArraylist.add("kaikaluru");
        getResponse();
    }
    private void getResponse() {
        String URL = "http://server.dsreddyconsultancy.co.in:8095/main.php?key=dsreddy&ChargeBoxID=DC001&cmd=getConnectorState&ConnectorID=2";
        //String URL ="https://www.google.com";
        RequestQueue queue = Volley.newRequestQueue(cnt);
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse (String response) {
                        //latLngArrayList.add(srkr);
                        Toast.makeText(MapsActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        locationNameArraylist.add("srkr");

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(srkr));
                        if (response.toString().contains("Available")) {
                            Marker srkr1 = mMap.addMarker(
                                    new MarkerOptions()
                                            .position(srkr)
                                            .title("srkr")
                                            .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN)));
                        } else {
                            Marker srkr1 = mMap.addMarker(
                                    new MarkerOptions()
                                            .position(srkr)
                                            .title("srkr")
                                            .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED)));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Check Error", "Error");
                        Toast.makeText(MapsActivity.this,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return new byte[]{};

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_MAX_RETRIES));
        queue.add(request);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // below line is use to move camera.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(srkr));


        //final LatLng srkrLatLng = new LatLng(16.542688, 81.496886);
        //Marker srkr = mMap.addMarker(
        // new MarkerOptions()
        //  .position(srkrLatLng)
        // .title("Srkr"));
        final LatLng tanukuLatLng = new LatLng(16.753369, 81.67791);
        Marker tanuku = mMap.addMarker(
                new MarkerOptions()
                        .position(tanukuLatLng)
                        .title("tanuku")
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN)));
        final LatLng undiLatLng = new LatLng(17.098794, 81.737195);
        Marker undi = mMap.addMarker(
                new MarkerOptions()
                        .position(undiLatLng)
                        .title("undi"));
        final LatLng kaikaluruLatLng = new LatLng(16.556444, 81.211778);
        Marker kaikaluru = mMap.addMarker(
                new MarkerOptions()
                        .position(kaikaluruLatLng)
                        .title("kaikaluru")
                        .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN)));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String makertitle = marker.getTitle();

                Intent i = new Intent(MapsActivity.this, DetailsActvity.class);

                //we are passing  title to new activity
                i.putExtra("title", makertitle);
                startActivity(i);


                return false;
            }



        });







    }
}