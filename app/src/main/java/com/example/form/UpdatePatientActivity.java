package com.example.form;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdatePatientActivity extends AppCompatActivity
{
    EditText edtPatientFirstName, edtPatientLastName, edtPatientGender, edtPatientSymptoms;
    Button btnUpdate;

    FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity_layout);

        edtPatientFirstName = findViewById(R.id.edtPatientFirstName);
        edtPatientLastName = findViewById(R.id.edtPatientLastName);
        edtPatientGender = findViewById(R.id.edtPatientGender);
        edtPatientSymptoms = findViewById(R.id.edtPatientSymptoms);

        firestoreDB = FirebaseFirestore.getInstance();

        btnUpdate = findViewById(R.id.btnUpdate);

        PatientDetails pd = (PatientDetails) getIntent().getSerializableExtra("pd");

        edtPatientFirstName.setText(pd.getpFirstName());
        edtPatientLastName.setText(pd.getpLastName());
        edtPatientGender.setText(pd.getpGender());
        edtPatientSymptoms.setText(pd.getpSymptoms());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<>();
                map.put("pSymptoms",edtPatientSymptoms.getText().toString());
                firestoreDB.collection("PATIENT_DETAILS").document(pd.getpId()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdatePatientActivity.this,"Data SuccessFully Updated",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdatePatientActivity.this,"Data Updation Fail",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
