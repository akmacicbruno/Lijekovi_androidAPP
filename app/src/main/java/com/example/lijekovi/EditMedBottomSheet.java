package com.example.lijekovi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

    private EditText Name, Producer, Usage_days, Usage_time, Quantity, AddQuantity;
    private String sifraBS, nameBS, producerBS, daysBS, timeBS, quantityBS, addQuantity, newQuantity;
    private Integer addQuantityINT, editQuantity, newQuantityINT;
    private Button btn_edit;
    private DatabaseReference databaseReference;

    public EditMedBottomSheet() {
    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.bottom_sheet_edit_med, container, false);

        Name = view.findViewById(R.id.edt_name);
        Producer = view.findViewById(R.id.edt_producer);
        Usage_days = view.findViewById(R.id.edt_usage_days);
        Usage_time = view.findViewById(R.id.edt_usage_time);
        Quantity = view.findViewById(R.id.edt_quantity);
        AddQuantity = view.findViewById(R.id.edt_addQuantity);
        btn_edit = view.findViewById(R.id.btn_editMed);

        sifraBS = (String) this.getArguments().getString("sifra");

        nameBS = (String) this.getArguments().getString("naziv");
        Name.setText(nameBS);

        producerBS = (String) this.getArguments().getString("proizvodac");
        Producer.setText(producerBS);

        daysBS = (String) this.getArguments().getString("dani");
        Usage_days.setText(daysBS);

        timeBS = (String) this.getArguments().getString("vrijeme");
        Usage_time.setText(timeBS);

        quantityBS = (String) this.getArguments().getString("kolicina");
        Quantity.setText(quantityBS);

        databaseReference = FirebaseDatabase.getInstance().getReference("lijekovi");
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Name.getText().toString()) || TextUtils.isEmpty(Producer.getText().toString())|| TextUtils.isEmpty(Quantity.getText().toString()) || TextUtils.isEmpty(Usage_days.getText().toString()) || TextUtils.isEmpty(Usage_time.getText().toString()) ) {
                    Toast.makeText(getContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!TextUtils.isEmpty(AddQuantity.getText().toString())) {
                        addQuantity = AddQuantity.getText().toString();
                        addQuantityINT = Integer.parseInt(addQuantity);
                        editQuantity = Integer.parseInt(quantityBS);
                        newQuantityINT = editQuantity + addQuantityINT;
                        newQuantity = newQuantityINT.toString().trim();
                    }
                    if (TextUtils.isEmpty(AddQuantity.getText().toString())) {
                        addQuantityINT = 0;
                    }
                    Map<String, Object> lijek_update = new HashMap<>();
                    lijek_update.put("naziv", Name.getText().toString());
                    lijek_update.put("proizvodac", Producer.getText().toString());
                    lijek_update.put("primjena_dan", Usage_days.getText().toString());
                    lijek_update.put("primjena_vrijeme", Usage_time.getText().toString());
                    lijek_update.put("kolicina_na_raspolaganju", String.valueOf(Integer.parseInt(Quantity.getText().toString()) + addQuantityINT));
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
