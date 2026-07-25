package uz.talim.markaz.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.talim.markaz.R;
import uz.talim.markaz.model.Question;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.ContentViewModel;

public class TestListFragment extends Fragment {

    private int topicId;
    private QuestionAdapter adapter;
    private List<Question> currentQuestions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);

        topicId = getArguments() != null ? getArguments().getInt("topic_id") : -1;
        SessionManager session = new SessionManager(requireContext());

        RecyclerView rv = view.findViewById(R.id.rvQuestions);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new QuestionAdapter();
        rv.setAdapter(adapter);

        TextView tvEmpty = view.findViewById(R.id.tvEmptyQuestions);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddQuestion);
        Button btnStart = view.findViewById(R.id.btnStartTest);

        boolean isTeacher = session.isTeacher();

        if (isTeacher) {
            fabAdd.setVisibility(View.VISIBLE);
            fabAdd.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AddQuestionActivity.class);
                intent.putExtra("topic_id", topicId);
                startActivity(intent);
            });
        }

        ContentViewModel viewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        viewModel.getQuestions(topicId).observe(getViewLifecycleOwner(), questions -> {
            currentQuestions = questions;
            adapter.setQuestions(questions);
            boolean hasQuestions = questions != null && !questions.isEmpty();
            tvEmpty.setVisibility(hasQuestions ? View.GONE : View.VISIBLE);
            rv.setVisibility(isTeacher && hasQuestions ? View.VISIBLE : (isTeacher ? View.GONE : View.GONE));
            btnStart.setVisibility(!isTeacher && hasQuestions ? View.VISIBLE : View.GONE);
        });

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), TestActivity.class);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
        });

        return view;
    }
}
