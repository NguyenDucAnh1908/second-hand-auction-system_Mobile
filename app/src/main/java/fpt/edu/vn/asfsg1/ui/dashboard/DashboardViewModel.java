package fpt.edu.vn.asfsg1.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> _text;

    public DashboardViewModel() {
        _text = new MutableLiveData<>();
        _text.setValue("This is dashboard Fragment");
    }

    public LiveData<String> getText() {
        return _text;
    }
}
