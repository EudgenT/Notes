package notes.com.example.eudge_000.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import notes.com.example.eudge_000.notes.notes_adapter.NotesAdapter;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
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
}
