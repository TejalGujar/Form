package com.example.form;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.ItemViewHolder>
{

    List<PatientDetails> pList;
    Context ctx;

    FirebaseFirestore firestoreDB;

    public PatientDetailsAdapter(List<PatientDetails> pList, Context ctx){
        this.pList = pList;
        this.ctx = ctx;
        this.firestoreDB = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ItemViewHolder(LayoutInflater.from(ctx).inflate(R.layout.patient_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        PatientDetails pd = pList.get(position);

        holder.txtFirstName.setText(pd.getpFirstName());
        holder.txtLastName.setText(pd.getpLastName());
        holder.txtGender.setText(pd.getpGender());
        holder.txtSymptoms.setText(pd.getpSymptoms());

        holder.txtFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ctx,UpdatePatientActivity.class);
                i.putExtra("pd",pd);
                ctx.startActivity(i);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestoreDB.collection("PATIENT_DETAILS").document(pd.getpId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ctx,"Data Successfully Deleted",Toast.LENGTH_LONG).show();

                        pList.remove(position);

                        //refresh the adapter
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ctx,"Data Deletion Fail",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView txtFirstName, txtLastName, txtGender, txtSymptoms;
        Button btnDelete;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            this.txtFirstName = itemView.findViewById(R.id.txtpFirstName);
            this.txtLastName = itemView.findViewById(R.id.txtpLastName);
            this.txtGender = itemView.findViewById(R.id.txtpGender);
            this.txtSymptoms = itemView.findViewById(R.id.txtpSymptoms);
            this.btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
