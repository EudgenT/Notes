package notes.com.example.eudge_000.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import notes.com.example.eudge_000.notes.R;

public class EditNoteActivity extends AppCompatActivity {

    public static Intent newInstance(Context context){
        Intent intent = new Intent(context, EditNoteActivity.class);
        return intent;
    }

    public static final String DATA_KEY = "DATA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        String extraSting = getIntent().getStringExtra(DATA_KEY);
        Toast.makeText(this, extraSting, Toast.LENGTH_SHORT).show();
    }
}
