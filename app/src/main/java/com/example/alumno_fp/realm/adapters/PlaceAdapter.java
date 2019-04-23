package com.example.alumno_fp.realm.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alumno_fp.realm.R;
import com.example.alumno_fp.realm.models.Place;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolderPlace> implements View.OnClickListener {

    private Context context;
    private List<Place> places;
    private View.OnClickListener listener;

    public PlaceAdapter(Context context, List<Place> places){
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolderPlace onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_place,viewGroup,false);
        view.setOnClickListener(this);
        return new ViewHolderPlace(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlace viewHolderPlace, int i) {
        viewHolderPlace.textName.setText(places.get(i).getName());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(places.get(i).getDate());
        viewHolderPlace.textDate.setText(date);
        viewHolderPlace.textCountry.setText(places.get(i).getCountry());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolderPlace extends RecyclerView.ViewHolder{

        TextView textName;
        TextView textCountry;
        TextView textDate;

        public ViewHolderPlace(View itemView){
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textCountry = itemView.findViewById(R.id.textCountry);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}
