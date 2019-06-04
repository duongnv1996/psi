package com.skynet.psi.ui.base;


import com.skynet.psi.network.api.ApiService;

/**
 * Created by thaopt on 12/1/17.
 */

public abstract class Interactor {
    private ApiService mService;
    public Interactor(){
        mService = createService();

    }
    public abstract ApiService createService();
    public ApiService getmService() {
        return mService;
    }
}
