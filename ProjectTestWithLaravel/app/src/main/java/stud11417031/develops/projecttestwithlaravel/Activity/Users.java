package stud11417031.develops.projecttestwithlaravel.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import stud11417031.develops.projecttestwithlaravel.R;
import stud11417031.develops.projecttestwithlaravel.fragment.HomeFragment;
import stud11417031.develops.projecttestwithlaravel.fragment.SearchFragment;
import stud11417031.develops.projecttestwithlaravel.storage.SharedPrefManager;

public class Users extends AppCompatActivity {

    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (SharedPrefManager.LOGGED_IN) {
            inflater.inflate(R.menu.menulogin, menu);
        } else {
            inflater.inflate(R.menu.mymenu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.signup:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.createmarga:
                startActivity(new Intent(getApplicationContext(), TambahMarga.class));
                break;
            case R.id.logout:
                SharedPrefManager.LOGGED_IN=false;
                startActivity(new Intent(getApplicationContext(),Users.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.informasi:
                            selectedFragment = new SearchFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
