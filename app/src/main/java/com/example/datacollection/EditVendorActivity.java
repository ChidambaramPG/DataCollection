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
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.datacollection.ui.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditVendorActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextInputEditText name,address,phone,altPhone,qualification,experience;
    Spinner jobType;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vendor);

        getSupportActionBar().setTitle("Edit Vendor Details");

        Intent i = getIntent();
        Person person = (Person) i.getSerializableExtra("person");
        final String uid = i.getStringExtra("uid");
        System.out.println(person.getName());

        name = findViewById(R.id.nameField);
        address = findViewById(R.id.addressField);
        phone = findViewById(R.id.phoneField);
        altPhone = findViewById(R.id.alternatePhoneField);
        qualification = findViewById(R.id.qualifcationField);
        experience = findViewById(R.id.experienceField);
        jobType = findViewById(R.id.jobTypeField);
        saveBtn = findViewById(R.id.saveVendorBtn);

        name.setText(person.getName());
        address.setText(person.getAddress());
        phone.setText(person.getNum());
        altPhone.setText(person.getAltNum());
        qualification.setText(person.getQualification());
        experience.setText(person.getExperience());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = name.getText().toString();
                String addressStr = address.getText().toString();
                String phoneStr = phone.getText().toString();
                String altPhoneStr = altPhone.getText().toString();
                String qualificationStr = qualification.getText().toString();
                String experienceStr = experience.getText().toString();
                String jobsTypeStr = jobType.getSelectedItem().toString();


                if(!nameStr .isEmpty() &&  !addressStr .isEmpty() &&  !phoneStr .isEmpty() &&  !altPhoneStr .isEmpty() &&  !qualificationStr .isEmpty() &&  !experienceStr .isEmpty() &&  !jobsTypeStr.isEmpty() ){

                    Person person = new Person(nameStr,addressStr,phoneStr,altPhoneStr,qualificationStr,experienceStr,jobsTypeStr);

                    db.collection("People").document(uid).set(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EditVendorActivity.this,"Successfully updated",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(EditVendorActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{

                    if(nameStr.isEmpty()){
                        name.setError("Required");
                        return;
                    }
                    if(addressStr.isEmpty()){
                        address.setError("Required");
                        return;
                    }
                    if(phoneStr.isEmpty()){
                        phone.setError("Required");
                        return;
                    }
                    if(altPhoneStr.isEmpty()){
                        altPhone.setError("Required");
                        return;
                    }
                    if(qualificationStr.isEmpty()){
                        qualification.setError("Required");
                        return;
                    }
                    if(experienceStr.isEmpty()){
                        experience.setError("Required");
                        return;
                    }

                }

            }
        });
    }
}
