package com.anhkhiem.shoppingonlinepostal.Navigation.data_model;

import android.widget.ImageView;
import android.widget.TextView;

public class MenuIcons {
    TypeMenu typeMenu;
    int imgIcon;
    String tvIcon;

    public MenuIcons() {
    }

    public MenuIcons(TypeMenu typeMenu, int imgIcon, String tvIcon) {
        this.typeMenu = typeMenu;
        this.imgIcon = imgIcon;
        this.tvIcon = tvIcon;
    }

    public TypeMenu getTypeMenu() {
        return typeMenu;
    }

    public void setTypeMenu(TypeMenu typeMenu) {
        this.typeMenu = typeMenu;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getTvIcon() {
        return tvIcon;
    }

    public void setTvIcon(String tvIcon) {
        this.tvIcon = tvIcon;
    }

    public enum TypeMenu{
        HOME,
        LOGOUT,
        LOGIN
    }
}
