package com.example.findmynim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class LihatDataMhs extends AppCompatActivity {

    TextView lihatnama, lihatnim;
    Button backbut;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_mhs);

        lihatnama = findViewById(R.id.namahasil);
        lihatnim = findViewById(R.id.nimhasil);
        backbut = findViewById(R.id.backbut);


        progressDialog = new ProgressDialog(LihatDataMhs.this);
        progressDialog.setTitle("Sabar");
        progressDialog.setMessage("Menampilkan Data yang Dipilih");

        Intent intent = getIntent();

        if (intent != null) {
            lihatnama.setText(intent.getStringExtra("nama mahasiswa"));
            lihatnim.setText(intent.getStringExtra("NIM" +
                    ""));
        }
        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LihatDataMhs.this, DashboardMahasiswa.class);
                startActivity(intent);
            }
        });
    }
}