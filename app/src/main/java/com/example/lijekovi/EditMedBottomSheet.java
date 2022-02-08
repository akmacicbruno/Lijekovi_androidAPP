package com.example.lijekovi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;

public class EditMedBottomSheet extends BottomSheetDialogFragment {

    TextView naziv;

    public EditMedBottomSheet() {
    }
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saved){
        View view = inflater.inflate(R.layout.bottom_sheet_edit_med, container, false);

        EditText Name = view.findViewById(R.id.edt_name);
        EditText Producer = view.findViewById(R.id.edt_producer);
        EditText Usage_days = view.findViewById(R.id.edt_usage_days);
        EditText Usage_time = view.findViewById(R.id.edt_usage_time);

        String nameBS = (String) this.getArguments().getString("naziv");
        Name.setText(nameBS);

        String producerBS = (String) this.getArguments().getString("proizvodac");
        Producer.setText(producerBS);

        String daysBS = (String) this.getArguments().getString("dani");
        Usage_days.setText(daysBS);

        String timeBS = (String) this.getArguments().getString("vrijeme");
        Usage_time.setText(timeBS);

        return view;
    }
}
