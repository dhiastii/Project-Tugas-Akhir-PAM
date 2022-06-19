package com.example.findmynim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TampilanPilihan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_pilihan);

        Button bAdmin = (Button) findViewById(R.id.adminbut);
        Button bMhs = (Button) findViewById(R.id.mhsbut);

        bAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilanPilihan.this, DashboardAdmin.class);
                startActivity(intent);
            }
        });

        bMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TampilanPilihan.this, DashboardMahasiswa.class);
                startActivity(intent);
            }
        });
    }
}