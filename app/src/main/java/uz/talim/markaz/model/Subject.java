package uz.talim.markaz.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subjects")
public class Subject {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String iconName; // ixtiyoriy: fan uchun belgi

    public Subject(String name, String iconName) {
        this.name = name;
        this.iconName = iconName;
    }
}
