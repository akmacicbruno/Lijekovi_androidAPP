package com.example.lijekovi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHomeActivity extends RecyclerView.Adapter<AdapterHomeActivity.MedicineViewHolder>{

    private RecyclerViewClickListener listener;
    Context context;
    ArrayList<Medicine> list;
    ArrayList<User> list_user;

    public AdapterHomeActivity(Context context, ArrayList<Medicine> list, ArrayList<User> list_user, RecyclerViewClickListener listener) {
        this.context = context;
        this.list = list;
        this.list_user = list_user;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
//        if (dataList.get(viewType) instanceof User)
//        {
//            View view = (View) LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.user_info_view, viewGroup, false);
//            return new UserViewHolder(view);
//        }
//        else if (dataList.get(viewType) instanceof Medicine)
//        {
//            View view = (View) LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.medicine_info_view, viewGroup, false);
//            return new MedicineViewHolder(view);
//        }
//        return null;
        View v = LayoutInflater.from(context).inflate(R.layout.medicine_info_view, parent, false);
        return new MedicineViewHolder(v);

    }
    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder viewHolder, int position) {
//        if(dataList.get(position) instanceof User) {
//            UserViewHolder userViewHolder = (UserViewHolder) viewHolder;
//            userViewHolder.tvHolderImePrezime.setText(((User) dataList.get(position)).getFullName());
//            userViewHolder.tvHolderOIB.setText(((User) dataList.get(position)).getOib());
//        }
//        else if(dataList.get(position) instanceof Medicine) {
//            MedicineViewHolder medViewHolder = (MedicineViewHolder) viewHolder;
//            medViewHolder.tvHolderNazivLijeka.setText(((Medicine) dataList.get(position)).getNaziv());
//        }
        Medicine med = list.get(position);
        viewHolder.tvHolderNazivLijeka.setText(med.getNaziv());
        viewHolder.tvHolderProizvodac.setText(med.getProizvodac());
        viewHolder.tvHolderUpotreba.setText(med.getPropisana_primjena());
        Picasso.get().load(med.getSlika()).into(viewHolder.tvHolderSlikaLijeka);

        if (med.getNaziv().isEmpty()) {
            viewHolder.tvHolderNazivLijeka.setText("No data");
        }
        if (med.getProizvodac().isEmpty()){
            viewHolder.tvHolderProizvodac.setText("No data");
        }
        if (med.getPropisana_primjena().isEmpty()){
            viewHolder.tvHolderUpotreba.setText("No data");
        }
    }
    public void onBindViewHolder(@NonNull UserViewHolder viewHolder, int position) {
        User user = list_user.get(position);
        viewHolder.tvHolderOIB.setText(user.getOib());
        viewHolder.tvHolderImePrezime.setText(user.getFullName());

        if (user.getOib().isEmpty()) {
            viewHolder.tvHolderOIB.setText("No data");
        }
        if (user.getFullName().isEmpty()){
            viewHolder.tvHolderImePrezime.setText("No data");
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvHolderOIB;
        TextView tvHolderImePrezime;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHolderOIB = itemView.findViewById(R.id.textView_oib);
            tvHolderImePrezime = itemView.findViewById(R.id.textView_ime_prezime);
        }
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvHolderNazivLijeka, tvHolderProizvodac, tvHolderUpotreba;
        ImageView tvHolderSlikaLijeka;
        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHolderNazivLijeka = itemView.findViewById(R.id.textView_naziv_lijeka);
            tvHolderProizvodac = itemView.findViewById(R.id.textView_proizvodac);
            tvHolderUpotreba = itemView.findViewById(R.id.textView_usage);
            tvHolderSlikaLijeka = itemView.findViewById(R.id.imageView_lijek);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
