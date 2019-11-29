/*
 * Date: 2019.
 * Author: Chidambaram P G
 * Github: https://github.com/ChidambaramPG
 * Copyright : This application is written by the author for Techie's Solution.
 * Usage: Data collection app
 */

package com.example.datacollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.datacollection.ui.work.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProjectActivity extends AppCompatActivity {

    TextInputEditText totPrice,totArea,totTime,totWorkers,costPerHr,costPerSqFt,projName,desc,rooms,floors,bathrooms;
    Spinner type;
    Switch balcony;
    Button savePoject,deleteProj;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        totPrice = findViewById(R.id.totCost);
        totArea = findViewById(R.id.totSqreArea);
        totTime = findViewById(R.id.workDuration);
        totWorkers = findViewById(R.id.workerNumber);
        costPerHr = findViewById(R.id.costPerHour);
        costPerSqFt = findViewById(R.id.costPerSqFeet);
        savePoject = findViewById(R.id.saveProjectBtn);
        deleteProj = findViewById(R.id.deleteProjectBtn);

        projName = findViewById(R.id.projName);
        desc = findViewById(R.id.projDescr);
        rooms = findViewById(R.id.numRooms);
        floors = findViewById(R.id.numFloors);
        bathrooms = findViewById(R.id.numBathrooms);
        type= findViewById(R.id.projType);
        balcony = findViewById(R.id.balconyPresent);

        Intent i = getIntent();
        Work work = (Work) i.getSerializableExtra("project");
        System.out.println("------------------------------------------");
        System.out.println(work.getProjName());

        projName.setText(work.getProjName());
        desc.setText(work.getDesc());
        rooms.setText(work.getRooms());
        floors.setText(work.getFloors());
        bathrooms.setText(work.getBathrooms());
        totPrice.setText(work.getTotPrice());
        totArea.setText(work.getTotArea());
        totTime.setText(work.getTotTime());
        totWorkers.setText(work.getTotWorkers());
        costPerHr.setText(work.getCostPerHr());
        costPerSqFt.setText(work.getCostPerSqFt());

        final String uid = work.getUid();

        final Boolean[] balconyStr = {false};

        balcony.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    balconyStr[0] = true;
                }else{
                    balconyStr[0] = false;
                }
            }
        });

        savePoject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totPriceStr = totPrice.getText().toString();
                String totAreaStr = totArea.getText().toString();
                String totTimeStr = totTime.getText().toString();
                String totWorkersStr = totWorkers.getText().toString();
                String costPerHrStr = costPerHr.getText().toString();
                String costPerSqFtStr = costPerSqFt.getText().toString();
                String projNameStr = projName.getText().toString();
                String descStr = desc.getText().toString();
                String roomsStr = rooms.getText().toString();
                String floorsStr = floors.getText().toString();
                String bathroomsStr = bathrooms.getText().toString();
                String typeStr = type.getSelectedItem().toString();

                Work tempWork = new Work(totPriceStr,totAreaStr,totTimeStr,totWorkersStr,costPerHrStr,costPerSqFtStr,projNameStr,descStr,roomsStr,floorsStr,bathroomsStr,typeStr, balconyStr[0].toString());

                tempWork.setUid(uid);

                db.collection("Projects").document(uid).set(tempWork).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Project Updated Successfully",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


    }
}
