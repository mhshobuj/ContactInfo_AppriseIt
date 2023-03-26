package com.mhs.contactinfo_appriseit.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mhs.contactinfo_appriseit.Adapter.ContactListAdapter;
import com.mhs.contactinfo_appriseit.Common.Constants;
import com.mhs.contactinfo_appriseit.Common.PreferenceHelper;
import com.mhs.contactinfo_appriseit.Model.SavedInfoModel;
import com.mhs.contactinfo_appriseit.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<SavedInfoModel> savedInfoModels;
    private ContactListAdapter contactListAdapter;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(MainActivity.this);
        savedInfoModels = new ArrayList<>();
        binding.recyclerViewContact.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getContactInfo();

        binding.txtLogout.setOnClickListener(view -> {
            PreferenceHelper.deleteData(MainActivity.this, Constants.USER_ID);
            startActivity(new Intent(MainActivity.this, LoginRegActivity.class));
            finish();
        });

    }

    private void getContactInfo() {
        progressDialog.show();
        firebaseFirestore.collection("contact_info").orderBy("create_time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", "" + error);
                            progressDialog.dismiss();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                savedInfoModels.add(dc.getDocument().toObject(SavedInfoModel.class));
                            }

                            contactListAdapter = new ContactListAdapter(MainActivity.this, savedInfoModels);
                            binding.recyclerViewContact.setHasFixedSize(true);
                            binding.recyclerViewContact.setAdapter(contactListAdapter);
                            contactListAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}