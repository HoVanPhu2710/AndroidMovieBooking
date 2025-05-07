package com.client.androidmoviebooking.presentation.theater.TheaterBrand;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.client.androidmoviebooking.domain.model.theater.Theater;
import com.client.androidmoviebooking.domain.model.theater.TheaterBrand;
import com.client.androidmoviebooking.domain.usecase.GetCityUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterBrandUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterRecommendUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

public class TheaterViewModel extends ViewModel {
    private static final String TAG = "TheaterViewModel";

    private final GetCityUseCase getCityUseCase;
    private final GetTheaterBrandUseCase getTheaterBrandUseCase;
    private final GetTheaterUseCase getTheaterUseCase;
    private final GetTheaterRecommendUseCase getTheaterRecommendUseCase;
    private final MutableLiveData<List<String>> cities = new MutableLiveData<>();
    private final MutableLiveData<String> selectedCity = new MutableLiveData<>();
    private final MutableLiveData<List<TheaterBrand>> theaterBrands = new MutableLiveData<>();
    private final MutableLiveData<List<Theater>> theaters = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedBrandId = new MutableLiveData<>(0); // đề xuất
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Cache for brand logo
    private final Map<Integer, String> brandLogoCache = new HashMap<>();
    private final Set<Integer> favoriteTheaters = new HashSet<>();

    @Inject
    public TheaterViewModel(
            GetCityUseCase getCityUseCase,
            GetTheaterBrandUseCase getTheaterBrandUseCase,
            GetTheaterUseCase getTheaterUseCase,
            GetTheaterRecommendUseCase getTheaterRecommendUseCase) {
        this.getCityUseCase = getCityUseCase;
        this.getTheaterBrandUseCase = getTheaterBrandUseCase;
        this.getTheaterUseCase = getTheaterUseCase;
        this.getTheaterRecommendUseCase = getTheaterRecommendUseCase;
    }

    public LiveData<List<String>> getCities() {
        return cities;
    }

    public LiveData<String> getSelectedCity() {
        return selectedCity;
    }

    public LiveData<List<TheaterBrand>> getTheaterBrands() {
        return theaterBrands;
    }

    public LiveData<List<Theater>> getTheaters() {
        return theaters;
    }

    public LiveData<Integer> getSelectedBrandId() {
        return selectedBrandId;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setSelectedCity(String city) {
        if (city != null) {
            city = city.trim();
        }
        selectedCity.setValue(city);
    }

    public void setSelectedBrandId(int brandId) {
        selectedBrandId.setValue(brandId);
    }

    public void loadCities() {
        getCityUseCase.execute(new GetCityUseCase.OnResult() {
            @Override
            public void OnSuccess(List<String> cities) {
                TheaterViewModel.this.cities.postValue(cities != null ? cities : new ArrayList<>());
            }

            @Override
            public void OnFailure(Throwable throwable) {
                cities.postValue(new ArrayList<>());
            }
        });
    }

    public void loadTheaterBrands(String city) {
        if (city == null || city.trim().isEmpty()) {
            errorMessage.postValue("Thành phố không hợp lệ");
            return;
        }
        city = city.trim();

        Log.d(TAG, "Loading theater brands for city: " + city);
        getTheaterBrandUseCase.execute(city, new GetTheaterBrandUseCase.OnResult() {
            @Override
            public void onSuccess(List<TheaterBrand> theaterBrands) {
                Log.d(TAG, "Theater brands loaded: " + (theaterBrands != null ? theaterBrands.size() : 0) + " items");
                brandLogoCache.clear();
                if (theaterBrands != null) {
                    for (TheaterBrand brand : theaterBrands) {
                        Log.d(TAG, "BrandId: " + brand.getId() + ", Logo: " + brand.getLogo());
                        brandLogoCache.put(brand.getId(), brand.getLogo());
                    }
                    Log.d(TAG, "brandLogoCache size: " + brandLogoCache.size());
                    TheaterViewModel.this.theaterBrands.postValue(theaterBrands);
                } else {
                    Log.d(TAG, "Theater brands is null");
                    TheaterViewModel.this.theaterBrands.postValue(new ArrayList<>());
                    errorMessage.postValue("Không có thương hiệu rạp nào được tìm thấy");
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "Failed to load theater brands", throwable);
                errorMessage.postValue("Không thể tải thương hiệu rạp: " + throwable.getMessage());
                theaterBrands.postValue(new ArrayList<>());
            }
        });
    }

    public void LoadTheaterRecommends(String city) {
        if (city == null || city.trim().isEmpty()) {
            errorMessage.postValue("Thành phố không hợp lệ");
            theaters.postValue(new ArrayList<>());
            return;
        }
        city = city.trim();

        Log.d(TAG, "Loading recommended theaters for city: " + city + ", brandLogoCache size: " + brandLogoCache.size());
        isLoading.setValue(true);
        getTheaterRecommendUseCase.execute(city, new GetTheaterRecommendUseCase.OnResult() {
            @Override
            public void OnSuccess(List<Theater> theaterRecommends) {
                isLoading.setValue(false);
                if (theaterRecommends != null) {
                    Log.d(TAG, "Theater recommends loaded: " + theaterRecommends.size() + " items");
                    for (Theater theaterRecommend : theaterRecommends) {
                        Integer brandId = theaterRecommend.gettheaterBrandId();
                        String logo = brandId != null ? brandLogoCache.get(brandId) : null;
                        Log.d(TAG, "Theater: " + theaterRecommend.getName() + ", BrandId: " + brandId + ", Logo: " + logo);
                        if (logo != null) {
                            theaterRecommend.setLogo(logo);
                        } else {
                            Log.w(TAG, "No logo found for theater: " + theaterRecommend.getName() + ", BrandId: " + brandId);
                        }
                    }
                    TheaterViewModel.this.theaters.postValue(theaterRecommends);
                } else {
                    Log.d(TAG, "Theater recommends is null");
                    TheaterViewModel.this.theaters.postValue(new ArrayList<>());
                    errorMessage.postValue("Không có rạp nào được đề xuất");
                }
            }

            @Override
            public void OnFailure(Throwable throwable) {
                isLoading.setValue(false);
                Log.e(TAG, "Failed to load theater recommend", throwable);
                errorMessage.postValue("Không thể tải rạp đề xuất: " + throwable.getMessage());
                theaters.postValue(new ArrayList<>());
            }
        });
    }

    public void LoadTheaterByBrand(int brandId, String city) {
        if (city == null || city.trim().isEmpty()) {
            errorMessage.postValue("Thành phố không hợp lệ");
            theaters.postValue(new ArrayList<>());
            return;
        }
        city = city.trim();

        Log.d(TAG, "Loading theaters for brandId: " + brandId + ", city: " + city);
        // Đảm bảo brandLogoCache được điền trước
        if (brandLogoCache.isEmpty()) {
            Log.w(TAG, "brandLogoCache is empty, loading theater brands first");
            loadTheaterBrands(city);
        }

        getTheaterUseCase.execute(brandId, city, new GetTheaterUseCase.OnResult() {
            @Override
            public void OnSuccess(List<Theater> theaters) {
                if (theaters != null) {
                    Log.d(TAG, "Theaters by brand loaded: " + theaters.size() + " items");
                    for (Theater theater : theaters) {
                        Integer theaterBrandId = theater.gettheaterBrandId();
                        String logo = theaterBrandId != null ? brandLogoCache.get(theaterBrandId) : null;
                        Log.d(TAG, "Theater: " + theater.getName() + ", BrandId: " + theaterBrandId + ", Logo: " + logo);
                        if (logo != null) {
                            theater.setLogo(logo);
                        } else {
                            Log.w(TAG, "No logo found for theater: " + theater.getName() + ", BrandId: " + theaterBrandId);
                        }
                    }
                    TheaterViewModel.this.theaters.postValue(theaters);
                } else {
                    Log.d(TAG, "Theaters by brand is null");
                    TheaterViewModel.this.theaters.postValue(new ArrayList<>());
                    errorMessage.postValue("Không có rạp nào được tìm thấy");
                }
            }

            @Override
            public void OnFailure(Throwable throwable) {
                Log.e(TAG, "Failed to load theater by brand: " + throwable.getMessage());
                errorMessage.postValue("Không thể tải rạp: " + throwable.getMessage());
                theaters.postValue(new ArrayList<>());
            }
        });
    }
}