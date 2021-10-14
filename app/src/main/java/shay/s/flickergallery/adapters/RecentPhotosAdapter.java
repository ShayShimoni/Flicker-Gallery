package shay.s.flickergallery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import shay.s.flickergallery.databinding.ListItemRecentPhotoBinding;
import shay.s.flickergallery.model.FlickerPhoto;

public class RecentPhotosAdapter extends RecyclerView.Adapter<RecentPhotosAdapter.RecentPhotoViewHolder> {
    private ArrayList<FlickerPhoto> flickerPhotos;

    public RecentPhotosAdapter(ArrayList<FlickerPhoto> flickerPhotos) {
        this.flickerPhotos = flickerPhotos;
    }

    @NonNull
    @Override
    public RecentPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemRecentPhotoBinding binding = ListItemRecentPhotoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new RecentPhotoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPhotoViewHolder holder, int position) {
        FlickerPhoto flickerPhoto = flickerPhotos.get(position);
        Picasso.get().load(flickerPhoto.getUrlStr()).into(holder.binding.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return flickerPhotos.size();
    }

    static class RecentPhotoViewHolder extends RecyclerView.ViewHolder {
        ListItemRecentPhotoBinding binding;

        public RecentPhotoViewHolder(@NonNull ListItemRecentPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
