package av.smartnotes.util;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import av.smartnotes.R;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Artem on 17.01.2017.
 */

public class Utils {
    public static boolean validText(CharSequence text) {
        return !TextUtils.isEmpty(text) && text.length() > 4;
    }

    public static void showTooltip(View view, String text, int gravity) {
        new SimpleTooltip.Builder(view.getContext())
                .anchorView(view)
                .text(text)
                .gravity(gravity)
                .animated(false)
                .dismissOnOutsideTouch(true)
                .background(ContextCompat.getColor(view.getContext(), R.color.colorPrimary))
                .textColor(Color.WHITE)

                .arrowColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary))
                .build()
                .show();
    }

    public static void showTooltipTop(View anchorView, String text) {
        showTooltip(anchorView, text, Gravity.TOP);
    }
}