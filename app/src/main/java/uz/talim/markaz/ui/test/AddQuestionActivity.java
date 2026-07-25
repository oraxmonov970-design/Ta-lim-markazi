package uz.talim.markaz.ui.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.Question;
import uz.talim.markaz.viewmodel.ContentViewModel;

public class AddQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        int topicId = getIntent().getIntExtra("topic_id", -1);
        ContentViewModel viewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_question);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        EditText etQuestion = findViewById(R.id.etQuestionText);
        EditText etA = findViewById(R.id.etOptionA);
        EditText etB = findViewById(R.id.etOptionB);
        EditText etC = findViewById(R.id.etOptionC);
        EditText etD = findViewById(R.id.etOptionD);
        RadioGroup rgCorrect = findViewById(R.id.rgCorrectAnswer);

        findViewById(R.id.btnSaveQuestion).setOnClickListener(v -> {
            String question = etQuestion.getText().toString().trim();
            String a = etA.getText().toString().trim();
            String b = etB.getText().toString().trim();
            String c = etC.getText().toString().trim();
            String d = etD.getText().toString().trim();

            if (TextUtils.isEmpty(question) || TextUtils.isEmpty(a) || TextUtils.isEmpty(b)
                    || TextUtils.isEmpty(c) || TextUtils.isEmpty(d)) {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show();
                return;
            }

            int checkedId = rgCorrect.getCheckedRadioButtonId();
            String correct;
            if (checkedId == R.id.rbA) correct = "A";
            else if (checkedId == R.id.rbB) correct = "B";
            else if (checkedId == R.id.rbC) correct = "C";
            else if (checkedId == R.id.rbD) correct = "D";
            else {
                Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT).show();
                return;
            }

            Question q = new Question(topicId, question, a, b, c, d, correct);
            viewModel.addQuestion(q);
            Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
