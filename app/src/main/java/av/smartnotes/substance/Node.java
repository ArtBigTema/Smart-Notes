package av.smartnotes.substance;

/**
 * Created by Artem on 17.01.2017.
 */

public class Node {
    private String title;
    private String body;

    public Node(String title, String body) {
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
        return new Node(title, body);
    }

    @Override
    public String toString() {
        return "Node{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}