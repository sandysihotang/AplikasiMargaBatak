package stud11417031.develops.projecttestwithlaravel.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stud11417031.develops.projecttestwithlaravel.Connection.RetrofitClient;
import stud11417031.develops.projecttestwithlaravel.R;
import stud11417031.develops.projecttestwithlaravel.model.ResponeForSignUp;

public class MainActivity extends AppCompatActivity {
    EditText username, password, firstname, lastname;
    Button signUp;
    TextView logIn;
    private static final int REQUEST_CODE_GALERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTextAll();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponeForSignUp> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(RequestBody.create(MultipartBody.FORM, username.getText().toString()),
                                RequestBody.create(MultipartBody.FORM, password.getText().toString()),
                                RequestBody.create(MultipartBody.FORM, firstname.getText().toString()),
                                RequestBody.create(MultipartBody.FORM, lastname.getText().toString()));
                call.enqueue(new Callback<ResponeForSignUp>() {
                    @Override
                    public void onResponse(Call<ResponeForSignUp> call, Response<ResponeForSignUp> response) {
                        ResponeForSignUp responeForSignUp = response.body();
                        if (response.code() == 201) {
                            Toast.makeText(MainActivity.this, responeForSignUp.getMsg(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponeForSignUp> call, Throwable t) {

                    }
                });
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTextAll() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        signUp = (Button) findViewById(R.id.signup);
        logIn = (TextView) findViewById(R.id.login);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
