package com.example.lijekovi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewMedicineActivity extends AppCompatActivity {

    private EditText ime, sifra, proizvodac, kolicina, dani, vrijeme;
    private String sIme, sSifra, sProizvodac, sKolicina, sDani, sVrijeme, sSlikaUrl;
    private DatabaseReference databaseReference, databaseReferenceuserandmed;
    private ImageButton imgBtn_back;
    private Button btn_dodajSliku;
    private Button btn_dodajLijek;
    private ImageView slika;
    private String sCurrentUser;
    private String lastChild;
    private Integer newKey;

    int SELECT_PICTURE = 200;

    ArrayList<Medicine> noviLijek;
    private static final int ImageBack = 1;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        databaseReference = FirebaseDatabase.getInstance().getReference("lijekovi");
        databaseReferenceuserandmed = FirebaseDatabase.getInstance().getReference("korisnikov_lijek");

        ime = (EditText) findViewById(R.id.editText_newMed_name);
        sifra = (EditText) findViewById(R.id.editText_newMed_code);
        proizvodac = (EditText) findViewById(R.id.editText_newMed_producer);
        kolicina = (EditText) findViewById(R.id.editText_newMed_quantity);
        dani = (EditText) findViewById(R.id.editText_newMed_days);
        vrijeme = (EditText) findViewById(R.id.editText_newMed_time);
        slika = (ImageView) findViewById(R.id.imageView_medNew_lijek);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sCurrentUser = extras.getString("PrijavljeniKorisnik");
        }


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

        storageReference = FirebaseStorage.getInstance().getReference().child("medicine");

        btn_dodajLijek = (Button) findViewById(R.id.btn_add_medNew);
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
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,ImageBack);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == ImageBack) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (resultCode == RESULT_OK) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                btn_dodajLijek.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StorageReference imgName = storageReference.child("med"+ selectedImageUri.getLastPathSegment());
                        imgName.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Toast.makeText(NewMedicineActivity.this, "Image uploaded!", Toast.LENGTH_SHORT).show();
                                imgName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadUrl = uri;
                                        Toast.makeText(NewMedicineActivity.this, "Image uploaded!" + downloadUrl, Toast.LENGTH_SHORT).show();
                                        sIme = ime.getText().toString();
                                        sSifra = sifra.getText().toString();
                                        sProizvodac = proizvodac.getText().toString();
                                        sKolicina = kolicina.getText().toString();
                                        sDani = dani.getText().toString();
                                        sVrijeme = vrijeme.getText().toString();
                                        sSlikaUrl = downloadUrl.toString();

                                        Log.d("TAGslikaurl", "onClick: " + sSlikaUrl);

                                        Medicine new_med = new Medicine(sSifra, sIme, sProizvodac, sDani, sVrijeme, sKolicina, sSlikaUrl);
                                        databaseReference.child(sSifra).setValue(new_med);
                                    }
                                });
                            }

                        });
//                        sIme = ime.getText().toString();
                        sSifra = sifra.getText().toString();
//                        sProizvodac = proizvodac.getText().toString();
//                        sKolicina = kolicina.getText().toString();
//                        sDani = dani.getText().toString();
//                        sVrijeme = vrijeme.getText().toString();
//                        sSlikaUrl = imgName.getDownloadUrl().toString();
//
//                        Log.d("TAGslikaurl", "onClick: " + sSlikaUrl);
//
//                        Medicine new_med = new Medicine(sSifra, sIme, sProizvodac, sDani, sVrijeme, sKolicina, sSlikaUrl);
//                        databaseReference.child(sSifra).setValue(new_med);

                        databaseReferenceuserandmed.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                                    lastChild = childSnapshot.getKey().toString();
                                }
                                Log.d("TAGtest", "onDataChange: " + lastChild);
                                newKey = (Integer.parseInt(lastChild)+1);
                                Log.d("Nesto", "onChildAdded: " + lastChild + "---->" + newKey.toString());

                                Map<String, String> korisnik_lijek = new HashMap<>();
                                korisnik_lijek.put("id", newKey.toString());
                                korisnik_lijek.put("korisnik", sCurrentUser);
                                korisnik_lijek.put("lijek", sSifra);
                                Log.d("TAGtest", "newkey: " + newKey.toString());
                                databaseReferenceuserandmed.child(newKey.toString()).setValue(korisnik_lijek);

                                Toast.makeText(NewMedicineActivity.this, "Successfully added new medicine!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(NewMedicineActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    slika.setImageURI(selectedImageUri);
                    Log.d("TAGslika", "onActivityResultSLIKA: " + selectedImageUri);
                }
            }
        }
    }
}