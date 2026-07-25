package uz.talim.markaz.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String fullName;

    @NonNull
    public String username;

    @NonNull
    public String password;

    // "TEACHER" yoki "STUDENT"
    @NonNull
    public String role;

    public User(@NonNull String fullName, @NonNull String username, @NonNull String password, @NonNull String role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
