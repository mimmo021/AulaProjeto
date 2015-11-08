package dfj.aulaprojeto;

import android.app.Application;

import com.squareup.otto.Bus;

/**
 * Created by mimmo on 04/11/2015.
 */
public class ReceitaApp extends Application {
    Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mBus = new Bus();
    }

    public Bus getBus(){

        return mBus;
    }
}
