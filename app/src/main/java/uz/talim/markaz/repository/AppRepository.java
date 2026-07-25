package uz.talim.markaz.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

import uz.talim.markaz.database.AppDatabase;
import uz.talim.markaz.database.dao.BookDao;
import uz.talim.markaz.database.dao.MediaDao;
import uz.talim.markaz.database.dao.QuestionDao;
import uz.talim.markaz.database.dao.SubjectDao;
import uz.talim.markaz.database.dao.TestResultDao;
import uz.talim.markaz.database.dao.TopicDao;
import uz.talim.markaz.database.dao.TopicProgressDao;
import uz.talim.markaz.database.dao.UserDao;
import uz.talim.markaz.model.Book;
import uz.talim.markaz.model.MediaItem;
import uz.talim.markaz.model.Question;
import uz.talim.markaz.model.Subject;
import uz.talim.markaz.model.TestResult;
import uz.talim.markaz.model.Topic;
import uz.talim.markaz.model.TopicProgress;
import uz.talim.markaz.model.User;

public class AppRepository {

    private final UserDao userDao;
    private final SubjectDao subjectDao;
    private final TopicDao topicDao;
    private final BookDao bookDao;
    private final QuestionDao questionDao;
    private final MediaDao mediaDao;
    private final TestResultDao testResultDao;
    private final TopicProgressDao topicProgressDao;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        subjectDao = db.subjectDao();
        topicDao = db.topicDao();
        bookDao = db.bookDao();
        questionDao = db.questionDao();
        mediaDao = db.mediaDao();
        testResultDao = db.testResultDao();
        topicProgressDao = db.topicProgressDao();
    }

    public interface AuthCallback {
        void onResult(User user);
    }

    public interface RegisterCallback {
        void onResult(boolean success, String message, User user);
    }

    // ---------- AUTH ----------
    public void login(String username, String password, AuthCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = userDao.login(username, password);
            callback.onResult(user);
        });
    }

    public void register(String fullName, String username, String password, String role, RegisterCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            int exists = userDao.countByUsername(username);
            if (exists > 0) {
                callback.onResult(false, "Bu login band, boshqasini tanlang", null);
                return;
            }
            User user = new User(fullName, username, password, role);
            long id = userDao.insert(user);
            user.id = (int) id;
            callback.onResult(true, "Muvaffaqiyatli ro'yxatdan o'tdingiz", user);
        });
    }

    // ---------- SUBJECT ----------
    public LiveData<List<Subject>> getSubjects() {
        return subjectDao.getAll();
    }

    // ---------- TOPIC ----------
    public LiveData<List<Topic>> getTopics(int subjectId) {
        return topicDao.getBySubject(subjectId);
    }

    public void addTopic(Topic topic) {
        AppDatabase.databaseWriteExecutor.execute(() -> topicDao.insert(topic));
    }

    // ---------- BOOK ----------
    public LiveData<List<Book>> getBooks(int topicId) {
        return bookDao.getByTopic(topicId);
    }

    public void addBook(Book book) {
        AppDatabase.databaseWriteExecutor.execute(() -> bookDao.insert(book));
    }

    // ---------- QUESTION ----------
    public LiveData<List<Question>> getQuestions(int topicId) {
        return questionDao.getByTopic(topicId);
    }

    public void addQuestion(Question question) {
        AppDatabase.databaseWriteExecutor.execute(() -> questionDao.insert(question));
    }

    // ---------- MEDIA ----------
    public LiveData<List<MediaItem>> getMedia(int topicId) {
        return mediaDao.getByTopic(topicId);
    }

    public void addMedia(MediaItem mediaItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> mediaDao.insert(mediaItem));
    }

    // ---------- TEST RESULT & PROGRESS ----------
    public interface TestSubmitCallback {
        void onSaved(int score, int total, boolean passed);
    }

    public void submitTestResult(int studentId, int topicId, int score, int total, TestSubmitCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            TestResult result = new TestResult(studentId, topicId, score, total, System.currentTimeMillis());
            testResultDao.insert(result);

            boolean passed = total > 0 && ((double) score / total) >= 0.6;
            if (passed) {
                topicProgressDao.insert(new TopicProgress(studentId, topicId, true, System.currentTimeMillis()));
            }
            callback.onSaved(score, total, passed);
        });
    }

    public LiveData<List<TestResult>> getStudentResults(int studentId) {
        return testResultDao.getByStudent(studentId);
    }

    public LiveData<List<TopicProgress>> getStudentProgress(int studentId) {
        return topicProgressDao.getByStudent(studentId);
    }

    public interface CountCallback {
        void onResult(int count);
    }

    public void getTotalTopicsCount(CountCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> callback.onResult(topicDao.countAll()));
    }
}
