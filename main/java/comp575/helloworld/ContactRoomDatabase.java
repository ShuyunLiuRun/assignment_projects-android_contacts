package comp575.helloworld;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Room uses the DAO to issue queries to its database.
 *By default, to avoid poor UI performance, Room doesn't allow you to issue database queries on the main thread. LiveData applies this rule by automatically running the query asynchronously on a background thread, when needed.
 *Room provides compile-time checks of SQLite statements.
 *Your Room class must be abstract and extend RoomDatabase
 * Usually, you only need one instance of the Room database for the whole app.
 */

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactRoomDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    private static ContactRoomDatabase INSTANCE;
    public static synchronized ContactRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ContactRoomDatabase.class, "contact_database").build();

        }
        return INSTANCE;
    }


}

