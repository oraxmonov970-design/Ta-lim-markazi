package uz.talim.markaz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "media_items")
public class MediaItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int topicId;
    public String title;
    public String filePath;
    public String type; // "VIDEO" | "AUDIO"
    public long uploadedAt;

    public MediaItem(int topicId, String title, String filePath, String type, long uploadedAt) {
        this.topicId = topicId;
        this.title = title;
        this.filePath = filePath;
        this.type = type;
        this.uploadedAt = uploadedAt;
    }
}
