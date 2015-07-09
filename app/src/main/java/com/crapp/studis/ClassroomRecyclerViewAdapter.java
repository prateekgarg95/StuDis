package com.crapp.studis;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ClassroomRecyclerViewAdapter extends RecyclerView.Adapter<ClassroomRecyclerViewAdapter.ClassroomViewHolder> {

    public static class ClassroomViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageClassroom;
        TextView txtClassroomName,txtClassroomSubject;

        ClassroomViewHolder(View classroomView){
            super(classroomView);
            cardView=(CardView)classroomView.findViewById(R.id.classroom_cv);
            imageClassroom=(ImageView)classroomView.findViewById(R.id.image_classroom);
            txtClassroomName=(TextView)classroomView.findViewById(R.id.name_classroom);
            txtClassroomSubject=(TextView)classroomView.findViewById(R.id.subject_classroom);
        }

    }

    List<Classroom> classrooms;

    ClassroomRecyclerViewAdapter(List<Classroom> classrooms){
        this.classrooms = classrooms;
    }

    @Override
    public int getItemCount() {
        return classrooms.size();
    }

    @Override
    public ClassroomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_classroom, viewGroup, false);
        return new ClassroomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClassroomViewHolder classroomViewHolder, int i) {
        classroomViewHolder.txtClassroomName.setText(classrooms.get(i).getClassroomName());
        classroomViewHolder.txtClassroomSubject.setText(classrooms.get(i).getClassroomSubject());
        Bitmap bitmap = BitmapFactory.decodeFile(classrooms.get(i).getClassroomImagePath());
        classroomViewHolder.imageClassroom.setImageBitmap(bitmap);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
