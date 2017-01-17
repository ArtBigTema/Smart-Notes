package av.smartnotes.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;
import av.smartnotes.view.ItemsAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityWithToolbar {
    @ViewById(R.id.rv_main)
    protected RecyclerView recyclerView;

    @AfterViews
    public void afterView() {
        setToolbarTitle(R.string.app_name);
        setRecycleView();
    }

    private void setRecycleView() {
        if (recyclerView.getAdapter() != null) {
            recyclerView.swapAdapter(new ItemsAdapter(), false);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ItemsAdapter());
            // recyclerView.addItemDecoration(new DividerItemDecoration(5));
        }
    }
}