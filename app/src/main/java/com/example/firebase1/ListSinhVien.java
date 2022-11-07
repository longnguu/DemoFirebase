package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSinhVien extends AppCompatActivity {

    List<User> sinhViens=new ArrayList<User>();
    String idTruyen;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    ArrayAdapter<User> lopArrayAdapter;
    TextView tv1,tv2,tv3;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sinh_vien);

        ListView lvLop = (ListView) findViewById(R.id.lvSV);
        registerForContextMenu(lvLop);
//        myRef.child("users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                sinhViens=new ArrayList<User>();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    sinhViens.add(dataSnapshot.getValue(User.class));
//                    System.out.println(sinhViens.size()+"SIZE");
//                }
//                lopArrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        myRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                sinhViens.add(snapshot.getValue(User.class));
                    System.out.println(sinhViens.size()+"SIZE");
                lopArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lopArrayAdapter = new ArrayAdapter<>(ListSinhVien.this, android.R.layout.simple_list_item_1,sinhViens);
        lvLop.setAdapter(lopArrayAdapter);
        NhanDuLieu();
        lopArrayAdapter.notifyDataSetChanged();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulongclik,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        int id = item.getItemId();
        switch (id){
            case R.id.menuSua:
                SuaSV(pos);
                System.out.println(sinhViens.get(pos).getUsername().toString());
                break;
            case R.id.menuDel:
                DeleteSV(pos);
                break;
        }
        return true;
    }

    private void DeleteSV(int pos) {
        myRef.child("users").child(sinhViens.get(pos).getUsername().toString().trim()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ListSinhVien.this,"Đã xong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SuaSV(int pos) {
        Dialog dialog = new Dialog(ListSinhVien.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogsinhviencustom);
        dialog.show();
        TextView tv1 = (TextView) dialog.findViewById(R.id.isIDC);
        TextView tv2 = (TextView) dialog.findViewById(R.id.isNameC);
        TextView tv3 = (TextView) dialog.findViewById(R.id.isIDCate);
        Button btok = (Button) dialog.findViewById(R.id.btn_okC);
        Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelC);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User us = new User(tv1.getText().toString(),tv2.getText().toString(),tv3.getText().toString());
                myRef.child("users").child(sinhViens.get(pos).getUsername().toString().trim()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ListSinhVien.this,"Đã xong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myRef.child("users").child(tv1.getText().toString().trim()).setValue(us);
                lopArrayAdapter.notifyDataSetChanged();
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.headmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuadd:
                addLop();
                break;
            case R.id.menuexit:
                break;
        }
        return true;
    }
    private void addLop() {
        Dialog dialog = new Dialog(ListSinhVien.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogsinhviencustom);
        dialog.show();
        TextView tv1 = (TextView) dialog.findViewById(R.id.isIDC);
        TextView tv2 = (TextView) dialog.findViewById(R.id.isNameC);
        TextView tv3 = (TextView) dialog.findViewById(R.id.isIDCate);
        Button btok = (Button) dialog.findViewById(R.id.btn_okC);
        Button btcancel = (Button) dialog.findViewById(R.id.btn_cancelC);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User us = new User(tv1.getText().toString(),tv2.getText().toString(),tv3.getText().toString());
                myRef.child("users").child(tv1.getText().toString().trim()).setValue(us);
                lopArrayAdapter.notifyDataSetChanged();
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    private void NhanDuLieu() {

    }

}