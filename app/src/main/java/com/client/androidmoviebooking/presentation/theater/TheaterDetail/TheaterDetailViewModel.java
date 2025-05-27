package com.client.androidmoviebooking.presentation.theater.TheaterDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.client.androidmoviebooking.domain.model.theater.DateItem;
import com.client.androidmoviebooking.domain.model.theater.Theater;
import com.client.androidmoviebooking.domain.model.theater.TheaterMovie;
import com.client.androidmoviebooking.domain.usecase.GetDateTimeUseCase;
import com.client.androidmoviebooking.domain.usecase.GetTheaterMoviesUseCase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TheaterDetailViewModel extends ViewModel {
    private static final String TAG = "TheaterDetailViewModel";
    private final GetDateTimeUseCase getDateTimeUseCase;
    private final GetTheaterMoviesUseCase getTheaterMoviesUseCase;
    private final MutableLiveData<Theater> theater = new MutableLiveData<>();
    private final MutableLiveData<List<DateItem>> dates = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> selectedDatePosition = new MutableLiveData<>(0);
    private final MutableLiveData<List<TheaterMovie>> theaterMovies = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private int theaterId;

    @Inject
    public TheaterDetailViewModel(GetDateTimeUseCase getDateTimeUseCase, GetTheaterMoviesUseCase getTheaterMoviesUseCase) {
        this.getDateTimeUseCase = getDateTimeUseCase;
        this.getTheaterMoviesUseCase = getTheaterMoviesUseCase;
    }

    public LiveData<Theater> getTheater() {
        return theater;
    }

    public LiveData<List<TheaterMovie>> getTheaterMovies() {
        return theaterMovies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<DateItem>> getDates() {
        return dates;
    }

    public LiveData<Integer> getSelectedDatePosition() {
        return selectedDatePosition;
    }

    public void setSelectedDatePosition(int position) {
        selectedDatePosition.setValue(position);
        List<DateItem> availableDates = dates.getValue();
        if (availableDates != null && position < availableDates.size()) {
            String selectedDate = availableDates.get(position).getDate(); // Sử dụng getDate() thay vì getDay()
            loadTheaterMovies(theaterId, selectedDate);
        }
    }

    public void loadTheaterDetails(int theaterId) {
        if (theaterId <= 0) {
            errorMessage.postValue("ID rạp không hợp lệ");
            return;
        }

        this.theaterId = theaterId;

        isLoading.setValue(true);

        // Load available dates
        loadAvailableDates();

        // Load theater movies for the default date
        String defaultDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        loadTheaterMovies(theaterId, defaultDate);
    }

    private void loadAvailableDates() {
        getDateTimeUseCase.execute(7, new GetDateTimeUseCase.OnResult() {
            @Override
            public void onSuccess(List<DateItem> dateItems) {
                dates.postValue(dateItems != null ? dateItems : new ArrayList<>());
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.postValue("Không thể tải danh sách ngày: " + throwable.getMessage());
                dates.postValue(new ArrayList<>());
            }
        });
    }

    private void loadTheaterMovies(int theaterId, String date) {
        isLoading.setValue(true);
        getTheaterMoviesUseCase.execute(theaterId, date, new GetTheaterMoviesUseCase.OnResult() {
            @Override
            public void onSuccess(Theater theaterDetails, List<TheaterMovie> movies) {
                isLoading.setValue(false);
                theater.postValue(theaterDetails);
                theaterMovies.postValue(movies != null ? movies : new ArrayList<>());
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.postValue("Không thể tải dữ liệu rạp: " + throwable.getMessage());
                theaterMovies.postValue(new ArrayList<>());
            }
        });
    }
}