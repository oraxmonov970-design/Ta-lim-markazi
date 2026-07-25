package uz.talim.markaz.ui.topic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import uz.talim.markaz.ui.book.BookListFragment;
import uz.talim.markaz.ui.media.MediaListFragment;
import uz.talim.markaz.ui.test.TestListFragment;

public class TopicPagerAdapter extends FragmentStateAdapter {

    private final int topicId;

    public TopicPagerAdapter(@NonNull FragmentActivity fragmentActivity, int topicId) {
        super(fragmentActivity);
        this.topicId = topicId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new TestListFragment();
                break;
            case 2:
                fragment = new MediaListFragment();
                break;
            default:
                fragment = new BookListFragment();
        }
        android.os.Bundle args = new android.os.Bundle();
        args.putInt("topic_id", topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
