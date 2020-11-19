package com.anhkhiem.shoppingonlinepostal.PermissionUtil;

import android.content.Context;

import com.anhkhiem.shoppingonlinepostal.Cache.SaveCache;

public class PermissionUserUtil {
    public static final String ADMIN_PERMISSION ="Admin";
    public static final String MANAGER_PERMISSION ="Manager";
    public static final String USER_PERMISSION ="User";
    private static PermissionUserUtil instance;
    Context context;

    public PermissionUserUtil(Context context){
        this.context = context;
    }

    public static PermissionUserUtil with(Context context){
        if (instance == null){
            instance = new PermissionUserUtil(context);
        }
        return instance;
    }

    public void checkPermission(IPermissionUser permissionUser){
        if (SaveCache.getInstance(context).isLogin()){
            String role = SaveCache.getInstance(context).getUser().getRoles();
            if (role.equals(ADMIN_PERMISSION)){
                permissionUser.admin();
            }
            if (role.equals(MANAGER_PERMISSION)){
                permissionUser.manager();
            }
            if (role.equals(USER_PERMISSION)){
                permissionUser.user();
            }
        }
    }

    public interface IPermissionUser {
        void admin();

        void manager();

        void user();
    }
}
