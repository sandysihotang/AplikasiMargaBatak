package stud11417031.develops.projecttestwithlaravel.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stud11417031.develops.projecttestwithlaravel.Connection.RetrofitClient;
import stud11417031.develops.projecttestwithlaravel.R;
import stud11417031.develops.projecttestwithlaravel.model.Marga;
import stud11417031.develops.projecttestwithlaravel.model.ResponseMargaDetail;

public class DetailMarga extends AppCompatActivity {
    TextView marga, deskrispi;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marga);
        setData();
        int id = getIntent().getIntExtra("id", 0);
        Call<ResponseMargaDetail> call = RetrofitClient.getInstance().getApi().getMarga(id);
        call.enqueue(new Callback<ResponseMargaDetail>() {
            @Override
            public void onResponse(Call<ResponseMargaDetail> call, Response<ResponseMargaDetail> response) {
                Marga margas = response.body().getMarga();
                marga.setText(margas.getNama());
                deskrispi.setText(margas.getDeskripsi());
                Call<ResponseBody> call1 = RetrofitClient.getInstance().getApi().getUrl(margas.getImage());
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseMargaDetail> call, Throwable t) {

            }
        });
    }

    private void setData() {
        marga = (TextView) findViewById(R.id.judul);
        deskrispi = (TextView) findViewById(R.id.cerita);
        imageView = (ImageView) findViewById(R.id.imagesView);
    }
}
