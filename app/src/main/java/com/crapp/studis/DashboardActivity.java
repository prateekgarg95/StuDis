package com.crapp.studis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private TextView txtName,txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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

        setupNavigationView();


        recyclerView = (RecyclerView)findViewById(R.id.classrooms_recycler_view);

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

    private void setupNavigationView(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        Toast.makeText(DashboardActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.settings:
                        Toast.makeText(DashboardActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(DashboardActivity.this, "Fishy", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        txtName = (TextView)findViewById(R.id.name_nav);
        txtName.setText("Prateek Garg");
        txtEmail = (TextView)findViewById(R.id.email_nav);
        txtEmail.setText("prateekgarg95@gmail.com");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(Gravity.LEFT))
        {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }




}
