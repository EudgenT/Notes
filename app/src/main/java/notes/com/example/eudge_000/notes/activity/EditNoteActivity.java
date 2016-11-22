package notes.com.example.eudge_000.notes.activity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.R;
import notes.com.example.eudge_000.notes.db.NotesContract;
import notes.com.example.eudge_000.notes.model.Note;
import notes.com.example.eudge_000.notes.util.DateUtil;

public class EditNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.title_text_view)
    protected TextView mTitleTextView;
    @BindView(R.id.edit_note_text_view)
    protected TextView mContentTextView;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private long mId = -1;

    private String mOriginalTitle = "";
    private String mOriginalText = "";

    @NonNull
    public static Intent newInstance(@NonNull Context context) {
        return new Intent(context, EditNoteActivity.class);
    }

    @NonNull
    public static Intent newInstance(@NonNull Context context, long id){
        Intent intent = newInstance(context);
        intent.putExtra(ProviGenBaseContract._ID, id);
        return intent;
    }

    private static final String SHARE_TYPE = "text/*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkIntentByExtraId();
        setTitle("Create note");
    }

    private void checkIntentByExtraId() {
        Intent intent = getIntent();
        if(!intent.hasExtra(ProviGenBaseContract._ID)) return;
        mId = intent.getLongExtra(ProviGenBaseContract._ID, mId);
        if(mId == -1) return;
        getLoaderManager().initLoader(R.id.edit_note_loader, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                safetyFinish(() -> finish());
                break;
            }
            case R.id.action_share: {
                share();
                break;
            }
            case R.id.action_create: {
                onSaveBtnClick();
                break;
            }
            case R.id.action_delete: {
                deleteNote();
                break;
            }
            case R.id.action_settings: {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_help: {
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNoteForSharing());
        shareIntent.setType(SHARE_TYPE);
        startActivity(shareIntent);
    }

    private String prepareNoteForSharing() {
        return getString(R.string.sharing_template, mTitleTextView.getText(), mContentTextView.getText());
    }

    private void deleteNote() {
        if(isNoteUpdatable()){
            getContentResolver().delete(
                    Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
                    null,
                    null);
        }
        finish();
    }

    private void insertNote() {
        ContentValues values = new ContentValues();
        values.put(NotesContract.TITLE_COLUMN, mTitleTextView.getText().toString());
        values.put(NotesContract.TEXT_COLUMN, mContentTextView.getText().toString());
        values.put(NotesContract.TIME_COLUMN, DateUtil.formatCurrentDate());
        getContentResolver().insert(NotesContract.CONTENT_URI, values);
    }

    public void onSaveBtnClick() {
        save();
        finish();
    }

    private void save() {
        if(isNoteUpdatable()){
            updateNote();
        } else{
            insertNote();
        }
    }

    private void updateNote() {
        final ContentValues values = new ContentValues();
        values.put(NotesContract.TITLE_COLUMN, mTitleTextView.getText().toString());
        values.put(NotesContract.TEXT_COLUMN, mContentTextView.getText().toString());
        values.put(NotesContract.TIME_COLUMN, DateUtil.formatCurrentDate());
        getContentResolver().update(
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
                values,
                null,
                null);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                this,
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) return;
        Note note = new Note(cursor);
        mTitleTextView.setText(note.getTitle());
        mContentTextView.setText(note.getText());
        mOriginalTitle = note.getTitle();
        mOriginalText = note.getText();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private boolean isNoteUpdatable() {
        return mId != -1;
    }

    @Override
    public void onBackPressed() {
        safetyFinish(() -> EditNoteActivity.super.onBackPressed());
    }

    private void safetyFinish(Runnable runnable){
        if(mOriginalText.equals(mContentTextView.getText().toString())
                && mOriginalTitle.equals(mTitleTextView.getText().toString())){
            runnable.run();
            return;
        }
        showDoYouSureAlert(runnable);
    }

    private void showDoYouSureAlert(final Runnable finish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.do_you_sure_alert_title);
        builder.setMessage(R.string.do_yout_sure_alert_do_you_want_to_save_change);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
            save();
            finish.run();
        });
        builder.setNegativeButton(android.R.string.no, (dialogInterface, i) -> finish.run());
        builder.show();
    }
}