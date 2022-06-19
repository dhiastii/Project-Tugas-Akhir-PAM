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
import android.view.View;
import android.widget.Button;
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

public class DashboardAdmin extends AppCompatActivity {


    Button bAdd;
    RecyclerView daftarmahasiswa;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<AdminModel> list = new ArrayList<>();
    AdminAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        bAdd = findViewById(R.id.bAdd);
        daftarmahasiswa = findViewById(R.id.daftarmahasiswa);
        progressDialog = new ProgressDialog(DashboardAdmin.this);
        progressDialog.setTitle("Wait A Minute");
        progressDialog.setMessage("Proses Input Data...");
        adapter = new AdminAdapter(getApplicationContext(), list);

        adapter.setDialog(new AdminAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit Data","Hapus Data"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DashboardAdmin.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //manggil edit data
                                Intent intent = new Intent(getApplicationContext(), InsertAdmin.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama mahasiswa", list.get(pos).getNama());
                                intent.putExtra("NIM", list.get(pos).getNim());
                                startActivity(intent);
                                break;
                            case 1:
                                //manggil hapus data
                                deleteData(list.get(pos).getId());
                                break;

                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        daftarmahasiswa.setLayoutManager(layoutManager);
        daftarmahasiswa.addItemDecoration(decoration);
        daftarmahasiswa.setAdapter(adapter);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(getApplicationContext(), InsertAdmin.class);
                startActivity(data);
            }
        });
    }

    private void deleteData(String id) {
        progressDialog.show();
        db.collection("mahasiswa").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DashboardAdmin.this, "Data Mahasiswa Gagal Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(DashboardAdmin.this, "Data Mahasiswa Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        getData();
                    }
                });
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
                            Toast.makeText(DashboardAdmin.this, "Data Mahasiswa Gagal diproses", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}