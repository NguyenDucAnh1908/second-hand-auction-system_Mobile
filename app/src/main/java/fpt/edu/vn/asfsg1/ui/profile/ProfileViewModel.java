package fpt.edu.vn.asfsg1.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> _text;

    public ProfileViewModel() {
        _text = new MutableLiveData<>();
        _text.setValue("This is profile Fragment");
    }

    public LiveData<String> getText() {
        return _text;
    }
}
