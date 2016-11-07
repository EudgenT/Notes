package notes.com.example.eudge_000.notes.notes_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import notes.com.example.eudge_000.notes.R;
import notes.com.example.eudge_000.notes.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Notes_View_Holder> {

    private List<Note> mDataSource = null;

    public void setDataSource(List<Note> mDataSource) {
        this.mDataSource = mDataSource;
        notifyDataSetChanged();
    }

    @Override
    public Notes_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_notes_item, parent, false);
        return new Notes_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(Notes_View_Holder holder, int position) {
        Note note = mDataSource.get(position);
        holder.bindView(note);
    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0:mDataSource.size();
    }

    static class Notes_View_Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.primary_text_view)
        protected TextView mPrimaryTextView;
        @BindView(R.id.secondary_text_view)
        protected TextView mSecondaryTextView;
        @BindView(R.id.date_text_view)
        protected TextView mDateTextView;

        void bindView(Note note) {
            mPrimaryTextView.setText(note.getTitle());
            mSecondaryTextView.setText(note.getText());
            mDateTextView.setText(String.valueOf(note.getTime()));
        }

        public Notes_View_Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
