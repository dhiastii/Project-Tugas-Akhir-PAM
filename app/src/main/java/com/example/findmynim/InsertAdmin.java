package com.example.findmynim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InsertAdmin extends AppCompatActivity {

    private EditText namamhsi, nimmhsi;
    private Button addbut;

    /*FirebaseFireStore*/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_admin);

        namamhsi = findViewById(R.id.namamhs);
        nimmhsi = findViewById(R.id.nimmhs);
        addbut = findViewById(R.id.addbut);

        /* Penggunaan Popup Loading */
        progressDialog = new ProgressDialog(InsertAdmin.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data Berhasil Ditambahkan...");
        
        addbut.setOnClickListener(v->{
            if (namamhsi.getText().length() > 0 &&nimmhsi.getText().length() > 0){
                save(namamhsi.getText().toString(), nimmhsi.getText().toString());
            }
            else {
                Toast.makeText(this, "Silahkan Isi Semua Data", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getStringExtra("id");
            namamhsi.setText(intent.getStringExtra("nama mahasiswa"));
            nimmhsi.setText(intent.getStringExtra("NIM"));
        }

    }

    private void save(String nma, String nim) {
        Map<String,Object> Mahasiswa = new HashMap<>();

        Mahasiswa.put("nama mahasiswa", nma);
        Mahasiswa.put("NIM", nim);

        progressDialog.show();

        if(id != null){
            db.collection("mahasiswa").document(id)
                    .set(Mahasiswa)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(InsertAdmin.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(InsertAdmin.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            db.collection("mahasiswa")
                    .add(Mahasiswa)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(InsertAdmin.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InsertAdmin.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }


}