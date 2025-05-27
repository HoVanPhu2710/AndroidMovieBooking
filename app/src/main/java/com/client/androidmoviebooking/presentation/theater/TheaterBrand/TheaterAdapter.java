package com.client.androidmoviebooking.presentation.theater.TheaterBrand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.domain.model.theater.Theater;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {

    private List<Theater> theaterList;
        private OnTheaterClickListener theaterClickListener;

    public interface OnTheaterClickListener {
        void onTheaterClick(int theaterId);
    }

    public TheaterAdapter(List<Theater> theaterList, OnTheaterClickListener theaterClickListener) {
        this.theaterList = theaterList;
        this.theaterClickListener = theaterClickListener;
    }

    @NonNull
    @Override
    public TheaterAdapter.TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_theater, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterAdapter.TheaterViewHolder holder, int position) {
        Theater theater = theaterList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(theater.getLogo())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.theaterLogo);

        holder.theaterName.setText(theater.getName());
        holder.theaterCity.setText(theater.getCity());
        holder.theaterAddress.setText(theater.getAddress());
        holder.theaterTotalScreen.setText(String.format("Tổng số phòng chiếu: %d phòng", theater.getTotalScreens()));

        holder.itemView.setOnClickListener(v -> {
            theaterClickListener.onTheaterClick(theater.getId());
        });
    }

    @Override
    public int getItemCount() {
        return theaterList != null ? theaterList.size() : 0;
    }

    public class TheaterViewHolder extends RecyclerView.ViewHolder {
        ImageView theaterLogo;
        TextView theaterName;
        TextView theaterCity;
        TextView theaterAddress;
        ImageView detailsButton;

        TextView theaterTotalScreen;

        public TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            theaterLogo = itemView.findViewById(R.id.theater_logo);
            theaterName = itemView.findViewById(R.id.theater_name);
            theaterCity = itemView.findViewById(R.id.theater_city);
            theaterTotalScreen = itemView.findViewById(R.id.theater_total_screen);
            theaterAddress = itemView.findViewById(R.id.theater_address);
            detailsButton = itemView.findViewById(R.id.theater_details_button);
        }
    }
}
