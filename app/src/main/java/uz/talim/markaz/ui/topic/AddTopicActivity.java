package uz.talim.markaz.ui.topic;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.Topic;
import uz.talim.markaz.viewmodel.SubjectViewModel;

public class AddTopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);

        int subjectId = getIntent().getIntExtra(TopicListActivity.EXTRA_SUBJECT_ID, -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_topic);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        EditText etName = findViewById(R.id.etTopicName);
        EditText etDesc = findViewById(R.id.etTopicDesc);

        SubjectViewModel viewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        findViewById(R.id.btnSaveTopic).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show();
                return;
            }

            Topic topic = new Topic(subjectId, name, desc, System.currentTimeMillis());
            viewModel.addTopic(topic);
            Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
