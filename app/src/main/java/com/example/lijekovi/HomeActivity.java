package com.example.lijekovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private AdapterHomeActivity.RecyclerViewClickListener listener;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference databaseReference;
    private DatabaseReference database;
    private DatabaseReference ref;
    private DatabaseReference database_korisnik_lijek;
    private DatabaseReference database_korisnik;

    ArrayList<Medicine> list;
    ArrayList<User> list_users;

    AdapterHomeActivity myAdapter;

    ImageButton btn_logout;
    FloatingActionButton btn_add_new;

    TextView tv_name, tv_oib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_home);

        database = FirebaseDatabase.getInstance().getReference("lijekovi");
        database_korisnik_lijek = FirebaseDatabase.getInstance().getReference("korisnikov_lijek");
        database_korisnik = FirebaseDatabase.getInstance().getReference("korisnici");

        list_users = new ArrayList<>();
        list = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("userEmail","email: " + user.getUid());
        Query userEmailQuery = database_korisnik.orderByChild("email").equalTo(user.getEmail());
        userEmailQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot usersSnapshot : snapshot.getChildren()) {
                    User user7 = usersSnapshot.getValue(User.class);
                    tv_name = (TextView) findViewById(R.id.textView_profile_name);
                    tv_oib = (TextView) findViewById(R.id.textView_profile_oib);

                    tv_name.setText(user7.getPuno_ime());
                    tv_oib.setText(user7.getOib());
                    Log.d("userSnap", "oib: " + user7.getOib());

                    Query userMedQuery = database_korisnik_lijek.orderByChild("korisnik").equalTo(user7.getOib());
                    userMedQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot lijekSnapshot : snapshot.getChildren()) {
                                String lijek = lijekSnapshot.child("lijek").getValue(String.class);
                                Log.d("lijekoviSifre", "sifre: " + lijek);
                                Query MedQuery = database.orderByChild("sifra").equalTo(lijek);
                                MedQuery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                            Medicine medicine = dataSnapshot.getValue(Medicine.class);
                                            list.add(medicine);
                                            Log.d("listMed", medicine.toString());
                                        }
                                        myAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("userSnapERROR", "Fail");
            }
        });


        setAdapter();



//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        databaseReference= rootRef.child("korisnikov_lijek");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    String theraid = dataSnapshot1.child("korisnik").getValue(String.class);
//                    String theraid2 = dataSnapshot1.child("lijek").getValue(String.class);
//
//                    DatabaseReference userRef = rootRef.child("korisnici").child(theraid);
//                    DatabaseReference lijekRef = rootRef.child("lijekovi").child(theraid2);
//                    userRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot userSnapshot) {
//                            User user = userSnapshot.getValue(User.class);
//                            list_users.add(user);
//                            Log.d("listUser", user.toString());
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            throw databaseError.toException();
//                        }
//                    });
//                    lijekRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot userSnapshot) {
//                            Medicine medicine = userSnapshot.getValue(Medicine.class);
//                            list.add(medicine);
//                            Log.d("listMed", medicine.toString());
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            throw databaseError.toException();
//                        }
//                    });
//                    myAdapter.notifyDataSetChanged();
//
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });

//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                    Medicine medicine = dataSnapshot.getValue(Medicine.class);
//                    list.add(medicine);
//                    Log.d("listMed", medicine.toString());
//                }
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        btn_logout = (ImageButton) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_add_new = findViewById(R.id.btn_dodaj_novi_lijek);
        btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewMedicineActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setAdapter() {
        setOnClickListener();
        myAdapter = new AdapterHomeActivity(this, list, list_users, listener);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myAdapter);
    }

    private void setOnClickListener() {
        listener = new AdapterHomeActivity.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(HomeActivity.this, MedDetailActivity.class);
                intent.putExtra("naziv", list.get(position).getNaziv());
                intent.putExtra("sifra", list.get(position).getSifra());
                intent.putExtra("proizvodac", list.get(position).getProizvodac());
                intent.putExtra("primjena_dan", list.get(position).getPrimjena_dan());
                intent.putExtra("primjena_vrijeme", list.get(position).getPrimjena_vrijeme());
                intent.putExtra("kolicina_na_raspolaganju", list.get(position).getKolicina_na_raspolaganju());
                intent.putExtra("slika", list.get(position).getSlika());
                startActivity(intent);
            }
        };
    }
}