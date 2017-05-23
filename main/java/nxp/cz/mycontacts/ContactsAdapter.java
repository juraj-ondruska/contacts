package nxp.cz.mycontacts;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Juraj Ondruska on 5/23/2017.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {
    LayoutInflater layoutInflater;

    public ContactsAdapter(@NonNull Context context, @NonNull List<Contact> objects) {
        super(context, R.layout.row_contact, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contact = getItem(position);
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_contact, null);
        }
        TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
        TextView textViewNumber = (TextView) view.findViewById(R.id.textViewNumber);
        textViewName.setText(contact.getName());
        textViewNumber.setText(contact.getNumber());
        return view;
    }
}
