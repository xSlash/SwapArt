package swapart.martin.swapart;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import swapart.martin.swapartmockup.R;

/**
 * Created by Martin on 31-03-2015.
 */
public class ArtObjectAdapter extends BaseAdapter {

    Context context;
    ArrayList<ArtObject> aao;
    String[] data;
    Bitmap img;
    private static LayoutInflater inflater = null;

    public ArtObjectAdapter(Context context, ArrayList<ArtObject> aao) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.aao = aao;
        //this.data = data;
        //this.img = img;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return aao.size();
        //return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        //return data[position];
        return aao.indexOf(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.gallery_row, null);
        TextView text = (TextView) vi.findViewById(R.id.rowTextView);
        text.setText(aao.get(position).getTitle());
        ImageView tmpimg = (ImageView) vi.findViewById(R.id.rowImage);
        tmpimg.setImageBitmap(aao.get(position).getImage());
        return vi;
    }
}
