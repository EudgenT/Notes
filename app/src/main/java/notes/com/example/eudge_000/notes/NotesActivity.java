package notes.com.example.eudge_000.notes;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import notes.com.example.eudge_000.notes.notes_adapter.NotesAdapter;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter();
        List<String> dataSource = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            dataSource.add("title: " + i);
        }
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(dataSource);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)  {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Snackbar.make(recyclerView,R.string.settings,Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.action_help:
                Snackbar.make(recyclerView,R.string.help,Snackbar.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }
}
