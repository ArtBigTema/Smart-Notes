package av.smartnotes.substance.controller;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import av.smartnotes.substance.Node;
import av.smartnotes.util.Constant;

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
        for (int i = 0; i < 10; i++) {
            Node.construct(Constant.title.concat(String.valueOf(i + 1)),
                    Constant.body.concat(String.valueOf(i + 1)));
        }
    }

    public static void swapList(List<Node> nodes) {
        deleteAbsAll();
        for (Node node : nodes) {
            node.save();
        }
    }
}