package uz.talim.markaz.ui.book;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import uz.talim.markaz.R;
import uz.talim.markaz.model.Book;
import uz.talim.markaz.utils.FileUtils;
import uz.talim.markaz.viewmodel.ContentViewModel;

public class AddBookActivity extends AppCompatActivity {

    private Uri chosenUri;
    private TextView tvChosenFile;
    private int topicId;
    private ContentViewModel viewModel;

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
        setContentView(R.layout.activity_add_book);

        topicId = getIntent().getIntExtra("topic_id", -1);
        viewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_book);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        EditText etTitle = findViewById(R.id.etBookTitle);
        tvChosenFile = findViewById(R.id.tvChosenFile);
        Button btnChoose = findViewById(R.id.btnChooseFile);
        Button btnSave = findViewById(R.id.btnSaveBook);

        btnChoose.setOnClickListener(v -> filePicker.launch("application/pdf"));

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

            String path = FileUtils.copyFileToInternalStorage(this, chosenUri, "books");
            if (path == null) {
                Toast.makeText(this, "Faylni saqlashda xatolik", Toast.LENGTH_SHORT).show();
                return;
            }

            Book book = new Book(topicId, title, path, System.currentTimeMillis());
            viewModel.addBook(book);
            Toast.makeText(this, R.string.save, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
