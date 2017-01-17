package av.smartnotes.substance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import av.smartnotes.util.Constant;

/**
 * Created by Artem on 17.01.2017.
 */

public class CollectionsManager {
    private static volatile CollectionsManager instance;
    private List<Node> itemList;

    private CollectionsManager() {
        itemList = Collections.synchronizedList(new ArrayList<Node>());
    }

    public static CollectionsManager getInstance() {
        if (instance == null) {
            instance = new CollectionsManager();
        }
        return instance;
    }

    public List<Node> getItemList() {
        return itemList;
    }

    public boolean isEmpty() {
        return itemList == null || itemList.size() == 0;
    }

    public void add(Node node) {
        itemList.add(node);
    }

    public void add(CharSequence title, CharSequence body) {
        itemList.add(0, Node.construct(title.toString(), body.toString()));
    }

    public void set(int id, CharSequence title, CharSequence body) {
        if (id >= itemList.size() || id < 0) {
            return;
        }

        itemList.set(id, Node.construct(title.toString(), body.toString()));
    }

    public void remove(int id) {
        if (id >= itemList.size() || id < 0) {
            return;
        }

        itemList.remove(id);
    }

    public void createList() {
        for (int i = 0; i < 10; i++) {
            add(Constant.title.concat(String.valueOf(i + 1)),
                    Constant.body.concat(String.valueOf(i + 1)));
        }
    }

    public void clear() {
        itemList.clear();
    }

    public String toText() {
        StringBuilder sb = new StringBuilder();
        for (Node node : itemList) {
            sb.append(node.toText());
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
