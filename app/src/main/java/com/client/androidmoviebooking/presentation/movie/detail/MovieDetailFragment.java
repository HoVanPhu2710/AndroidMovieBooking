package com.client.androidmoviebooking.presentation.movie.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.client.androidmoviebooking.App;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.di.ViewModelFactory;
import com.client.androidmoviebooking.domain.model.Movie;
import com.client.androidmoviebooking.domain.model.MovieCast;
import com.client.androidmoviebooking.domain.model.PersonInMovie;
import com.client.androidmoviebooking.domain.repository.CastItem;
import com.client.androidmoviebooking.domain.repository.DirectorItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MovieDetailFragment extends Fragment {
    private static final String ARG_MOVIE_SLUG = "movie_slug";
    private MovieDetailViewModel viewModel;
    private TextView titleTextView, genresTextView, descriptionTextView, releaseDateTextView, durationTextView, ratingTextView;
    private RecyclerView directorCastRecyclerView;
    private ImageView posterImageView;
    private Button trailerButton, buyTicketButton;
    private Toolbar detailToolbar;

    private ImageButton closeButton;


    @Inject
    ViewModelFactory viewModelFactory;

    public static MovieDetailFragment newInstance(String movieSlug) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_SLUG, movieSlug);
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
        directorCastRecyclerView = view.findViewById(R.id.rv_director_casts);
        ratingTextView = view.findViewById(R.id.tv_rating);
        posterImageView = view.findViewById(R.id.iv_poster);
        trailerButton = view.findViewById(R.id.btn_trailer);
        buyTicketButton = view.findViewById(R.id.btn_buy_ticket);
        detailToolbar = view.findViewById(R.id.toolbar);
        closeButton = view.findViewById(R.id.btnClose);

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
        String movieSlug = String.valueOf(MovieDetailFragmentArgs.fromBundle(getArguments()).getMovieSlug());
        Log.d("DEBUG", "Received movieSlug = " + movieSlug);
        if (movieSlug != null) {
            viewModel.loadMovieDetail(movieSlug);
            Log.d("TAG", "ở đây à: ");
        }

        // Quan sát dữ liệu phim
        viewModel.getMovieDetail().observe(getViewLifecycleOwner(), movieDetail -> {
            if (movieDetail != null) {
                Log.d("TAG", "onViewCreated: ");
                titleTextView.setText(movieDetail.getTitle());
                genresTextView.setText(String.join(", ", movieDetail.getGenreNames()));
                descriptionTextView.setText(movieDetail.getDescription());
                ratingTextView.setText(String.format("★ %.1f/10", movieDetail.getRating()));

                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date date = inputFormat.parse(movieDetail.getReleaseDate());
                    releaseDateTextView.setText(outputFormat.format(date));
                } catch (Exception e) {
                    releaseDateTextView.setText(movieDetail.getReleaseDate());
                }

                durationTextView.setText(String.format("%d phút", movieDetail.getDuration()));

                Glide.with(this)
                        .load(movieDetail.getPosterUrl())
                        .into(posterImageView);

                // Dữ liệu cho RecyclerView đạo diễn và diễn viên
                List<PersonInMovie> items = new ArrayList<>();
                if (movieDetail.getDirector() != null) {
                    items.add(new DirectorItem(movieDetail.getDirector()));
                }
                if (movieDetail.getMovieCasts() != null) {
                    for (MovieCast movieCast : movieDetail.getMovieCasts()) {
                        items.add(new CastItem(movieCast));
                    }
                }

                directorCastRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
                directorCastRecyclerView.setAdapter(new DirectorCastAdapter(items));

                trailerButton.setOnClickListener(v -> {
                    // TODO: Mở trailerUrl
                });

                buyTicketButton.setOnClickListener(v -> {
                    // TODO: Mua vé
                });
            }
        });


        // Quan sát lỗi
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            // TODO: Hiển thị thông báo lỗi nếu cần
        });
        detailToolbar.setNavigationOnClickListener(v -> {
            requireActivity().onBackPressed(); // Gọi back truyền thống
        });
        closeButton.setOnClickListener(v -> {
            requireActivity().finishAffinity(); // Đóng toàn bộ task, không chỉ Fragment hiện tại
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