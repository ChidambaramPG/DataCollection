package com.example.datacollection.ui.people;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datacollection.EditVendorActivity;
import com.example.datacollection.PersonWorks;
import com.example.datacollection.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleHolder> {

    private ArrayList<Person> persons = new ArrayList<>();
    Activity act;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PeopleAdapter(FragmentActivity activity) {
        this.act = activity;
    }

    @NonNull
    @Override
    public PeopleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_people, parent, false);
        return new PeopleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PeopleHolder holder, final int position) {
        final Person curPerson = persons.get(position);
        holder.personName.setText(curPerson.getName());
        holder.contactNum.setText(curPerson.getNum());
        holder.jobType.setText(curPerson.getJobType());
//        holder.profPic.setImageBitmap(getBitmapFromURL(curPerson.getUrl()));
        if(curPerson.getUrl() != null){
            Picasso.get().load(curPerson.getUrl()).into(holder.profPic);
        }


        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(act);

        final String name = curPerson.getName();
        final String uid = curPerson.getUid();

        holder.personCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked: " + position);
                Intent userWorksIntent = new Intent(act.getApplicationContext(), PersonWorks.class);
                userWorksIntent.putExtra("name", name);
                userWorksIntent.putExtra("uid", uid);
                userWorksIntent.putExtra("person", curPerson);
                act.startActivity(userWorksIntent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked: " + position);
                Intent userWorksIntent = new Intent(act.getApplicationContext(), EditVendorActivity.class);
                userWorksIntent.putExtra("uid", uid);
                userWorksIntent.putExtra("person", curPerson);
                act.startActivity(userWorksIntent);
            }
        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + curPerson.getNum()));

                int checkPermission = ContextCompat.checkSelfPermission(act, Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            act,
                            new String[]{Manifest.permission.CALL_PHONE},101
                            );
                } else {
                    act.startActivity(callIntent);
                }

            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Are you sure you want to delete this vendor?").setTitle("Delete Vendor")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                db.collection("People").document(curPerson.getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(act,"User has been deleted",
                                                Toast.LENGTH_SHORT).show();
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
        return persons.size();
    }

    public void setPersons(ArrayList<Person> persons){
        this.persons = persons;
        notifyDataSetChanged();
    }

    class PeopleHolder extends RecyclerView.ViewHolder{

        private TextView personName,contactNum,jobType;
        private ConstraintLayout personCard;
        FloatingActionButton edit,call,delete;
        CircleImageView profPic;

        public PeopleHolder(@NonNull View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.projName);
            contactNum = itemView.findViewById(R.id.projCost);
            jobType = itemView.findViewById(R.id.projType);
            personCard = itemView.findViewById(R.id.personCard);
            edit = itemView.findViewById(R.id.editVendorBtn);
            call = itemView.findViewById(R.id.callVendorBtn);
            delete = itemView.findViewById(R.id.deleteVendorBtn);
            profPic = itemView.findViewById(R.id.profPic);
        }
    }

}
