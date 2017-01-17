package av.smartnotes.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Artem on 17.01.2017.
 */

public class FileController {
    private static final String TAG = FileController.class.getSimpleName();

    public static boolean writeTextToFile(Context context, Object text) {
        try {
            return writeJsonToFileWithName(context, text, Constant.TEXT_FILE);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public static boolean writeJsonToFileWithName(Context context, Object text, String name) throws IOException {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(text);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (oos != null) {
                oos.close();
            }
        }
        return true;
    }

    public static Object readJson(Context context) {
        try {
            return readJsonFromFile(context, Constant.TEXT_FILE);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }


    public static Object readJsonFromFile(Context context, String fileName) throws Exception {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;

        try {
            fis = context.openFileInput(fileName);

            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        return object;
    }

    public static boolean fileExist(Context context) {
        File file = context.getFileStreamPath(Constant.TEXT_FILE);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}