package av.smartnotes.activity.activity_with;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerActivity;

import av.smartnotes.activity.DetailActivity_;
import av.smartnotes.activity.EditDetailActivity_;
import av.smartnotes.activity.MapsActivity_;
import av.smartnotes.util.Constant;

/**
 * Created by Artem on 18.01.2017.
 */

public class ActivityController {
    public static void startDetailActivity(Context ctx, long id) {
        startDetailActivity(ctx, id, false);
    }

    public static void startDetailActivity(Context ctx, long id, boolean fromMap) {
        DetailActivity_
                .intent(ctx)
                .id(id)
                .fromMap(fromMap)
                .start();
    }

    public static void startEditDetailActivity(Context ctx) {
        EditDetailActivity_.intent(ctx).start();
    }

    public static void startEditDetailActivity(Context ctx, long id) {
        EditDetailActivity_
                .intent(ctx)
                .id(id)
                .start();

    }

    public static void startMapsActivity(Context ctx, boolean viewMode, long id) {
        MapsActivity_
                .intent(ctx)
                .viewMode(viewMode)
                .id(id)
                .start();
    }

    public static void startMaps(Context ctx, Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        shareIntent.setData(uri);
        ctx.startActivity(Intent.createChooser(shareIntent, "view by"));
    }

    public static void startShareText(Context ctx, String text) {
        Intent shareIntent = new Intent();
        shareIntent.setType("text/*");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ctx.startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public static void startShareTextImage(Context ctx, String path, String text) {
        Intent shareIntent = new Intent();
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
        shareIntent.setType("image/*");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ctx.startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public static boolean startViewImage(Context ctx, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "image/*");
        if (intent.resolveActivity(ctx.getPackageManager()) != null) {
            ctx.startActivity(intent);
            return true;
        }
        return false;
    }

    public static void startImagePicker(Activity activity) {
        Intent intent = new Intent(activity, ImagePickerActivity.class);

        intent.putExtra(ImagePicker.EXTRA_FOLDER_MODE, true);
        intent.putExtra(ImagePicker.EXTRA_MODE, ImagePicker.MODE_SINGLE);
        intent.putExtra(ImagePicker.EXTRA_SHOW_CAMERA, false);
        intent.putExtra(ImagePicker.EXTRA_FOLDER_TITLE, "Album");
        intent.putExtra(ImagePicker.EXTRA_IMAGE_TITLE, "Tap to select images");
        intent.putExtra(ImagePicker.EXTRA_RETURN_AFTER_FIRST, true);

        activity.startActivityForResult(intent, Constant.CODE);
    }
}