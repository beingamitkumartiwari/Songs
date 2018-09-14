package com.crownstack.songs;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongsViewHolder>{

    private Context context;
    private ArrayList<Result> resultArrayList;
    OnSongClick onSongClick;

    public void setOnSongClick(OnSongClick onSongClick)
    {
        this.onSongClick = onSongClick;
    }

    public SongListAdapter(Context context, ArrayList<Result> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @NonNull
    @Override
    public SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item_view,
                parent, false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsViewHolder holder, int position) {
        holder.tvSongName.setText(resultArrayList.get(position).getTrackName());
        holder.tvArtistName.setText(resultArrayList.get(position).getArtistName());
        Picasso.get().load(resultArrayList.get(position).getArtworkUrl60())
                .into(holder.ivSong);
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivSong)
        ImageView ivSong;
        @BindView(R.id.tvSongName)
        TextView tvSongName;
        @BindView(R.id.tvArtistName)
        TextView tvArtistName;

        SongsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onSongClick != null)
                onSongClick.onSongDetail(v, getLayoutPosition());
        }
    }
}
