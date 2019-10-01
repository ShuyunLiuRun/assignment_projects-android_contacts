package comp575.helloworld;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ContactRepository contactRepository;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();
    private ArrayAdapter<Contact> adapter;
    private ListView contactListView;
    //Use ViewModelProviders to associate your ViewModel with your UI controller.
    //private ContactViewModel contactViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setup Adapter
        contactListView = (ListView) findViewById(R.id.contactsListView);
        adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, contacts);
        if(contactListView!= null) {
            contactListView.setAdapter(adapter);

            // onitemclickListener is used to respond to click events on each items in an AdapterView
            contactListView.setOnItemClickListener(this);

            //get a viewmodel from the viewmodelProvider
            //contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

            // create a contact repository and register an observer
            contactRepository = new ContactRepository(this);
            // to observe the table, do something as long as it changed
            contactRepository.getAllContacts().observe(this, new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> updatedContacts) {
                    // update the contacts list when the database changes
                    adapter.clear();
                    adapter.addAll(updatedContacts);
                    adapter.notifyDataSetChanged();
                }
            });
        }
        // Add some Contacts
        if (savedInstanceState != null) {
            for (Parcelable contact : savedInstanceState.getParcelableArrayList(
                    "contacts"))
            {
                contacts.add((Contact) contact);
            }
        }
//        else {
//            contacts.add(new Contact("Joe Bloggs", "joe@bloggs.co.nz",
//                    "021123456"));
//            contacts.add(new Contact("Jane Doe", "jane@doe.co.nz",
//                    "022123456"));
//        }


    }

    public void deleteContact(View view){
        EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();

        EditText emailField = (EditText) findViewById(R.id.email);
        String email = emailField.getText().toString();

        EditText phoneField = (EditText) findViewById(R.id.mobile);
        String phone = phoneField.getText().toString();

        contactListView = (ListView) findViewById(R.id.contactsListView);
        adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, contacts);
        contactListView.setAdapter(adapter);

        contactRepository.delete(new Contact(name, email, phone));
    }

    public void saveContact(View view){
        EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();

        EditText emailField = (EditText) findViewById(R.id.email);
        String email = emailField.getText().toString();

        EditText phoneField = (EditText) findViewById(R.id.mobile);
        String phone = phoneField.getText().toString();

        // show some text in the interface
        Toast.makeText(this, "Saved contact for: " + name +"\n" +"email: "+ email+ "\n" + "mobile: "+phone,
                Toast.LENGTH_SHORT).show();



        // conect to the ListView in "layout/activity_main/activity_main.xml"
        contactListView = (ListView) findViewById(R.id.contactsListView);
        adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1, contacts);
        contactListView.setAdapter(adapter);

        //save to database
        //List<Contact> list = (List<Contact>) contactRepository.getAllContacts();
        ArrayList<String> arr = new ArrayList<String>();
        Boolean addOrNot = true;
        for (Contact c: contacts){
            arr.add(c.name);

        }
        Contact contact = new Contact(name, email, phone);
        if(arr.contains(name)) {
            int a = arr.indexOf(name);
            contactRepository.update(contact);
        }else {
            contactRepository.insert(contact);
        }



        //    old code

        // decide to add new element in contacts or not
        //Boolean addOrNot = true;
        // to get all the names in contacts
        //ArrayList<String> arr = new ArrayList<String>();


        //System.out.println("****************************test****************");
        // check whether the name is in the name array "arr"
//        for (Contact c :contacts ) {
//                System.out.println(c);
//                arr.add(c.name);
//               if( arr.contains(name)){
//
//                   int a = arr.indexOf(name);
//
//                   contacts.get(a).email = email;
//                   contacts.get(a).mobile = phone;
//                    addOrNot = false;
//               }
//        }
//
//        if(addOrNot){
//            // add the new contact input by user
//            contacts.add(new Contact(name, email, phone));
//        }

        //System.out.println("****************************test****************");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact) parent.getAdapter().getItem(position);
        EditText nameField = (EditText) findViewById(R.id.name);
        nameField.setText(contact.name);
        EditText mailField = (EditText) findViewById(R.id.email);
        mailField.setText(contact.email);
        EditText phoneField = (EditText) findViewById(R.id.mobile);
        phoneField.setText(contact.mobile);

        Toast.makeText(parent.getContext(), "Clicked " + contact+"\n"+contact+"'s email is : "+ contact.email
                        +"\n phone number is: "+ contact.mobile,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        savedState.putParcelableArrayList("contacts", contacts);
        super.onSaveInstanceState(savedState);}
}
