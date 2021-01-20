package com.example.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contact.data.Contact;
import com.example.contact.data.ContactDB;

public class ContactDetailActivity extends AppCompatActivity {

    EditText name;
    EditText contactNo;

    TextView bName,date;

    Contact c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        c = new Contact();

        Bundle b = getIntent().getExtras();
        c = b.getParcelable("contact");

        date = findViewById(R.id.textView5);
        bName =findViewById(R.id.textView3);
        name =  findViewById(R.id.et1);
        contactNo = findViewById(R.id.et2);

        contactNo.setEnabled(false);

        date.setText("19/1/2021");
        bName.setText(c.getName());

        name.setText(c.getName());
        contactNo.setText(c.getContactNo());

        Button btn = findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteContact(c);
            }
        });

        Button editBtn = findViewById(R.id.button);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btn = "EditBtn";

                Intent intent = new Intent(ContactDetailActivity.this, AddContactActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("contact",c);
                b.putString("p",btn);
                intent.putExtras(b);
                startActivity(intent);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return true;
    }



    private void deleteContact(Contact c){

        ContactDB db = ContactDB.getInstance(this.getApplicationContext());
        db.contactDao().deleteContact(c);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }


}