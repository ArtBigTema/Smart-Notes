package av.smartnotes.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.androidannotations.annotations.AfterViews;
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
        }

        setToolbarSaveButton(this);
    }

    @OptionsItem(android.R.id.home)
    protected void optionsBackClick() {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (saveNode()) {
            markToolbarButton(false);
            disableToolbarButton();
        } else {
            // todo show dlg
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