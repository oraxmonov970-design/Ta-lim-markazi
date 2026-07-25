package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uz.talim.markaz.model.MediaItem;

@Dao
public interface MediaDao {

    @Insert
    long insert(MediaItem mediaItem);

    @Query("SELECT * FROM media_items WHERE topicId = :topicId ORDER BY uploadedAt DESC")
    LiveData<List<MediaItem>> getByTopic(int topicId);
}
