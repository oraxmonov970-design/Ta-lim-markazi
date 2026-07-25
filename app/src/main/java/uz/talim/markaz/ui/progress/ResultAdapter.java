package uz.talim.markaz.ui.progress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uz.talim.markaz.R;
import uz.talim.markaz.model.TestResult;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<TestResult> results = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public void setResults(List<TestResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        TestResult result = results.get(position);
        holder.tvTopic.setText("Mavzu #" + result.topicId);
        holder.tvDate.setText(dateFormat.format(new Date(result.takenAt)));
        holder.tvScore.setText(result.score + "/" + result.totalQuestions);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvDate, tvScore;

        ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopic = itemView.findViewById(R.id.tvResultTopic);
            tvDate = itemView.findViewById(R.id.tvResultDate);
            tvScore = itemView.findViewById(R.id.tvResultScore);
        }
    }
}
