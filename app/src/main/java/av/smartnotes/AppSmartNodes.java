package av.smartnotes;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import org.androidannotations.annotations.EApplication;

import av.smartnotes.substance.Node;
import av.smartnotes.substance.controller.NodeController;
import av.smartnotes.util.Utils;

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
        configurationBuilder.setDatabaseVersion(4);
        ActiveAndroid.initialize(configurationBuilder.create());

        NodeController.updatePostDb();
        Utils.startLocationListener(this);
    }

    @Override
    public void onTerminate() {
        ActiveAndroid.dispose();
        super.onTerminate();
    }
}