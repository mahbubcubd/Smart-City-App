package eee.cu.ac.bd.smartcity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;


import static android.content.ContentValues.TAG;


public class writepost extends AppCompatActivity {

    EditText name,  issue,decription;
    public static Bitmap bitmap;
    public  Bitmap dstBmp;
    public String test;

    String getName, getLocation, getIssue, getLatitude, getLongitude, getDescription;
    TextView locationName,mLatitude,mLongitude;

    Button buttonSubmit,buttonImage;
    public String bits;



    dbBitmapUtility imdb= new dbBitmapUtility();

    Bitmap SelectedImage;
    int PLACE_PICKER_REQUEST = 1;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writepost);

        name = (EditText) findViewById(R.id.name);
        locationName=(TextView) findViewById(R.id.location);
        mLatitude=(TextView) findViewById(R.id.setlats);
        mLongitude=(TextView) findViewById(R.id.setlongs);
        issue = (EditText) findViewById(R.id.title);
        decription=(EditText) findViewById(R.id.problem);
        buttonSubmit = (Button) findViewById(R.id.submit);
        buttonImage= (Button) findViewById(R.id.image);


        final AlertDialog.Builder builder=null;






        buttonImage.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               //******Create intenet to open image application like gallery and google photos
                                               Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                               // Start the intent
                                               startActivityForResult(galleryIntent, 2);

                                           }
        });




        //hooking onclick listener of button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getName = name.getText().toString();
                getLocation=locationName.getText().toString();
                getLatitude=mLatitude.getText().toString();
                getLongitude=mLongitude.getText().toString();
                getIssue = issue.getText().toString();
                getDescription=decription.getText().toString();





                RequestQueue queue = Volley.newRequestQueue(writepost.this);
                String URL = "https://roomy.pythonanywhere.com/rumysecuritytest/default/smartcity";

                final ProgressDialog loading = ProgressDialog.show(writepost.this,"Uploading...","Please wait...",false,false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Do something with the response
                                Toast.makeText(writepost.this,"Success",Toast.LENGTH_LONG).show();
                                loading.dismiss();
                                Log.v(TAG,"Response: "+response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                loading.dismiss();

                                Toast.makeText(writepost.this,"An unexpected error occurred",Toast.LENGTH_LONG).show();

                            }
                        }){
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", getName);
                        params.put("location", getLocation);
                        params.put("issue", getIssue);
                        params.put("description",getDescription);
                        params.put("latitude", getLatitude);
                        params.put("longitude", getLongitude);
                        params.put("imgData","No no Image");
                        return params;
                    }
                };

               MySingleton.getInstance(writepost.this).addTorequestrue(stringRequest);








            }
        });}




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);



        try{

            switch (requestCode) {

                case (1):
                {
                        if (resultCode == RESULT_OK) {
                            Place place = PlacePicker.getPlace(data, this);
                            String locate = String.format("%s %s", place.getName(), place.getAddress());
                            LatLng ltlng = place.getLatLng();
                            double latit = ltlng.latitude;
                            String latitude = String.format("%s", latit);
                            double longit = ltlng.longitude;
                            String longitude = String.format("%s", longit);

                            TextView locationName = (TextView) findViewById(R.id.locationName);
                            TextView location = (TextView) findViewById(R.id.location);
                            locationName.setText("Location Name: ");
                            location.setText(locate);

                            TextView lats = (TextView) findViewById(R.id.lats);
                            TextView setLat = (TextView) findViewById(R.id.setlats);
                            lats.setText("Latitude:");
                            setLat.setText(latitude);

                            TextView longs = (TextView) findViewById(R.id.longs);
                            TextView setlongs = (TextView) findViewById(R.id.setlongs);
                            longs.setText("Logitude:");
                            setlongs.setText(longitude);

                            Button mapButton = (Button) findViewById(R.id.mapButton);
                            mapButton.setText("Edit Location");
                            Log.i(TAG, "Lat: " + latitude + " Lon: " + longitude);
                    }
                }
                break;

                case (2):
                {
                    // do this if request code is 11.
                    //when an image is picked

                    if (resultCode == RESULT_OK && null != data) {

                        Uri filePath = data.getData();
                        try {
                            //Getting the Bitmap from Gallery
                             bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                            //Setting the Bitmap to ImageView


                            ImageView img=(ImageView) findViewById(R.id.imageView);


                            test=dbBitmapUtility.BitMapToString(bitmap);
                            Log.i("imagedata: ",test);
                            img.setImageBitmap(bitmap);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
                break;
            }



        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Somethig went embarassing",Toast.LENGTH_LONG).show();
        }
    }


    public void mappicker(View view){
        try {
            startActivityForResult(builder.build(this), 1);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }




    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}





