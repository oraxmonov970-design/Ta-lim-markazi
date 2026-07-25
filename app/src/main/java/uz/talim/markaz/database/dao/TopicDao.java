package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uz.talim.markaz.model.Topic;

@Dao
public interface TopicDao {

    @Insert
    long insert(Topic topic);

    @Query("SELECT * FROM topics WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    LiveData<List<Topic>> getBySubject(int subjectId);

    @Query("SELECT * FROM topics WHERE id = :id LIMIT 1")
    Topic getById(int id);

    @Query("SELECT COUNT(*) FROM topics WHERE subjectId = :subjectId")
    int countBySubject(int subjectId);

    @Query("SELECT COUNT(*) FROM topics")
    int countAll();
}
