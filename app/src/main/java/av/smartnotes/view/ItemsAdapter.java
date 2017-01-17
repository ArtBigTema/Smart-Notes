package av.smartnotes.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import av.smartnotes.R;
import av.smartnotes.activity.DetailActivity_;
import av.smartnotes.substance.Node;

/**
 * Created by Artem on 17.01.2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private List<Node> list = Collections.emptyList();
    private final int LAYOUT = R.layout.rv_item;

    public ItemsAdapter() {

    }

    public ItemsAdapter(List<Node> list) {
        this.list = list;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);
        return new ItemsViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ItemsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private Node item;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Node node) {
            item = node;
            TextView title = (TextView) itemView.findViewById(R.id.tv_node_title);
            title.setText(node.getTitle());
        }

        @Override
        public void onClick(View v) {
            DetailActivity_
                    .intent(v.getContext())
                    .title(item.getTitle())
                    .body(item.getBody())
                    .start();
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }
}