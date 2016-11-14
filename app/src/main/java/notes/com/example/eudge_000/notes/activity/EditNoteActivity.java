package notes.com.example.eudge_000.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.R;

public class EditNoteActivity extends AppCompatActivity {

    @BindView(R.id.title_text_view)
    protected TextView mTitleTextView;
    @BindView(R.id.edit_note_text_view)
    protected TextView mContentTextView;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    public static Intent newInstance(Context context) {
        return new Intent(context, EditNoteActivity.class);
    }

    private static final String SHARE_TYPE = "text/*";
    public static final String DATA_KEY = "DATA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
//        String extraSting = getIntent().getStringExtra(DATA_KEY);
//        Toast.makeText(this, extraSting, Toast.LENGTH_SHORT).show();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Create note");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
//                finish();
                Intent intent = new Intent(this,NotesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }
            case R.id.action_share: {
                share();
                break;
            }
            case R.id.action_create:{
                Toast.makeText(this, "Create notes", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_settings: {
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_help:{
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
