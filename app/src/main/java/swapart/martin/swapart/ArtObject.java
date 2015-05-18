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

    public String getTitle() {
        return title;
    }

    public String getArtist() { return artist; }

    public String getYear() { return year; }

    public String getDimensions() { return dimensions; }

    public String getType() { return type; }

    public Bitmap getImage() {
        return img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
