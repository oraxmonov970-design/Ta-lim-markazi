package uz.talim.markaz.database.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uz.talim.markaz.model.Subject;

@Dao
public interface SubjectDao {

    @Insert
    long insert(Subject subject);

    @Query("SELECT * FROM subjects ORDER BY name ASC")
    LiveData<List<Subject>> getAll();

    @Query("SELECT COUNT(*) FROM subjects")
    int count();

    @Query("SELECT * FROM subjects WHERE id = :id LIMIT 1")
    Subject getById(int id);
}
