package uz.talim.markaz.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.Question;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.ContentViewModel;
import uz.talim.markaz.viewmodel.TestViewModel;

public class TestActivity extends AppCompatActivity {

    private final List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private int topicId;

    private TextView tvQuestionNumber, tvQuestionText;
    private RadioGroup rgOptions;
    private RadioButton rbA, rbB, rbC, rbD;
    private ProgressBar progressBar;
    private android.widget.Button btnNext;

    private TestViewModel testViewModel;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        topicId = getIntent().getIntExtra("topic_id", -1);
        session = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.tab_test);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        progressBar = findViewById(R.id.progressBar);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestionText = findViewById(R.id.tvQuestionText);
        rgOptions = findViewById(R.id.rgOptions);
        rbA = findViewById(R.id.rbOptA);
        rbB = findViewById(R.id.rbOptB);
        rbC = findViewById(R.id.rbOptC);
        rbD = findViewById(R.id.rbOptD);
        btnNext = findViewById(R.id.btnNext);

        testViewModel = new ViewModelProvider(this).get(TestViewModel.class);

        ContentViewModel contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        contentViewModel.getQuestions(topicId).observe(this, list -> {
            if (list != null && !list.isEmpty() && questions.isEmpty()) {
                questions.addAll(list);
                showQuestion();
            }
        });

        btnNext.setOnClickListener(v -> handleNext());

        testViewModel.resultData.observe(this, result -> {
            Intent intent = new Intent(this, TestResultActivity.class);
            intent.putExtra("score", result[0]);
            intent.putExtra("total", result[1]);
            intent.putExtra("passed", result[2] == 1);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
            finish();
        });
    }

    private void showQuestion() {
        if (currentIndex >= questions.size()) return;

        Question q = questions.get(currentIndex);
        tvQuestionNumber.setText("Savol " + (currentIndex + 1) + " / " + questions.size());
        tvQuestionText.setText(q.questionText);
        rbA.setText("A) " + q.optionA);
        rbB.setText("B) " + q.optionB);
        rbC.setText("C) " + q.optionC);
        rbD.setText("D) " + q.optionD);
        rgOptions.clearCheck();

        progressBar.setMax(questions.size());
        progressBar.setProgress(currentIndex);

        btnNext.setText(currentIndex == questions.size() - 1
                ? getString(R.string.finish_test)
                : getString(R.string.next_question));
    }

    private void handleNext() {
        int checkedId = rgOptions.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(this, "Javobni tanlang", Toast.LENGTH_SHORT).show();
            return;
        }

        Question q = questions.get(currentIndex);
        String selected;
        if (checkedId == R.id.rbOptA) selected = "A";
        else if (checkedId == R.id.rbOptB) selected = "B";
        else if (checkedId == R.id.rbOptC) selected = "C";
        else selected = "D";

        if (selected.equals(q.correctOption)) {
            score++;
        }

        currentIndex++;

        if (currentIndex >= questions.size()) {
            testViewModel.submitResult(session.getUserId(), topicId, score, questions.size());
        } else {
            showQuestion();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Testni tark etmoqchimisiz? Natija saqlanmaydi.")
                .setPositiveButton("Ha", (dialog, which) -> super.onBackPressed())
                .setNegativeButton("Yo'q", null)
                .show();
    }
}
