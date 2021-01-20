package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.contact.data.Contact;
import com.example.contact.data.ContactDB;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddContactActivity extends AppCompatActivity {


    //public static final String CONTACT_ADDED = "newContact";
    private TextInputEditText name;
    private TextInputEditText contactNo;
    private TextInputLayout name1;
    private TextInputLayout contactNo1;
    boolean boo = false;
    Contact c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        name1 = findViewById(R.id.tx3);
        contactNo1 = findViewById(R.id.tx4);
        name = findViewById(R.id.et3);
        contactNo = findViewById(R.id.et4);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(boo){
                validateName();
                }
            }
        });

        contactNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(boo) {
                    validateContact();
                }
            }
        });


        Button btn = findViewById(R.id.button3);

        String cBtn = null;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            c = new Contact();
            c = b.getParcelable("contact");
            cBtn = b.getString("p");
        }

        if (cBtn != null) {

            name.setText(c.getName());
            contactNo.setText(c.getContactNo());

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boo = true;
                    if (validate()) {
                        editContact(c.getId(), name.getText().toString(), contactNo.getText().toString());
                    }
                }

            });

        } else {


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boo = true;
                    if (validate()) {

                        addContact(name.getText().toString(), contactNo.getText().toString());

                    }
                }
            });
        }

    }

    private void editContact(int id, String name, String contact) {
        ContactDB db = ContactDB.getInstance(this.getApplicationContext());

        Contact b = new Contact();
        b.id = id;
        b.name = name;
        b.contactNo = contact;
        db.contactDao().updateContact(b);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void addContact(String name, String contactNo) {
        ContactDB db = ContactDB.getInstance(this.getApplicationContext());

        Contact contact = new Contact();
        contact.name = name;
        contact.contactNo = contactNo;
        db.contactDao().insertContact(contact);

        finish();
    }

    private boolean validateName() {
        String username = name1.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            name1.setError("Field cannot be empty");
            return false;
        }
        if (username.length() > 50) {
            name1.setError("Username too long");
            return false;
        }
        if (username.length() < 5) {
            name1.setError("Username too short");
            return false;
        } else {
            name1.setError(null);
            return true;
        }
    }

    private boolean validateContact() {
        String contact = contactNo1.getEditText().getText().toString().trim();

        if (contact.isEmpty()) {
            contactNo1.setError("Fields cannot be empty");
            return false;
        } else {
            contactNo1.setError(null);
            return true;
        }
    }

    private boolean validate() {

        Boolean b = true;

        if (!validateName()) {
            b = false;
        }

        if (!validateContact()) {
            b = false;
        }

        return b;
    }
}


