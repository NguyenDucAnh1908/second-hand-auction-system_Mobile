package fpt.edu.vn.asfsg1.activity.data.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import fpt.edu.vn.asfsg1.activity.data.LoginDataSource;
import fpt.edu.vn.asfsg1.activity.data.LoginRepository;
import fpt.edu.vn.asfsg1.helper.APIClient;
import fpt.edu.vn.asfsg1.services.AuthService;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            AuthService authService = APIClient.getClient().create(AuthService.class);

            // Tạo một instance của LoginDataSource với AuthService
            LoginDataSource loginDataSource = new LoginDataSource(authService);

            return (T) new LoginViewModel(LoginRepository.getInstance(loginDataSource));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}