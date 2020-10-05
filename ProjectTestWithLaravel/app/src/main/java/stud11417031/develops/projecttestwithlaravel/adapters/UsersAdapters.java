package stud11417031.develops.projecttestwithlaravel.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stud11417031.develops.projecttestwithlaravel.Activity.DetailMarga;
import stud11417031.develops.projecttestwithlaravel.Connection.RetrofitClient;
import stud11417031.develops.projecttestwithlaravel.R;
import stud11417031.develops.projecttestwithlaravel.model.Marga;

public class UsersAdapters extends RecyclerView.Adapter<UsersAdapters.UsersViewHolder> {
    private Context ctx;
    private List<Marga> listUser;

    public UsersAdapters(Context ctx, List<Marga> listUser) {
        this.ctx = ctx;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recyclerview_users, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder usersViewHolder, int i) {
        Marga user = listUser.get(i);
        usersViewHolder.marga.setText(user.getNama());
        usersViewHolder.cerita.setText(user.getDeskripsi());
        Call<ResponseBody> call1= RetrofitClient.getInstance().getApi().getUrl(user.getImage());
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Bitmap bitmap= BitmapFactory.decodeStream(response.body().byteStream());
                        usersViewHolder.imageView.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView marga,cerita;
        ImageView imageView;

        public UsersViewHolder(@NonNull final View itemView) {
            super(itemView);
            marga = (TextView) itemView.findViewById(R.id.marga);
            cerita = (TextView) itemView.findViewById(R.id.cerita);
            imageView=(ImageView)itemView.findViewById(R.id.imageCard);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Intent intent=new Intent(itemView.getContext(), DetailMarga.class);
                    intent.putExtra("id",listUser.get(pos).getId());
                    ctx.startActivity(intent);
                }
            });

        }
    }
}
