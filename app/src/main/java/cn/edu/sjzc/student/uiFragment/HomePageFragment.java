package cn.edu.sjzc.student.uiFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.uiActivity.HomeInfoActivity;
import cn.edu.sjzc.student.uiActivity.LoginActivity;
import cn.edu.sjzc.student.uiActivity.RetroactionActivity;

public class HomePageFragment extends BaseFragment {

    public static BaseFragment newInstance(int index) {
        BaseFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        fragment.setIndex(index);
        return fragment;
    }

    private LinearLayout home_news, home_info, home_exchange;

    private int[] images = null;
    private String[] titles = null;

    private ArrayList<ImageView> imageSource = null;
    private ArrayList<View> dots = null;
    private TextView title = null,tab1_oneitem, tab1_twoitem, tab1_threeitem, tab1_fouritem;
    private ViewPager viewPager,viewPagerTab;
    private MyPagerAdapter adapter;
    private int currPage = 0;
    private int oldPage = 0;
    private View dot1;
    private int offset = 0;// 动画图片偏移量
    private int curr = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private ImageView tab1_oneitem_iv, tab1_twoitem_iv, tab1_threeitem_iv, tab1_fouritem_iv;
    private List<Fragment> fragments;
    private int selectedColor, unSelectedColor, tabSelectedColor, tabUnSelectedColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = myInflater.inflate(R.layout.fragment_homepage, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init_viewpager();
        initView();
        InitTextView();
        InitViewPager();


    }

    private void InitViewPager() {
        viewPagerTab = (ViewPager) getActivity().findViewById(R.id.viewPagerTab);
        Fragment oneItemFragment = new OneItemFragment();
        Fragment twoItemFragment = new TwoItemFragment();
//        Fragment fourItemFragment = new FourItemFragment();
//        Fragment threeItemFragment = new ThreeItemFragment();

        fragments = new ArrayList<Fragment>();
        fragments.add(oneItemFragment);
        fragments.add(twoItemFragment);
//        fragments.add(fourItemFragment);

//        fragments.add(threeItemFragment);

        viewPagerTab.setAdapter(new tabPagerAdapter(getChildFragmentManager(), fragments));
        viewPagerTab.setCurrentItem(0);
        viewPagerTab.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void InitTextView() {
        //viewpager
        this.selectedColor = getActivity().getResources().getColor(R.color.tab_title_pressed_color);
        this.unSelectedColor = getActivity().getResources().getColor(R.color.tab_title_normal_color);
        this.tabSelectedColor = getActivity().getResources().getColor(R.color.tab_select);
        this.tabUnSelectedColor = getActivity().getResources().getColor(R.color.tab_unselect);

        tab1_oneitem = (TextView) getActivity().findViewById(R.id.tab1_oneitem);
        tab1_twoitem = (TextView) getActivity().findViewById(R.id.tab1_twoitem);
//        tab1_threeitem = (TextView) getActivity().findViewById(R.id.tab1_threeitem);
//        tab1_fouritem = (TextView) getActivity().findViewById(R.id.tab1_fouritem);

        tab1_oneitem_iv = (ImageView) getActivity().findViewById(R.id.tab1_oneitem_iv);
        tab1_twoitem_iv = (ImageView) getActivity().findViewById(R.id.tab1_twoitem_iv);
//        tab1_threeitem_iv = (ImageView) getActivity().findViewById(R.id.tab1_threeitem_iv);
//        tab1_fouritem_iv = (ImageView) getActivity().findViewById(R.id.tab1_fouritem_iv);

        tab1_oneitem.setTextColor(selectedColor);
        tab1_twoitem.setTextColor(unSelectedColor);
//        tab1_threeitem.setTextColor(unSelectedColor);
//        tab1_fouritem.setTextColor(unSelectedColor);

        tab1_oneitem_iv.setBackgroundColor(tabSelectedColor);
        tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//        tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//        tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);

        tab1_oneitem.setText("文传");
        tab1_twoitem.setText("物电");
//        tab1_threeitem.setText("化工");
//        tab1_fouritem.setText("教育");

        tab1_oneitem.setOnClickListener(new tabOnClickListener(0));
        tab1_twoitem.setOnClickListener(new tabOnClickListener(1));
//        tab1_threeitem.setOnClickListener(new tabOnClickListener(2));
//        tab1_fouritem.setOnClickListener(new tabOnClickListener(3));

        tab1_oneitem_iv.setOnClickListener(new tabOnClickListener(0));
        tab1_twoitem_iv.setOnClickListener(new tabOnClickListener(1));
//        tab1_threeitem_iv.setOnClickListener(new tabOnClickListener(2));
//        tab1_fouritem_iv.setOnClickListener(new tabOnClickListener(3));
    }


    public class MyOnPageChangeListener implements OnPageChangeListener {
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量


        public void onPageScrollStateChanged(int index) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * curr, one
                    * index, 0, 0);// 显然这个比较简洁，只有一行代码。
            curr = index;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);

            switch (index) {
                case 0:
                    tab1_oneitem.setTextColor(selectedColor);
                    tab1_twoitem.setTextColor(unSelectedColor);
//                    tab1_threeitem.setTextColor(unSelectedColor);
//                    tab1_fouritem.setTextColor(unSelectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);

                    break;
                case 1:
                    tab1_oneitem.setTextColor(unSelectedColor);
                    tab1_twoitem.setTextColor(selectedColor);
//                    tab1_threeitem.setTextColor(unSelectedColor);
//                    tab1_fouritem.setTextColor(unSelectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabUnSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);
                    break;
                case 2:
                    tab1_oneitem.setTextColor(unSelectedColor);
                    tab1_twoitem.setTextColor(unSelectedColor);
//                    tab1_threeitem.setTextColor(selectedColor);
//                    tab1_fouritem.setTextColor(unSelectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabUnSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);
                    break;
                case 3:
                    tab1_oneitem.setTextColor(unSelectedColor);
                    tab1_twoitem.setTextColor(unSelectedColor);
//                    tab1_threeitem.setTextColor(unSelectedColor);
//                    tab1_fouritem.setTextColor(selectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabUnSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabSelectedColor);
                    break;
            }
        }
    }


    private class tabOnClickListener implements OnClickListener {
        private int index = 0;

        private tabOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            switch (index) {
                case 0:
                    tab1_oneitem.setTextColor(selectedColor);
                    tab1_twoitem.setTextColor(unSelectedColor);
//                    tab1_threeitem.setTextColor(unSelectedColor);
//                    tab1_fouritem.setTextColor(unSelectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);

                    break;
                case 1:
                    tab1_oneitem.setTextColor(unSelectedColor);
                    tab1_twoitem.setTextColor(selectedColor);
//                    tab1_threeitem.setTextColor(unSelectedColor);
//                    tab1_fouritem.setTextColor(unSelectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabUnSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);
                    break;
                case 2:
                    tab1_oneitem.setTextColor(unSelectedColor);
                    tab1_twoitem.setTextColor(unSelectedColor);
//                    tab1_threeitem.setTextColor(selectedColor);
//                    tab1_fouritem.setTextColor(unSelectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabUnSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabUnSelectedColor);
                    break;
                case 3:
                    tab1_oneitem.setTextColor(unSelectedColor);
                    tab1_twoitem.setTextColor(unSelectedColor);
//                    tab1_threeitem.setTextColor(unSelectedColor);
//                    tab1_fouritem.setTextColor(selectedColor);

                    tab1_oneitem_iv.setBackgroundColor(tabUnSelectedColor);
                    tab1_twoitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_threeitem_iv.setBackgroundColor(tabUnSelectedColor);
//                    tab1_fouritem_iv.setBackgroundColor(tabSelectedColor);
                    break;
            }
            viewPagerTab.setCurrentItem(index);

        }
    }

    class tabPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private FragmentManager fragmentManager;

        /**
         * 这个构造方法是必须写的
         * 而且必须要重写getitem方法
         *
         * @param fragmentManager
         * @param fragmentList
         */
        public tabPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
            super(fragmentManager);
            this.fragmentManager = fragmentManager;
            this.fragmentList = fragmentList;
        }

        public void setFragments(ArrayList<Fragment> fragments) {
            if (this.fragmentList != null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                for (Fragment f : this.fragmentList) {
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fragmentManager.executePendingTransactions();
            }
            this.fragmentList = fragments;
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


    }


    public void initView() {
        View dot1 = (View) getActivity().findViewById(R.id.dot1);
        dot1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), LoginActivity.class);
                HomePageFragment.this.startActivity(it);
            }
        });

        LinearLayout home_info = (LinearLayout) getActivity().findViewById(R.id.home_info);
        home_info.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(getActivity(), HomeInfoActivity.class);
                HomePageFragment.this.startActivity(it);
            }
        });

        LinearLayout home_exchange = (LinearLayout) getActivity().findViewById(R.id.home_exchange);
        home_exchange.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(getActivity(), RetroactionActivity.class);
                HomePageFragment.this.startActivity(it);
            }
        });


    }


    public void init_viewpager() {
        images = new int[]{R.drawable.sjzc, R.drawable.computer,
                R.drawable.c, R.drawable.d, R.drawable.e};
        titles = new String[]{"石家庄首页", "计算机学院", "化工学院", "历史文华学院", "数信学院"};
        imageSource = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(getActivity());
            image.setBackgroundResource(images[i]);
            imageSource.add(image);

            final int j = i;
            image.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                    if (j == 0) {

                        Uri uri = Uri.parse("http://www.sjzc.edu.cn/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);

                        Toast.makeText(getActivity(), "石家庄首页", 1000).show();
                    }
                    if (j == 1) {
                        Uri uri = Uri.parse("http://jsj.sjzc.edu.cn/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);

                        Toast.makeText(getActivity(), "计算机学院", 1000).show();
                    }
                    if (j == 2) {
                        Uri uri = Uri.parse("http://hgxy.sjzc.edu.cn/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);

                        Toast.makeText(getActivity(), "化工学院", 1000).show();
                    }
                    if (j == 3) {
                        Uri uri = Uri.parse("http://210.31.249.13/lsx/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);

                        Toast.makeText(getActivity(), "历史文华学院", 1000).show();
                    }
                    if (j == 4) {
                        Uri uri = Uri.parse("http://210.31.249.13/shuxue/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);

                        Toast.makeText(getActivity(), "数信学院", 1000).show();
                    }


                }
            });

        }

        dots = new ArrayList<View>();
        dots.add(getActivity().findViewById(R.id.dot1));
        dots.add(getActivity().findViewById(R.id.dot2));
        dots.add(getActivity().findViewById(R.id.dot3));
        dots.add(getActivity().findViewById(R.id.dot4));
        dots.add(getActivity().findViewById(R.id.dot5));

        title = (TextView) getActivity().findViewById(R.id.title);
        title.setText(titles[0]);

        viewPager = (ViewPager) getActivity().findViewById(R.id.vp);
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        MyPageChangeListener listener = new MyPageChangeListener();
        viewPager.setOnPageChangeListener(listener);

        ScheduledExecutorService scheduled = Executors
                .newSingleThreadScheduledExecutor();
        ViewPagerTask pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 6, 6, TimeUnit.SECONDS);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            View view = dots.get(position);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent it = new Intent(getActivity(), LoginActivity.class);
                    HomePageFragment.this.startActivity(it);
                }
            });
            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view);
            return dots.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageSource.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageSource.get(position));
            return imageSource.get(position);
        }
    }

    private class MyPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(final int position) {
            title.setText(titles[position]);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            dots.get(oldPage).setBackgroundResource(R.drawable.dot_normal);

            oldPage = position;
            currPage = position;
        }
    }

    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currPage = (currPage + 1) % images.length;
            handler.sendEmptyMessage(0);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currPage);
        }

        ;
    };
}
