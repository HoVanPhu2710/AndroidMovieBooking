package com.client.androidmoviebooking.presentation.movie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.domain.model.RecommendMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GenreMovieAdapter extends RecyclerView.Adapter<GenreMovieAdapter.GenreViewHolder> {
    private List<GenreItem> genreItems = new ArrayList<>();
    private final Consumer<MovieListAdapter.MovieItem> onMovieClick;

    public GenreMovieAdapter(Consumer<MovieListAdapter.MovieItem> onMovieClick) {
        this.onMovieClick = onMovieClick;
    }

    public void setGenreMovies(Map<String, List<RecommendMovie>> moviesByGenre) {
        this.genreItems.clear();
        for (Map.Entry<String, List<RecommendMovie>> entry : moviesByGenre.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                genreItems.add(new GenreItem(entry.getKey(), entry.getValue()));
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genre_section, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        GenreItem genreItem = genreItems.get(position);
        holder.bind(genreItem);
    }

    @Override
    public int getItemCount() {
        return genreItems.size();
    }

    static class GenreItem {
        private final String genreName;
        private final List<RecommendMovie> movies;

        public GenreItem(String genreName, List<RecommendMovie> movies) {
            this.genreName = genreName;
            this.movies = movies;
        }

        public String getGenreName() {
            return genreName;
        }

        public List<RecommendMovie> getMovies() {
            return movies;
        }
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {
        private final TextView genreTitleTextView;
        private final RecyclerView rvMovies;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTitleTextView = itemView.findViewById(R.id.tv_genre_title);
            rvMovies = itemView.findViewById(R.id.rv_genre_movies);
            rvMovies.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

        public void bind(GenreItem genreItem) {
            genreTitleTextView.setText(genreItem.getGenreName());
            MovieListAdapter movieAdapter = new MovieListAdapter(onMovieClick, false);
            rvMovies.setAdapter(movieAdapter);
            movieAdapter.setMovies(genreItem.getMovies());
        }
    }
}