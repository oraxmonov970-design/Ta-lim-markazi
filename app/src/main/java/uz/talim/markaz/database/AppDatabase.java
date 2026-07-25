package uz.talim.markaz.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

@Database(
        entities = {User.class, Subject.class, Topic.class, Book.class, Question.class,
                MediaItem.class, TestResult.class, TopicProgress.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract SubjectDao subjectDao();
    public abstract TopicDao topicDao();
    public abstract BookDao bookDao();
    public abstract QuestionDao questionDao();
    public abstract MediaDao mediaDao();
    public abstract TestResultDao testResultDao();
    public abstract TopicProgressDao topicProgressDao();

    private static volatile AppDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    private static final String[] DEFAULT_SUBJECTS = {
            "Matematika", "Fizika", "Kimyo", "Biologiya",
            "Tarix", "Ona tili va adabiyot", "Ingliz tili", "Informatika"
    };

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "talim_markazi_db")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@androidx.annotation.NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                SubjectDao dao = INSTANCE.subjectDao();
                for (String name : DEFAULT_SUBJECTS) {
                    dao.insert(new Subject(name, null));
                }
            });
        }
    };
}
