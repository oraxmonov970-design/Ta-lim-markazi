package uz.talim.markaz.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uz.talim.markaz.R;
import uz.talim.markaz.ui.main.MainActivity;

public class TestResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        boolean passed = getIntent().getBooleanExtra("passed", false);
        int topicId = getIntent().getIntExtra("topic_id", -1);

        TextView tvScore = findViewById(R.id.tvScore);
        TextView tvMessage = findViewById(R.id.tvResultMessage);
        ImageView ivIcon = findViewById(R.id.ivResultIcon);

        tvScore.setText(score + " / " + total);

        if (passed) {
            tvMessage.setText("Tabriklaymiz! Mavzu muvaffaqiyatli tugatildi.");
            ivIcon.setImageResource(R.drawable.ic_check_circle);
        } else {
            tvMessage.setText("Natija yetarli emas. Qayta urinib ko'ring (kamida 60% kerak).");
        }

        findViewById(R.id.btnRetake).setOnClickListener(v -> {
            Intent intent = new Intent(this, TestActivity.class);
            intent.putExtra("topic_id", topicId);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.btnBackHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
