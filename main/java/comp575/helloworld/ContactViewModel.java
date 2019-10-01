package comp575.helloworld;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;

    private LiveData<List<Contact>> mAllContacts;

    //Add a constructor that gets a reference to the repository and gets the list of words from the repository.
    public ContactViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getAllContacts();
    }

    //Add a "getter" method for all the contacts
    LiveData<List<Contact>> getAllContacts() { return mAllContacts; }

    //Create a wrapper insert() method that calls the Repository's insert() method.
    // In this way, the implementation of insert() is completely hidden from the UI.
    public void insert(Contact contact) { mRepository.insert(contact); }
}
