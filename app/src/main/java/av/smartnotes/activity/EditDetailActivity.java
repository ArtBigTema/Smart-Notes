package av.smartnotes.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;
import av.smartnotes.substance.CollectionsManager;
import av.smartnotes.util.Constant;
import av.smartnotes.util.Utils;

/**
 * Created by Artem on 17.01.2017.
 */

@EActivity(R.layout.activity_edit_detail)
public class EditDetailActivity extends ActivityWithToolbar
        implements MaterialFavoriteButton.OnClickListener {
    @ViewById(R.id.et_node_title)
    protected EditText nodeTitle;

    @ViewById(R.id.et_node_body)
    protected EditText nodeBody;

    @ViewById(R.id.fab)
    protected FloatingActionButton fab;

    @Extra
    protected String title;
    @Extra
    protected String body;
    @Extra
    protected int id = -1;

    @AfterViews
    public void afterView() {
        super.afterView();

        setToolbarTitle(R.string.app_name);
        displayHomeClose();

        setViews();
    }

    private void setViews() {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
            nodeTitle.setText(title);
            nodeBody.setText(body);
        } else {
            fab.setVisibility(View.GONE);
        }

        setToolbarSaveButton(this);
    }

    @OptionsItem(android.R.id.home)
    protected void optionsBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (!Utils.validText(nodeTitle.getText()) || !Utils.validText(nodeBody.getText())) {
            super.onBackPressed();
            return;
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(
                new ContextThemeWrapper(this, R.style.DialogTheme));
        dialog.setTitle(R.string.alert_dialog_title);
        dialog.setMessage(R.string.alert_dialog_body_close);

        dialog.setPositiveButton(R.string.alert_dialog_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditDetailActivity.this.onClick(null);
            }
        });
        dialog.setNegativeButton(R.string.alert_dialog_btn_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog.create().show();
    }

    @Click(R.id.fab)
    protected void fabClick() {
        CollectionsManager.getInstance().remove(id);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (saveNode()) {
            markToolbarButton(false);
            disableToolbar();
        } else {
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme))
                    .setTitle(R.string.alert_dialog_title)
                    .setMessage(R.string.alert_dialog_body_save)
                    .setCancelable(true)
                    .create()
                    .show();
        }
    }

    private boolean saveNode() {
        if (!Utils.validText(nodeTitle.getText()) || !Utils.validText(nodeBody.getText())) {
            return false;
        }

        if (id < 0) {
            CollectionsManager.getInstance().add(
                    nodeTitle.getText(), nodeBody.getText());
        } else {
            CollectionsManager.getInstance().set(id,
                    nodeTitle.getText(), nodeBody.getText());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, Constant.ACTIVITY_FINISH);

        return true;
    }
}