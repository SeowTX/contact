package com.example.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.contact.data.Contact;
import com.example.contact.data.ContactDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Contact> contactList= new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*contactList.add(new Contact("Jack","011-123456789"));
        contactList.add(new Contact("Jackson","012-123456789"));
        contactList.add(new Contact("Ace","013-123456789"));
        contactList.add(new Contact("Shaw","014-123456789"));
        contactList.add(new Contact("May","015-123456789"));*/

        //database = ContactDB.getInstance(this);

        //contactList = database.contactDao().getContactList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new ContactAdapter(this);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {

                Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
                intent.putExtra("contact",adapter.getContact(position));
                startActivity(intent);
            }
        });


        loadContactList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater =getMenuInflater();
        //inflater.inflate(R.menu.menu,menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int i = item.getItemId();

        if(i == R.id.add){
            startActivityForResult(new Intent(MainActivity.this, AddContactActivity.class),100);
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadContactList(){
        ContactDB db = ContactDB.getInstance(this.getApplicationContext());
        List<Contact> contactList = db.contactDao().getContactList();
        adapter.setContactList(contactList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100){
            loadContactList();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}