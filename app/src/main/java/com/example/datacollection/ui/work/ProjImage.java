/*
 * Date: 2019.
 * Author: Chidambaram P G
 * Github: https://github.com/ChidambaramPG
 * Copyright : This application is written by the author for Techie's Solution.
 * Usage: Data collection app
 */

package com.example.datacollection.ui.work;

import android.graphics.Bitmap;

public class ProjImage {
    String imageUrl,projId;
    Bitmap image;

    public ProjImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ProjImage(String imageUrl, String projId) {
        this.imageUrl = imageUrl;
        this.projId = projId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }
}
