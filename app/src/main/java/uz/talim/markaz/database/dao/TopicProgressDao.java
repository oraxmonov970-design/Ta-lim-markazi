package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import uz.talim.markaz.model.TopicProgress;

@Dao
public interface TopicProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TopicProgress progress);

    @Query("SELECT * FROM topic_progress WHERE studentId = :studentId")
    LiveData<List<TopicProgress>> getByStudent(int studentId);

    @Query("SELECT COUNT(*) FROM topic_progress WHERE studentId = :studentId AND completed = 1")
    int countCompleted(int studentId);
}
