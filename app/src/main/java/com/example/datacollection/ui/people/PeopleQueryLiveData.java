package com.example.datacollection.ui.people;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.Query;

public class PeopleQueryLiveData extends LiveData {

    private final Query query;
//    private final MyValueEventListener listener = new MyValueEventListener();

    public PeopleQueryLiveData(Query query, Query query1) {
        this.query = query1;
    }


}
