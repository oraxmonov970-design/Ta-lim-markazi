package uz.talim.markaz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topic_progress", primaryKeys = {"studentId", "topicId"})
public class TopicProgress {

    @androidx.annotation.NonNull
    public int studentId;

    @androidx.annotation.NonNull
    public int topicId;

    public boolean completed;
    public long completedAt;

    public TopicProgress(int studentId, int topicId, boolean completed, long completedAt) {
        this.studentId = studentId;
        this.topicId = topicId;
        this.completed = completed;
        this.completedAt = completedAt;
    }
}
