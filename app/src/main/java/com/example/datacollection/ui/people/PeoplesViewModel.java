package com.example.datacollection.ui.people;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PeoplesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private LiveData<ArrayList<Person>> people;
    private PeopleRepository repo;


    public PeoplesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");

        System.out.println("Model is loaded, accessing repo methods");
        repo = new PeopleRepository();
        people = repo.getPeople();
    }

//    public PeoplesViewModel(){
//
//    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<Person>> getPeople(){
//        Log.d("People",people.toString());
        return people;
    }
}