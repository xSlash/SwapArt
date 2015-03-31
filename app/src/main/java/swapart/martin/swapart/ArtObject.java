package swapart.martin.swapart;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Martin on 31-03-2015.
 */
public class ArtObject {

    private String title;
    private String artist;
    private String year;
    private String dimensions;
    private String type;
    private Bitmap img;



    public ArtObject(String Title, String Artist, String Year, String Dimensions, String Type, Bitmap Img) {

        this.title = Title;
        this.artist = Artist;
        this.year = Year;
        this.dimensions = Dimensions;
        this.type = Type;
        this.img = Img;
    }
}
