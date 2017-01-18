package av.smartnotes.activity.activity_with;

import android.view.KeyEvent;

import com.github.clans.fab.FloatingActionMenu;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;

/**
 * Created by Artem on 18.01.2017.
 */
@EActivity
public class ActivityWithFabMenu extends ActivityWithToolbar {
    @ViewById(R.id.fab_menu)
    protected FloatingActionMenu fab;

    public void afterView() {
        super.afterView();
        fab.setClosedOnTouchOutside(true);
    }

    protected void disableFab(){
        fab.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.showMenu(true);
    }

    @Override
    protected void onPause() {
        fab.hideMenu(true);
        super.onPause();
    }

    protected void toggleFab(boolean hide) {
        if (hide) {
            fab.hideMenu(true);
        } else {
            fab.showMenu(true);
        }
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                fab.toggle(true);
                return true;
        }
        return super.onKeyDown(keycode, e);
    }

    @Override
    public void onBackPressed() {
        if (fab.isOpened()) {
            fab.toggle(true);
        } else {
            super.onBackPressed();
        }
    }
}