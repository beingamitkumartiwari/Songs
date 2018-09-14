package com.crownstack.songs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SongsListFragment extends Fragment {

    public static final String TAG = "ProductList";
    @BindView(R.id.rvSongList)
    RecyclerView rvSongList;
    RecyclerView.LayoutManager layoutManager;
    SongListAdapter songListAdapter;
    ApiService apiService;
    Call<SongListPojo> songListPojoCall;
    ArrayList<Result> resultArrayList = new ArrayList<>();

    public SongsListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs_list, container, false);
        ButterKnife.bind(this, view);
        apiService = RetrofitService.createService(ApiService.class);
        setupRecyclerView();
        getSongList();
        return view;
    }

    private void setupRecyclerView() {

        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        if (songListAdapter == null) {
            DividerItemDecoration itemDecoration = new DividerItemDecoration(rvSongList
                    .getContext(), DividerItemDecoration.VERTICAL);
            songListAdapter = new SongListAdapter(getActivity(), resultArrayList);
            rvSongList.setLayoutManager(layoutManager);
            rvSongList.addItemDecoration(itemDecoration);
            rvSongList.setAdapter(songListAdapter);
            rvSongList.setItemAnimator(new DefaultItemAnimator());
            rvSongList.setNestedScrollingEnabled(true);
        } else {
            songListAdapter.notifyDataSetChanged();
        }
        setupOnClick();
    }


    private void getSongList() {
        songListPojoCall = apiService.getAllSongs("Michael+jackson");
        songListPojoCall.enqueue(new Callback<SongListPojo>() {
            @Override
            public void onResponse(@NonNull Call<SongListPojo> call,
                                   @NonNull Response<SongListPojo> response) {
                if (response.code() == 200) {
                    resultArrayList.clear();
                    SongListPojo songListPojo = response.body();
                    assert songListPojo != null;
                    List<Result> resultList = songListPojo.getResults();
                    resultArrayList.addAll(resultList);
                    songListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SongListPojo> call,
                                  @NonNull Throwable t) {

            }
        });
    }

    private void setupOnClick() {
        if (songListAdapter != null) {
            songListAdapter.setOnSongClick(new OnSongClick() {
                @Override
                public void onSongDetail(View view, int position) {
                    Result result = resultArrayList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("collectionName", result.getCollectionName());
                    bundle.putString("artistName", result.getArtistName());
                    bundle.putString("trackName", result.getTrackName());
                    bundle.putString("wrapperType", result.getWrapperType());
                    bundle.putString("collectionCensoredName", result.getCollectionCensoredName());
                    bundle.putString("trackCensoredName", result.getTrackCensoredName());
                    bundle.putString("artworkUrl100", result.getArtworkUrl100());
                    bundle.putString("artistViewUrl", result.getArtistViewUrl());
                    bundle.putString("collectionPrice", String.valueOf(result.getCollectionPrice()));
                    bundle.putString("trackPrice", String.valueOf(result.getTrackPrice()));
                    bundle.putString("releaseDate", result.getReleaseDate());
                    bundle.putString("country", result.getCountry());
                    bundle.putString("currency", result.getCurrency());
                    assert getFragmentManager() != null;
                    SongFragment songFragment = new SongFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    songFragment.setArguments(bundle);
                    transaction.add(R.id.fragment_container, songFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }

}
