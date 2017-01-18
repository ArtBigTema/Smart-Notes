package av.smartnotes.substance.controller;

import android.database.SQLException;
import android.util.Log;
import android.util.Pair;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.Random;

import av.smartnotes.substance.Node;
import av.smartnotes.util.Constant;
import av.smartnotes.util.Utils;

/**
 * Created by Artem on 17.01.2017.
 */
public class NodeController {
    public static List<Node> getAbsAll() {
        return new Select()
                .from(Node.class)
                .orderBy("id DESC")
                .execute();
    }

    public static List<Node> deleteAbsAll() {
        return new Delete()
                .from(Node.class)
                .execute();
    }

    public static Node get(Long mId) {
        return Node.load(Node.class, mId);
    }

    public static Node getRandomSingle() {
        return new Select()
                .from(Node.class)
                .orderBy("RANDOM()")
                .executeSingle();
    }

    public static boolean isEmpty() {
        return getRandomSingle() == null;
    }

    public static void createList() {
        deleteAbsAll();

        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            Node node = new Node();
            node.setTitle(Constant.title.concat(String.valueOf(i + 1)));
            node.setBody(Constant.body.concat(String.valueOf(i + 1)));
            node.setPriority(r.nextInt(4));
            node.save();
        }
    }

    public static void swapList(List<Node> nodes) {
        deleteAbsAll();
        for (Node node : nodes) {
            node.save();
        }
    }

    public static void updatePostDb() {
        // Cursor c = ActiveAndroid.getDatabase().rawQuery(new Select().from(Node.class).toSql(), null);
        // String[] names = c.getColumnNames();

        String[] newColumns = getColumnNamesForUpdateNodeDb(ActiveAndroid.getDatabase().getVersion());
        for (String s : newColumns) {
            try {
                ActiveAndroid.getDatabase().execSQL("ALTER TABLE Node ADD COLUMN " + s);
            } catch (SQLException e) {
                Log.w(NodeController.class.getSimpleName(), e.getMessage());
            }
        }
    }

    private static String[] getColumnNamesForUpdateNodeDb(int version) {
        Pair[] newColumns = null;
        switch (version) {
            case 2:
                newColumns = getColumnForPostDb2();
                break;
            case 3:
                newColumns = getColumnForPostDb3();
                break;
            default:
                break;
        }
        return Utils.arrPairsToArrStr(newColumns);
    }

    private static Pair[] getColumnForPostDb2() {
        Pair<String, String> c1 = Pair.create("priority", " INTEGER");
        return new Pair[]{c1};
    }

    private static Pair[] getColumnForPostDb3() {
        Pair<String, String> c1 = Pair.create("priority", " INTEGER");
        Pair<String, String> c2 = Pair.create("imagePath", " TEXT");
        return new Pair[]{c1, c2};
    }
}