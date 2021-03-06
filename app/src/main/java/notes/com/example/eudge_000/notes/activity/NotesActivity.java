package notes.com.example.eudge_000.notes.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import notes.com.example.eudge_000.notes.R;
import notes.com.example.eudge_000.notes.db.NotesContract;
import notes.com.example.eudge_000.notes.model.Note;
import notes.com.example.eudge_000.notes.notes_adapter.NotesAdapter;
import notes.com.example.eudge_000.notes.notes_adapter.NotesAdapter.Notes_View_Holder;

public class NotesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.fab_button)
    protected FloatingActionButton mFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        getSupportLoaderManager().initLoader(R.id.notes_loader, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.fab_button)
    public void onFabBtnClick() {
        startActivity(NoteCreateActivity.newInstance(this));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Snackbar.make(mRecyclerView,R.string.settings,Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.action_help:
                Snackbar.make(mRecyclerView,R.string.help,Snackbar.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                NotesContract.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<Note> dataSource = new ArrayList<>();
        while (data.moveToNext()) {
            dataSource.add(new Note(data));
        }
        NotesAdapter adapter = new NotesAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setDataSource(dataSource);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        Notes_View_Holder holder = (Notes_View_Holder) mRecyclerView.findContainingViewHolder(view);
        if(holder == null) return;
        startActivity(EditNoteActivity.newInstance(this, holder.getNote().getId()));
    }
}
