package av.smartnotes.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import av.smartnotes.R;
import av.smartnotes.activity.activity_with.ActivityWithToolbar;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActivityWithToolbar {

    @AfterViews
    public void afterView() {
        setToolbarTitle(R.string.app_name);
    }
}