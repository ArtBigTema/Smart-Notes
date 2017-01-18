package av.smartnotes.substance;

import android.graphics.Color;

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

    @Expose
    @Column(name = "priority")
    private int priority;

    @Expose
    @Column(name = "imagePath")
    private String imagePath;

    public Node() {//Don't delete crash at first init
        super();
        priority = Color.WHITE;
    }

    public Node(String title, String body) {
        this();
        this.title = title;
        this.body = body;
        imagePath = "";
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

    public int getPriority() {
        return priority;
    }

    public void setColor(int color) {
        setPriority(Priority.fromId(color).ordinal());
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static Node construct(String title, String body, int color, String path) {
        Node node = new Node(title, body);
        node.setPriority(Priority.fromId(color).ordinal());
        node.setImagePath(path);
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