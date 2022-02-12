package com.example.lijekovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.nio.BufferUnderflowException;
import java.util.HashMap;
import java.util.Map;


public class MedDetailActivity extends AppCompatActivity {

    public final String TAG = "MedDetailTAG";
    private String sNaziv, sSifra, sProizovdac, sKolicina, sPrimjena_dan, sPrimjena_vrijeme, sSlikaUrl;
    private DatabaseReference databaseReference, databaseReferenceKorisnikovLijek;

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
        ImageButton btn_back = (ImageButton) findViewById(R.id.imgBtn_back1);
        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        Button btn_med_taken = (Button) findViewById(R.id.btn_med_taken);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sNaziv = extras.getString("naziv");
            sSifra = extras.getString("sifra");
            sProizovdac = extras.getString("proizvodac");
//            sKolicina = extras.getString("kolicina_na_raspolaganju");
            sPrimjena_dan = extras.getString("primjena_dan");
            sPrimjena_vrijeme = extras.getString("primjena_vrijeme");
            sSlikaUrl = extras.getString("slika");
            Picasso.get().load(sSlikaUrl).into(slika);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("lijekovi");
        databaseReferenceKorisnikovLijek = FirebaseDatabase.getInstance().getReference("korisnikov_lijek");

        Query MedQuery = databaseReference.orderByChild("sifra").equalTo(sSifra);
        MedQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String lijekKol = dataSnapshot.child("kolicina_na_raspolaganju").getValue(String.class);
                    Integer lijekSifraINT = Integer.parseInt(lijekKol);
                    if (lijekSifraINT <= 0) {
                        btn_med_taken.setEnabled(false);
                    }
                    name.setText(sNaziv);
                    sifra.setText(sSifra);
                    pro.setText(sProizovdac);
                    kol.setText(lijekKol);
                    primjena_dan.setText(sPrimjena_dan);
                    primjena_vrijeme.setText(sPrimjena_vrijeme);

                    Integer KolicinaINT = Integer.parseInt(kol.getText().toString());
                    String NovaKolicina = String.valueOf(KolicinaINT-1);

                    if (lijekSifraINT <= 5) {
                        Drawable danger = getApplicationContext().getResources().getDrawable(R.drawable.ic_danger);
                        kol.setCompoundDrawablesWithIntrinsicBounds(danger, null, null, null);
                    }
                    kol.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MedDetailActivity.this, "Test", Toast.LENGTH_SHORT).show();
                        }
                    });


                    btn_med_taken.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MedDetailActivity.this);
                            builder.setMessage(getResources().getString(R.string.dialog_take_question));
                            builder.setTitle(getResources().getString(R.string.dialog_take_title));
                            builder.setCancelable(false);
                            builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setPositiveButton(getResources().getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Map<String, Object> lijek_update = new HashMap<>();
                                    lijek_update.put("kolicina_na_raspolaganju", NovaKolicina);
                                    databaseReference.child(sSifra).updateChildren(lijek_update);
                                    TextView kolicina = (TextView) findViewById(R.id.textView_medKolicina);
                                    Integer oldKolicinaINT = Integer.parseInt(kolicina.getText().toString());
                                    String newKolicina = String.valueOf(oldKolicinaINT - 1);
                                    kolicina.setText(newKolicina);
                                    Toast.makeText(MedDetailActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MedDetailActivity.this, "Fail!", Toast.LENGTH_SHORT).show();

            }
        });

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
                bundle.putString("kolicina", kol.getText().toString());
                bundle.putString("naziv", sNaziv);
                bundle.putString("proizvodac", sProizovdac);
                bundle.putString("dani", sPrimjena_dan);
                bundle.putString("vrijeme", sPrimjena_vrijeme);

                EditMedBottomSheet bottomSheet = new EditMedBottomSheet();
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(), "TAG");
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MedDetailActivity.this);
                builder.setMessage(getResources().getString(R.string.dialog_delete_question));
                builder.setTitle(getResources().getString(R.string.dialog_delete_title));
                builder.setCancelable(false);
                builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(getResources().getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child(sSifra).removeValue();
                        Query deleteKorisnikovLijek = databaseReferenceKorisnikovLijek.orderByChild("lijek").equalTo(sSifra);
                        deleteKorisnikovLijek.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String key = dataSnapshot.getKey();
                                    Toast.makeText(MedDetailActivity.this, "KEY:" + key, Toast.LENGTH_SHORT).show();
                                    databaseReferenceKorisnikovLijek.child(key).removeValue();
                                    Intent intent = new Intent(MedDetailActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MedDetailActivity.this, "Failed to delete!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}