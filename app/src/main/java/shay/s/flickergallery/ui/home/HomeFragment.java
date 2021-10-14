package shay.s.flickergallery.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shay.s.flickergallery.adapters.RecentPhotosAdapter;
import shay.s.flickergallery.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private HomeViewModel mHomeViewModel;
    private int page;
    private int totalPages;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mHomeViewModel.getPhotosAndInfoLiveData().observe(getViewLifecycleOwner(), photosAndInfo -> {
            page = photosAndInfo.getPage();
            totalPages = photosAndInfo.getPages();

            binding.rvRecentPhotos.setAdapter(new RecentPhotosAdapter(photosAndInfo.getPhotos()));
            binding.rvRecentPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        });

    }

}