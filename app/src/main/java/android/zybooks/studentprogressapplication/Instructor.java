package android.zybooks.studentprogressapplication;

import android.provider.ContactsContract;

public class Instructor {
    private String mName;
    private String mPhone;
    private String mEmail;

    Instructor(String name, String phone, String email) {
        mName = name;
        mPhone = phone;
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }
}
