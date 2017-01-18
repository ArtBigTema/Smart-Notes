package av.smartnotes.activity;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithFabMenu;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Constant;
import av.smartnotes.util.FileController;
import av.smartnotes.util.Utils;
import av.smartnotes.view.DividerItemDecoration;
import av.smartnotes.view.ItemsAdapter;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityWithFabMenu
        implements MaterialFavoriteButton.OnClickListener, MaterialFavoriteButton.OnLongClickListener {

    @ViewById(R.id.rv_main)
    protected RecyclerView recyclerView;

    @AfterViews
    public void afterView() {
        super.afterView();

        setToolbarTitle(R.string.app_name);
        setRecycleView();

        showInitDlg();
        setToolbarExportButton(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!NodeController.isEmpty()) {
            recyclerView.swapAdapter(
                    new ItemsAdapter(),
                    false);
        }
    }

    private void showInitDlg() {
        if (NodeController.isEmpty()) {
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme))
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(R.string.hint_fab)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }

    private void setRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new ItemsAdapter());

        recyclerView.addItemDecoration(new DividerItemDecoration(5));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                toggleFab(dy > 0);
            }
        });
    }

    @Click(R.id.fab_add)
    protected void clickFabAdd() {
        EditDetailActivity_.intent(this).start();
    }

    @Click(R.id.fab_map)
    protected void clickFabMap() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//ripple
                MapsActivity_
                        .intent(MainActivity.this)
                        .viewMode(true)
                        .id(-1)
                        .start();
            }
        }, Constant.DURATION);
    }

    @LongClick(R.id.fab_add)
    protected void fabLongClick() {
        addTestDataToList();
    }

    private void addTestDataToList() {
        NodeController.createList();

        recyclerView.setAdapter(new ItemsAdapter());
    }

    @Override
    public void onClick(View v) {
        if (NodeController.isEmpty()) {
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
        FileController.writeTextToFile(this, Utils.toJson(NodeController.getAbsAll()));
        // if error do smth
    }

    @Override
    public boolean onLongClick(View v) {
        if (FileController.fileExist(this)) {
            readListFromFile();
        } else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Background
    protected void readListFromFile() {
        Object json = FileController.readJson(this);
        if (json != null && json instanceof String) {
            NodeController.swapList(Utils.parseItems(String.class.cast(json)));
            setAdapter();
        }
    }

    @UiThread
    protected void setAdapter() {
        recyclerView.setAdapter(new ItemsAdapter());
    }
}