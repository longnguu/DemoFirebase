package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    DatabaseReference myRef;
    boolean kt=false;
    ConstraintLayout mMainLayout;
    ImageView fingerprint ;
    EditText passLogin,usernameLogin;
    SharedPreferences sharedPreferences;
    CheckBox checkBox;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        firestore= FirebaseFirestore.getInstance();
//        Map<String,Object> users = new HashMap<>();
//        users.put("username","abc1");
//        users.put("password","abc1");
//        firestore.collection("user").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this,"Failue",Toast.LENGTH_SHORT).show();
//            }
//        });
        sharedPreferences = getSharedPreferences("Datalogin",MODE_PRIVATE);
        fingerprint = (ImageView) findViewById(R.id.fingerprint);
        usernameLogin = (EditText) findViewById(R.id.usernamelogin);
        passLogin = (EditText) findViewById(R.id.passLogin);
        checkBox = (CheckBox) findViewById(R.id.rememberCB);
        if (sharedPreferences.getBoolean("checked",false)==true){
            usernameLogin.setText(sharedPreferences.getString("taikhoan",""));
            passLogin.setText(sharedPreferences.getString("matkhau",""));
            checkBox.setChecked(true);
        }else checkBox.setChecked(false);

        ImageView imageView = (ImageView) findViewById(R.id.avtsi);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] hinhAnh = byteArrayOutputStream.toByteArray();
        imagemTratada(hinhAnh);
        User duser = new User("Long","29/06/02","0123456789","levanlong@gmail.com","012345678","admin","admin");
//        duser.setImage(hinhAnh);
        BTP.userList.add(duser);
        Button bttonLogin = (Button) findViewById(R.id.btlogin);
        TextView linkdk = (TextView) findViewById(R.id.linkdk);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users");
        bttonLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                myRef.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if (dataSnapshot.getValue(User.class).getUsername().toString().equals(usernameLogin.getText().toString().trim())) {
                                BTP.user = new User();
                                BTP.user = dataSnapshot.getValue(User.class);
                                kt=true;
                            }
                            System.out.println(dataSnapshot.getValue(User.class));

                        }
                        if (kt==true){
                            Intent intent = new Intent(MainActivity.this, ListSinhVien.class);
                            startActivity(intent);
                            checkRemember();
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công vào tài khoản :" + BTP.user.getUsername(), Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        linkdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });


    }
    private byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }
    private void checkRemember() {

        String us=usernameLogin.getText().toString().trim()+"";
        String pas= passLogin.getText().toString().trim()+"";
        if (checkBox.isChecked()) {
            //   Toast.makeText(Signin.this, "Đã lưu", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("taikhoan", us);
            editor.putString("matkhau", pas);
            editor.putBoolean("checked", true);
            editor.commit();
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Toast.makeText(Signin.this, "không lưu", Toast.LENGTH_SHORT).show();
            editor.putString("taikhoan", us);
            editor.putString("matkhau", pas);
            editor.putBoolean("checked", false);
            editor.commit();
        }

    }
}