package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uz.talim.markaz.model.Question;

@Dao
public interface QuestionDao {

    @Insert
    long insert(Question question);

    @Query("SELECT * FROM questions WHERE topicId = :topicId")
    LiveData<List<Question>> getByTopic(int topicId);

    @Query("SELECT * FROM questions WHERE topicId = :topicId")
    List<Question> getByTopicSync(int topicId);

    @Query("SELECT COUNT(*) FROM questions WHERE topicId = :topicId")
    int countByTopic(int topicId);
}
