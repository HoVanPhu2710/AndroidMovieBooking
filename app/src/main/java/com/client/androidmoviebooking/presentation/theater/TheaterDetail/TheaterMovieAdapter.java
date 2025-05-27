package com.client.androidmoviebooking.presentation.theater.TheaterDetail;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.domain.model.theater.Showtime;
import com.client.androidmoviebooking.domain.model.theater.TheaterMovie;

import java.util.List;

public class TheaterMovieAdapter extends RecyclerView.Adapter<TheaterMovieAdapter.TheaterMovieViewHolder> {

    private List<TheaterMovie> theaterMovies;

    public TheaterMovieAdapter(List<TheaterMovie> theaterMovies) {
        this.theaterMovies = theaterMovies;
    }

    @NonNull
    @Override
    public TheaterMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater_movie, parent, false);
        return new TheaterMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterMovieViewHolder holder, int position) {
        TheaterMovie theaterMovie = theaterMovies.get(position);

        holder.titleTextView.setText(theaterMovie.getTitle());

        // Set movie info (genres, duration, etc.)
        String movieInfo = theaterMovie.getGenresAsString() + " | " + theaterMovie.getDuration() + " phút";
        holder.infoTextView.setText(movieInfo);

        // Load poster image
        Glide.with(holder.itemView.getContext())
                .load(theaterMovie.getPosterUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.posterImageView);

        // Set up showtimes
        setupShowtimes(holder, theaterMovie.getShowtimes());

        // Set up detail button click
        holder.detailButton.setOnClickListener(v -> {
            if (onDetailClickListener != null) {
                onDetailClickListener.onDetailClick(theaterMovie);
            }
        });

        // Set up trailer button click
        holder.trailerButton.setOnClickListener(v -> {
            if (onTrailerClickListener != null) {
                onTrailerClickListener.onTrailerClick(theaterMovie);
            }
        });
    }

    private void setupShowtimes(TheaterMovieViewHolder holder, List<Showtime> showtimes) {
        Log.d("TheaterMovieAdapter", "Số lượng showtimes: " + showtimes.size());
        for (Showtime showtime : showtimes) {
            Log.d("TheaterMovieAdapter", "Showtime: " + showtime.getFormattedStartTime() + " - " + showtime.getFormattedEndTime());
        }
        // Clear existing showtimes
        holder.showtimesContainer.removeAllViews();

        // Inflate and add showtimes
        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

        for (Showtime showtime : showtimes) {
            View showtimeView = inflater.inflate(R.layout.item_theater_showtime, holder.showtimesContainer, false);

            TextView timeTextView = showtimeView.findViewById(R.id.text_time);
            TextView endTimeTextView = showtimeView.findViewById(R.id.text_end_time);
            TextView seatsTextView = showtimeView.findViewById(R.id.text_seats);

            // Sử dụng phương thức đã định dạng để hiển thị chỉ giờ và phút
            timeTextView.setText(showtime.getFormattedStartTime());
            endTimeTextView.setText("~ " + showtime.getFormattedEndTime());
            seatsTextView.setText(showtime.getSeatsInfo());

            holder.showtimesContainer.addView(showtimeView);
            Log.d("TheaterMovieAdapter", "Đã thêm showtime: " + showtime.getFormattedStartTime());
        }
    }

    @Override
    public int getItemCount() {
        return theaterMovies != null ? theaterMovies.size() : 0;
    }

    // Add method to update movies
    public void updateMovies(List<TheaterMovie> newMovies) {
        if (newMovies != null) {
            this.theaterMovies = newMovies;
            notifyDataSetChanged();
        }
    }

    // Interface for detail button click listener
    public interface OnDetailClickListener {
        void onDetailClick(TheaterMovie movie);
    }

    private OnDetailClickListener onDetailClickListener;

    public void setOnDetailClickListener(OnDetailClickListener listener) {
        this.onDetailClickListener = listener;
    }

    // Interface for trailer button click listener
    public interface OnTrailerClickListener {
        void onTrailerClick(TheaterMovie movie);
    }

    private OnTrailerClickListener onTrailerClickListener;

    public void setOnTrailerClickListener(OnTrailerClickListener listener) {
        this.onTrailerClickListener = listener;
    }

    public class TheaterMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;
        TextView infoTextView;
        ViewGroup showtimesContainer;
        Button detailButton;
        Button trailerButton;

        TheaterMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.image_poster);
            titleTextView = itemView.findViewById(R.id.text_title);
            infoTextView = itemView.findViewById(R.id.text_info);
            showtimesContainer = itemView.findViewById(R.id.container_showtimes);
            detailButton = itemView.findViewById(R.id.button_detail);
            trailerButton = itemView.findViewById(R.id.button_trailer);
        }
    }
}