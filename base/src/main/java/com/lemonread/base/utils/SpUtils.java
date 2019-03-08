package com.lemonread.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;


import com.lemonread.base.application.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * @desc 使用SharedPreferences保存信息的工具类
 * @author zhao
 * @time 2019/3/5 17:10
 */
public class SpUtils {
    private static final String TAG = "SpUtils";
    private static SharedPreferences mPreferences;

    //获取sp对象
    public static SharedPreferences getPreferences() {
        if (mPreferences == null) {
            // 获取sp
            mPreferences = BaseApplication.getContext().getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    //保存int类型数据
    public static void putInt(String key, int value) {
        // 获取sp
        SharedPreferences sp = getPreferences();
        // 获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    //获取int类型数据
    public static int getInt(String key, int defValue) {
        // 获取sp
        SharedPreferences sp = getPreferences();

        return sp.getInt(key, defValue);
    }

    private static final String USER_ID_KEY = "user_id_key";
    private static final String USER_TOKEN_KEY = "user_token_key";
    //保存userId
    public static void putUserId(int userId) {
        // 获取sp
        SharedPreferences sp = getPreferences();
        // 获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(USER_ID_KEY, userId);
        edit.apply();
    }
    //获取userId
    public static int getUserId() {
        // 获取sp
        SharedPreferences sp = getPreferences();

        return sp.getInt(USER_ID_KEY, 0);
    }
    //获取token
    public static String getToken() {
        // 获取sp
        SharedPreferences sp = getPreferences();

        return sp.getString(USER_TOKEN_KEY, "");
    }

    //保存token
    public static void putToken(String token) {
        // 获取sp
        SharedPreferences sp = getPreferences();
        // 获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(USER_TOKEN_KEY, token);
        edit.apply();
    }

    //保存long类型数据
    public static void putLong(String key, long value) {
        // 获取sp
        SharedPreferences sp = getPreferences();
        // 获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    //获取long类型数据
    public static long getLong(String key, int defValue) {
        // 获取sp
        SharedPreferences sp = getPreferences();

        return sp.getLong(key, defValue);
    }

    //保存boolean类型数据
    public static void putBoolean(String key, boolean value) {
        // 获取sp
        SharedPreferences sp = getPreferences();
        // 获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    //获取boolean类型数据
    public static boolean getBoolean(String key, boolean defValue) {
        // 获取sp
        SharedPreferences sp = getPreferences();

        return sp.getBoolean(key, defValue);
    }

    //保存String类型数据
    public static void putString(String key, String value) {
        // 获取sp
        SharedPreferences sp = getPreferences();
        // 获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //获取String类型数据
    public static String getString(String key, String defValue) {
        // 获取sp
        SharedPreferences sp = getPreferences();

        return sp.getString(key, defValue);
    }
    /**
     * 存放实体类以及任意类型
     *
     * @param key
     * @param obj
     */
    public static void putBean(String key, Object obj) throws IOException {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                SharedPreferences sp = getPreferences();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public static Object getBean(String key) {
        Object obj = null;
        SharedPreferences sp = getPreferences();
        try {
            String base64 = sp.getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
