package nxp.cz.mycontacts;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Random;

public class ContactsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> valuesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, loadNumbers());
        setListAdapter(valuesAdapter);
    }

    private String[] loadNumbers() {
        Random random = new Random();
        String[] result = new String[50];
        for (int i = 0; i < result.length; i++) {
            result[i] = Long.toString(100000000 + random.nextInt(99999999));
        }
        return result;
    }

}
