package uz.talim.markaz.ui.book;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.talim.markaz.R;
import uz.talim.markaz.utils.FileUtils;
import uz.talim.markaz.utils.SessionManager;
import uz.talim.markaz.viewmodel.ContentViewModel;

public class BookListFragment extends Fragment {

    private int topicId;
    private BookAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        topicId = getArguments() != null ? getArguments().getInt("topic_id") : -1;
        SessionManager session = new SessionManager(requireContext());

        RecyclerView rv = view.findViewById(R.id.rvBooks);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView tvEmpty = view.findViewById(R.id.tvEmpty);

        adapter = new BookAdapter(book -> {
            try {
                File file = new File(book.filePath);
                Uri uri = FileUtils.getUriForFile(requireContext(), file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Faylni ochib bo'lmadi", Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAddBook);
        if (session.isTeacher()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), AddBookActivity.class);
                intent.putExtra("topic_id", topicId);
                startActivity(intent);
            });
        }

        ContentViewModel viewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        viewModel.getBooks(topicId).observe(getViewLifecycleOwner(), books -> {
            adapter.setBooks(books);
            tvEmpty.setVisibility(books == null || books.isEmpty() ? View.VISIBLE : View.GONE);
        });

        return view;
    }
}
