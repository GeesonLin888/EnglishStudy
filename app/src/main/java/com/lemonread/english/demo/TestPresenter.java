package com.lemonread.english.demo;

import com.lemonread.base.net.HttpRequestLoader;
import com.lemonread.base.net.ResultCallBack;
import com.lemonread.base.net.RetrofitServiceManager;
import com.lemonread.base.utils.LogUtils;
import com.lemonread.base.vp.BasePresenter;
import com.lemonread.base.vp.BaseRxPresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class TestPresenter extends BaseRxPresenter<Contract.TestView> implements Contract.Presenter{
    private MyService mMyService;
    @Inject
    public TestPresenter(){
        mMyService = RetrofitServiceManager.getInstance().create(MyService.class);
    }

    /*public TestPresenter(int a,int b){
        LogUtils.i("a == "+ a+"   b=="+b);
        mMyService = RetrofitServiceManager.getInstance().create(MyService.class);
    }*/
    @Override
    public void getData(int currentPage,int pageSize) {
        Map<String,Object> params = new HashMap<>();
        params.put("currentPage",currentPage+"");
        params.put("pageSize",pageSize+"");
        params.putAll(HttpRequestLoader.getInstance().addCommonParams());
         addSubscribe(HttpRequestLoader.getInstance().requestToRetobj(mMyService.getPlanList(params), new ResultCallBack<TestBean>() {
             @Override
             public void onNext(TestBean testBean) {
                 LogUtils.i("==============testBean");
                 if(testBean != null){
                     getView().setData();
                 }
             }

             @Override
             public void onError(Throwable e,int errcode,String msg) {
                 LogUtils.i("============errcode=="+errcode+"  msg=="+msg);
             }
         }));
    }
}
