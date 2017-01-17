package av.smartnotes.util;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import av.smartnotes.R;
import av.smartnotes.substance.Node;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Artem on 17.01.2017.
 */

public class Utils {
    public static final String TAG = Utils.class.getName();

    public static String toJson(Object object) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(object);
    }

    public static List<Node> parseItems(String json) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Node>>() {
        }.getType();

        try {
            return gson.fromJson(json, listType);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return Collections.emptyList();
    }

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

    public static String[] arrPairsToArrStr(Pair<String, String>[] pairs) {
        if (pairs == null) {
            return new String[0];
        }
        List<String> newNames = new LinkedList<>();
        for (Pair<String, String> p : pairs) {
            newNames.add(p.first + p.second);
        }
        return newNames.toArray(new String[newNames.size()]);
    }

    public static Uri getUri(String path) {
        File file = new File(path);
        return Uri.fromFile(file);
    }
}