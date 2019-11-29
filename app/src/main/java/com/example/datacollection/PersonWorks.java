package com.example.datacollection;

import android.content.Intent;
import android.os.Bundle;

import com.example.datacollection.ui.people.PeopleAdapter;
import com.example.datacollection.ui.people.Person;
import com.example.datacollection.ui.work.ProjectsAdapter;
import com.example.datacollection.ui.work.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class PersonWorks extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_works);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent i = getIntent();
        String username = i.getStringExtra("name");
        final String uid = i.getStringExtra("uid");
        toolbar.setTitle(username.toUpperCase()+ "'S PROJECTS");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userWorksIntent = new Intent(getApplicationContext(), AddWorksActivity.class);
                userWorksIntent.putExtra("uid",uid);
                startActivity(userWorksIntent);
            }
        });



        RecyclerView people = findViewById(R.id.projectsRecyclerView);
        people.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        final ProjectsAdapter adapter = new ProjectsAdapter(this);
        people.setAdapter(adapter);

        db.collection("Projects").whereEqualTo("uid",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Work> projects = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    System.out.println(document.getId());
                    System.out.println(document.getData());
                    Work tempWork = document.toObject(Work.class);
                    tempWork.setUid(document.getId());
                    projects.add(tempWork);

                    System.out.println(tempWork.getProjName());
                }

                adapter.setProject(projects);
                adapter.notifyDataSetChanged();
            }
        });



    }

}
