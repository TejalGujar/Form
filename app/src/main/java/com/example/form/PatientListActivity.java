package com.example.form;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {
    FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_list_layout);

        RecyclerView rvpList = findViewById(R.id.rvpList);


        List<String>symValues = new ArrayList<>();
        symValues.add("Fever");
        symValues.add("Headache");

        firestoreDB = FirebaseFirestore.getInstance();
                firestoreDB.collection("PATIENT_DETAILS")
                        //.whereIn("pSymptoms",symValues)
                        //.whereEqualTo("pLastName","Patil")
                        //.whereEqualTo("pGender","Female")
                        .orderBy("pFirstName", Query.Direction.ASCENDING)
                        //.startAfter("P")
                        //.endAt("P")
                        //.startAt("P")
                        //.endBefore("P")
                        //.limit(3)

                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<PatientDetails> pd = new ArrayList<>();

                            for (DocumentSnapshot doc:task.getResult()){
                                PatientDetails pDetails = doc.toObject(PatientDetails.class);
                                pDetails.setpId(doc.getId());
                                pd.add(pDetails);
                            }

                            rvpList.setLayoutManager(new LinearLayoutManager(PatientListActivity.this,LinearLayoutManager.VERTICAL,false));
                            rvpList.setAdapter(new PatientDetailsAdapter(pd,PatientListActivity.this));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PatientListActivity.this,"Data Show Fail...",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
