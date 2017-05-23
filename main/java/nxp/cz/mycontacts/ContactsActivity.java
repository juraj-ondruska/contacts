package nxp.cz.mycontacts;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactsActivity extends ListActivity {
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        final ArrayAdapter<String> valuesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loadNumbers());
        setListAdapter(valuesAdapter);
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewContactDialog();
            }
        });
    }

    private void openNewContactDialog() {
        final EditText editTextNumber = new EditText(this);
        editTextNumber.setInputType(InputType.TYPE_CLASS_PHONE);
        new AlertDialog.Builder(this)
                .setTitle("Add new contact")
                .setView(editTextNumber)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((ArrayAdapter) ContactsActivity.this.getListAdapter()).add(editTextNumber.getText());
                    }
                })
                .create()
                .show();
    }

    private List<String> loadNumbers() {
        int size = 5;
        ArrayList<String> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(createRandomNumber());
        }
        return result;
    }

    private String createRandomNumber() {
        return
                Long.toString(700 + random.nextInt(99)) + "-" +
                Long.toString(100 + random.nextInt(899)) + "-" +
                Long.toString(100 + random.nextInt(899));
    }

}
