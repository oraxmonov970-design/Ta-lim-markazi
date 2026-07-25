package uz.talim.markaz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "test_results")
public class TestResult {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int studentId;
    public int topicId;
    public int score;
    public int totalQuestions;
    public long takenAt;

    public TestResult(int studentId, int topicId, int score, int totalQuestions, long takenAt) {
        this.studentId = studentId;
        this.topicId = topicId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.takenAt = takenAt;
    }
}
