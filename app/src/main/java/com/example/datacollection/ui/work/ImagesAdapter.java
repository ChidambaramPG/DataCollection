/*
 * Date: 2019.
 * Author: Chidambaram P G
 * Github: https://github.com/ChidambaramPG
 * Copyright : This application is written by the author for Techie's Solution.
 * Usage: Data collection app
 */

package com.example.datacollection.ui.work;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datacollection.R;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesHolder> {

    String imgesUrl,projectId;
    Activity act;
    ArrayList<ProjImage> imgs = new ArrayList<>();

    public ImagesAdapter(Activity act) {
        this.act = act;
    }

    @NonNull
    @Override
    public ImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_recycler,parent,false);
        return new ImagesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesHolder holder, final int position) {
        ProjImage prjImg = imgs.get(position);
//        holder.img.setImageDrawable(act.getResources().getDrawable(R.drawable.ic_user));
        holder.img.setImageBitmap(prjImg.getImage());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgs.remove(position);
                ImagesAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public class ImagesHolder extends RecyclerView.ViewHolder {
        ImageView img,del;
        public ImagesHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.projImage);
            del = itemView.findViewById(R.id.delProj);
        }
    }

    public void setImgs(ArrayList<ProjImage> imgs) {
        this.imgs = imgs;
    }

    public ArrayList<ProjImage> getImgs() {
        return imgs;
    }
}
