package com.example.airscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airscan.Models.ViewAsset;

import java.util.ArrayList;

public class ViewAssetAdapter extends RecyclerView.Adapter<ViewAssetAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ViewAsset> mViewAssetList;

    public ViewAssetAdapter(Context mContext, ArrayList<ViewAsset> mViewAssetList) {
        this.mContext = mContext;
        this.mViewAssetList = mViewAssetList;
    }

    @NonNull
    @Override
    public ViewAssetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.view_asset_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAssetAdapter.ViewHolder holder, int position) {
        ViewAsset asset = mViewAssetList.get(position);
        holder.mText1.setText(asset.getmName());
        holder.mText2.setText(asset.getMvalue());
        holder.mImg.setImageResource(asset.getmImage());
    }

    @Override
    public int getItemCount() {
        return mViewAssetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImg;
        private TextView mText1;
        private TextView mText2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.image);
            mText1 = itemView.findViewById(R.id.text1);
            mText2 = itemView.findViewById(R.id.text2);
        }
    }
}
