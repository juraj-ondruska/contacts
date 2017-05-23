package nxp.cz.mycontacts;

import android.app.ListActivity;
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
            result[i] =
                    Long.toString(700 + random.nextInt(99)) + "-" +
                    Long.toString(100 + random.nextInt(899)) + "-" +
                    Long.toString(100 + random.nextInt(899));
        }
        return result;
    }

}
