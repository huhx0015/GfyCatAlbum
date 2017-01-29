package com.gfycat.album.application;

import android.app.Application;

import com.gfycat.album.constants.GfyConstants;
import com.gfycat.album.interfaces.ApiComponent;
import com.gfycat.album.interfaces.DaggerApiComponent;
import com.gfycat.album.modules.ApplicationModule;
import com.gfycat.album.modules.RetrofitModule;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class GfyApplication extends Application {

    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Creates the Dagger singleton component.
        apiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule(GfyConstants.BASE_URL))
                .build();
    }

    public ApiComponent getNetComponent() {
        return apiComponent;
    }
}
