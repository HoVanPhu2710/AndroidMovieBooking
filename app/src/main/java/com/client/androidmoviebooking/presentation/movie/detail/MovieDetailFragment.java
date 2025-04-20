package com.client.androidmoviebooking.presentation.movie.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.App;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.di.ViewModelFactory;
import com.client.androidmoviebooking.domain.model.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_MOVIE_ID = "movie_id";
    private MovieDetailViewModel viewModel;
    private TextView titleTextView, genresTextView, descriptionTextView, releaseDateTextView, durationTextView, directorTextView, ratingTextView;
    private ImageView posterImageView;
    private Button trailerButton, buyTicketButton;

    @Inject
    ViewModelFactory viewModelFactory;

    public static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Khởi tạo các view
        titleTextView = view.findViewById(R.id.tv_title);
        genresTextView = view.findViewById(R.id.tv_genres);
        descriptionTextView = view.findViewById(R.id.tv_description);
        releaseDateTextView = view.findViewById(R.id.tv_release_date);
        durationTextView = view.findViewById(R.id.tv_duration);
        directorTextView = view.findViewById(R.id.tv_director);
        ratingTextView = view.findViewById(R.id.tv_rating);
        posterImageView = view.findViewById(R.id.iv_poster);
        trailerButton = view.findViewById(R.id.btn_trailer);
        buyTicketButton = view.findViewById(R.id.btn_buy_ticket);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ẩn thanh menu
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }

        // Khởi tạo ViewModel
        ((App) requireActivity().getApplication()).getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieDetailViewModel.class);

        // Lấy movieId từ arguments
        int movieId = MovieDetailFragmentArgs.fromBundle(getArguments()).getMovieId();
        Log.d("DEBUG", "Received movieId = " + movieId);
        if (movieId != -1) {
            viewModel.loadMovieDetail(movieId);
        }

        // Quan sát dữ liệu phim
        viewModel.getMovie().observe(getViewLifecycleOwner(), movie -> {
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                genresTextView.setText(String.join(", ", movie.getGenreNames()));
                descriptionTextView.setText(movie.getDescription());
                directorTextView.setText(movie.getDirector().getName());
                ratingTextView.setText(String.format("★ %.1f/10", movie.getRating()));

                // Định dạng ngày khởi chiếu
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date date = inputFormat.parse(movie.getReleaseDate());
                    releaseDateTextView.setText(outputFormat.format(date));
                } catch (Exception e) {
                    releaseDateTextView.setText(movie.getReleaseDate());
                }

                // Thời lượng
                durationTextView.setText(String.format("%d phút", movie.getDuration()));

                // Poster
                Glide.with(this)
                        .load(movie.getPosterUrl())
                        .into(posterImageView);

                // Nút Trailer
                trailerButton.setOnClickListener(v -> {
                    // TODO: Mở trailerUrl bằng Intent hoặc WebView
                });

                // Nút Mua vé
                buyTicketButton.setOnClickListener(v -> {
                    // TODO: Xử lý mua vé
                });
            }
        });

        // Quan sát lỗi
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            // TODO: Hiển thị thông báo lỗi nếu cần
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hiện lại thanh menu khi thoát
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.VISIBLE);
        }
    }
}