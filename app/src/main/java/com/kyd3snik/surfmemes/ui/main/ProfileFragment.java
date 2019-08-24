package com.kyd3snik.surfmemes.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.kyd3snik.surfmemes.R;
import com.kyd3snik.surfmemes.adapters.MemesAdapter;
import com.kyd3snik.surfmemes.api.NetworkService;
import com.kyd3snik.surfmemes.models.Meme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements Callback<List<Meme>> {
    private TextView nameTv;
    private TextView descriptionTv;
    private ImageButton menuIb;
    private RecyclerView favoriteMemesRv;
    private MemesAdapter memesAdapter;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initViews(View view) {
        nameTv = view.findViewById(R.id.username_tv);
        descriptionTv = view.findViewById(R.id.user_description_tv);
        menuIb = view.findViewById(R.id.menu_ib);

        favoriteMemesRv = view.findViewById(R.id.memes_rv);
        favoriteMemesRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        nameTv.setText("Some name");
        descriptionTv.setText("Very very very very very very very very very very long long long long description");

        final PopupMenu pm = new PopupMenu(
                new ContextThemeWrapper(getActivity(), R.style.PopupMenu), menuIb);
        pm.getMenuInflater().inflate(R.menu.profile_menu, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), String.valueOf(item.getItemId()), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        menuIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.show();
            }
        });
        showMemes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    void setMemes(List<Meme> memes) {
        if (memesAdapter == null) {
            memesAdapter = new MemesAdapter(memes);
            favoriteMemesRv.setAdapter(memesAdapter);
        } else {
            memesAdapter.setMemes(memes);
            memesAdapter.notifyDataSetChanged();
        }
    }

    void showMemes() {

        NetworkService.getInstance().getMemeApi().getMemes().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Meme>> call, Response<List<Meme>> response) {
        if (response.isSuccessful())
            setMemes(response.body());
    }

    @Override
    public void onFailure(Call<List<Meme>> call, Throwable t) {

    }
}
