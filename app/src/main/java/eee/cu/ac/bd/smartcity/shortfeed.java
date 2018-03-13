package eee.cu.ac.bd.smartcity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by mahbub on 10/2/17.
 */

public class shortfeed {
    private String Title;
    private String Name;
    private String Location;
    private String id;

    public shortfeed(){


    }



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}


