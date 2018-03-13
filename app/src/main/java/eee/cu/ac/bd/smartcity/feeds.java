package eee.cu.ac.bd.smartcity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import eee.cu.ac.bd.smartcity.MySingleton;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class feeds extends AppCompatActivity {

    ListView list;
    feedAdapter adapter;
    ArrayList<shortfeed> feedlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        list=(ListView) findViewById(R.id.feeds);
        feedlist = new ArrayList<shortfeed>();



        String url ="https://roomy.pythonanywhere.com/rumysecuritytest/default/feeds.json";
        final ProgressDialog loading = ProgressDialog.show(feeds.this,"Loading Feeds","Please wait",false,false);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url," ",
                        new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray feedjson=response.getJSONArray("feeds");
                            for(int i=0;i<feedjson.length();i++){

                                shortfeed f=new shortfeed();
                                JSONObject feed= feedjson.getJSONObject(i);

                                f.setName(feed.getString("name"));
                                f.setLocation(feed.getString("location"));
                                f.setTitle(feed.getString("issue"));
                                f.setId(feed.getString("id"));


                                feedlist.add(f);


                                feedAdapter adapter=new feedAdapter(getApplicationContext(),R.layout.feedlist,feedlist);
                                list.setAdapter(adapter);
                                loading.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loading.dismiss();
                        Toast.makeText(feeds.this,"An unexpected error occurred",Toast.LENGTH_LONG).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(feeds.this).addTorequestrue(jsObjRequest);



list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(feeds.this, details.class);
        String message = ((TextView)view.findViewById(R.id.fMore)).getText().toString();
        intent.putExtra("id", message);
        startActivity(intent);
    }
});

    }






}
