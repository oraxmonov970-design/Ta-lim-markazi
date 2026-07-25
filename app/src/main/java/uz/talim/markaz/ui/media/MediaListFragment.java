package uz.talim.markaz.ui.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.talim.markaz.R;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.ContentViewModel;

public class MediaListFragment extends Fragment {

    private int topicId;
    private MediaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_list, container, false);

        topicId = getArguments() != null ? getArguments().getInt("topic_id") : -1;
        SessionManager session = new SessionManager(requireContext());

        RecyclerView rv = view.findViewById(R.id.rvMedia);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView tvEmpty = view.findViewById(R.id.tvEmptyMedia);

        adapter = new MediaAdapter(mediaItem -> {
            Intent intent;
            if ("AUDIO".equals(mediaItem.type)) {
                intent = new Intent(requireContext(), AudioPlayerActivity.class);
            } else {
                intent = new Intent(requireContext(), VideoPlayerActivity.class);
            }
            intent.putExtra("title", mediaItem.title);
            intent.putExtra("file_path", mediaItem.filePath);
            startActivity(intent);
        });
        rv.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAddMedia);
        if (session.isTeacher()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AddMediaActivity.class);
                intent.putExtra("topic_id", topicId);
                startActivity(intent);
            });
        }

        ContentViewModel viewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        viewModel.getMedia(topicId).observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
            tvEmpty.setVisibility(items == null || items.isEmpty() ? View.VISIBLE : View.GONE);
        });

        return view;
    }
}
