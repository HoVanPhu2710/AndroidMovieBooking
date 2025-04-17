package com.client.androidmoviebooking.presentation.movie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.R;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private List<MovieItem> items = new ArrayList<>();
    private final Consumer<MovieItem> onMovieClick;
    private final boolean enableInfiniteScroll;

    public MovieListAdapter(Consumer<MovieItem> onMovieClick, boolean enableInfiniteScroll) {
        this.onMovieClick = onMovieClick;
        this.enableInfiniteScroll = enableInfiniteScroll;
    }

    public void setMovies(List<? extends MovieItem> items) {
        this.items = new ArrayList<>(items != null ? items : new ArrayList<>());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (items.isEmpty()) {
            return; // Không bind nếu danh sách rỗng
        }
        int index = enableInfiniteScroll ? position % items.size() : position;
        MovieItem item = items.get(index);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.isEmpty() ? 0 : (enableInfiniteScroll ? Integer.MAX_VALUE : items.size());
    }

    public interface MovieItem {
        String getTitle();
        String getPosterUrl();
        float getRating();
        List<String> getGenreNames();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private final ImageView posterImageView;
        private final TextView titleTextView;
        private final TextView genresTextView;
        private final TextView ratingTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_movie_poster);
            titleTextView = itemView.findViewById(R.id.tv_movie_title);
            genresTextView = itemView.findViewById(R.id.tv_genre);
            ratingTextView = itemView.findViewById(R.id.tv_rating);
        }

        public void bind(MovieItem item) {
            titleTextView.setText(item.getTitle());
            genresTextView.setText(String.join(", ", item.getGenreNames()));
            ratingTextView.setText(String.format("%.1f", item.getRating()));
            Glide.with(itemView.getContext())
                    .load(item.getPosterUrl())
                    .into(posterImageView);
            itemView.setOnClickListener(v -> onMovieClick.accept(item));
        }
    }
}