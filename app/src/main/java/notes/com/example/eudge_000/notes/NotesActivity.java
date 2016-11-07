package notes.com.example.eudge_000.notes;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.notes_adapter.NotesAdapter;

public class NotesActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter();
        List<String> dataSource = new ArrayList<>();
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
