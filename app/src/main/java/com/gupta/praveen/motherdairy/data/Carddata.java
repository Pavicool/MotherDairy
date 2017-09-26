package com.gupta.praveen.motherdairy.data;

/**
 * Created by prave on 25-09-2017.
 */

public class Carddata {
    String imgtxt;
    int images;
    int button;

    public Carddata(String imgtxt, int images, int button){
        this.imgtxt=imgtxt;
        this.images=images;
        this.button=button;
    }

    public String getImgtxt() {
        return imgtxt;
    }

    public void setImgtxt(String imgtxt) {
        this.imgtxt = imgtxt;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }


}
