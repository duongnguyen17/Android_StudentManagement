package com.example.studentmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<DataViewHolder> {

    List<Student> listStudents;
    Context context;

    public RecyclerAdapter(Context context, List<Student> listStudents) {
        this.context = context;
        this.listStudents = listStudents;
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studentView;
        studentView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new DataViewHolder(studentView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        int id=listStudents.get(position).getId();
        String name=listStudents.get(position).getName();
        String address=listStudents.get(position).getAddress();
        String email = listStudents.get(position).getEmail();
        holder.tvName.setText(name);
        holder.tvAddress.setText(address);
        holder.tvEmail.setText(email);
        holder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view) {
                Log.e("itemOnClick", "item was clicked!");
                Intent intent = new Intent(view.getContext(), InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", name);
                bundle.putString("address", address);
                bundle.putString("email", email);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listStudents==null? 0: listStudents.size();
    }
}

class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvName;
    TextView tvAddress;
    TextView tvEmail;

    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public DataViewHolder(View studentView) {
        super(studentView);
        tvName = studentView.findViewById(R.id.tv_name);
        tvAddress= studentView.findViewById(R.id.tv_address);
        tvEmail = studentView.findViewById(R.id.tv_email);
        studentView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        itemClick.onClick(v);
    }

}