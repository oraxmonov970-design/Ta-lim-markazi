package uz.talim.markaz.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.talim.markaz.R;
import uz.talim.markaz.model.TopicProgress;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.ProgressViewModel;
import uz.talim.markaz.viewmodel.SubjectViewModel;

public class TopicListActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT_ID = "subject_id";
    public static final String EXTRA_SUBJECT_NAME = "subject_name";

    private int subjectId;
    private TopicAdapter adapter;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        session = new SessionManager(this);
        subjectId = getIntent().getIntExtra(EXTRA_SUBJECT_ID, -1);
        String subjectName = getIntent().getStringExtra(EXTRA_SUBJECT_NAME);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(subjectName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rvTopics);
        rv.setLayoutManager(new LinearLayoutManager(this));

        TextView tvEmpty = findViewById(R.id.tvEmpty);

        adapter = new TopicAdapter(topic -> {
            Intent intent = new Intent(this, TopicDetailActivity.class);
            intent.putExtra("topic_id", topic.id);
            intent.putExtra("topic_name", topic.name);
            startActivity(intent);
        });
        rv.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAddTopic);
        if (session.isTeacher()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddTopicActivity.class);
                intent.putExtra(EXTRA_SUBJECT_ID, subjectId);
                startActivity(intent);
            });
        }

        SubjectViewModel subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        subjectViewModel.getTopics(subjectId).observe(this, topics -> {
            adapter.setTopics(topics);
            tvEmpty.setVisibility(topics == null || topics.isEmpty() ? View.VISIBLE : View.GONE);
        });

        if (!session.isTeacher()) {
            ProgressViewModel progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);
            progressViewModel.getProgress(session.getUserId()).observe(this, this::onProgressLoaded);
        }
    }

    private void onProgressLoaded(List<TopicProgress> progressList) {
        Set<Integer> completed = new HashSet<>();
        if (progressList != null) {
            for (TopicProgress p : progressList) {
                if (p.completed) completed.add(p.topicId);
            }
        }
        adapter.setCompletedIds(completed);
    }
}
