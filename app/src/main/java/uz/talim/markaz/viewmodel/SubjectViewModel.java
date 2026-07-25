package uz.talim.markaz.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uz.talim.markaz.model.Subject;
import uz.talim.markaz.model.Topic;
import uz.talim.markaz.repository.AppRepository;

public class SubjectViewModel extends AndroidViewModel {

    private final AppRepository repository;
    private final LiveData<List<Subject>> subjects;

    public SubjectViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        subjects = repository.getSubjects();
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjects;
    }

    public LiveData<List<Topic>> getTopics(int subjectId) {
        return repository.getTopics(subjectId);
    }

    public void addTopic(Topic topic) {
        repository.addTopic(topic);
    }
}
