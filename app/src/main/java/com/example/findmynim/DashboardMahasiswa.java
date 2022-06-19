package com.example.findmynim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.findmynim.adapter.AdminAdapter;
import com.example.findmynim.model.AdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardMahasiswa extends AppCompatActivity {

    RecyclerView daftarmahasiswa;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<AdminModel> list = new ArrayList<>();
    AdminAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_mahasiswa);

        daftarmahasiswa = findViewById(R.id.daftarmahasiswa);
        progressDialog = new ProgressDialog(DashboardMahasiswa.this);
        progressDialog.setTitle("Wait A Minute");
        progressDialog.setMessage("Proses Input Data...");
        adapter = new AdminAdapter(getApplicationContext(), list);


        adapter.setDialog(new AdminAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Lihat Data"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DashboardMahasiswa.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //manggil edit data
                                Intent intent = new Intent(getApplicationContext(), LihatDataMhs.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama mahasiswa", list.get(pos).getNama());
                                intent.putExtra("NIM", list.get(pos).getNim());
                                startActivity(intent);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        daftarmahasiswa.setLayoutManager(layoutManager);
        daftarmahasiswa.addItemDecoration(decoration);
        daftarmahasiswa.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    //select data
    private void getData() {
        progressDialog.show();

        db.collection("mahasiswa")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                AdminModel adminModel = new AdminModel(document.getString("nama mahasiswa"), document.getString("NIM"));
                                adminModel.setId(document.getId());
                                list.add(adminModel);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(DashboardMahasiswa.this, "Data Mahasiswa Gagal diproses", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
}}