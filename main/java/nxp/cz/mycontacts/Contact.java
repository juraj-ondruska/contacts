package nxp.cz.mycontacts;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by Juraj Ondruska on 5/23/2017.
 */

public class Contact implements Parcelable {
    private String name;
    private String number;
    private String address;
    private String email;
    private Bitmap photo;
    private String id;

    public Contact(String name, String number, String address, String email, Bitmap photo) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.email = email;
        this.photo = photo;
        this.id = UUID.randomUUID().toString();
    }

    protected Contact(Parcel in) {
        name = in.readString();
        number = in.readString();
        address = in.readString();
        email = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        id = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeParcelable(photo, 0);
        dest.writeString(id);
    }

    @Override
    public String toString() {
        String bmp = photo == null ? "null" : id + ".png";
        return name + FileHandler.SEPARATOR + number + FileHandler.SEPARATOR + address + FileHandler.SEPARATOR + email + FileHandler.SEPARATOR + bmp;
    }
}
