package com.example.project_who_yak;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SLiderAdapter_Camara_scan extends RecyclerView.Adapter<SLiderAdapter_Camara_scan.SliderViewHolder> {

    private static final String TAG = "SliderAdapter";

    private Context mContext;
    private List<String> sliderItems;

    public SLiderAdapter_Camara_scan(Context context, List<String> sliderItems){
        mContext = context;
        this.sliderItems = sliderItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new SLiderAdapter_Camara_scan(SlideItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public int getItemCount(){
        return sliderItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder Holder, int position){
        Holder.bind(sliderItems.get(position));
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{
        private SlideItemBinding mBinding;

        public SliderViewHolder(SlideItemBinding binding){
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(String sliderItem) {
            try {
                Glide.with(mContext).load(sliderItem).into(mBinding.imageSlider);
            }catch (Exception e){
                Log.d(TAG,"ERROR : " + e.getMessage());
            }
        }
    }
}
