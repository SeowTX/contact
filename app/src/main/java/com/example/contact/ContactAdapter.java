package com.example.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.contact.data.Contact;
import com.example.contact.data.ContactDB;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


     private List<Contact> contactList;
     private Context context;
     private ContactDB database;
     private OnItemClickListener mListener;



     public void setContactList(List<Contact>contactList){
         this.contactList = contactList;
         notifyDataSetChanged();
     }


     public interface OnItemClickListener{
         void onItemCLick(int position);

     }

     public void setOnItemClickListener(OnItemClickListener listener){
         mListener = listener;
     }

     //constructor
    public ContactAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout,parent,false);
        ViewHolder v = new ViewHolder(view, mListener); //pass view to viewholder
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

         Contact data = contactList.get(position);

         database = ContactDB.getInstance(context);


        //set data in items
        holder.name.setText(this.contactList.get(position).name);
        holder.contact.setText(this.contactList.get(position).contactNo);


        /*holder.dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact d = contactList.get(holder.getAdapterPosition());

                //delete text from database
                database.contactDao().deleteContact(d);

                //notify when data is delete
                int position = holder.getAdapterPosition();
                contactList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,contactList.size());


            }
        });*/
    }

    @Override
    public int getItemCount() {

        return this.contactList.size();

    }

    public Contact getContact(int position){
         return contactList.get(position);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        //init the item views
        TextView name;
        TextView contact;
        Button dltBtn;
        Button editBtn;

        public ViewHolder(View itemView, final OnItemClickListener listener) {

            super(itemView);

            //get the reference of item views
            name = itemView.findViewById(R.id.tv1);
            contact = itemView.findViewById(R.id.tv2);
            //dltBtn = itemView.findViewById(R.id.button4);
            editBtn = itemView.findViewById(R.id.button);


           itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemCLick(position);
                        }
                    }
                }
            });

        }

    }
}
