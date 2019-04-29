package com.example.alumno_fp.realm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alumno_fp.realm.R;
import com.example.alumno_fp.realm.interfaces.AdapterCustomClick;
import com.example.alumno_fp.realm.models.Place;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolderPlace>{

    private Context context;
    private List<Place> places;
    private AdapterCustomClick listener;

    public PlaceAdapter(Context context, List<Place> places, AdapterCustomClick listener){
        this.context = context;
        this.places = places;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderPlace onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place,viewGroup,false);
        final ViewHolderPlace vhp = new ViewHolderPlace(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(view, vhp.getAdapterPosition());
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(view, vhp.getAdapterPosition());
                return false;
            }
        });

        return vhp;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlace viewHolderPlace, int i) {
        viewHolderPlace.textName.setText(places.get(i).getName());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(places.get(i).getDate());
        viewHolderPlace.textDate.setText(date);
        viewHolderPlace.textCountry.setText(places.get(i).getCountry());
        viewHolderPlace.textCategory.setText(places.get(i).getCategory().getName());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolderPlace extends RecyclerView.ViewHolder{

        TextView textName;
        TextView textCountry;
        TextView textDate;
        TextView textCategory;

        public ViewHolderPlace(View itemView){
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            textDate = itemView.findViewById(R.id.textDate);
            textCategory = itemView.findViewById(R.id.textCategory);
        }
    }
}
