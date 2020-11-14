 package com.example.leminhmy.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.leminhmy.CategoryModel;
import com.example.leminhmy.Category_Adapter;
import com.example.leminhmy.GirdProductLayoutAdapter;
import com.example.leminhmy.HorizontalProductScrollAdapter;
import com.example.leminhmy.HorizontalProductScrollModel;
import com.example.leminhmy.R;
import com.example.leminhmy.SliderAdapter;
import com.example.leminhmy.SliderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }

    private RecyclerView categoryRecyclerView;
    private Category_Adapter categoryAdapter;

    //////// Banner slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME =  3000;
    final private long PERIOD_TIME = 3000;
    ////////Banner Slider


    ///////// Strip Ad

    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;
    ///////// Strip Ad

    //////////////Horizontal Product layout
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllBtn;
    private RecyclerView horizontalRecyclerView;

    //////////////Horizontal Product layout
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        categoryRecyclerView = view.findViewById(R.id.category_recylerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("Link","Quần"));
        categoryModelList.add(new CategoryModel("Link","Áo"));
        categoryModelList.add(new CategoryModel("Link","Mũ"));
        categoryModelList.add(new CategoryModel("Link","Giày"));
        categoryModelList.add(new CategoryModel("Link","Dép"));
        categoryModelList.add(new CategoryModel("Link","Khác"));

        categoryAdapter = new Category_Adapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        //////// Banner slider
       bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);
       sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.iphone,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.xaomi,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.lg,"#FFE0F4F2"));

        sliderModelList.add(new SliderModel(R.drawable.iphone,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.xaomi,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.lg,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.iphone,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.xaomi,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.lg,"#FFE0F4F2"));

        sliderModelList.add(new SliderModel(R.drawable.iphone,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.xaomi,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.lg,"#FFE0F4F2"));
        sliderModelList.add(new SliderModel(R.drawable.lg,"#FFE0F4F2"));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);



        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if(i == ViewPager.SCROLL_STATE_IDLE){
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startbannerSlideShow();
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopbannerSlideShow();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    startbannerSlideShow();
                }
                return false;
            }
        });
        ////////Banner Slider



        /////////////strip Ad
        stripAdImage = view.findViewById(R.id.strip_ad_image);
        stripAdContainer = view.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.drawable.hinhanh);
        stripAdContainer.setBackgroundColor(Color.parseColor("#000000"));

        /////////////strip Ad

        //////////////Horizontal Product layout
        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalViewAllBtn = view.findViewById(R.id.horizontal_scroll_view_all_layout_btn);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.hinhanh,"Redmi 5a","SD 625 Processor","Rs.9999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.hinhanh,"Redmi 5a","SD 625 Processor","Rs.9999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.hinhanh,"Redmi 5a","SD 625 Processor","Rs.9999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.hinhanh,"Redmi 5a","SD 625 Processor","Rs.9999/-"));
        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        //////////////Horizontal Product layout

        //////////////Grid Product Layout
        TextView girdLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        Button girdLayoutViewAllBtn = view.findViewById(R.id.grid_product_layout_viewall_btn);
        GridView gridView = view.findViewById(R.id.gird_product_layout_girdview);

        gridView.setAdapter(new GirdProductLayoutAdapter(horizontalProductScrollModelList));

        //////////////Grid Product Layout

        return  view;
    }
    ////////Banner Slider


    private void pageLooper(){
        if(currentPage == sliderModelList.size() - 2)
        {
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
        if(currentPage == 1)
        {
            currentPage = sliderModelList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }

    private void startbannerSlideShow(){
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage >= sliderModelList.size()){
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }
    private void stopbannerSlideShow(){
        timer.cancel();
    }

    ////////Banner Slider

}