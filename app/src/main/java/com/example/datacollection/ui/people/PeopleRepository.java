package com.example.datacollection.ui.people;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PeopleRepository {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference dbRef = db.collection("People");
    private MutableLiveData<ArrayList<Person>> people = new MutableLiveData<>();

    public PeopleRepository(){

        db.collection("People")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Person> temp = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
                        System.out.println(document.getId());
                        System.out.println(document.getData());

                        Person tempPerson = document.toObject(Person.class);
                        tempPerson.setUid(document.getId());
                        temp.add(tempPerson);

                        System.out.println(tempPerson.getName());
                    }

                    people.postValue(temp);

                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
                    System.out.println(task.getException());

                }

            }
        });
    }

    public LiveData<ArrayList<Person>> getPeople(){
        System.out.println("returning data");
        return people;
    }

}
