package stud11417031.develops.projecttestwithlaravel.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import stud11417031.develops.projecttestwithlaravel.R;


public class SearchFragment extends Fragment {
    TextView desk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        desk=(TextView)view.findViewById(R.id.desk);
        desk.setText("Aplikasi untuk menambah informasi merngenai Marga seseorang, dan User dapat menambah Marga dan cerita dari Marga yang yang di tambahkan.");
        return view;
    }
}
