package com.lemonread.base.utils;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;


/**
 * @desc
 * @author zhao
 * @time 2019/3/5 17:06
 */

public class TurnPageUtils {
    /**
     * 给listview 添加 setOnKeyListener 事件处理, 解决的bug: 进入列表页后的第一次翻页键事件不起作用
     * @param listview
     */
    public static void setListViewOnKeyListener(final AbsListView listview) {
        listview.setFocusableInTouchMode(true);
        listview.requestFocus();
        listview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LogUtils.i("keyCode=" + keyCode);
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
                        return true;
                    }else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN){
                        return true;
                    }
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
                        listview.smoothScrollBy(-listview.getHeight(), 0);
                        return true;
                    } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
                        listview.smoothScrollBy(listview.getHeight(), 0);
                        return true;
                    }
                }
                return false;
            }
        });
        
    }

    public interface TurnPageListener {
        public void onPageUpPrevious();

        public void onPageDownNext();

        public void onPageLeftPrevious();

        public void onPageRightNext();

    }

    public static void setFragmentOnKeyListener(Fragment fragment, final TurnPageListener turnPageListener) {
        if (fragment != null && fragment.getView() != null) {
            fragment.getView().setFocusableInTouchMode(true);
            fragment.getView().requestFocus();
            fragment.getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
                            if (turnPageListener != null) {
                                LogUtils.i("onPageLeftPrevious");
                                turnPageListener.onPageLeftPrevious();
                            }
                            return true;
                        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
                            if (turnPageListener != null) {
                                LogUtils.i("onPageRightNext");
                                turnPageListener.onPageRightNext();
                            }
                            return true;
                        }

                    }
                    return false;
                }
            });
        }
    }
//    public static void setListviewOnKeyListener(final AbsListView absListView) {
//        if (absListView!= null) {
//            absListView.setFocusableInTouchMode(true);
//            absListView.requestFocus();
//            absListView.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_UP) {
//                        if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
//                            LogUtils.i("onPageLeftPrevious");
//                            absListView.smoothScrollBy(-absListView.getHeight(), 0);
//                            return true;
//                        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
//                            LogUtils.i("onPageRightNext");
//                            absListView.smoothScrollBy(absListView.getHeight(), 0);
//                            return true;
//                        }
//
//                    }
//                    return false;
//                }
//            });
//        }
//    }
//    public static void setListviewOnKeyListener(final Activity activity, final AbsListView absListView) {
//        if (activity!=null && absListView!= null) {
//            activity.getWindow().getDecorView().setFocusable(false);
//            activity.getWindow().getDecorView().setFocusableInTouchMode(true);
//            activity.getWindow().getDecorView().requestFocus();
//            activity.getWindow().getDecorView().setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_UP) {
//                        if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
//                            LogUtils.i("onPageLeftPrevious");
//                            absListView.smoothScrollBy(-absListView.getHeight(), 0);
//                            return true;
//                        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
//                            LogUtils.i("onPageRightNext");
//                            absListView.smoothScrollBy(absListView.getHeight(), 0);
//                            return true;
//                        }
//
//                    }
//                    return false;
//                }
//            });
//        }
//    }
//    public static void setListviewOnKeyListener(final Fragment fragment, final AbsListView absListView) {
//        if (fragment!=null && absListView!= null) {
//            fragment.getView().setFocusableInTouchMode(true);
//            fragment.getView().requestFocus();
//            fragment.getView().setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_UP) {
//                        if (keyCode == KeyEvent.KEYCODE_PAGE_UP) {
//                            LogUtils.i("onPageLeftPrevious");
//                            absListView.smoothScrollBy(-absListView.getHeight(), 0);
//                            return true;
//                        } else if (keyCode == KeyEvent.KEYCODE_PAGE_DOWN) {
//                            LogUtils.i("onPageRightNext");
//                            absListView.smoothScrollBy(absListView.getHeight(), 0);
//                            return true;
//                        }
//
//                    }
//                    return false;
//                }
//            });
//        }
//    }

    public static void setViewTurnPageListener(View view, final TurnPageListener turnPageListener) {
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            float mPosX;
            float mPosY;
            float mCurPosX;
            float mCurPosY;
            float distanceX;
            float distanceY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        distanceX = mCurPosX - mPosX;
                        distanceY = mCurPosY - mPosY;
                        if (Math.abs(distanceX) > Math.abs(distanceY)) {//横滑
                            if (distanceX > 0 && (Math.abs(distanceX) > 50)) {//右滑
                                if (turnPageListener != null) {
                                    turnPageListener.onPageLeftPrevious();
                                    return true;
                                }
                            } else if (distanceX < 0 && (Math.abs(distanceX) > 50)) {//左滑
                                if (turnPageListener != null) {

                                    turnPageListener.onPageRightNext();
                                    return true;
                                }
                            }
                        } else {//纵滑
                            if (distanceY > 0 && (Math.abs(distanceY) > 50)) {//下滑
                                if (turnPageListener != null) {
                                    //手指下滑, 上一页
                                    turnPageListener.onPageUpPrevious();
                                    return true;
                                }
                            } else if (distanceY < 0 && (Math.abs(distanceY) > 50)) {//上滑
                                if (turnPageListener != null) {
                                    //手指上滑, 下一页
                                    turnPageListener.onPageDownNext();
                                    return true;
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        };
        view.setOnTouchListener(onTouchListener);

    }
    public static void setListViewTurnPageListener(ListView listview, final TurnPageListener turnPageListener) {
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            float mPosX;
            float mPosY;
            float mCurPosX;
            float mCurPosY;
            float distanceX;
            float distanceY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        distanceX = mCurPosX - mPosX;
                        distanceY = mCurPosY - mPosY;
                        if (Math.abs(distanceX) > Math.abs(distanceY)) {//横滑
                            if (distanceX > 0 && (Math.abs(distanceX) > 50)) {//右滑
                                if (turnPageListener != null) {
                                    turnPageListener.onPageLeftPrevious();
                                    return true;
                                }
                            } else if (distanceX < 0 && (Math.abs(distanceX) > 50)) {//左滑
                                if (turnPageListener != null) {

                                    turnPageListener.onPageRightNext();
                                    return true;
                                }
                            }
                        } else {//纵滑
                            if (distanceY > 0 && (Math.abs(distanceY) > 50)) {//下滑
                                if (turnPageListener != null) {
                                    //手指下滑, 上一页
                                    turnPageListener.onPageUpPrevious();
                                    return true;
                                }
                            } else if (distanceY < 0 && (Math.abs(distanceY) > 50)) {//上滑
                                if (turnPageListener != null) {
                                    //手指上滑, 下一页
                                    turnPageListener.onPageDownNext();
                                    return true;
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        };
        listview.setOnTouchListener(onTouchListener);

    }
    public static void setGridViewTurnPageListener(GridView gridView, final TurnPageListener turnPageListener) {
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            float mPosX;
            float mPosY;
            float mCurPosX;
            float mCurPosY;
            float distanceX;
            float distanceY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        distanceX = mCurPosX - mPosX;
                        distanceY = mCurPosY - mPosY;
                        if (Math.abs(distanceX) > Math.abs(distanceY)) {//横滑
                            if (distanceX > 0 && (Math.abs(distanceX) > 50)) {//右滑
                                if (turnPageListener != null) {
                                    turnPageListener.onPageLeftPrevious();
                                    return true;
                                }
                            } else if (distanceX < 0 && (Math.abs(distanceX) > 50)) {//左滑
                                if (turnPageListener != null) {

                                    turnPageListener.onPageRightNext();
                                    return true;
                                }
                            }
                        } else {//纵滑
                            if (distanceY > 0 && (Math.abs(distanceY) > 50)) {//下滑
                                if (turnPageListener != null) {
                                    //手指下滑, 上一页
                                    turnPageListener.onPageUpPrevious();
                                    return true;
                                }
                            } else if (distanceY < 0 && (Math.abs(distanceY) > 50)) {//上滑
                                if (turnPageListener != null) {
                                    //手指上滑, 下一页
                                    turnPageListener.onPageDownNext();
                                    return true;
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        };
        gridView.setOnTouchListener(onTouchListener);
    }
}
