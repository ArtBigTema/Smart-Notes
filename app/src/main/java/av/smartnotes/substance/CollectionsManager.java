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

    public void add(Node node) {
        itemList.add(node);
    }

    public void add(String title, String body) {
        itemList.add(Node.construct(title, body));
    }

    public void createList() {
        for (int i = 0; i < 10; i++) {
            add(Constant.title.concat(String.valueOf(i + 1)),
                    Constant.body.concat(String.valueOf(i + 1)));
        }
    }
}
