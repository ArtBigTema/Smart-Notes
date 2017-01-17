package av.smartnotes.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;
import av.smartnotes.substance.CollectionsManager;
import av.smartnotes.util.FileController;
import av.smartnotes.util.Utils;
import av.smartnotes.view.DividerItemDecoration;
import av.smartnotes.view.ItemsAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityWithToolbar
        implements MaterialFavoriteButton.OnClickListener {
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

        if (!CollectionsManager.getInstance().isEmpty()) {
            recyclerView.swapAdapter(
                    new ItemsAdapter(CollectionsManager.getInstance().getItemList()),
                    false);
            setToolbarExportButton(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // CollectionsManager.getInstance().clear();
    }

    private void showTooltip() {
        if (CollectionsManager.getInstance().isEmpty()) {
            Utils.showTooltipTop(fab,
                    getString(R.string.hint_fab));
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

    @Override
    public void onClick(View v) {
        saveList();
        Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
    }

    @Background
    protected void saveList() {
        FileController.writeTextToFile(this, CollectionsManager.getInstance().toText());
        // if error do smth
    }

    private void addTestDataToList() {
        CollectionsManager.getInstance().createList();

        recyclerView.setAdapter(
                new ItemsAdapter(CollectionsManager.getInstance().getItemList()));

        setToolbarExportButton(this);
    }
}