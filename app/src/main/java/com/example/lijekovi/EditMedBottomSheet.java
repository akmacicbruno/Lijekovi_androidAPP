package com.example.lijekovi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EditMedBottomSheet extends BottomSheetDialogFragment {

    TextView naziv;
    private DatabaseReference databaseReference;

    public EditMedBottomSheet() {
    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.bottom_sheet_edit_med, container, false);

        EditText Name = view.findViewById(R.id.edt_name);
        EditText Producer = view.findViewById(R.id.edt_producer);
        EditText Usage_days = view.findViewById(R.id.edt_usage_days);
        EditText Usage_time = view.findViewById(R.id.edt_usage_time);
        Button btn_edit = view.findViewById(R.id.btn_editMed);

        String sifraBS = (String) this.getArguments().getString("sifra");

        String nameBS = (String) this.getArguments().getString("naziv");
        Name.setText(nameBS);

        String producerBS = (String) this.getArguments().getString("proizvodac");
        Producer.setText(producerBS);

        String daysBS = (String) this.getArguments().getString("dani");
        Usage_days.setText(daysBS);

        String timeBS = (String) this.getArguments().getString("vrijeme");
        Usage_time.setText(timeBS);

        databaseReference = FirebaseDatabase.getInstance().getReference("lijekovi");
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Name.toString() == null || Producer.toString() == null || Usage_days.toString() == null || Usage_time.toString() == null) {
                    Toast.makeText(getContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> lijek_update = new HashMap<>();
                    lijek_update.put("naziv", Name.getText().toString());
                    lijek_update.put("proizvodac", Producer.getText().toString());
                    lijek_update.put("primjena_dan", Usage_days.getText().toString());
                    lijek_update.put("primjena_vrijeme", Usage_time.getText().toString());
                    databaseReference.child(sifraBS).updateChildren(lijek_update);
                    Toast.makeText(getContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    dismiss();
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
