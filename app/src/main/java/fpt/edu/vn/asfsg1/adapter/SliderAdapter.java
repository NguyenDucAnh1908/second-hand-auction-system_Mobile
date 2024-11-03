package fpt.edu.vn.asfsg1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.domains.SliderItemsDomain;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{
    private final ArrayList<SliderItemsDomain> sliderItemDomains;
    private final ViewPager2 viewPager2;
    private Context context;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentSize = sliderItemDomains.size();
            if (currentSize > 0) {
                sliderItemDomains.addAll(sliderItemDomains);
                notifyDataSetChanged();
            }
        }
    };

    public SliderAdapter(ArrayList<SliderItemsDomain> sliderItemDomains, ViewPager2 viewPager2){
        this.sliderItemDomains = sliderItemDomains;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.slide_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {
        holder.setImage(sliderItemDomains.get(position));
        if(position == sliderItemDomains.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItemDomains.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
        void setImage(SliderItemsDomain sliderItemsDomain){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop());
            Glide.with(context)
                    .load(sliderItemsDomain.getUrl())
                    .apply(requestOptions)
                    .into(imageView);
        }
    }
}
