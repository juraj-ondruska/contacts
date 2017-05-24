package nxp.cz.mycontacts;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactsActivity extends ListActivity {
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        final ContactsAdapter contactsAdapter = new ContactsAdapter(this, loadContacts());
        setListAdapter(contactsAdapter);
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewContactDialog();
            }
        });
    }

    private void openNewContactDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogContent = layoutInflater.inflate(R.layout.dialog_new_contact, null);
        final EditText editTextNumber = (EditText) dialogContent.findViewById(R.id.editTextNumber);
        final EditText editTextName = (EditText) dialogContent.findViewById(R.id.editTextName);
        final EditText editTextAddress = (EditText) dialogContent.findViewById(R.id.editTextAddress);
        final EditText editTextEmail = (EditText) dialogContent.findViewById(R.id.editTextEmail);
        new AlertDialog.Builder(this)
                .setTitle("Add new contact")
                .setView(dialogContent)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String number = editTextNumber.getText().toString();
                        String address = editTextAddress.getText().toString();
                        String email = editTextEmail.getText().toString();
                        Contact contact = new Contact(name, number, address, email);
                        ((ContactsAdapter) ContactsActivity.this.getListAdapter()).add(contact);
                    }
                })
                .create()
                .show();
    }

    private List<Contact> loadContacts() {
        int size = 5;
        ArrayList<Contact> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(new Contact("Contact " + (i + 1), createRandomNumber(), "", ""));
        }
        return result;
    }

    private String createRandomNumber() {
        return
                Long.toString(700 + random.nextInt(99)) + "-" +
                Long.toString(100 + random.nextInt(899)) + "-" +
                Long.toString(100 + random.nextInt(899));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Contact contact = (Contact) getListAdapter().getItem(position);
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra(ContactDetailActivity.KEY_CONTACT, contact);
        startActivity(intent);
    }
}
