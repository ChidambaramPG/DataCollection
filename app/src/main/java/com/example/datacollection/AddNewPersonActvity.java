package com.example.datacollection;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.datacollection.ui.people.Person;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddNewPersonActvity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    TextInputEditText name,address,phone,altPhone,qualification,experience;
    Spinner jobsType;
    Button addPerson;
    public static final int PICK_IMAGE = 1;
    Bitmap bitmap;
    Menu menu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_form_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Vendor");
        setSupportActionBar(toolbar);

        name = findViewById(R.id.nameField);
        address = findViewById(R.id.addressField);
        phone = findViewById(R.id.phoneField);
        altPhone = findViewById(R.id.alternatePhoneField);
        qualification = findViewById(R.id.qualifcationField);
        experience = findViewById(R.id.experienceField);
        jobsType = findViewById(R.id.jobTypeField);
        addPerson = findViewById(R.id.saveVendorBtn);

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = name.getText().toString();
                String addressStr = address.getText().toString();
                String phoneStr = phone.getText().toString();
                String altPhoneStr = altPhone.getText().toString();
                String qualificationStr = qualification.getText().toString();
                String experienceStr = experience.getText().toString();
                String jobsTypeStr = jobsType.getSelectedItem().toString();

                final ProgressDialog pd = new ProgressDialog(AddNewPersonActvity.this);
                pd.setMessage("loading");

                if(!nameStr .isEmpty() &&  !addressStr .isEmpty() &&  !phoneStr .isEmpty() &&  !altPhoneStr .isEmpty() &&  !qualificationStr .isEmpty() &&  !experienceStr .isEmpty() &&  !jobsTypeStr.isEmpty() ){

                    if(bitmap == null){

                        Toast.makeText(AddNewPersonActvity.this, "select user image also before uploading data", Toast.LENGTH_SHORT).show();

                    }else{
                        pd.show();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        final byte[] data = baos.toByteArray();

                        Person person = new Person(nameStr,addressStr,phoneStr,altPhoneStr,qualificationStr,experienceStr,jobsTypeStr);

                        db.collection("People").add(person).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(final DocumentReference documentReference) {
                                Toast.makeText(AddNewPersonActvity.this,"Person Added Successfully. Uploading image..",Toast.LENGTH_SHORT).show();
                                System.out.println(documentReference.getId());

                                UploadTask task = storageRef.child("VendorImage/"+documentReference.getId()+"/pic.jpeg").putBytes(data);
                                task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if(task.isSuccessful()){
                                            pd.dismiss();
                                            Toast.makeText(AddNewPersonActvity.this, "Upload completed", Toast.LENGTH_SHORT).show();
                                            storageRef.child("VendorImage/"+documentReference.getId()+"/pic.jpeg").getDownloadUrl()
                                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    if(task.isSuccessful()){
                                                        System.out.println(task.getResult());
                                                        db.collection("People").document(documentReference.getId())
                                                                .update("url",task.getResult().toString());

                                                        finish();
                                                    }
                                                }
                                            });
                                        }else{
                                            pd.dismiss();
                                            Toast.makeText(AddNewPersonActvity.this, "Upload Filed. Try later", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(AddNewPersonActvity.this,"Person Details Failed To Add",Toast.LENGTH_LONG).show();
                            }
                        });

                    }




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_form_menu, menu);
        this.menu1 = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_image:

                int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},102
                    );
                } else {

                    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    getIntent.setType("image/*");

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");

                    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                    startActivityForResult(chooserIntent, PICK_IMAGE);

                }



                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = BitmapFactory.decodeFile(picturePath);
            menu1.getItem(0).setIcon(new BitmapDrawable(getResources(),bitmap));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
