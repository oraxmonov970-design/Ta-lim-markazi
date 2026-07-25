package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uz.talim.markaz.model.TestResult;

@Dao
public interface TestResultDao {

    @Insert
    long insert(TestResult testResult);

    @Query("SELECT * FROM test_results WHERE studentId = :studentId ORDER BY takenAt DESC")
    LiveData<List<TestResult>> getByStudent(int studentId);

    @Query("SELECT * FROM test_results WHERE studentId = :studentId AND topicId = :topicId ORDER BY takenAt DESC LIMIT 1")
    TestResult getLatest(int studentId, int topicId);
}
