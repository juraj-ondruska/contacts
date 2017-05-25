package nxp.cz.mycontacts;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactsActivity extends ListActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    Random random = new Random();
    ImageButton imageButtonPhoto;
    FileHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        handler = new FileHandler();
        handler.clean();
        List<Contact> list = handler.getContactsFromFile();
        if (list == null || list.isEmpty()) list = loadContacts();

        final ContactsAdapter contactsAdapter = new ContactsAdapter(this, list);
        setListAdapter(contactsAdapter);
        ImageButton addButton = (ImageButton) findViewById(R.id.imageButtonAdd);
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
        imageButtonPhoto = (ImageButton) dialogContent.findViewById(R.id.imageButtonPhoto);
        imageButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });
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
                        Bitmap photo = null;
                        Drawable photoDrawable = imageButtonPhoto.getDrawable();
                        if (photoDrawable instanceof BitmapDrawable) {
                            photo = ((BitmapDrawable) photoDrawable).getBitmap();
                        }
                        Contact contact = new Contact(name, number, address, email, photo);
                        handler.save(contact);
                        ((ContactsAdapter) ContactsActivity.this.getListAdapter()).add(contact);
                    }
                })
                .create()
                .show();
    }

    private List<Contact> loadContacts() {
        int size = 5;
        ArrayList<Contact> result = new ArrayList<>(size);
        Contact contact = null;
        for (int i = 0; i < size; i++) {
            contact = new Contact("Contact " + (i + 1), createRandomNumber(), "", "", null);
            handler.save(contact);
            result.add(contact);
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

    protected void capturePhoto() {
        Intent capturePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (capturePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(capturePhotoIntent, REQUEST_CODE_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            if (imageButtonPhoto != null) {
                imageButtonPhoto.setImageBitmap(imageBitmap);
            }
        }
    }
}
