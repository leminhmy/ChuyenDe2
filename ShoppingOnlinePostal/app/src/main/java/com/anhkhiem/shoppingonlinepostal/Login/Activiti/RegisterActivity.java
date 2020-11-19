package com.anhkhiem.shoppingonlinepostal.Login.Activiti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.anhkhiem.shoppingonlinepostal.Login.data_models.User;
import com.anhkhiem.shoppingonlinepostal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail,edtPass,edtPassRepeat,edtName,edtArea,edtPhone;
    Button btnSignUp,btnLogin;
    Uri imgUri;
    ImageView imgUser;
    FirebaseAuth mAuth;
    String imageStoregeURL;
    StorageReference mImageStorage;
    String id;
    DatabaseReference mRef;
    StorageTask uploadTask;
    static final int IMAGE_REQUEST = 1;
    Spinner spnRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        anhXa();

        btnLogin.setOnClickListener(onLoginClick);
        imgUser.setOnClickListener(onClickImage);
        btnSignUp.setOnClickListener(onClickRegister);

        mRef = FirebaseDatabase.getInstance().getReference("Users");
        mImageStorage = FirebaseStorage.getInstance().getReference("ImageUser");
    }

    private View.OnClickListener onClickRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String email = edtEmail.getText().toString().trim();
            final String password = edtPass.getText().toString().trim();
            String passwordrepeat = edtPassRepeat.getText().toString().trim();
            final String name = edtName.getText().toString().trim();
            final String area = edtArea.getText().toString().trim();
            final String phone = edtPhone.getText().toString().trim();

            final StorageReference fileRef;
            if (email.isEmpty()) {
                edtEmail.setError("Vui Lòng Nhập Email");
                edtEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Email Không Hợp Lệ");
                edtEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                edtPass.setError("Vui Lòng Nhập Mật Khẩu");
                edtPass.requestFocus();
                return;
            }
            if (password.length() < 6) {
                edtPass.setError("Mật khẩu phải từ 6 kí tự trở lên");
                edtPass.requestFocus();
                return;
            }
            if ((passwordrepeat.compareTo(password)) != 0) {
                edtPassRepeat.setError("Mật khẩu xác nhận không đúng");
                edtPassRepeat.requestFocus();
                return;
            }
            if (name.isEmpty()) {
                edtName.setError("Vui lòng nhập tên");
                edtName.requestFocus();
                return;
            }
            if (area.isEmpty()) {
                edtArea.setError("Vui lòng nhập địa chỉ");
                edtArea.requestFocus();
                return;
            }
            if (phone.length() != 10) {
                edtPhone.setError("Số điện thoại không hợp lệ");
                edtPhone.requestFocus();
                return;
            }
            if (imgUri == null) {
                Toast.makeText(RegisterActivity.this, "Vui lòng chọn ảnh người dùng", Toast.LENGTH_SHORT).show();
                return;
            } else {
                fileRef = mImageStorage.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));
            }
            uploadTask = fileRef.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri downloadUrl = uriTask.getResult();
                    imageStoregeURL = downloadUrl.toString();
                    final String role = spnRole.getSelectedItem().toString().trim();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                id = uID;
                                final User users = new User(uID,email,password,name,area,phone,role,imageStoregeURL);
                                users.setPassword(password);
                                mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getApplicationContext(), "Email đăng kí đã được sử dụng", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            });
        }
    };

    private View.OnClickListener onClickImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST);
        }
    };

    private void anhXa() {
        edtEmail = findViewById(R.id.edtEmailSignUp);
        edtPass = findViewById(R.id.edtPassSignup);
        edtPassRepeat = findViewById(R.id.edtPassUserRepeat);
        edtName = findViewById(R.id.edtNameUser);
        edtArea = findViewById(R.id.edtAreaUser);
        edtPhone = findViewById(R.id.edtUserPhone);
        imgUser = findViewById(R.id.imgUserSignup);
        btnLogin = findViewById(R.id.btnUserLogin);
        btnSignUp = findViewById(R.id.btnUserRegister);
        spnRole = findViewById(R.id.spnRole);
    }

    private View.OnClickListener onLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            Toast.makeText(RegisterActivity.this, "Go to Login", Toast.LENGTH_SHORT).show();
        }
    };

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imgUri = data.getData();
            Picasso.get().load(imgUri).into(imgUser);
        }
    }
}