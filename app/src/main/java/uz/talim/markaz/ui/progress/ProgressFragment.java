package uz.talim.markaz.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uz.talim.markaz.R;
import uz.talim.markaz.model.TopicProgress;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.ProgressViewModel;

public class ProgressFragment extends Fragment {

    private ResultAdapter adapter;
    private ProgressBar progressOverall;
    private TextView tvCompletedCount;
    private int completedCount = 0;
    private int totalTopics = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        SessionManager session = new SessionManager(requireContext());

        progressOverall = view.findViewById(R.id.progressOverall);
        tvCompletedCount = view.findViewById(R.id.tvCompletedCount);

        RecyclerView rv = view.findViewById(R.id.rvResults);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ResultAdapter();
        rv.setAdapter(adapter);

        TextView tvEmpty = view.findViewById(R.id.tvEmptyResults);

        ProgressViewModel viewModel = new ViewModelProvider(this).get(ProgressViewModel.class);

        viewModel.getResults(session.getUserId()).observe(getViewLifecycleOwner(), results -> {
            adapter.setResults(results);
            tvEmpty.setVisibility(results == null || results.isEmpty() ? View.VISIBLE : View.GONE);
        });

        viewModel.getProgress(session.getUserId()).observe(getViewLifecycleOwner(), this::onProgressUpdated);

        viewModel.totalTopics.observe(getViewLifecycleOwner(), total -> {
            totalTopics = total;
            updateProgressUI();
        });

        return view;
    }

    private void onProgressUpdated(List<TopicProgress> progressList) {
        completedCount = 0;
        if (progressList != null) {
            for (TopicProgress p : progressList) {
                if (p.completed) completedCount++;
            }
        }
        updateProgressUI();
    }

    private void updateProgressUI() {
        progressOverall.setMax(Math.max(totalTopics, 1));
        progressOverall.setProgress(completedCount);
        tvCompletedCount.setText(completedCount + " / " + totalTopics + " mavzu tugatildi");
    }
}
