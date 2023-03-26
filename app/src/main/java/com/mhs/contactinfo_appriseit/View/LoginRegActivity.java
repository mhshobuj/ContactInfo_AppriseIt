package com.mhs.contactinfo_appriseit.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firestore.v1.DocumentTransform;
import com.mhs.contactinfo_appriseit.Common.Constants;
import com.mhs.contactinfo_appriseit.Common.PreferenceHelper;
import com.mhs.contactinfo_appriseit.Model.SavedInfoModel;
import com.mhs.contactinfo_appriseit.R;
import com.mhs.contactinfo_appriseit.databinding.ActivityLoginRegBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LoginRegActivity extends AppCompatActivity {
    private ActivityLoginRegBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(LoginRegActivity.this);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(LoginRegActivity.this);

        binding.txtNoAccount.setOnClickListener(view -> {
            binding.layoutReg.setVisibility(View.VISIBLE);
            binding.layoutLogin.setVisibility(View.GONE);
        });

        binding.txtYesAccount.setOnClickListener(view -> {
            binding.layoutReg.setVisibility(View.GONE);
            binding.layoutLogin.setVisibility(View.VISIBLE);
        });

        binding.btnReg.setOnClickListener(view ->
                registration()
        );

        binding.btnLoginIn.setOnClickListener(view ->
                logIn()
        );
    }

    private void logIn() {
        progressDialog.show();
        String email = binding.edtEmail.getText().toString();
        String pass = binding.edtPassword.getText().toString();

        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    startActivity(new Intent(LoginRegActivity.this, MainActivity.class));
                    finish();
                    PreferenceHelper.insertData(this, Constants.USER_ID, authResult.getUser().getUid());
                    progressDialog.cancel();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LoginRegActivity.this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                });
    }

    private void registration() {
        progressDialog.show();
        String name = binding.edtNameReg.getText().toString();
        String phone = binding.edtPhoneReg.getText().toString();
        String email = binding.edtEmailReg.getText().toString();
        String pass = binding.edtPasswordReg.getText().toString();
        long time = getCurrentLongDateTimeAccordingToUTC();

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(authResult -> {
                    progressDialog.cancel();
                    firebaseFirestore.collection("contact_info")
                            .document(FirebaseAuth.getInstance().getUid())
                            .set(new SavedInfoModel(name, phone, email, time));

                    binding.edtNameReg.setText("");
                    binding.edtPhoneReg.setText("");
                    binding.edtEmailReg.setText("");
                    binding.edtPasswordReg.setText("");

                    binding.layoutReg.setVisibility(View.GONE);
                    binding.layoutLogin.setVisibility(View.VISIBLE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(LoginRegActivity.this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                });
    }

    private long getCurrentLongDateTimeAccordingToUTC() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                .getTimeInMillis();
    }
}