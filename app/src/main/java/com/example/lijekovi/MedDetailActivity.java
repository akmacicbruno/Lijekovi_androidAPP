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
        TextView primjena = findViewById(R.id.textView_medDan);
        ImageView slika = findViewById(R.id.imageView_lijek_slika);
        Button btn_back = (Button) findViewById(R.id.btn_back);

        String naziv = "Name not found";
        String sifra1 = "Sifra not found";
        String pro1 = "Pro not found";
        String kol1 = "Kol not found";
        String primjena1 = "Primjena not found";
        String slikaUrl;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            naziv = extras.getString("naziv");
            sifra1 = extras.getString("sifra");
            pro1 = extras.getString("proizvodac");
            kol1 = extras.getString("kolicina_na_raspolaganju");
            primjena1 = extras.getString("propisana_primjena");
            slikaUrl = extras.getString("slika");
            Picasso.get().load(slikaUrl).into(slika);
        }
        name.setText(naziv);
        sifra.setText(sifra1);
        pro.setText(pro1);
        kol.setText(kol1);
        primjena.setText(primjena1);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Log.d("SIFRA", "SIFRA: " + sifra1);
    }
}