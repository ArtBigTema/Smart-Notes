package av.smartnotes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithFabMenu;
import av.smartnotes.substance.Node;
import av.smartnotes.substance.Priority;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Constant;
import av.smartnotes.util.Utils;

/**
 * Created by Artem on 17.01.2017.
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends ActivityWithFabMenu
        implements MaterialFavoriteButton.OnClickListener {
    @ViewById(R.id.et_node_title)
    protected TextView nodeTitle;

    @ViewById(R.id.et_node_body)
    protected TextView nodeBody;

    @ViewById(R.id.iv_node)
    protected ImageView imageView;

    @Extra
    protected long id = -1;

    private Node node;

    @AfterViews
    public void afterView() {
        super.afterView();
        setToolbarTitle(R.string.app_name);
        node = NodeController.get(id);

        displayHomeArrow();

        setViews();

        changeColor(Priority.values()[node.getPriority()].id());
    }

    private void setViews() {
        if (id > -1) {
            setToolbarEditButton(this);

            nodeTitle.setText(node.getTitle());
            nodeBody.setText(node.getBody());
            if (!TextUtils.isEmpty(node.getImagePath())) {
                imageView.setImageURI(Utils.getUri(node.getImagePath()));
            }
        }
    }

    @OptionsItem(android.R.id.home)
    protected void optionsBackClick() {
        onBackPressed();
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
                .viewMode(true)
                .id(id)
                .start();
    }

    @Click(R.id.fab_share)
    protected void fabShareClick() {
        //show intent chooser
    }

    @Click(R.id.iv_node)
    protected void imageViewClick() {
        if (!TextUtils.isEmpty(node.getImagePath())) {
            startIntentExtraImageActivity(node.getImagePath());//fixme if false
        }
    }

    public boolean startIntentExtraImageActivity(String imageUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + imageUrl), "image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {//onclick edit
        markToolbarButton(false);
        disableToolbar();
        disableFab();
        showEditDetailActivity();
    }

    private void showEditDetailActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EditDetailActivity_
                        .intent(DetailActivity.this)
                        .id(id)
                        .start();

                finish();
            }
        }, Constant.ACTIVITY_FINISH);
    }
}