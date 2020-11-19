package com.anhkhiem.shoppingonlinepostal.Navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anhkhiem.shoppingonlinepostal.Main.BaseActivity;
import com.anhkhiem.shoppingonlinepostal.Navigation.data_model.MenuIcons;
import com.anhkhiem.shoppingonlinepostal.R;

import java.util.List;

public class Adapter_Icons extends BaseAdapter {
    private Context context;
    List<MenuIcons> lstIcon;

    public Adapter_Icons(Context context,List<MenuIcons> lstIcon){
        this.context = context;
        this.lstIcon = lstIcon;
    }


    @Override
    public int getCount() {
        return lstIcon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolders viewHolders;
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item_layout,null);
            viewHolders = new ViewHolders();
            viewHolders.imgMenuIcon = view.findViewById(R.id.rowImageIcon);
            viewHolders.txtMenu = view.findViewById(R.id.txtIcon);
            view.setTag(viewHolders);
        }
        else {
            viewHolders = (ViewHolders) view.getTag();
        }
        viewHolders.imgMenuIcon.setImageResource(lstIcon.get(i).getImgIcon());
        viewHolders.txtMenu.setText(lstIcon.get(i).getTvIcon());
        return view;
    }

    private class ViewHolders{
        ImageView imgMenuIcon;
        TextView txtMenu;
    }
}
