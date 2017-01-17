package av.smartnotes.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.melnykov.fab.FloatingActionButton;
import com.woalk.apps.lib.colorpicker.ColorPickerDialog;
import com.woalk.apps.lib.colorpicker.ColorPickerSwatch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;
import av.smartnotes.substance.Node;
import av.smartnotes.substance.Priority;
import av.smartnotes.substance.controller.NodeController;
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

    @ViewById(R.id.btn_color)
    protected Button colorBtn;

    @Extra
    protected long id = -1;

    private int color;
    private Node node;

    @AfterViews
    public void afterView() {
        super.afterView();
        if (id > -1) {
            node = NodeController.get(id);
        }

        setToolbarTitle(R.string.app_name);
        displayHomeClose();

        setViews();
    }

    private void setViews() {
        if (id > -1) {
            nodeTitle.setText(node.getTitle());
            nodeBody.setText(node.getBody());
            colorBtn.setBackgroundColor(Priority.values()[node.getPriority()].id());
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
        node.delete();
        finish();
    }

    @Click(R.id.btn_color)
    protected void btnColorClick() {
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title,
                new int[]{Color.WHITE, Color.GREEN, Color.YELLOW, Color.RED},
                Color.TRANSPARENT,
                5,
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int c) {
                color = c;
                colorBtn.setBackgroundColor(c);
                changeColor(c);
            }
        });

        dialog.show(getFragmentManager(), Constant.DLG_TAG);
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
            Node.construct(nodeTitle.getText().toString(),
                    nodeBody.getText().toString(),
                    color);
        } else {
            node.setTitle(nodeTitle.getText().toString());
            node.setBody(nodeBody.getText().toString());
            node.setColor(color);
            node.save();
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