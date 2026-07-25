package uz.talim.markaz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import uz.talim.markaz.model.User;
import uz.talim.markaz.repository.AppRepository;

public class AuthViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public final MutableLiveData<User> loginResult = new MutableLiveData<>();
    public final MutableLiveData<Boolean> loginFailed = new MutableLiveData<>();

    public final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    public final MutableLiveData<String> registerMessage = new MutableLiveData<>();
    public final MutableLiveData<User> registeredUser = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void login(String username, String password) {
        repository.login(username, password, user -> {
            if (user != null) {
                loginResult.postValue(user);
            } else {
                loginFailed.postValue(true);
            }
        });
    }

    public void register(String fullName, String username, String password, String role) {
        repository.register(fullName, username, password, role, (success, message, user) -> {
            registerSuccess.postValue(success);
            registerMessage.postValue(message);
            if (success) {
                registeredUser.postValue(user);
            }
        });
    }
}
