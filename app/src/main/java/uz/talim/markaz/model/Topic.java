package uz.talim.markaz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topics")
public class Topic {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int subjectId;
    public String name;
    public String description;
    public long createdAt;

    public Topic(int subjectId, String name, String description, long createdAt) {
        this.subjectId = subjectId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }
}
