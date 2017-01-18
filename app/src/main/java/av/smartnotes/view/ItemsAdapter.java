package av.smartnotes.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityController;
import av.smartnotes.substance.Node;
import av.smartnotes.substance.Priority;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Utils;

/**
 * Created by Artem on 17.01.2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private List<Node> list = Collections.emptyList();
    private final int LAYOUT = R.layout.rv_item;

    public ItemsAdapter() {
        this.list = NodeController.getAbsAll();
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
            title.setTextColor(Priority.values()[node.getPriority()].id());

            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_node);

            if (!TextUtils.isEmpty(node.getImagePath())) {
                Glide.with(itemView.getContext())
                        .load(Utils.getUri(node.getImagePath()))
                        .error(R.drawable.error)
                        .into(imageView);
            }

            if (itemView instanceof CardView) {
                ((CardView) itemView).setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
            }
        }

        @Override
        public void onClick(View v) {
            ActivityController.startDetailActivity(v.getContext(), item.getId());
        }

        @Override
        public boolean onLongClick(View v) {
            ActivityController.startEditDetailActivity(v.getContext(), item.getId());
            return true;
        }
    }
}