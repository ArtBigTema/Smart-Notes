package av.smartnotes.substance;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Artem on 17.01.2017.
 */

@Table(name = "Node", id = "id")
public class Node extends Model {

    @Expose
    @Column(name = "title")
    private String title;

    @Expose
    @Column(name = "body")
    private String body;

    public Node() {//Don't delete crash at first init
        super();
    }

    public Node(String title, String body) {
        this();
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static Node construct(String title, String body) {
        // long ms = System.currentTimeMillis();
        Node node = new Node(title, body);
        node.save();
        return node;
    }

    @Override
    public String toString() {
        return "Node{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String toText() {
        // return toString();
        return "Node:" +
                " title='" + title + '\'' +
                ", body='" + body + '\'' +
                "; ";
    }
}