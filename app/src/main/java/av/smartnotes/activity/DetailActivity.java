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
import av.smartnotes.substance.CollectionsManager;
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
    protected String title;
    @Extra
    protected String body;
    @Extra
    protected int id = -1;

    @AfterViews
    public void afterView() {
        super.afterView();

        setToolbarTitle(R.string.app_name);
        displayHomeArrow();

        setViews();
    }

    private void setViews() {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
            setToolbarEditButton(this);

            nodeTitle.setText(title);
            nodeBody.setText(body);
        }
    }

    @OptionsItem(android.R.id.home)
    protected void optionsBackClick() {
        onBackPressed();
    }

    @Click(R.id.fab)
    protected void fabClick() {
        CollectionsManager.getInstance().remove(id);
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
                        .title(title)
                        .body(body)
                        .id(id)
                        .start();

                finish();
            }
        }, Constant.ACTIVITY_FINISH);
    }
}