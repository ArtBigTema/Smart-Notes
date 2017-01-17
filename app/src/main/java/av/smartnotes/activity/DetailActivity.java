package av.smartnotes.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import av.smartnotes.substance.Node;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Constant;

/**
 * Created by Artem on 17.01.2017.
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends ActivityWithToolbar
        implements MaterialFavoriteButton.OnClickListener {
    @ViewById(R.id.et_node_title)
    protected TextView nodeTitle;

    @ViewById(R.id.et_node_body)
    protected TextView nodeBody;

    @ViewById(R.id.fab)
    protected FloatingActionButton fab;

    @Extra
    protected long id = -1;

    private Node node;

    @AfterViews
    public void afterView() {
        super.afterView();
        node = NodeController.get(id);

        setToolbarTitle(R.string.app_name);
        displayHomeArrow();

        setViews();

        //  changeColor(Color.RED);
    }

    private void setViews() {
        if (!TextUtils.isEmpty(node.getTitle())
                && !TextUtils.isEmpty(node.getBody())) {
            setToolbarEditButton(this);

            nodeTitle.setText(node.getTitle());
            nodeBody.setText(node.getBody());
        }
    }

    @OptionsItem(android.R.id.home)
    protected void optionsBackClick() {
        onBackPressed();
    }

    @Click(R.id.fab)
    protected void fabClick() {
        node.delete();
        finish();
    }

    @Override
    public void onClick(View v) {
        markToolbarButton(false);
        disableToolbar();
        fab.setEnabled(false);
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