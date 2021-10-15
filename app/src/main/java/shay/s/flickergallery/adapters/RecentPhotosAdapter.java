package shay.s.flickergallery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import shay.s.flickergallery.databinding.ListItemRecentPhotoBinding;
import shay.s.flickergallery.model.FlickrPhoto;

public class RecentPhotosAdapter extends RecyclerView.Adapter<RecentPhotosAdapter.RecentPhotoViewHolder> {
    private final ArrayList<FlickrPhoto> flickrPhotos;
    private final MutableLiveData<FlickrPhoto> selectedPhotoLiveData;

    public RecentPhotosAdapter(ArrayList<FlickrPhoto> flickrPhotos, MutableLiveData<FlickrPhoto> selectedPhotoLiveData) {
        this.flickrPhotos = flickrPhotos;
        this.selectedPhotoLiveData = selectedPhotoLiveData;
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
        FlickrPhoto flickrPhoto = flickrPhotos.get(position);
        Picasso.get().load(flickrPhoto.getUrlStr()).into(holder.binding.ivPhoto);
        holder.itemView.setOnClickListener(v -> selectedPhotoLiveData.postValue(flickrPhoto));
    }

    @Override
    public int getItemCount() {
        return flickrPhotos.size();
    }

    public void addToList(List<FlickrPhoto> newPhotos) {
        flickrPhotos.addAll(newPhotos);
    }

    static class RecentPhotoViewHolder extends RecyclerView.ViewHolder {
        ListItemRecentPhotoBinding binding;

        public RecentPhotoViewHolder(@NonNull ListItemRecentPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
