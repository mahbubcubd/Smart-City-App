package eee.cu.ac.bd.smartcity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        String url ="https://roomy.pythonanywhere.com/rumysecuritytest/default/feeds.json";
        final ProgressDialog loading = ProgressDialog.show(MapsActivity.this,"Loading Feeds","Please wait",false,false);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url," ",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray feedjson=response.getJSONArray("feeds");
                                    for(int i=0;i<feedjson.length();i++){


                                        JSONObject feed= feedjson.getJSONObject(i);

                                        double latitude=feed.getDouble("latitude");
                                        double longitude=feed.getDouble("longitude");
                                        String spot=feed.getString("location");
                                        // Add a marker in Sydney and move the camera
                                        LatLng pointers = new LatLng(latitude, longitude);
                                        mMap.addMarker(new MarkerOptions().position(pointers).title(spot));

                                    }


                                    LatLng set=new LatLng(23.9741894,90.2226902);

                                    mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(set,9.5f));

                                    loading.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loading.dismiss();
                        Toast.makeText(MapsActivity.this,"Json Loading error",Toast.LENGTH_LONG).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(MapsActivity.this).addTorequestrue(jsObjRequest);



    }
}
