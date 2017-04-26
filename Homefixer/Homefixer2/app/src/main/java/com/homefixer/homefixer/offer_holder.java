package com.homefixer.homefixer;

import android.widget.ImageView;
import android.widget.TextView;

public class offer_holder {

    ImageView image;

    public offer_holder(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
