package com.mhs.contactinfo_appriseit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhs.contactinfo_appriseit.Common.ConvertTimeToTimeAgo;
import com.mhs.contactinfo_appriseit.Model.SavedInfoModel;
import com.mhs.contactinfo_appriseit.R;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<SavedInfoModel> savedInfoModels;

    public ContactListAdapter(Context context, ArrayList<SavedInfoModel> savedInfoModels) {
        this.context = context;
        this.savedInfoModels = savedInfoModels;
    }

    @NonNull
    @Override
    public ContactListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.MyViewHolder holder, int position) {
        holder.txtName.setText(new StringBuilder(" ").append(savedInfoModels.get(position).getName()));
        holder.txtContact.setText(new StringBuilder(" ").append(savedInfoModels.get(position).getPhone()));
        holder.txtLastLoginTime.setText(new StringBuilder(" ").append(ConvertTimeToTimeAgo.getTimeAgo(savedInfoModels.get(position).getCreate_time())));
    }

    @Override
    public int getItemCount() {
        return savedInfoModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtContact, txtLastLoginTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtContact = itemView.findViewById(R.id.txtContact);
            txtLastLoginTime = itemView.findViewById(R.id.txtLastLoginTime);
        }
    }
}
