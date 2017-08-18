package com.example.administrator.popopwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.popopwindow.R.id.supplier_list_activity;
import static com.example.administrator.popopwindow.R.id.supplier_list_sort;
//PopupWindow属于下拉列表  组合式自定义控件

/**
 * 1.首先添加ButterKnife的依赖 这里用的是7.0.1,取消ActionBar 使用ToolBar代替
 * 2.完成整体的布局,初始化控件,设置点击事件
 * 3.初始化PopupWindow要显示的数据
 * 4.初始化PopupWindow控件的设置
 * 5.PopupWindow与ListView相关联
 * 6.三个PopupWindow 所依附的LinearLayout根据点击事件做对应的逻辑处理(改变TextView的颜色,显示效果)
 *
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 全部
     */
    private TextView mSupplierListProductTv;
    private LinearLayout mSupplierListProduct;
    /**
     * 综合排序
     */
    private TextView mSupplierListSortTv;
    private LinearLayout mSupplierListSort;
    /**
     * 优惠活动
     */
    private TextView mSupplierListActivityTv;
    private LinearLayout mSupplierListActivity;
    private ListView mSupplierListLv;
    String[] menuStr1 = new String[]{"全部", "粮油", "衣服", "图书", "电子产品", "酒水饮料", "水果"};
    String[] menuStr2 = new String[]{"综合排序", "配送费最低"};
    String[] menuStr3 = new String[]{"优惠活动", "特价活动", "免配送费", "可在线支付"};
    private List<Map<String,String>> mData1;
    private List<Map<String,String>> mData2;
    private List<Map<String,String>> mData3;
    private ListView mPopListView;
    private SimpleAdapter simpleAdapter1;
    private SimpleAdapter simpleAdapter2;
    private SimpleAdapter simpleAdapter3;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //初始化要显示的数据  要封装三个假数据
        initData();
        //初始化PopupWindw
        initPopupWindow();

    }
    private void initPopupWindow() {
        //把包裹ListView的XML文件转换为View对象
        View popView = LayoutInflater.from(this).inflate(R.layout.poplist, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        //是指里边的ListView有点击  焦点
        popupWindow.setFocusable(true);
        //如果要动画效果的话,首先要有背景
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置结束时的监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置TextView的颜色,把所有LinearLayout的文本颜色该为灰色
                mSupplierListActivityTv.setTextColor(Color.parseColor("#5a5959"));
                mSupplierListProductTv.setTextColor(Color.parseColor("#5a5959"));
                mSupplierListSortTv.setTextColor(Color.parseColor("#5a5959"));

            }
        });
        //设置点击PopupWindw之外的地方,PopupWindw消失
        LinearLayout list_bottom = popView.findViewById(R.id.popwin_supplier_list_bottom);
        list_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        //获取ListView对象

        mPopListView = popView.findViewById(R.id.popwin_supplier_list_lv);
        //SimpleAdapter 简单数据类型适配器:Map集合和ArrayList集合
        simpleAdapter1 = new SimpleAdapter(this, mData1, R.layout.item1, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        simpleAdapter2 = new SimpleAdapter(this, mData2, R.layout.item1, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        simpleAdapter3 = new SimpleAdapter(this, mData3, R.layout.item1, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        mPopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                switch (meunIndex) {
                    case 0:
                        String currentProduct = mData1.get(i).get("name");
                        mSupplierListProductTv.setText(currentProduct);
                        break;
                    case 1:
                        String currentSort = mData2.get(i).get("name");
                        mSupplierListSortTv.setText(currentSort);
                        break;
                    case 2:
                        String currentActivity = mData3.get(i).get("name");
                        mSupplierListActivityTv.setText(currentActivity);
                        break;
                }

            }
        });
    }
    private int meunIndex = 0;
    private void initData() {
        //创建一个存放PopupWindow加载数据的大盒子,Map集合(键值)
        mData1 = new ArrayList<>();
        mData2 = new ArrayList<>();
        mData3 = new ArrayList<>();
        //存放String的字符串数据   全局变量在上面
        //创建一个小盒子,放编号和值
        Map<String,String> map1;
        for (int i = 0; i < menuStr1.length; i++) {
            map1=new HashMap<>();
            map1.put("name",menuStr1[i]);
            mData1.add(map1);
        }
        //创建第二个盒子
        Map<String,String> map2;
        for (int i = 0; i < menuStr2.length; i++) {
            map2=new HashMap<>();
            map2.put("name",menuStr2[i]);
            mData2.add(map2);
        }
        //创建第三个盒子
        Map<String,String> map3;
        for (int i = 0; i < menuStr3.length; i++) {
            map3=new HashMap<>();
            map3.put("name",menuStr3[i]);
            mData3.add(map3);
        }
        //测试一下  有木有数据
        System.out.println(mData1.get(0).get("name"));
    }

    private void initView() {
        mSupplierListProductTv = (TextView) findViewById(R.id.supplier_list_product_tv);
        mSupplierListProduct = (LinearLayout) findViewById(R.id.supplier_list_product);
        mSupplierListProduct.setOnClickListener(this);
        mSupplierListSortTv = (TextView) findViewById(R.id.supplier_list_sort_tv);
        mSupplierListSort = (LinearLayout) findViewById(supplier_list_sort);
        mSupplierListSort.setOnClickListener(this);
        mSupplierListActivityTv = (TextView) findViewById(R.id.supplier_list_activity_tv);
        mSupplierListActivity = (LinearLayout) findViewById(supplier_list_activity);
        mSupplierListActivity.setOnClickListener(this);
        mSupplierListLv = (ListView) findViewById(R.id.supplier_list_lv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplier_list_product:
                //设置其textview点击是绿色
                mSupplierListProductTv.setTextColor(Color.parseColor("#39ac69"));
                //设置popupwindow里的Listview适配器
                mPopListView.setAdapter(simpleAdapter1);
                //让popupwindow显示出来  参数1View对象  23 则是偏移量
                popupWindow.showAsDropDown(mSupplierListProduct,0,2);
                meunIndex=0;
                break;
            case supplier_list_sort:
                //设置其textview点击是绿色
                mSupplierListSortTv.setTextColor(Color.parseColor("#39ac69"));
                //设置popupwindow里的Listview适配器
                mPopListView.setAdapter(simpleAdapter2);
                //让popupwindow显示出来  参数1View对象  23 则是偏移量
                popupWindow.showAsDropDown(mSupplierListProduct,0,2);
                meunIndex=1;
                break;
            case supplier_list_activity:
                //设置其textview点击是绿色
                mSupplierListActivityTv.setTextColor(Color.parseColor("#39ac69"));
                //设置popupwindow里的Listview适配器
                mPopListView.setAdapter(simpleAdapter3);
                //让popupwindow显示出来  参数1View对象  23 则是偏移量
                popupWindow.showAsDropDown(mSupplierListProduct,0,2);
                meunIndex=2;
                break;
        }
    }
}
