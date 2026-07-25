package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uz.talim.markaz.model.Book;

@Dao
public interface BookDao {

    @Insert
    long insert(Book book);

    @Query("SELECT * FROM books WHERE topicId = :topicId ORDER BY uploadedAt DESC")
    LiveData<List<Book>> getByTopic(int topicId);
}
