package uz.talim.markaz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int topicId;
    public String title;
    public String filePath;   // ilova ichki xotirasidagi fayl manzili
    public long uploadedAt;

    public Book(int topicId, String title, String filePath, long uploadedAt) {
        this.topicId = topicId;
        this.title = title;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
    }
}
