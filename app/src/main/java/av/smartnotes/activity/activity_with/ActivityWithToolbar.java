package av.smartnotes.activity.activity_with;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;

/**
 * Created by Artem on 17.01.2017.
 */

@EActivity
public class ActivityWithToolbar extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    public void afterView() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setToolbarTitle(CharSequence title) {
        toolbar.setTitle(title);
    }

    public void setToolbarTitle(int title) {
        toolbar.setTitle(title);
    }

    protected void displayHomeArrow() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}