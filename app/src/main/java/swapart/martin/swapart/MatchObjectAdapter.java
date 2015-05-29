package swapart.martin.swapart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import swapart.martin.swapartmockup.R;

/**
 * Created by Martin on 28-05-2015.
 */
public class MatchObjectAdapter extends BaseAdapter {

    Context context;
    ArrayList<MatchObject> amo;
    private static LayoutInflater inflater = null;

    public MatchObjectAdapter(Context context, ArrayList<MatchObject> amo) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.amo = amo;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return amo.size();
        //return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        //return data[position];
        return amo.indexOf(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.matches_row, null);
        }

        ImageView ownimg = (ImageView) vi.findViewById(R.id.matchesownimage);
        ownimg.setImageBitmap(amo.get(position).getOwnimage());

        ImageView matchedimg = (ImageView) vi.findViewById(R.id.matcheslikedimage);
        matchedimg.setImageBitmap(amo.get(position).getMatchedimage());

        TextView timestampformatch = (TextView) vi.findViewById(R.id.matches_timestamp);
        timestampformatch.setText(amo.get(position).getTimestamp());

        return vi;

    }

}


