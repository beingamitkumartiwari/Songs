package com.crownstack.songs;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment {


    @BindView(R.id.tvSongName)
    TextView tvSongName;
    @BindView(R.id.tvArtistName)
    TextView tvArtistName;
    @BindView(R.id.tvCollectionName)
    TextView tvCollectionName;
    @BindView(R.id.tvTypeName)
    TextView tvTypeName;
    @BindView(R.id.tvTrackPrice)
    TextView tvTrackPrice;
    @BindView(R.id.tvCollectionPriceName)
    TextView tvCollectionPriceName;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @BindView(R.id.ivArtist)
    ImageView ivArtist;


    public SongFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        assert bundle != null;
        String dateTime = bundle.getString("releaseDate");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = sdf.parse(dateTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String formatedDate = cal.get(Calendar.DATE) +
                    "/" + (cal.get(Calendar.MONTH) + 1) + "/" +
                    cal.get(Calendar.YEAR);
            tvReleaseDate.setText(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.get().load(bundle.getString("artworkUrl100"))
                .into(ivArtist);
        tvSongName.setText(bundle.getString("trackName"));
        tvArtistName.setText(bundle.getString("artistName"));
        tvCollectionName.setText(bundle.getString("collectionName"));
        tvTypeName.setText(bundle.getString("wrapperType"));
        tvTrackPrice.setText(bundle.getString("trackPrice") + "$");
        tvCollectionPriceName.setText(bundle.getString("collectionPrice") + "$");
        final String artistViewUrl = bundle.getString("artistViewUrl");
        ivArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("artistUrl", artistViewUrl);
                assert getFragmentManager() != null;
                WebFragment webFragment = new WebFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                webFragment.setArguments(bundle);
                transaction.add(R.id.fragment_container, webFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
