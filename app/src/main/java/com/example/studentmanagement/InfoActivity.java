package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class InfoActivity extends AppCompatActivity {

    private int id;
    private String name, address, email;
    private EditText edtName, edtAddress, edtEmail;
    private Button btnAdd, btnUpdate, btnDelete;
    private Bundle extras = null;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        db=new DBHandler(this);
        edtName=findViewById(R.id.edt_name);
        edtAddress=findViewById(R.id.edt_address);
        edtEmail=findViewById(R.id.edt_email);
        btnAdd=findViewById(R.id.btn_add);
        btnDelete=findViewById(R.id.btn_delete);
        btnUpdate=findViewById(R.id.btn_update);

        extras= getIntent().getExtras();
        if(extras!=null){
            id = extras.getInt("id");
            name = extras.getString("name");
            address = extras.getString("address");
            email = extras.getString("email");
            edtName.setText(name);
            edtAddress.setText(address);
            edtEmail.setText(email);
            btnAdd.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }
        else{
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(edtName.getText().toString(), edtAddress.getText().toString(), edtEmail.getText().toString());
                db.addStudent(student);
                Intent intent=new Intent(InfoActivity.this, ManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student=new Student(id, String.valueOf(edtName.getText()), String.valueOf(edtAddress.getText()), String.valueOf(edtEmail.getText()));
                db.updateStudent(student);
                Intent intent=new Intent(InfoActivity.this, ManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student=new Student(id, name, address, email);
                db.deleteStudent(student);
                Intent intent=new Intent(InfoActivity.this, ManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
