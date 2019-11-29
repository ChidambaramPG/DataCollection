package com.example.datacollection.ui.people;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datacollection.AddNewPersonActvity;
import com.example.datacollection.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.opencensus.metrics.LongGauge;

public class PeoplesFragment extends Fragment {

    private PeoplesViewModel peoplesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_peoples, container, false);



        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPersonIntent = new Intent(getActivity(), AddNewPersonActvity.class);
                startActivity(addPersonIntent);
            }
        });

        RecyclerView people = root.findViewById(R.id.peopleRecyclerView);
        people.setLayoutManager(new LinearLayoutManager(getActivity()));
        final PeopleAdapter adapter = new PeopleAdapter(getActivity());
        people.setAdapter(adapter);

        peoplesViewModel = ViewModelProviders.of(this).get(PeoplesViewModel.class);
        System.out.println(peoplesViewModel.getPeople());
        System.out.println(peoplesViewModel);

//        Log.d("People",peoplesViewModel.getPeople().toString());
        peoplesViewModel.getPeople().observe(this, new Observer<ArrayList<Person>>() {
            @Override
            public void onChanged(ArrayList<Person> people) {
                adapter.setPersons(people);
            }
        });


        return root;
    }
}