package com.example.libit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.example.libit.network.ImageRequester;
import com.example.libit.network.NetworkService;
import com.example.libit.network.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle mToggle;
    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageRequester imageRequester = ImageRequester.getInstance();
        NetworkImageView editImage = findViewById(R.id.chooseImage);
        imageRequester.setImageFromUrl(editImage, NetworkService.getBaseUrl() + "/images/testAvatarHen.jpg");
        sessionManager = SessionManager.getInstance(this);
        textView = findViewById(R.id.textMainActivity);

        if (sessionManager.isLogged) {
            String message = "Hello, dear friend! Nice to see you again!";
            textView.setText(message);
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.bringToFront();

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onNavItemSelected(item);
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onNavItemSelected(MenuItem menuItem) {
        Intent intent;
        Toast toast;
        // Handle item selection
        switch (menuItem.getItemId()) {
            case R.id.main:
                drawerLayout.closeDrawers();
                break;
            case R.id.categories_v1:
                intent = new Intent(this, CategoriesActivity.class);
                startActivity(intent);
                break;
            case R.id.categories_v2:
                intent = new Intent(this, CategoriesRecyclerActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                toast = Toast.makeText(getApplicationContext(),
                        "You have clicked SEARCH", Toast.LENGTH_LONG);
                toast.show();
                drawerLayout.closeDrawers();
                break;
            case R.id.login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.profile:
                if (!sessionManager.isLogged) {
                    intent = new Intent(this, LoginActivity.class);
                } else {
                    intent = new Intent(this, ProfileActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.logout:
                sessionManager = SessionManager.getInstance(this);
                String message = "See you later!";
                textView.setText(message);
                sessionManager.logout();
                toast = Toast.makeText(getApplicationContext(),
                        "You have been signed out successfully", Toast.LENGTH_LONG);
                toast.show();
                drawerLayout.closeDrawers();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}