package android.zybooks.studentprogressapplication.Database;

import android.content.Context;
import android.zybooks.studentprogressapplication.DAO.TermDAO;
import android.zybooks.studentprogressapplication.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class}, version = 1, exportSchema = false)
public abstract class TermDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();

    private static volatile TermDatabaseBuilder INSTANCE;

    static TermDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TermDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    TermDatabaseBuilder.class, "TermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
