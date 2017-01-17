package av.smartnotes.activity.activity_with;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import av.smartnotes.R;
import av.smartnotes.util.Constant;

/**
 * Created by Artem on 17.01.2017.
 */

@EActivity
public class ActivityWithToolbar extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;

    @ViewById(R.id.toolbar_button)
    protected MaterialFavoriteButton toolbarButton;

    public void afterView() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setToolbarTitle(int title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        } else {
            toolbar.setTitle(title);
        }
        setToolbarTitleParam();
    }

    private void setToolbarTitleParam() {
        if (toolbar.getChildAt(1) instanceof TextView) {
            toolbar.getChildAt(1).setBackgroundColor(Color.TRANSPARENT);
            ViewGroup.LayoutParams params = toolbar.getChildAt(1).getLayoutParams();
            params.height = Toolbar.LayoutParams.WRAP_CONTENT;
            params.width = Toolbar.LayoutParams.MATCH_PARENT;
            toolbar.getChildAt(1).setLayoutParams(params);
        }
    }

    protected void displayHomeClose() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_close);
        }
        displayHomeArrow();
    }


    protected void displayHomeArrow() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setToolbarSaveButton(MaterialFavoriteButton.OnClickListener listener) {
        setToolbarSaveButton(listener, true);
    }

    protected void setToolbarSaveButton(MaterialFavoriteButton.OnClickListener listener, boolean favorite) {
        toolbarButton.setVisibility(View.VISIBLE);
        toolbarButton.setFavoriteResource(R.drawable.icon_floppy);
        toolbarButton.setNotFavoriteResource(R.drawable.icon_ok);
        toolbarButton.setFavorite(favorite);
        toolbarButton.setOnClickListener(listener);
        toolbarButton.setAnimateFavorite(true);
        toolbarButton.setAnimateUnfavorite(true);
        toolbarButton.setRotationDuration(Constant.DURATION_ROTATION);
    }

    protected void setToolbarEditButton(MaterialFavoriteButton.OnClickListener listener) {
        toolbarButton.setVisibility(View.VISIBLE);
        toolbarButton.setFavoriteResource(R.drawable.icon_edit);
        toolbarButton.setOnClickListener(listener);
        toolbarButton.setNotFavoriteResource(R.drawable.icon_ok);
        toolbarButton.setFavorite(true);
        toolbarButton.setAnimateFavorite(true);
        toolbarButton.setAnimateUnfavorite(true);
        toolbarButton.setRotationDuration(Constant.DURATION_ROTATION);
    }

    protected void setToolbarExportButton(MaterialFavoriteButton.OnClickListener clickListener,
                                          MaterialFavoriteButton.OnLongClickListener listener) {
        toolbarButton.setVisibility(View.VISIBLE);
        toolbarButton.setFavoriteResource(R.drawable.icon_file_export);
        toolbarButton.setOnClickListener(clickListener);
        toolbarButton.setOnLongClickListener(listener);
        toolbarButton.setNotFavoriteResource(R.drawable.icon_ok);
        toolbarButton.setFavorite(true);
        toolbarButton.setAnimateFavorite(true);
        toolbarButton.setAnimateUnfavorite(true);
        toolbarButton.setRotationDuration(Constant.DURATION_ROTATION);
    }

    protected void markToolbarButton(boolean turn) {
        toolbarButton.setFavorite(turn);
    }

    protected void disableToolbar() {
        //  toolbarButton.setEnabled(false);
        toolbar.setEnabled(false);
    }
}