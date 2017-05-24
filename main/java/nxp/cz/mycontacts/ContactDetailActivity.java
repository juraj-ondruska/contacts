package nxp.cz.mycontacts;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Juraj Ondruska on 5/24/2017.
 */

public class ContactDetailActivity extends Activity {
    public static String KEY_CONTACT = "contact";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setValues();
    }

    private void setValues() {
        Contact contact = (Contact) getIntent().getExtras().get(KEY_CONTACT);
        ((TextView) findViewById(R.id.textViewName)).setText(contact.getName());
        ((TextView) findViewById(R.id.textViewNumber)).setText(contact.getNumber());
        ((TextView) findViewById(R.id.textViewAddress)).setText(contact.getAddress());
        ((TextView) findViewById(R.id.textViewEmail)).setText(contact.getEmail());
        if (contact.getPhoto() != null) {
            ((ImageView) findViewById(R.id.imageViewPhoto)).setImageBitmap(contact.getPhoto());
        }
    }

}
