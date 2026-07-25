package uz.talim.markaz.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import uz.talim.markaz.model.TestResult;
import uz.talim.markaz.model.TopicProgress;
import uz.talim.markaz.repository.AppRepository;

public class ProgressViewModel extends AndroidViewModel {

    private final AppRepository repository;
    public final MutableLiveData<Integer> totalTopics = new MutableLiveData<>(0);

    public ProgressViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        repository.getTotalTopicsCount(totalTopics::postValue);
    }

    public LiveData<List<TestResult>> getResults(int studentId) {
        return repository.getStudentResults(studentId);
    }

    public LiveData<List<TopicProgress>> getProgress(int studentId) {
        return repository.getStudentProgress(studentId);
    }
}
