package com.example.datacollection.ui.statistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StatisticsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("List Items");
    }

    public LiveData<String> getText() {
        return mText;
    }

//    public LiveData<ArrayList> getEntries(){
////        ArrayList<>
//
//        return new ArrayList<String>
//    }
}