package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class ManagementActivity extends AppCompatActivity {
    private RecyclerView rvStudents;
    private List<Student> listStudents;
    private Button btnNew, btnSearch;
    private EditText edtSearch;
    private DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        db=new DBHandler(this);
        listStudents=db.getAllStudents();
        rvStudents= findViewById(R.id.rv_students);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvStudents.setLayoutManager(layoutManager);
        rvStudents.setHasFixedSize(true);
        rvStudents.setAdapter(new RecyclerAdapter(this, listStudents));

        btnNew= findViewById(R.id.btn_newStudent);
        edtSearch= findViewById(R.id.edt_search);
        btnSearch= findViewById(R.id.btn_search);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManagementActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("student", edtSearch.getText().toString());
                listStudents=db.searchStudent(edtSearch.getText().toString());
                rvStudents.setAdapter(new RecyclerAdapter(ManagementActivity.this, listStudents));
            }
        });
    }

}