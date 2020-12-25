package com.example.frame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class  MainActivity extends AppCompatActivity {
    List<Cell> allFilesPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String TAG = "showImages()";
        Log.e(TAG, "MainActivity --- onCreate top of method ---");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        // previosuly activity main and fragment_home

        /* the following code is for the navigation bar */
        /*THE LAST LINE SETS WHAT FRAGMENT WILL BE THE FIRST FRAGMENT AFTER LOGIN :D*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        // RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        // recyclerView.setHasFixedSize(true);
        // RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        // recyclerView.setLayoutManager(layoutManager);
        // ArrayList<Cell> cells = prepareData();
        // MyAdapter adapter = new MyAdapter(getApplicationContext(), cells);
        // recyclerView.setAdapter(adapter);
        // ASKS FOR PERMISSIONS IF NEEDED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            //SHOW THE IMAGES

            TAG = "showImages()";
            Log.e(TAG, "MainActivity --- onCreate before showImages ---");

            showImages();

            Log.e(TAG, "MainActivity --- onCreate after showImages ---");
        }
    }

    //////----------------------------------------------------- PHOTOS WORK---------------------------------------------


    //FOR STORAGE PERMISSION


    //METHOD FOR SHOWING THE IMAGES ON SCREEN
    private void showImages() {

        String TAG = "showImages()";
        Log.e(TAG, "MainActivity \t\t--- showImages start of showImages() ---");

        //THIS IS FOR FINDING THE FOLDER WITH IMAGES
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
        allFilesPaths = new ArrayList<>();
        allFilesPaths = listAllFiles(path);
       // System.out.println("Start Test");

        Log.e(TAG, "MainActivity \t\t--- path=\""+path+"\" ---");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);

        recyclerView.setHasFixedSize(true);
        //MAKE THE LIST WITH 3 COLUMNS
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Cell> cells = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(adapter);

        //LinearLayoutManager manager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(manager);

        Log.e(TAG, "MainActivity \t\t--- showImages end of showImages() ---");
    }

    private ArrayList<Cell> prepareData() {
        ArrayList<Cell> allImages = new ArrayList<>();
        for (Cell c : allFilesPaths) {
            Cell cell = new Cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return allImages;
    }

    //METHOD FOR LOADING ALL FILES FROM THE FOLDER
    private List<Cell> listAllFiles(String pathName) {
        List<Cell> allFiles = new ArrayList<>();
        File file = new File(pathName);
        File[] files = file.listFiles();
        if(files != null) {
            for (File f : files) {
                Cell cell = new Cell();
                cell.setTitle(f.getName());
                cell.setPath(f.getAbsolutePath());
                allFiles.add(cell);
            }
        }
        return allFiles;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //SHOWS THE IMAGES
                showImages();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    Fragment selectedFragment = null;


// previously was null ^^
                    switch (item.getItemId()) {
                        case R.id.home:

//                    openHome();
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.albums:
//                    openAlbum();
                            selectedFragment = new AlbumsFragment();
                            break;

                        case R.id.groups:
//                    openGroups();
                            selectedFragment = new GroupsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();

                    return true;
                }
            };


}


