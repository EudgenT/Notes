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

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Notes_View_Holder> {

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    private List<String> dataSource = null;

    @Override
    public Notes_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_notes_item, parent, false);
        return new Notes_View_Holder(view);
    }

    @Override
    public void onBindViewHolder(Notes_View_Holder holder, int position) {
        String title = dataSource.get(position);
        holder.bindView(title);
    }

    @Override
    public int getItemCount() {
        if (dataSource == null)
            return 0;
        return dataSource.size();
    }

    static class Notes_View_Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_text_view)
        protected TextView titleTextView;

        void bindView(String title) {
            titleTextView.setText(title);
        }

        public Notes_View_Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
