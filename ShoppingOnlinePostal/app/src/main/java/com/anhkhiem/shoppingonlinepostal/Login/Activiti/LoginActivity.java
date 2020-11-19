package com.anhkhiem.shoppingonlinepostal.Login.Activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anhkhiem.shoppingonlinepostal.Cache.SaveCache;
import com.anhkhiem.shoppingonlinepostal.Login.data_models.User;
import com.anhkhiem.shoppingonlinepostal.Main.MainActivity;
import com.anhkhiem.shoppingonlinepostal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnDangNhap,btnDangKy;
    private FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;
    ProgressDialog dialog;
    Intent intent;
    EditText edtEmail;
    TextInputLayout edtPassword;
    TextView tvQuenPass,tvMess;
    String ADMIN = "Admin";
    String Manager = "Manager";
    String USER = "User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        intent = getIntent();
        anhXa();
        dialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        btnDangNhap.setOnClickListener(onClickLogin);
    }

    private View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.setMessage("Đang kiểm tra tài khoản");
            dialog.show();
            if (edtEmail.getText().toString().isEmpty() && edtPassword.getEditText().getText().toString().isEmpty()){
                dialog.dismiss();
                tvMess.setText("Vui lòng nhập email và password");
            } else if (edtEmail.getText().toString().isEmpty() || edtPassword.getEditText().getText().toString().isEmpty()){
                dialog.dismiss();
                tvMess.setText("Vui lòng nhập email và password");
            } else {
                String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getEditText().getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String id =user.getUid();

                            DatabaseReference rootRef =FirebaseDatabase.getInstance().getReference();
                            DatabaseReference uIDRef = rootRef.child("Users").child(id);

                            uIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    intent = new Intent();
                                    dialog.dismiss();
                                    User user1 = snapshot.getValue(User.class);
//                                    roles = user1.getRoles();
                                    SaveCache.getInstance(LoginActivity.this).setUser(user1);
                                    SaveCache.getInstance(LoginActivity.this).setLogin(true);
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(intent);
                                    finish();
//                                    if (roles.equals(ADMIN)){
//                                        intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                        startActivity(intent);
//                                        finish();
//                                    } else if (roles.equals(Manager)){
//                                        intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                        startActivity(intent);
//                                        finish();
//                                    } else if (roles.equals(USER)){
//                                        intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                        startActivity(intent);
//                                        finish();
//                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            edtEmail.setText("");
                            edtPassword.getEditText().setText("");
                        } else {
                            dialog.dismiss();
                            tvMess.setText("Sai Email hoặc Password");
                        }
                    }
                });
            }
        }
    };

    private void anhXa() {
        btnDangNhap = findViewById(R.id.btnLogin);
        btnDangKy = findViewById(R.id.btnCusRegisterlayoutlogin);
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPassword);
        tvQuenPass = findViewById(R.id.txtQuenMK);;
        tvMess = findViewById(R.id.tvMesseage);
    }


}