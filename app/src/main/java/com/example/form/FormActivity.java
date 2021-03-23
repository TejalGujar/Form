package com.example.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity
{

    EditText edtPatientFirstName, edtPatientLastName, edtPatientGender, edtPatientSymptoms;
    Button btnSubmit, btnShowDetails;

    //Declaration
    FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity_layout);


        edtPatientFirstName = findViewById(R.id.edtPatientFirstName);
        edtPatientLastName = findViewById(R.id.edtPatientLastName);
        edtPatientGender = findViewById(R.id.edtPatientGender);
        edtPatientSymptoms = findViewById(R.id.edtPatientSymptoms);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnShowDetails = findViewById(R.id.btnShowDetails);


        //Initialization
        firestoreDB = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strFirstName = edtPatientFirstName.getText().toString();
                String  strLastName = edtPatientLastName.getText().toString();
                String strGender = edtPatientGender.getText().toString();
                String strSymptoms = edtPatientSymptoms.getText().toString();

                PatientDetails pd = new PatientDetails(strFirstName,strLastName,strGender,strSymptoms);

                //Insertion
                firestoreDB.collection("PATIENT_DETAILS").add(pd.toMap()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(FormActivity.this,"Data Added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FormActivity.this,"Fail...",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),PatientListActivity.class);
                startActivity(i);
            }
        });

    }
}
