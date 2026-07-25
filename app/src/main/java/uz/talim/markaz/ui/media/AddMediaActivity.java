package uz.talim.markaz.ui.media;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.MediaItem;
import uz.talim.markaz.utils.FileUtils;
import uz.talim.markaz.viewmodel.ContentViewModel;

public class AddMediaActivity extends AppCompatActivity {

    private Uri chosenUri;
    private TextView tvChosenFile;
    private int topicId;
    private ContentViewModel viewModel;
    private RadioGroup rgType;

    private final ActivityResultLauncher<String> filePicker =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    chosenUri = uri;
                    tvChosenFile.setText(FileUtils.getFileName(this, uri));
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        topicId = getIntent().getIntExtra("topic_id", -1);
        viewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_media);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        EditText etTitle = findViewById(R.id.etMediaTitle);
        tvChosenFile = findViewById(R.id.tvChosenMediaFile);
        rgType = findViewById(R.id.rgMediaType);
        Button btnChoose = findViewById(R.id.btnChooseMediaFile);
        Button btnSave = findViewById(R.id.btnSaveMedia);

        btnChoose.setOnClickListener(v -> {
            boolean isAudio = rgType.getCheckedRadioButtonId() == R.id.rbAudio;
            filePicker.launch(isAudio ? "audio/*" : "video/*");
        });

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, R.string.error_empty_field, Toast.LENGTH_SHORT).show();
                return;
            }
            if (chosenUri == null) {
                Toast.makeText(this, R.string.choose_file, Toast.LENGTH_SHORT).show();
                return;
            }

            String type = rgType.getCheckedRadioButtonId() == R.id.rbAudio ? "AUDIO" : "VIDEO";
            String path = FileUtils.copyFileToInternalStorage(this, chosenUri, "media");
            if (path == null) {
                Toast.makeText(this, "Faylni saqlashda xatolik", Toast.LENGTH_SHORT).show();
                return;
            }

            MediaItem item = new MediaItem(topicId, title, path, type, System.currentTimeMillis());
            viewModel.addMedia(item);
            Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
