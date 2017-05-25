package nxp.cz.mycontacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal_zan on 24.5.17.
 */

public class FileHandler {

    public static final String SEPARATOR = ";";

    File dir;
    File thumbDir;
    File file;

    public FileHandler() {
        dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyContacts");
        thumbDir = new File(dir.getAbsolutePath() + "/thumbs");
        file = new File(dir.getAbsolutePath() + "/contacts.txt");
    }

    public List<Contact> getContactsFromFile() {
        ArrayList<Contact> result = new ArrayList<Contact>();

        if (file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                while (line != null) {
                    Contact contact = parse(line);
                    result.add(contact);
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    private Contact parse(String line) {
        if (line == null || line.isEmpty()) return null;
        String[] parts = line.split(SEPARATOR);
        Bitmap bitmap = null;

        if (!parts[4].equals("null")) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(thumbDir.getAbsolutePath() + "/" + parts[4], options);
        }

        return new Contact(parts[0], parts[1], parts[2], parts[3], bitmap);
    }

    public void save(Contact contact) {
        if (!thumbDir.exists()) thumbDir.mkdirs();
        FileOutputStream thumbFos = null;
        File thumb = null;
        FileOutputStream contactsFos = null;
        try {
            contactsFos = new FileOutputStream(file, true);
            contactsFos.write((contact.toString() + "\n").getBytes());
            if (contact.getPhoto() != null) {
                thumb = new File(thumbDir.getAbsoluteFile() + "/" + contact.getId() + ".png");
                thumbFos = new FileOutputStream(thumb);
                contact.getPhoto().compress(Bitmap.CompressFormat.PNG, 100, thumbFos);
                thumbFos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (contactsFos != null) contactsFos.close();
                if (thumbFos != null) thumbFos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clean() {

    }

}
