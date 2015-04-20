package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import swapart.martin.swapartmockup.R;

/**
 * Created by Martin on 15-04-2015.
 */
public class CustomAdapter extends ArrayAdapter<ImageView> {


    Context context;

    public CustomAdapter(Context context, int resourceId, //resourceId=your layout
                         List<ImageView> items) {
        super(context, resourceId, items);
        this.context = context;
    }
    /*
    //private view holder class
    private class ViewHolder {
        ImageView imageView;
        //TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ImageView rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.swipeart_element, null);
            holder = new ViewHolder();
            //holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.art_element_image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.imageView.setImageResource(R.drawable.art1);

        return convertView;
    }*/

}
