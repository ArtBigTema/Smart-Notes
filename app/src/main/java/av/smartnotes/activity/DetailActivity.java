package av.smartnotes.activity;

import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;

/**
 * Created by Artem on 17.01.2017.
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends ActivityWithToolbar {
    @ViewById(R.id.et_node_title)
    protected EditText nodeTitle;

    @ViewById(R.id.et_node_body)
    protected EditText nodeBody;

    @Extra
    protected String title;
    @Extra
    protected String body;

    @AfterViews
    public void afterView() {
        super.afterView();

        displayHomeArrow();

        setViews();
    }

    private void setViews() {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(body)) {
            setAsTextEdit();
        } else {
            setAsTextView();
        }
    }

    private void setAsTextEdit() {
        nodeTitle.setEnabled(true);
        nodeBody.setEnabled(true);
        nodeTitle.setFocusable(true);
        nodeBody.setFocusable(true);
        nodeTitle.setHint(R.string.prompt_title);
        nodeBody.setHint(R.string.prompt_body);
    }

    private void setAsTextView() {
        nodeTitle.setEnabled(false);
        nodeBody.setEnabled(false);
        nodeTitle.setFocusable(false);
        nodeBody.setFocusable(false);
        nodeTitle.setText(title);
        nodeBody.setText(body);
    }

    @OptionsItem(android.R.id.home)
    protected void optionsBackClick() {
        onBackPressed();
    }

}