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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.nio.BufferUnderflowException;
import java.util.HashMap;
import java.util.Map;


public class MedDetailActivity extends AppCompatActivity {

    public final String TAG = "MedDetailTAG";
    private String sNaziv, sSifra, sProizovdac, sKolicina, sPrimjena_dan, sPrimjena_vrijeme, sSlikaUrl;
    private DatabaseReference databaseReference;

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
        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        Button btn_med_taken = (Button) findViewById(R.id.btn_med_taken);

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

        databaseReference = FirebaseDatabase.getInstance().getReference("lijekovi");
        Integer KolicinaINT = Integer.parseInt(sKolicina);
        String NovaKolicina = String.valueOf(KolicinaINT-1);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String nameBS = sNaziv;
                String producerBS = sProizovdac;
                bundle.putString("sifra", sSifra);
                bundle.putString("kolicina", sKolicina);
                bundle.putString("naziv", sNaziv);
                bundle.putString("proizvodac", sProizovdac);
                bundle.putString("dani", sPrimjena_dan);
                bundle.putString("vrijeme", sPrimjena_vrijeme);

                EditMedBottomSheet bottomSheet = new EditMedBottomSheet();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(), "TAG");
            }
        });
        btn_med_taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> lijek_update = new HashMap<>();
                lijek_update.put("kolicina_na_raspolaganju", NovaKolicina);
                databaseReference.child(sSifra).updateChildren(lijek_update);
                Toast.makeText(MedDetailActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MedDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}