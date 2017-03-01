package notes.com.example.eudge_000.notes.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.R;
import notes.com.example.eudge_000.notes.db.NotesContract;
import notes.com.example.eudge_000.notes.notes_adapter.NotesFragmentPagerAdapter;
import notes.com.example.eudge_000.notes.util.DateUtil;

public class NoteCreateActivity extends AppCompatActivity {

    private String mOriginalTitle = "";
    private String mOriginalText = "";
    private long mId = -1;
    private static final String SHARE_TYPE = "text/*";

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.title_text_view)
    protected TextView mTitleTextView;
    @BindView(R.id.edit_note_text_view)
    protected TextView mContentTextView;

    @NonNull
    public static Intent newInstance(@NonNull Context context) {
        return new Intent(context, NoteCreateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_create);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Create note");
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

    public void share() {
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
            return;
        } else{
            insertNote();
        }
    }

    private boolean isNoteUpdatable() {
        return mId != -1;
    }

    @Override
    public void onBackPressed() {
        safetyFinish(() -> NoteCreateActivity.super.onBackPressed());
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
