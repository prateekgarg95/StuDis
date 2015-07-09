package com.crapp.studis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";

    private List<Classroom> classroomList;

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private ClassroomRecyclerViewAdapter classroomRecyclerViewAdapter;

    private Toolbar toolbar;

    private CollapsingToolbarLayout collapsingToolbar;

    private int mutedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupToolbar();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_classroom_image);
        String imageDirectory = Environment.getExternalStorageDirectory().getPath() + "/TestApp";
        File directory = new File(imageDirectory);
        directory.mkdirs();
        File file = new File(imageDirectory,"defaultClassroomImage.png");
        if (!file.exists()){
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                outputStream.flush();
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
        }


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        classroomList = new ArrayList<>();

        classroomList.add(new Classroom("3rd Eye", "Physics"));
        classroomList.add(new Classroom("3rd Eye","Chemistry"));
        classroomList.add(new Classroom("3rd Eye","Mathematics"));
        classroomList.add(new Classroom("3rd Eye","Biology"));

        classroomRecyclerViewAdapter = new ClassroomRecyclerViewAdapter(classroomList);
        recyclerView.setAdapter(classroomRecyclerViewAdapter);
    }

    private void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Dashboard");
        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        collapsingToolbar.setContentScrimColor(DashboardActivity.this.getResources().getColor(R.color.classroom_blue));

        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.backdrop_blue);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });*/
    }
}
