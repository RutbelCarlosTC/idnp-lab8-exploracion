package com.example.threads;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.threads.fragments.AsyncTaskFragment;
import com.example.threads.fragments.ExecutorFragment;
import com.example.threads.fragments.WorkManagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private AsyncTaskFragment asyncTaskFragment;
    private ExecutorFragment executorFragment;
    private WorkManagerFragment workManagerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

        init();

    }


    private void init() {
        loadFragment(AsyncTaskFragment.newInstance("", ""));
    }

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.asynctask) {
                asyncTaskFragment = AsyncTaskFragment.newInstance("", "");
                loadFragment(asyncTaskFragment);
                return true;
            } else if (item.getItemId() == R.id.executor) {
                executorFragment = ExecutorFragment.newInstance("", "");
                loadFragment(executorFragment);
                return true;
            } else if (item.getItemId() == R.id.work_manager) {
                workManagerFragment = WorkManagerFragment.newInstance("","");
                loadFragment(workManagerFragment);
                return true;
            }
            else {
                return false;
            }
        }
    };

    private void loadFragment(Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
            fragmentTransaction.commit();
        }
    }
}