package av.smartnotes.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;
import av.smartnotes.substance.CollectionsManager;
import av.smartnotes.util.Utils;
import av.smartnotes.view.DividerItemDecoration;
import av.smartnotes.view.ItemsAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityWithToolbar {
    @ViewById(R.id.rv_main)
    protected RecyclerView recyclerView;

    @ViewById(R.id.fab)
    protected FloatingActionButton fab;

    @AfterViews
    public void afterView() {
        super.afterView();

        setToolbarTitle(R.string.app_name);
        setRecycleView();

        showTooltip();
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.swapAdapter(
                new ItemsAdapter(CollectionsManager.getInstance().getItemList()),
                false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CollectionsManager.getInstance().clear();
    }

    private void showTooltip() {
        if (CollectionsManager.getInstance().isEmpty()) {
            Utils.showTooltip(fab,
                    getString(R.string.hint_fab),
                    ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    private void setRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(
                new ItemsAdapter(CollectionsManager.getInstance().getItemList()));

        recyclerView.addItemDecoration(new DividerItemDecoration(5));

        fab.attachToRecyclerView(recyclerView);
    }

    @Click(R.id.fab)
    protected void fabClick() {
        EditDetailActivity_.intent(this).start();
    }

    @LongClick(R.id.fab)
    protected void fabLongClick() {
        addTestDataToList();
    }


    private void addTestDataToList() {
        CollectionsManager.getInstance().createList();

        recyclerView.setAdapter(
                new ItemsAdapter(CollectionsManager.getInstance().getItemList()));
    }
}