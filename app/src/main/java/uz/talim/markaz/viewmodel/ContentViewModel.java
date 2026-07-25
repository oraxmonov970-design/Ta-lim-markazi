package uz.talim.markaz.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uz.talim.markaz.model.Book;
import uz.talim.markaz.model.MediaItem;
import uz.talim.markaz.model.Question;
import uz.talim.markaz.repository.AppRepository;

public class ContentViewModel extends AndroidViewModel {

    private final AppRepository repository;

    public ContentViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    // Books
    public LiveData<List<Book>> getBooks(int topicId) {
        return repository.getBooks(topicId);
    }

    public void addBook(Book book) {
        repository.addBook(book);
    }

    // Questions
    public LiveData<List<Question>> getQuestions(int topicId) {
        return repository.getQuestions(topicId);
    }

    public void addQuestion(Question question) {
        repository.addQuestion(question);
    }

    // Media
    public LiveData<List<MediaItem>> getMedia(int topicId) {
        return repository.getMedia(topicId);
    }

    public void addMedia(MediaItem mediaItem) {
        repository.addMedia(mediaItem);
    }
}
