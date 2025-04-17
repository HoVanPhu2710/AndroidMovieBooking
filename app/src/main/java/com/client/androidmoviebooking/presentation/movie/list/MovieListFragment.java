package com.client.androidmoviebooking.presentation.movie.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.client.androidmoviebooking.App;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.di.ViewModelFactory;
import com.client.androidmoviebooking.domain.model.Movie;
import com.client.androidmoviebooking.domain.model.RecommendMovie;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class MovieListFragment extends Fragment {
    private RecyclerView rvPopularMovies;
    private RecyclerView rvGenreMovies;
    private MovieListAdapter popularAdapter;
    private GenreMovieAdapter genreAdapter;
    private MovieListViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        rvPopularMovies = view.findViewById(R.id.rv_popular_movies);
        if (rvPopularMovies == null) {
            return view;
        }
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPopularMovies.setLayoutManager(popularLayoutManager);

        rvGenreMovies = view.findViewById(R.id.rv_genre_movies);
        if (rvGenreMovies == null) {
            return view;
        }
        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvGenreMovies.setLayoutManager(genreLayoutManager);

        popularAdapter = new MovieListAdapter(movie -> {}, true);
        genreAdapter = new GenreMovieAdapter(movie -> {});

        rvPopularMovies.setAdapter(popularAdapter);
        rvGenreMovies.setAdapter(genreAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((App) requireActivity().getApplication()).getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieListViewModel.class);

        viewModel.getPopularMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && !movies.isEmpty()) {
                popularAdapter.setMovies(movies);
                int middlePosition = Integer.MAX_VALUE / 2;
                rvPopularMovies.scrollToPosition(middlePosition - (middlePosition % movies.size()));
            } else {
                popularAdapter.setMovies(new ArrayList<>());
            }
        });

        viewModel.getMoviesByGenre().observe(getViewLifecycleOwner(), moviesByGenre -> {
            if (moviesByGenre != null) {
                genreAdapter.setGenreMovies(moviesByGenre);
            } else {
                genreAdapter.setGenreMovies(new HashMap<>());
            }
        });

        viewModel.loadMovies();
    }
}