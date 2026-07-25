package uz.talim.markaz.ui.subject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uz.talim.markaz.R;
import uz.talim.markaz.ui.topic.TopicListActivity;
import uz.talim.markaz.viewmodel.SubjectViewModel;

public class SubjectsFragment extends Fragment {

    private SubjectViewModel viewModel;
    private SubjectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subjects, container, false);

        RecyclerView rv = view.findViewById(R.id.rvSubjects);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new SubjectAdapter(subject -> {
            Intent intent = new Intent(requireContext(), TopicListActivity.class);
            intent.putExtra("subject_id", subject.id);
            intent.putExtra("subject_name", subject.name);
            startActivity(intent);
        });
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        viewModel.getSubjects().observe(getViewLifecycleOwner(), adapter::setSubjects);

        return view;
    }
}
