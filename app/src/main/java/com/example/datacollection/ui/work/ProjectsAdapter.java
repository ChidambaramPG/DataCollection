/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.datacollection.ui.work;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datacollection.EditProjectActivity;
import com.example.datacollection.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectsHolder>{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Work> project = new ArrayList<>();
    Activity act;
    public ProjectsAdapter(Activity act) {
        this.act = act;
    }


    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_project,parent,false);
        return new ProjectsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        final Work proj = project.get(position);
        holder.name.setText(proj.getProjName());
        holder.cost.setText(proj.getTotPrice());
        holder.type.setText(proj.getType());

        final String uid = proj.getUid();


        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(act);


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProjectIntent = new Intent(act, EditProjectActivity.class);
                editProjectIntent.putExtra("uid", uid);
                editProjectIntent.putExtra("project", proj);
                editProjectIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(editProjectIntent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Are you sure you want to delete this project?").setTitle("Delete Project")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                db.collection("Projects").document(uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(act,"Project Deleted",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(act,task.getException().toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return project.size();
    }

    public void setProject(ArrayList<Work> project) {
        this.project = project;
    }

    class ProjectsHolder extends RecyclerView.ViewHolder{

        TextView name,cost,type;
        FloatingActionButton edit,delete;

        public ProjectsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.projName);
            cost = itemView.findViewById(R.id.projCost);
            type = itemView.findViewById(R.id.projType);
            edit = itemView.findViewById(R.id.editProjectBtn);

            delete = itemView.findViewById(R.id.deleteProjectBtn);

        }
    }
}
