package com.client.androidmoviebooking.presentation.theater.TheaterDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.client.androidmoviebooking.App;
import com.client.androidmoviebooking.R;
import com.client.androidmoviebooking.di.ViewModelFactory;
import com.client.androidmoviebooking.domain.model.theater.DateItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TheaterDetailFragment extends Fragment {

    private RecyclerView dateRecyclerView;
    private RecyclerView movieRecyclerView;
    private DateAdapter dateAdapter;
    private TheaterMovieAdapter movieAdapter;
    private List<DateItem> dateItems;
    private TextView theaterAddressTextView;
    private TheaterDetailViewModel viewModel;
    private int theaterId;

    @Inject
    ViewModelFactory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theater_detail, container, false);

        ((App) requireActivity().getApplication()).getAppComponent().inject(this);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(TheaterDetailViewModel.class);

        TheaterDetailFragmentArgs args = TheaterDetailFragmentArgs.fromBundle(getArguments());
        theaterId = args.getTheaterId();

        initUI(view);

        setupObservers();

        viewModel.loadTheaterDetails(theaterId);

        return view;
    }

    private void initUI(View view) {
        dateRecyclerView = view.findViewById(R.id.date_recycler_view);
        movieRecyclerView = view.findViewById(R.id.movie_recycler_view);
        theaterAddressTextView = view.findViewById(R.id.theater_address);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        view.findViewById(R.id.btnSupport).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Hỗ trợ", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.btnClose).setOnClickListener(v -> {
            requireActivity().finish();
        });

        setupDateRecyclerView();

        setupMovieRecyclerView();
    }

    private void setupDateRecyclerView() {
        dateItems = new ArrayList<>();
        dateAdapter = new DateAdapter(dateItems, (dateItem, position) -> {
            viewModel.setSelectedDatePosition(position);
        });
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dateRecyclerView.setAdapter(dateAdapter);
        dateRecyclerView.setVisibility(View.GONE);
    }

    private void setupMovieRecyclerView() {
        movieAdapter = new TheaterMovieAdapter(new ArrayList<>());
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.setVisibility(View.GONE);

        movieAdapter.setOnTrailerClickListener(movie -> {
            Toast.makeText(getContext(), "Phát trailer cho: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setupObservers() {
        viewModel.getTheater().observe(getViewLifecycleOwner(), theater -> {
            if (theater != null) {
                androidx.appcompat.widget.Toolbar toolbar = getView().findViewById(R.id.toolbar);
                toolbar.setTitle(theater.getName());
                if (theaterAddressTextView != null) {
                    theaterAddressTextView.setText(theater.getAddress());
                }
            }
        });

        viewModel.getTheaterMovies().observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                movieAdapter.updateMovies(movies);
                movieRecyclerView.setVisibility(movies.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getDates().observe(getViewLifecycleOwner(), dateItems -> {
            if (dateItems != null) {
                this.dateItems.clear();
                this.dateItems.addAll(dateItems);
                dateAdapter.notifyDataSetChanged();
                dateRecyclerView.setVisibility(dateItems.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getSelectedDatePosition().observe(getViewLifecycleOwner(), position -> {
            if (position != null) {
                dateAdapter.setSelectedPosition(position);
                dateAdapter.notifyDataSetChanged();
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dateRecyclerView = null;
        movieRecyclerView = null;
        theaterAddressTextView = null;
    }
}