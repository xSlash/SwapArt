package swapart.martin.swapart;

import android.graphics.Bitmap;

/**
 * Created by MTKX on 28-05-2015.
 */
public class MatchObject {

    private Bitmap ownimage;
    private Bitmap matchedimage;
    private String timestamp;

    public MatchObject (Bitmap Ownimage, Bitmap Matchedimage, String Timestamp) {

        this.ownimage = Ownimage;
        this.matchedimage = Matchedimage;
        this.timestamp = Timestamp;
    }

    public Bitmap getOwnimage() {
        return ownimage;
    }

    public void setOwnimage(Bitmap ownimage) {
        this.ownimage = ownimage;
    }

    public Bitmap getMatchedimage() {
        return matchedimage;
    }

    public void setMatchedimage(Bitmap matchedimage) {
        this.matchedimage = matchedimage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
