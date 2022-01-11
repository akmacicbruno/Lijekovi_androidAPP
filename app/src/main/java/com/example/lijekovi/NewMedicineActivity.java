package com.example.lijekovi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewMedicineActivity extends AppCompatActivity {

    private EditText ime, sifra, proizvodac, kolicina, dani, vrijeme;
    private String sIme, sSifra, sProizvodac, sKolicina, sDani, sVrijeme, sSlikaUrl;
    private DatabaseReference databaseReference;
    private ImageButton imgBtn_back;
    private Button btn_dodajSliku;
    private Button btn_dodajLijek;
    private ImageView slika;

    int SELECT_PICTURE = 200;

    ArrayList<Medicine> noviLijek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        databaseReference = FirebaseDatabase.getInstance().getReference("lijekovi");

        ime = (EditText) findViewById(R.id.editText_newMed_name);
        sifra = (EditText) findViewById(R.id.editText_newMed_code);
        proizvodac = (EditText) findViewById(R.id.editText_newMed_producer);
        kolicina = (EditText) findViewById(R.id.editText_newMed_quantity);
        dani = (EditText) findViewById(R.id.editText_newMed_days);
        vrijeme = (EditText) findViewById(R.id.editText_newMed_time);
        slika = (ImageView) findViewById(R.id.imageView_medNew_lijek);


        sIme = ime.getText().toString();
        sSifra = sifra.getText().toString();
        sProizvodac = proizvodac.getText().toString();
        sKolicina = kolicina.getText().toString();
        sDani = dani.getText().toString();
        sVrijeme = vrijeme.getText().toString();
        sSlikaUrl = "";

        //sSifra, sIme, sProizvodac, sDani, sVrijeme, sKolicina, sSlikaUrl


        imgBtn_back = (ImageButton) findViewById(R.id.imgBtn_back);
        imgBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewMedicineActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_dodajSliku = (Button) findViewById(R.id.btn_add_image);
        btn_dodajSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromPhone();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewMedicineActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void chooseImageFromPhone() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    slika.setImageURI(selectedImageUri);
                }
            }
        }
    }
}