package com.example.lijekovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MedDetailActivity extends AppCompatActivity {

    public final String TAG = "MedDetailTAG";
    private String sNaziv, sSifra, sProizovdac, sKolicina, sPrimjena_dan, sPrimjena_vrijeme, sSlikaUrl;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_detail);

        TextView name = findViewById(R.id.textView_medName);
        TextView sifra = findViewById(R.id.textView_medSifra);
        TextView pro = findViewById(R.id.textView_medProizvodac);
        TextView kol = findViewById(R.id.textView_medKolicina);
        TextView primjena_dan = findViewById(R.id.textView_medDan);
        TextView primjena_vrijeme = findViewById(R.id.textView_medVrijeme);
        ImageView slika = findViewById(R.id.imageView_lijek_slika);
        Button btn_back = (Button) findViewById(R.id.btn_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sNaziv = extras.getString("naziv");
            sSifra = extras.getString("sifra");
            sProizovdac = extras.getString("proizvodac");
            sKolicina = extras.getString("kolicina_na_raspolaganju");
            sPrimjena_dan = extras.getString("primjena_dan");
            sPrimjena_vrijeme = extras.getString("primjena_vrijeme");
            sSlikaUrl = extras.getString("slika");
            Picasso.get().load(sSlikaUrl).into(slika);
        }

        name.setText(sNaziv);
        sifra.setText(sSifra);
        pro.setText(sProizovdac);
        kol.setText(sKolicina);
        primjena_dan.setText(sPrimjena_dan);
        primjena_vrijeme.setText(sPrimjena_vrijeme);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}