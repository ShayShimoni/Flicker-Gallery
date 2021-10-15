package shay.s.flickergallery.ui.display;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import shay.s.flickergallery.R;
import shay.s.flickergallery.databinding.FragmentFlickrPhotoDisplayBinding;
import shay.s.flickergallery.model.FlickrPhoto;


public class FlickrPhotoDisplayFragment extends Fragment {
    FragmentFlickrPhotoDisplayBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlickrPhotoDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FlickrPhoto flickrPhoto = requireArguments().getParcelable(getString(R.string.bundle_key_photo));
        Picasso.get().load(flickrPhoto.getUrlStr()).into(binding.ivDisplayedPhoto);
    }
}