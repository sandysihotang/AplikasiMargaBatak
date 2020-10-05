package stud11417031.develops.projecttestwithlaravel.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stud11417031.develops.projecttestwithlaravel.Activity.Users;
import stud11417031.develops.projecttestwithlaravel.Connection.RetrofitClient;
import stud11417031.develops.projecttestwithlaravel.R;
import stud11417031.develops.projecttestwithlaravel.adapters.UsersAdapters;
import stud11417031.develops.projecttestwithlaravel.model.Marga;
import stud11417031.develops.projecttestwithlaravel.model.ResponseMarga;
import stud11417031.develops.projecttestwithlaravel.model.ResponseUser;
import stud11417031.develops.projecttestwithlaravel.model.User;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Marga> userList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_barangjual,container,false);
        viewRecycler(view);
        return view;
    }
    private void viewRecycler(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Call<ResponseMarga> call = RetrofitClient.getInstance().getApi().getAllMarga();
        call.enqueue(new Callback<ResponseMarga>() {
            @Override
            public void onResponse(Call<ResponseMarga> call, Response<ResponseMarga> response) {
                userList = response.body().getMargas();
                UsersAdapters usersAdapters = new UsersAdapters(getActivity(), userList);
                recyclerView.setAdapter(usersAdapters);
            }

            @Override
            public void onFailure(Call<ResponseMarga> call, Throwable t) {

            }
        });
    }
}
