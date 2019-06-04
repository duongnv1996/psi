package com.skynet.psi.network.api;


import com.skynet.psi.ui.base.OnFinishListener;
import com.skynet.psi.utils.AppConstant;

public class  ExceptionHandler<T> {
    private OnFinishListener listener;
    private ApiResponse<T> apiResponse;



    public   ExceptionHandler(OnFinishListener onFinishListener, ApiResponse<T> apiResponse) {
       this.apiResponse =  apiResponse;
        listener = onFinishListener;
    }

    public void excute() {
        switch (apiResponse.getCode()) {
            case AppConstant.CODE_EXPIRED: {
                listener.onErrorAuthorization();
                break;
            }
            case AppConstant.CODE_NOT_FOUND: {
                listener.onError(apiResponse.getMessage());
                break;
            }
            case AppConstant.CODE_SERVER_ERROR: {
                listener.onErrorApi(apiResponse.getMessage());
                break;

            }
            default:
                listener.onError(apiResponse.getMessage());
                break;

        }
    }
}
