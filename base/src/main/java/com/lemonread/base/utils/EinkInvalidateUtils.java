package com.lemonread.base.utils;

import android.app.Activity;
import android.view.View;

/**
 * 1. 全屏刷新（EPD_FULL），仅刷新一次；刷新速度慢，闪屏一次， 无残影，16灰节显示正常
 * getActivity().getWindow().getDecorView().invalidate(-1010, -1010, -1010,
 * -1010);
 * <p>
 * 2. 更改默认刷新（EPD_PART），以后都是该刷新方式；速度一般，rk系统默认刷新方式，有轻度残影，16灰节显示正常
 * getActivity().getWindow().getDecorView().invalidate(-2020, -2020, -2020,
 * -2020);
 * <p>
 * 3. 更改默认刷新 （EPD_AUTO），以后都是该刷新方式；速度较快 ，残影较重，16灰节显示正常
 * getActivity().getWindow().getDecorView().invalidate(-2021, -2021, -2021,
 * -2021);
 * <p>
 * 4. 更改默认刷新 （EPD_A2）, 以后都是该刷新方式；速度最快，残影较重，只有黑白两种灰节
 * getActivity().getWindow().getDecorView().invalidate(-2022, -2022, -2022,
 * -2022);
 * <p>
 * 5.恢复到EPD_A2以前的默认刷新，恢复后也是全局的
 * getActivity().getWindow().getDecorView().invalidate(-2023, -2023, -2023,
 * -2023);
 * <p>
 * 6. REAGL刷新，以后都是该刷新方式；适用于纯文本界面的刷新，残影弱，
 * getActivity().getWindow().getDecorView().invalidate(-2024, -2024, -2024,
 * -2024);
 */
public class EinkInvalidateUtils {
    public enum RefreshEnum{
        EPD_DEFAULT(-2020), /*default*/
        EPD_FULL(-1010),
        EPD_PART(-2020), /*default*/
        EPD_AUTO(-2021),
        EPD_A2(-2022),
        EPD_RESUME_PRE_A2(-2023),
        EPD_REAGL(-2024);

        public int intValue;

        RefreshEnum(int value){
            this.intValue=value;
        }

    }

    public static void fullRefresh(Activity activity) {
        if (activity != null && activity.isFinishing() == false && activity.isDestroyed() == false) {
            refreshByType(activity.getWindow().getDecorView(), RefreshEnum.EPD_FULL);
        }

    }

    public static void partRefresh(Activity activity){
        if (activity != null && activity.isFinishing() == false && activity.isDestroyed() == false) {
            refreshByType(activity.getWindow().getDecorView(), RefreshEnum.EPD_PART);
        }
    }

    public static void A2Refresh(Activity activity){
        if (activity != null && activity.isFinishing() == false && activity.isDestroyed() == false) {
            refreshByType(activity.getWindow().getDecorView(), RefreshEnum.EPD_A2);
        }
    }
    public static void reaglRefresh(Activity activity){
        if (activity != null && activity.isFinishing() == false && activity.isDestroyed() == false) {
            refreshByType(activity.getWindow().getDecorView(), RefreshEnum.EPD_REAGL);
        }
    }

    public static void fullRefresh(View view) {
        refreshByType(view, RefreshEnum.EPD_FULL);
    }
    public static void refreshByType(View view, RefreshEnum refreshEnum) {
        if (view != null) {
            switch (refreshEnum){
                case EPD_DEFAULT:{
                    view.invalidate(RefreshEnum.EPD_DEFAULT.intValue,
                            RefreshEnum.EPD_DEFAULT.intValue,
                            RefreshEnum.EPD_DEFAULT.intValue,
                            RefreshEnum.EPD_DEFAULT.intValue);
                    break;
                }
                case EPD_FULL:{
                    view.invalidate(RefreshEnum.EPD_FULL.intValue,
                            RefreshEnum.EPD_FULL.intValue,
                            RefreshEnum.EPD_FULL.intValue,
                            RefreshEnum.EPD_FULL.intValue);
                    break;
                }
                case EPD_PART:{
                    view.invalidate(RefreshEnum.EPD_PART.intValue,
                            RefreshEnum.EPD_PART.intValue,
                            RefreshEnum.EPD_PART.intValue,
                            RefreshEnum.EPD_PART.intValue);
                    break;
                }
                case EPD_AUTO:{
                    view.invalidate(RefreshEnum.EPD_AUTO.intValue,
                            RefreshEnum.EPD_AUTO.intValue,
                            RefreshEnum.EPD_AUTO.intValue,
                            RefreshEnum.EPD_AUTO.intValue);
                    break;
                }
                case EPD_A2:{
                    view.invalidate(RefreshEnum.EPD_A2.intValue,
                            RefreshEnum.EPD_A2.intValue,
                            RefreshEnum.EPD_A2.intValue,
                            RefreshEnum.EPD_A2.intValue);
                    break;
                }
                case EPD_RESUME_PRE_A2:{
                    view.invalidate(RefreshEnum.EPD_RESUME_PRE_A2.intValue,
                            RefreshEnum.EPD_RESUME_PRE_A2.intValue,
                            RefreshEnum.EPD_RESUME_PRE_A2.intValue,
                            RefreshEnum.EPD_RESUME_PRE_A2.intValue);
                    break;
                }
                case EPD_REAGL:{
                    view.invalidate(RefreshEnum.EPD_REAGL.intValue,
                            RefreshEnum.EPD_REAGL.intValue,
                            RefreshEnum.EPD_REAGL.intValue,
                            RefreshEnum.EPD_REAGL.intValue);
                    break;
                }
            }

        }

    }
}
