package swapart.martin.swapart;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;

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

        final int numberofposition = position;


        TextView text = (TextView) vi.findViewById(R.id.titleTextView);
        text.setText(aao.get(position).getTitle());
        TextView dataForArt = (TextView) vi.findViewById(R.id.artData);
        dataForArt.setText("By: " + aao.get(position).getArtist() + ". Year: " + aao.get(position).getYear() + ". Dimensions: " + aao.get(position).getDimensions() + ". Type: " + aao.get(position).getType());
        /*TextView artistText = (TextView) vi.findViewById(R.id.artist_editText);
        TextView yearText = (TextView) vi.findViewById(R.id.year_editText);
        TextView dimText = (TextView) vi.findViewById(R.id.dimensions_editText);*/
        ImageView tmpimg = (ImageView) vi.findViewById(R.id.rowImage);
        tmpimg.setImageBitmap(aao.get(position).getImage());

        Button editBT = (Button) vi.findViewById(R.id.rowChangeButton);
        editBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.gallery_popup_edit_art);

                final EditText edt = (EditText) dialog.findViewById(R.id.title_editText);
                edt.setText(aao.get(numberofposition).getTitle());

                final EditText edt2 = (EditText) dialog.findViewById(R.id.artist_editText);
                edt2.setText(aao.get(numberofposition).getArtist());

                final EditText edt3 = (EditText) dialog.findViewById(R.id.year_editText);
                edt3.setText(aao.get(numberofposition).getYear());

                final EditText edt4 = (EditText) dialog.findViewById(R.id.dimensions_editText);
                edt4.setText(aao.get(numberofposition).getDimensions());

                final EditText edt5 = (EditText) dialog.findViewById(R.id.type_editText);
                edt5.setText(aao.get(numberofposition).getType());

                Button saveButton = (Button) dialog.findViewById(R.id.save_button);
                saveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        aao.get(numberofposition).setTitle(edt.getText().toString());
                        aao.get(numberofposition).setArtist(edt2.getText().toString());
                        aao.get(numberofposition).setYear(edt3.getText().toString());
                        aao.get(numberofposition).setDimensions(edt4.getText().toString());
                        aao.get(numberofposition).setType(edt5.getText().toString());
                        //SharedPreferences.Editor editor = getSharedPreferences("User_Object", Context.MODE_PRIVATE).edit();

                        //Context prefs = context;
                        SharedPreferences.Editor editor = context.getSharedPreferences("User_Object", Context.MODE_PRIVATE).edit();
                        editor.putString("title_"+(numberofposition+1), edt.getText().toString());
                        editor.putString("artist_"+(numberofposition+1), edt2.getText().toString());
                        editor.putString("year_"+(numberofposition+1), edt3.getText().toString());
                        editor.putString("dimension_"+(numberofposition+1), edt4.getText().toString());
                        editor.putString("type_"+(numberofposition+1), edt5.getText().toString());


                        editor.commit();

                        /*ListView listview = (ListView) findViewById(R.id.artObjectlistView);
                        //listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, new String[]{ title }, imageBitmap));
                        listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, ArtObjectArrayList));*/

                        dialog.dismiss();
                        notifyDataSetChanged();
                    }

                });



                dialog.show();
                // Your code that you want to execute on this button click
            }

        });

        return vi;
    }
}
