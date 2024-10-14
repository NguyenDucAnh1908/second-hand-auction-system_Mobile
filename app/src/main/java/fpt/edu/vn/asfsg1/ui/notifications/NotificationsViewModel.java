package fpt.edu.vn.asfsg1.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> _text;

    public NotificationsViewModel() {
        _text = new MutableLiveData<>();
        _text.setValue("This is notifications Fragment");
    }

    public LiveData<String> getText() {
        return _text;
    }
}
