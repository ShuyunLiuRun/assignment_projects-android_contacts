package comp575.helloworld;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Contact implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String email;
    public String mobile;

    public Contact(String name, String email, String mobile) {

        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
    @Ignore
    public Contact(String name, String email, String mobile, Long id){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.id = id;
    }


    public Contact(Parcel in) {
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String toString() {
        return name;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(mobile);
    }
}