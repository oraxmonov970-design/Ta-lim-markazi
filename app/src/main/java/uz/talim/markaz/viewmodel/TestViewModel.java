package uz.talim.markaz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import uz.talim.markaz.repository.AppRepository;

public class TestViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public final MutableLiveData<int[]> resultData = new MutableLiveData<>(); // [score, total, passed(1/0)]

    public TestViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void submitResult(int studentId, int topicId, int score, int total) {
        repository.submitTestResult(studentId, topicId, score, total, (s, t, passed) -> {
            resultData.postValue(new int[]{s, t, passed ? 1 : 0});
        });
    }
}
