package notes.com.example.eudge_000.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.R;

public class EditNoteActivity extends AppCompatActivity {

    @BindView(R.id.title_text_view)
    protected TextView mTitleTextView;
    @BindView(R.id.edit_note_text_view)
    protected TextView mEditNoteTextView;
    @BindView(R.id.create_button)
    protected Button mButtonCreate;

    public static Intent newInstance(Context context){
        Intent intent = new Intent(context, EditNoteActivity.class);
        return intent;
    }

    public static final String DATA_KEY = "DATA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        String extraSting = getIntent().getStringExtra(DATA_KEY);
        Toast.makeText(this, extraSting, Toast.LENGTH_SHORT).show();
    }
}
