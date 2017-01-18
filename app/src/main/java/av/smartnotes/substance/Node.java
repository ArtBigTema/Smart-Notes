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

    @Expose
    @Column(name = "priority")
    private int priority;

    @Expose
    @Column(name = "imagePath")
    private String imagePath;

    @Expose
    @Column(name = "lat")
    private double lat;
    @Expose
    @Column(name = "lng")
    private double lng;

    public Node() {//Don't delete crash at first init
        super();
        priority = 0;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
                " title = '" + title + '\'' +
                ", body = '" + body + '\'' +
                ", geo = '" + lat + ':' + lng + '\'' +
                "; ";
    }
}