package shay.s.flickergallery.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shay.s.flickergallery.R;
import shay.s.flickergallery.adapters.RecentPhotosAdapter;
import shay.s.flickergallery.databinding.HomeFragmentBinding;
import shay.s.flickergallery.model.FlickrPhoto;
import shay.s.flickergallery.repository.Repository;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private HomeViewModel mHomeViewModel;
    private int totalPages;
    private int page;
    private int oldSize;

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
        binding.progressBar.setVisibility(View.VISIBLE);

        //Init the RecycleView list.
        mHomeViewModel.getPhotosAndInfoLiveData().observe(getViewLifecycleOwner(), photosAndInfo -> {
            totalPages = photosAndInfo.getTotalPages();
            page = photosAndInfo.getPage();

            RecentPhotosAdapter recentPhotosAdapter = new RecentPhotosAdapter(photosAndInfo.getPhotos(), mHomeViewModel.getSelectedPhotoLiveData());
            binding.rvRecentPhotos.setAdapter(recentPhotosAdapter);
            binding.rvRecentPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            binding.progressBar.setVisibility(View.GONE);

            addScrollBehavior();
            getNextPage(recentPhotosAdapter);
            getSelectedPhotoAndNavigate();
        });

    }

    //Checks if the user scrolled down, reached the end of the list, and request next page.
    private void addScrollBehavior() {
        binding.rvRecentPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && !binding.rvRecentPhotos.canScrollVertically(RecyclerView.VERTICAL)) {
                    if (page <= totalPages) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        mHomeViewModel.getNextPage(Repository.METHOD_GET_RECENT, ++page);
                    }
                }
            }
        });
    }

    //Makes the list with continues scroll to the next page.
    private void getNextPage(RecentPhotosAdapter recentPhotosAdapter) {
        mHomeViewModel.getNextPageLiveData().observe(getViewLifecycleOwner(), photosAndInfoNextPage -> {
            int newSize = recentPhotosAdapter.getItemCount();
            recentPhotosAdapter.addToList(photosAndInfoNextPage.getPhotos());
            recentPhotosAdapter.notifyItemRangeChanged(oldSize, newSize);
            oldSize = newSize;
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void getSelectedPhotoAndNavigate() {
        mHomeViewModel.getSelectedPhotoLiveData().observe(getViewLifecycleOwner(), flickrPhoto -> {
            if (flickrPhoto == null)
                return;
            mHomeViewModel.changePhotoToLargeSize(flickrPhoto);
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.bundle_key_photo), flickrPhoto);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_homeFragment_to_flickrPhotoDisplayFragment, bundle);
            mHomeViewModel.getSelectedPhotoLiveData().postValue(null);
        });
    }
}