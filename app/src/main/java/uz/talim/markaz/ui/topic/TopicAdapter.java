package uz.talim.markaz.ui.topic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uz.talim.markaz.R;
import uz.talim.markaz.model.Topic;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    public interface OnTopicClickListener {
        void onClick(Topic topic);
    }

    private List<Topic> topics = new ArrayList<>();
    private Set<Integer> completedTopicIds = new HashSet<>();
    private final OnTopicClickListener listener;

    public TopicAdapter(OnTopicClickListener listener) {
        this.listener = listener;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
        notifyDataSetChanged();
    }

    public void setCompletedIds(Set<Integer> completedIds) {
        this.completedTopicIds = completedIds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.tvName.setText(topic.name);
        holder.tvDesc.setText(topic.description);
        holder.ivCompleted.setVisibility(completedTopicIds.contains(topic.id) ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(v -> listener.onClick(topic));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        ImageView ivCompleted;

        TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTopicName);
            tvDesc = itemView.findViewById(R.id.tvTopicDesc);
            ivCompleted = itemView.findViewById(R.id.ivCompleted);
        }
    }
}
