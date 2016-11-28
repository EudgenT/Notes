package notes.com.example.eudge_000.notes.notes_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import notes.com.example.eudge_000.notes.fragments.Note_fragment;
import notes.com.example.eudge_000.notes.model.Note;

public class NotesFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Note> mDataSource = null;

    public NotesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        long id = mDataSource.get(position).getId();
        return Note_fragment.newInstance(id);
    }

    @Override
    public int getCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    public void setDataSource (List<Note> dataSource){
        mDataSource = dataSource;
        notifyDataSetChanged();
    }
}
