package fpt.edu.vn.asfsg1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> _text;

    public HomeViewModel() {
        _text = new MutableLiveData<>();
        _text.setValue("This is home Fragment");
    }

    public LiveData<String> getText() {
        return _text;
    }
}
