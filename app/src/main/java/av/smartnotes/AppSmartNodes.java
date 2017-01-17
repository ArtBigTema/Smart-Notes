package av.smartnotes;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import org.androidannotations.annotations.EApplication;

import av.smartnotes.substance.Node;
import av.smartnotes.substance.controller.NodeController;

/**
 * Created by Artem on 17.01.2017.
 */
@EApplication
public class AppSmartNodes extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClass(Node.class);
        configurationBuilder.setDatabaseVersion(2);
        ActiveAndroid.initialize(configurationBuilder.create());

        NodeController.updatePostDb();
    }

    @Override
    public void onTerminate() {
        ActiveAndroid.dispose();
        super.onTerminate();
    }
}