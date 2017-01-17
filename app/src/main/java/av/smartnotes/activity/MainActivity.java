package av.smartnotes.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
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
import org.androidannotations.annotations.UiThread;
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
        implements MaterialFavoriteButton.OnClickListener, MaterialFavoriteButton.OnLongClickListener {
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
        setToolbarExportButton(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!CollectionsManager.getInstance().isEmpty()) {
            recyclerView.swapAdapter(
                    new ItemsAdapter(CollectionsManager.getInstance().getItemList()),
                    false);
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

    private void addTestDataToList() {
        CollectionsManager.getInstance().createList();

        recyclerView.setAdapter(
                new ItemsAdapter(CollectionsManager.getInstance().getItemList()));
    }

    @Override
    public void onClick(View v) {
        if (CollectionsManager.getInstance().isEmpty()) {
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme))
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(R.string.alert_dialog_body_export)
                    .setCancelable(true)
                    .create()
                    .show();
        } else {
            saveList();
            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    protected void saveList() {
        FileController.writeTextToFile(this, CollectionsManager.getInstance().toJson());
        // if error do smth
    }

    @Override
    public boolean onLongClick(View v) {
        if (FileController.fileExist(this)) {
            readListFromFile();
        } else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Background
    protected void readListFromFile() {
        Object json = FileController.readJson(this);
        if (json != null && json instanceof String) {
            CollectionsManager.getInstance().addList(
                    Utils.parseItems(String.class.cast(json)));
            setAdapter();
        }
    }

    @UiThread
    protected void setAdapter() {
        recyclerView.setAdapter(
                new ItemsAdapter(CollectionsManager.getInstance().getItemList()));
    }
}