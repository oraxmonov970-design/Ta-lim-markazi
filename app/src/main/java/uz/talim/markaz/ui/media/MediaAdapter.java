package uz.talim.markaz.ui.media;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uz.talim.markaz.R;
import uz.talim.markaz.model.MediaItem;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    public interface OnMediaClickListener {
        void onClick(MediaItem mediaItem);
    }

    private List<MediaItem> items = new ArrayList<>();
    private final OnMediaClickListener listener;

    public MediaAdapter(OnMediaClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<MediaItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        MediaItem item = items.get(position);
        holder.tvTitle.setText(item.title);
        holder.ivType.setImageResource("AUDIO".equals(item.type) ? R.drawable.ic_audio : R.drawable.ic_media);
        holder.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivType;

        MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMediaTitle);
            ivType = itemView.findViewById(R.id.ivMediaType);
        }
    }
}
