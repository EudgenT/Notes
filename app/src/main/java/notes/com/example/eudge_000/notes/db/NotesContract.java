package notes.com.example.eudge_000.notes.db;

import android.net.Uri;

import notes.com.example.eudge_000.notes.BuildConfig;
import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;


public interface NotesContract extends ProviGenBaseContract {

    @Column(Column.Type.TEXT)
    String TITLE_COLUMN = "TITLE";

    @Column(Column.Type.TEXT)
    String TEXT_COLUMN = "TEXT";

    @Column(Column.Type.TEXT)
    String TIME_COLUMN = "TIME";

    String TABLE_NAME = "notes_table";

    String TABLE_URI_TEMPLATE = "content://%s/%s";

    String URI = String.format(
            TABLE_URI_TEMPLATE,
            BuildConfig.APPLICATION_ID,
            TABLE_NAME);

    @ContentUri
    Uri CONTENT_URI = Uri.parse(URI);

}
