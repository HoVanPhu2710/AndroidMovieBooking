package com.client.androidmoviebooking.presentation.theater.TheaterBrand;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.client.androidmoviebooking.domain.model.theater.Theater;
import com.client.androidmoviebooking.domain.model.theater.TheaterBrand;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TheaterFragment extends Fragment {
    private static final String TAG = "TheaterFragment";
    private RecyclerView theaterRecyclerView;
    private RecyclerView brandRecyclerView;
    private TheaterAdapter theaterAdapter;
    private BrandAdapter brandAdapter;
    private List<Theater> theaterList;
    private List<TheaterBrand> brandList;
    private TextView suggestedTheaterCount;
    private TextView citySelector;
    private EditText searchEditText;
    private TextView headerTitle;
    private TheaterViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theater, container, false);

        // Inject Dagger
        ((App) requireActivity().getApplication()).getAppComponent().inject(this);

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this, viewModelFactory).get(TheaterViewModel.class);

        // Initialize UI components
        initUI(view);

        // Setup observers
        setupObservers();

        // Load initial data
        viewModel.loadCities();

        return view;
    }

    private void initUI(View view) {
        theaterRecyclerView = view.findViewById(R.id.theater_recycler_view);
        brandRecyclerView = view.findViewById(R.id.brand_recycler_view);
        suggestedTheaterCount = view.findViewById(R.id.suggested_theaters_count);
        citySelector = view.findViewById(R.id.city_selector);
        searchEditText = view.findViewById(R.id.search_edit_text);
        headerTitle = view.findViewById(R.id.header_title);

        // Setup toolbar button click listeners
        view.findViewById(R.id.btn_headphones).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Hỗ trợ", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.btn_close).setOnClickListener(v -> {
            requireActivity().finishAffinity();
        });

        // Setup city selector
        citySelector.setOnClickListener(v -> {
            showCitySelectionDialog();
        });

        // Setup search functionality
        searchEditText.setOnClickListener(v -> {
            // Điều hướng đến SearchFragment
            // Navigation.findNavController(v).navigate(R.id.action_theaterFragment_to_searchFragment);
        });

        // Setup theater brands RecyclerView
        setupTheaterBrands();

        // Setup theater list RecyclerView
        setupTheaterList();
    }

    private void setupObservers() {
        // Observe error messages
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe cities
        viewModel.getCities().observe(getViewLifecycleOwner(), cities -> {
            if (cities != null && !cities.isEmpty()) {
                // Set default city
                viewModel.setSelectedCity(cities.get(0));
            }
        });

        // Observe selected city
        viewModel.getSelectedCity().observe(getViewLifecycleOwner(), city -> {
            if (city != null) {
                citySelector.setText(city);
                Log.d(TAG, "Selected city: " + city);
                viewModel.loadTheaterBrands(city);
            }
        });

        // Observe theater brands
        viewModel.getTheaterBrands().observe(getViewLifecycleOwner(), brands -> {
            Log.d(TAG, "Theater brands loaded: " + (brands != null ? brands.size() : 0) + " items");
            if (brands != null) {
                brandList.clear();
                // Add "Đề xuất" as first item
                brandList.add(new TheaterBrand(0, "Đề xuất", null));
                brandList.addAll(brands);
                brandAdapter.notifyDataSetChanged();

                // Tải rạp đề xuất sau khi brandLogoCache được điền
                String city = viewModel.getSelectedCity().getValue();
                if (city != null) {
                    viewModel.setSelectedBrandId(0);
                    viewModel.LoadTheaterRecommends(city);
                }
            }
        });

        // Observe theaters
        viewModel.getTheaters().observe(getViewLifecycleOwner(), theaters -> {
            Log.d(TAG, "Updating theaters: " + (theaters != null ? theaters.size() : 0) + " items");
            if (theaters != null) {
                theaterList.clear();
                theaterList.addAll(theaters);
                theaterAdapter.notifyDataSetChanged();
                updateHeader();
            }
        });

        // Observe selected brand
        viewModel.getSelectedBrandId().observe(getViewLifecycleOwner(), brandId -> {
            if (brandId != null) {
                brandAdapter.setSelectedBrand(brandId);
                brandAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateHeader() {
        Integer selectedBrandId = viewModel.getSelectedBrandId().getValue();
        if (selectedBrandId != null) {
            if (selectedBrandId == 0) {
                headerTitle.setText("Rạp đề xuất");
            } else {
                for (TheaterBrand brand : brandList) {
                    if (brand.getId() == selectedBrandId) {
                        headerTitle.setText(brand.getName());
                        break;
                    }
                }
            }
            suggestedTheaterCount.setText("(" + theaterList.size() + ")");
        }
    }

    private void setupTheaterBrands() {
        brandList = new ArrayList<>();
        brandAdapter = new BrandAdapter(brandList, 0, brandId -> {
            viewModel.setSelectedBrandId(brandId);
            String city = viewModel.getSelectedCity().getValue();
            if (city != null) {
                if (brandId == 0) {
                    viewModel.LoadTheaterRecommends(city);
                } else {
                    viewModel.LoadTheaterByBrand(brandId, city);
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        brandRecyclerView.setLayoutManager(layoutManager);
        brandRecyclerView.setAdapter(brandAdapter);
    }

    private void setupTheaterList() {
        theaterList = new ArrayList<>();
        theaterAdapter = new TheaterAdapter(theaterList, theaterId -> {
            // Navigate to TheaterDetailFragment
            Navigation.findNavController(requireView())
                    .navigate(TheaterFragmentDirections.actionTheaterFragmentToTheaterDetailFragment(theaterId));
        });
        theaterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        theaterRecyclerView.setAdapter(theaterAdapter);
    }

    private void showCitySelectionDialog() {
        List<String> cities = viewModel.getCities().getValue();
        if (cities == null || cities.isEmpty()) return;

        String[] cityArray = cities.toArray(new String[0]);
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Chọn thành phố")
                .setItems(cityArray, (dialog, which) -> {
                    String selectedCity = cityArray[which];
                    viewModel.setSelectedCity(selectedCity);
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        theaterRecyclerView = null;
        brandRecyclerView = null;
    }
}