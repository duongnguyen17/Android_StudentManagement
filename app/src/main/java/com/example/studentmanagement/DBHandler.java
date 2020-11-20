package com.example.studentmanagement;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String TABLE_CONTACTS="student_contacts";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_ADDRESS="address";
    public static final String COLUMN_EMAIL="email";

    private static final String DATABASE_NAME="students.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_DATABASE="create table " + TABLE_CONTACTS+"( " + COLUMN_ID
            +" integer primary key autoincrement, "
            + COLUMN_NAME +" text, "
            + COLUMN_ADDRESS + " text, "
            + COLUMN_EMAIL + " text" + ")";

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHandler.class.getName(),"Upgrading database from version "+ oldVersion + "to "+ newVersion + "which will destroy all old data.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    //thêm sinh viên
    public void addStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_EMAIL, student.getEmail());
        db.insert(TABLE_CONTACTS,null,values);
        db.close();
    }


    //cập nhật thông tin sinh viên
    public void updateStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_EMAIL, student.getEmail());
        Log.e("update", student.getName());
        db.update(TABLE_CONTACTS, values, COLUMN_ID + "=?",new String[]{String.valueOf(student.getId())});
        db.close();
    }

    //xóa thông tin 1 sinh viên
    public void deleteStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID+"=" + String.valueOf(student.getId()),null);
        db.close();
    }

    //trả lại thông tin sinh viên qua id
    public Student getStudent(int id){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_CONTACTS, new String[] {COLUMN_ID, COLUMN_NAME, COLUMN_ADDRESS, COLUMN_EMAIL},
                COLUMN_ID+"= ?", new String[]{String.valueOf(id)},null,null, null, null);
        if(cursor !=null){
            cursor.moveToFirst();
        }
        Student student = new Student(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return student;
    }

    //in thông tin tất cả sinh viên
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<Student>();
        String query = "SELECT * FROM "+ TABLE_CONTACTS;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setAddress(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                students.add(student);
            }
            while (cursor.moveToNext());
        }
        return students;
    }

    //tìm kiếm sinh viên trong CSDL
    public List<Student> searchStudent(String str){
        List<Student> students = new ArrayList<Student>();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE "
                +COLUMN_NAME+ " LIKE '%" + str + "%' OR " + COLUMN_ADDRESS+ " LIKE '%"+ str +"%'";
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setAddress(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                students.add(student);
            }
            while(cursor.moveToNext());
        }
        return students;
    }
}
