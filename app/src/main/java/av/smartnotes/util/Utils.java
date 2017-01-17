package av.smartnotes.util;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Artem on 17.01.2017.
 */

public class Utils {
    public static boolean validText(CharSequence text) {
        return !TextUtils.isEmpty(text) && text.length() > 4;
    }


    public static void showTooltip(View anchorView, String text, int backgroundColor) {
        new SimpleTooltip.Builder(anchorView.getContext())
                .anchorView(anchorView)
                .text(text)
                .gravity(Gravity.TOP)
                .animated(false)
                .dismissOnOutsideTouch(true)
                .background(backgroundColor)
                .textColor(Color.WHITE)
                .arrowColor(backgroundColor)
                .build()
                .show();
    }
}