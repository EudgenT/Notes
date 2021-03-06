package notes.com.example.eudge_000.notes.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.R;
import notes.com.example.eudge_000.notes.db.NotesContract;
import notes.com.example.eudge_000.notes.model.Note;

public class Note_fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static Note_fragment newInstance (long id){
        Bundle args = new Bundle();
        args.putLong(ProviGenBaseContract._ID, id);
        Note_fragment fragment = new Note_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.title_text_view)
    public TextView mTitleTextView;
    @BindView(R.id.edit_note_text_view)
    public TextView mContentTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_note,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader((int)getArguments().getLong(ProviGenBaseContract._ID), null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long noteId = getArguments().getLong(ProviGenBaseContract._ID);
        return new CursorLoader(
                getActivity(),
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(noteId)),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor == null || !cursor.moveToFirst()) return;
        Note note = new Note(cursor);
        mTitleTextView.setText(note.getTitle());
        mContentTextView.setText(note.getText());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
