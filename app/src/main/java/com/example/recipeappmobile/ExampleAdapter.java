package com.example.recipeappmobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter <ExampleAdapter.ExampleViewHolder> {
    private  Context mContext;
    private  ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;
    private Object ArrayList;

    public void filterList(ArrayList<ExampleItem> filteredList) {
            ArrayList = filteredList;
            notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList){
        mContext = context;
        mExampleList = exampleList;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String title = currentItem.getTitle();
        int durationCount = currentItem.getDuration();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewDuration.setText("Duration of cooking" + durationCount);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewDuration;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle= itemView.findViewById(R.id.text_view_title);
            mTextViewDuration = itemView.findViewById(R.id.text_view_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}