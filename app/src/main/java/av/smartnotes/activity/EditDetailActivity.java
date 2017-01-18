package av.smartnotes.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.github.clans.fab.FloatingActionButton;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.woalk.apps.lib.colorpicker.ColorPickerDialog;
import com.woalk.apps.lib.colorpicker.ColorPickerSwatch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityController;
import av.smartnotes.activity.activity_with.ActivityWithFabMenu;
import av.smartnotes.substance.Node;
import av.smartnotes.substance.Priority;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Constant;
import av.smartnotes.util.Utils;

/**
 * Created by Artem on 17.01.2017.
 */

@EActivity(R.layout.activity_edit_detail)
public class EditDetailActivity extends ActivityWithFabMenu
        implements MaterialFavoriteButton.OnClickListener {
    @ViewById(R.id.et_node_title)
    protected EditText nodeTitle;

    @ViewById(R.id.et_node_body)
    protected EditText nodeBody;

    @ViewById(R.id.btn_color)
    protected Button colorBtn;

    @ViewById(R.id.iv_node)
    protected ImageView imageView;

    @ViewById(R.id.fab_delete)
    protected FloatingActionButton fabDelete;

    @Extra
    protected long id = -1;

    private int priorityColor;
    private String imagePath;
    private Node node;

    @AfterViews
    public void afterView() {
        super.afterView();
        if (id > -1) {
            node = NodeController.get(id);
        } else {
            node = new Node();
            double lat = Utils.getDouble(this, R.string.key_lat, Constant.SAMARA.latitude);
            double lng = Utils.getDouble(this, R.string.key_lng, Constant.SAMARA.longitude);
            node.setLat(lat);
            node.setLng(lng);
        }

        setToolbarTitle(R.string.app_name);
        displayHomeClose();

        setViews();
    }

    private void setViews() {
        if (id > -1) {
            nodeTitle.setText(node.getTitle());
            nodeBody.setText(node.getBody());
            priorityColor = Priority.values()[node.getPriority()].id();

            if (!TextUtils.isEmpty(node.getImagePath())) {
                // imageView.setImageURI(Utils.getUri(node.getImagePath()));
                Glide.with(this)
                        .load(Utils.getUri(node.getImagePath()))
                        .error(R.drawable.error)
                        .into(imageView);
            }
        } else {
            priorityColor = Color.BLUE;
            fabDelete.setEnabled(false);
        }

        colorBtn.setBackgroundColor(priorityColor);
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

    @Click(R.id.fab_delete)
    protected void fabDeleteClick() {
        node.delete();
        finish();
    }

    @Click(R.id.fab_map)
    protected void fabMapClick() {
        MapsActivity_
                .intent(this)
                .viewMode(false)
                .id(id)
                .startForResult(Constant.CODE_MAP);
    }

    @Click(R.id.iv_node)
    protected void imageViewClick() {
        ActivityController.startImagePicker(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.CODE && resultCode == RESULT_OK && data != null) {
            Image image = ImagePicker.getImages(data).get(0);
            imagePath = image.getPath();

            // imageView.setImageURI(Utils.getUri(imagePath));
            Glide.with(this)
                    .load(Utils.getUri(imagePath))
                    .error(R.drawable.error)
                    .into(imageView);
        }
        if (requestCode == Constant.CODE_MAP && resultCode == RESULT_OK && data != null) {
            node.setLat(data.getDoubleExtra(Constant.EXTRA_LAT, 0d));
            node.setLng(data.getDoubleExtra(Constant.EXTRA_LNG, 0d));
        }
    }

    @Click(R.id.btn_color)
    protected void btnColorClick() {
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title,
                new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED},
                priorityColor,
                5,
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int c) {
                priorityColor = c;
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

        node.setTitle(nodeTitle.getText().toString());
        node.setBody(nodeBody.getText().toString());
        node.setColor(priorityColor);
        node.setImagePath(imagePath);
        node.save();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, Constant.ACTIVITY_FINISH);

        return true;
    }
}