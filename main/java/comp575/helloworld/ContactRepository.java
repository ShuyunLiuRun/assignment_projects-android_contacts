package comp575.helloworld;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

// a Repository is a class that abstracts access to multiple data sources.
//A Repository class handles data operations. It provides a clean API to the rest of the app for app data.
//      -----Repository-------
//      |                    |
//     Dao                  Network

//A Repository manages query threads and allows you to use multiple backends.
public class ContactRepository {
    //Add member variables for the DAO and the list of contacts.
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;
    //Add a constructor that gets a handle to the database and initializes the member variables.
    ContactRepository(Context context) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(context);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();
    }
    //Add a wrapper for the insert() method. You must call this on a non-UI thread
    // or your app will crash. Room ensures that you don't do any
    // long-running operations on the main thread, blocking the UI.
    public void insert(Contact contact) {
        new InsertAsyncTask().execute(contact);
    }
    private class InsertAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(final Contact... params) {
            for (Contact contact : params) {
                contactDao.insert(contact);
            }
            return null;
        }
    }
    public void update(Contact contact) {
        new UpdateAsyncTask().execute(contact);
    }
    private class UpdateAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(final Contact... params) {
            for (Contact contact : params) {
                Long id = contactDao.findConByname(contact.name).id;
                contactDao.update(new Contact(contact.name,contact.email,contact.mobile,id));
            }
            return null;
        }
    }

    public void delete(Contact contact) {
        new deleteAsyncTask().execute(contact);
    }
    private class deleteAsyncTask extends AsyncTask<Contact, Void, Void> {
        @Override
        protected Void doInBackground(final Contact... params) {
            for (Contact contact : params) {
                Long id = contactDao.findConByname(contact.name).id;
                contactDao.delete(new Contact(contact.name,contact.email,contact.mobile,id));
            }
            return null;
        }
    }

    //Use a return value of type LiveData in your method description,
    // and Room generates all necessary code to update the LiveData when
    // the database is updated.

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }
}
