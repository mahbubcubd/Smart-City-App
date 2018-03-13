package eee.cu.ac.bd.smartcity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.client.cache.Resource;

/**
 * Created by mahbub on 10/2/17.
 */

public class feedAdapter extends ArrayAdapter<shortfeed> {

    ArrayList<shortfeed> mfeeds;
    int Resource;
    Context context;
    LayoutInflater inflater;

    public feedAdapter(Context context, int resource, ArrayList<shortfeed> objects) {
        super(context, resource, objects);
        mfeeds=objects;
        Resource=resource;
        this.context=context;
    inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     ViewHolder viewHolder;
        if(convertView==null){

            convertView=inflater.inflate(Resource,null);
            viewHolder=new ViewHolder();
            viewHolder.Name=(TextView)convertView.findViewById(R.id.fAuthor);
            viewHolder.Title=(TextView)convertView.findViewById(R.id.fTitle);
            viewHolder.Location=(TextView)convertView.findViewById(R.id.fLocation);
            viewHolder.seeMore=(TextView)convertView.findViewById(R.id.fMore);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();

        }

//        shortfeed im= new shortfeed();
//        Bitmap bit=im.bitImage;
        viewHolder.Title.setText(mfeeds.get(position).getTitle());
        viewHolder.Location.setText(mfeeds.get(position).getLocation());
        viewHolder.Name.setText(mfeeds.get(position).getName());
        viewHolder.seeMore.setText(mfeeds.get(position).getId());
        return convertView;
    }




    static class ViewHolder{
        public TextView Name;
        public TextView Location;
        public  TextView seeMore;
        public TextView Title;
    }
}
