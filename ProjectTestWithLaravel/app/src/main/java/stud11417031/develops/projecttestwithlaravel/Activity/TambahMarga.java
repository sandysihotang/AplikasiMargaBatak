package stud11417031.develops.projecttestwithlaravel.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stud11417031.develops.projecttestwithlaravel.Connection.RetrofitClient;
import stud11417031.develops.projecttestwithlaravel.R;
import stud11417031.develops.projecttestwithlaravel.model.MargaResponse;

public class TambahMarga extends AppCompatActivity {
    EditText marga, deskripsi;
    ImageView imageView;
    private static final int REQUEST_CODE_GALERY = 999;
    String part_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_marga);
        setField();
        findViewById(R.id.chooseImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });
        findViewById(R.id.tambah).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile = new File(part_image);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("foto", imageFile.getName(), requestBody);
                Call<MargaResponse> call= RetrofitClient.getInstance().getApi().sendMarga(
                        partImage,
                        RequestBody.create(MultipartBody.FORM, marga.getText().toString()),
                        RequestBody.create(MultipartBody.FORM, deskripsi.getText().toString())
                );
                call.enqueue(new Callback<MargaResponse>() {
                    @Override
                    public void onResponse(Call<MargaResponse> call, Response<MargaResponse> response) {
                        MargaResponse responeForSignUp = response.body();
                        if (response.code() == 201) {
                            Toast.makeText(getApplicationContext(), responeForSignUp.getMessage(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Users.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MargaResponse> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void setField() {
        marga = (EditText) findViewById(R.id.marga);
        deskripsi = (EditText) findViewById(R.id.cerita);
        imageView = (ImageView) findViewById(R.id.imagesView);
    }

    private void pilihGambar() {
        ActivityCompat.requestPermissions(
                TambahMarga.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_GALERY
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grandResult) {
        if (requestCode == REQUEST_CODE_GALERY) {
            if (grandResult.length > 0 && grandResult[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access Galery", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permission, grandResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                String[] imageProjection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, imageProjection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageProjection[0]);
                    part_image = cursor.getString(indexImage);
                    if (part_image != null) {
                        File image = new File(part_image);
                        imageView.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
