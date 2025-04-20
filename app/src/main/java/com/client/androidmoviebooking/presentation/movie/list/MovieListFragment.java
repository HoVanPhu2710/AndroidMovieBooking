package com.client.androidmoviebooking.presentation.movie.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.client.androidmoviebooking.App;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.di.ViewModelFactory;
import com.client.androidmoviebooking.domain.model.Movie;
import com.client.androidmoviebooking.domain.model.RecommendMovie;
import com.client.androidmoviebooking.databinding.FragmentMovieListBinding;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class MovieListFragment extends Fragment {
    private FragmentMovieListBinding binding;
    private MovieListAdapter popularAdapter;
    private GenreMovieAdapter genreAdapter;
    private MovieListViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inject Dagger
        ((App) requireActivity().getApplication()).getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieListViewModel.class);

        // Thiết lập RecyclerView cho phim phổ biến
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvPopularMovies.setLayoutManager(popularLayoutManager);
        popularAdapter = new MovieListAdapter(this::navigateToMovieDetail, true);
        binding.rvPopularMovies.setAdapter(popularAdapter);

        // Thiết lập RecyclerView cho phim theo thể loại
        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvGenreMovies.setLayoutManager(genreLayoutManager);
        genreAdapter = new GenreMovieAdapter(this::navigateToMovieDetail);
        binding.rvGenreMovies.setAdapter(genreAdapter);

        // Quan sát dữ liệu từ ViewModel
        viewModel.getPopularMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null && !movies.isEmpty()) {
                popularAdapter.setMovies(movies);
                int middlePosition = Integer.MAX_VALUE / 2;
                binding.rvPopularMovies.scrollToPosition(middlePosition - (middlePosition % movies.size()));
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

        // Tải dữ liệu
        viewModel.loadMovies();
    }

    private void navigateToMovieDetail(MovieListAdapter.MovieItem movieItem) {
        NavDirections action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movieItem.getId());
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh rò rỉ bộ nhớ
    }
}