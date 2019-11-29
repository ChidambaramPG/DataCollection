package com.example.datacollection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.example.datacollection.ui.work.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AddWorksActivity extends AppCompatActivity {

    TextInputEditText totPrice,totArea,totTime,totWorkers,costPerHr,costPerSqFt,projName,desc,rooms,floors,bathrooms;
    Spinner type;
    Switch balcony;
    Button savePoject;
    ImageView upldImg;

    FirebaseFirestore dbRef = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    ArrayList<Image> images = new ArrayList<>();
    LinearLayout imgsLyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_works);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent i = getIntent();
        final String uid = i.getStringExtra("uid");
        toolbar.setTitle("Add New Project");
        setSupportActionBar(toolbar);

        totPrice = findViewById(R.id.totCost);
        totArea = findViewById(R.id.totSqreArea);
        totTime = findViewById(R.id.workDuration);
        totWorkers = findViewById(R.id.workerNumber);
        costPerHr = findViewById(R.id.costPerHour);
        costPerSqFt = findViewById(R.id.costPerSqFeet);
        savePoject = findViewById(R.id.saveProjectBtn);

        projName = findViewById(R.id.projName);
        desc = findViewById(R.id.projDescr);
        rooms = findViewById(R.id.numRooms);
        floors = findViewById(R.id.numFloors);
        bathrooms = findViewById(R.id.numBathrooms);
        type= findViewById(R.id.projType);
        balcony = findViewById(R.id.balconyPresent);

        upldImg = findViewById(R.id.uploadImageBtn);
        imgsLyt = findViewById(R.id.imagesLayout);

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

                final ProgressDialog pd = new ProgressDialog(AddWorksActivity.this);
                pd.setMessage("loading");


                if(totPriceStr != "" && totAreaStr != "" && totTimeStr != "" && totWorkersStr != "" && costPerHrStr != "" && costPerSqFtStr != "" && projNameStr != "" && descStr != "" && roomsStr != "" && floorsStr != "" && bathroomsStr != "" && typeStr != ""){
                    pd.show();
                    Work tempWork = new Work(totPriceStr,totAreaStr,totTimeStr,totWorkersStr,costPerHrStr,costPerSqFtStr,projNameStr,descStr,roomsStr,floorsStr,bathroomsStr,typeStr, balconyStr[0].toString());

                    tempWork.setUid(uid);

                    final StorageReference stRef = storage.getReference();

                    dbRef.collection("Projects").add(tempWork).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Project Successfully Added",Toast.LENGTH_LONG).show();
                                if(images.size() > 0){
                                    for(int i=0;i<images.size();i++){
                                        Uri file = Uri.fromFile(new File(images.get(i).getPath()));
                                        final int finalI = i;
                                        stRef.child("ProjectImages").child(task.getResult().getId()+"/"+file.getLastPathSegment()).putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    System.out.println("Uploading complete"+ finalI);
                                                    Toast.makeText(getApplicationContext(),"Uploading complete",Toast.LENGTH_LONG).show();
                                                    pd.dismiss();
                                                    finish();
                                                }else{
                                                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                                                    pd.dismiss();
                                                }

                                            }
                                        });
                                    }
                                }

                            }else{
                                Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }
                        }
                    });
                }else{
                    Toast.makeText(AddWorksActivity.this, "Data missing", Toast.LENGTH_SHORT).show();
                }


            }
        });


        upldImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(AddWorksActivity.this)                         //  Initialize ImagePicker with activity or fragment context
                        .setToolbarColor("#212121")         //  Toolbar color
                        .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                        .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                        .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                        .setProgressBarColor("#4CAF50")     //  ProgressBar color
                        .setBackgroundColor("#212121")      //  Background color
                        .setCameraOnly(false)               //  Camera mode
                        .setMultipleMode(true)              //  Select multiple images or single image
                        .setFolderMode(true)                //  Folder mode
                        .setShowCamera(true)                //  Show camera button
                        .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
                        .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
                        .setDoneTitle("Done")               //  Done button title
                        .setLimitMessage("You have reached selection limit")    // Selection limit message
                        .setMaxSize(10)                     //  Max images can be selected
                        .setSavePath("ImagePicker")         //  Image capture folder name
                        .setSelectedImages(images)          //  Selected images
                        .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                        .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                        .setKeepScreenOn(true)              //  Keep screen on when selecting images
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            // do your logic here...
            System.out.println("Images selected");
            for(int i=0;i<images.size();i++){
                System.out.println(images.get(i));
                ImageView imgView = new ImageView(this);
                imgView.setId(i);
                Bitmap bmImg = BitmapFactory.decodeFile(images.get(i).getPath());
                imgView.setImageBitmap(bmImg);
                imgsLyt.addView(imgView);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);  // You MUST have this line to be here
        // so ImagePicker can work with fragment mode
    }
}
