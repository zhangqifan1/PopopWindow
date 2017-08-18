package bawei.com.popuwindow;
/**
 * popuwindow下拉列表，属于组合式控件
 * 1.添加Butterknife的依赖，取消掉ActionBar,使用ToolBar代替
 * 2.完成整体布局，初始化控件，设置点击事件
 * 3.初始化popuwindow所要显示的数据
 * 4.初始化popuwindow控件的设置
 * 5.popuwindow与listview相关联
 * 6.三个popuwindow所依附的linearLayout,更改点击事件，做对应逻辑处理（改变TextView的颜色，显示效果）
 */

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.supplier_list_product_tv)
    TextView mProductTv;  // 可以修改名称
    @Bind(R.id.supplier_list_product)
    LinearLayout mProduct;
    @Bind(R.id.supplier_list_sort_tv)
    TextView mSortTv;     // 可以修改名称
    @Bind(R.id.supplier_list_sort)
    LinearLayout mSort;
    @Bind(R.id.supplier_list_activity_tv)
    TextView mActivityTv;   // 可以修改名称
    @Bind(R.id.supplier_list_activity)
    LinearLayout mActivity;
    @Bind(R.id.supplier_list_lv)
    ListView mSupplierListLv;
    private ArrayList<Map<String, String>> meunData1;
    private ArrayList<Map<String, String>> meunData2;
    private ArrayList<Map<String, String>> meunData3;
    private PopupWindow mpopupWindow;
    private ListView mpopListView;
    private SimpleAdapter mMenuAdapter1;
    private SimpleAdapter mMenuAdapter2;
    private SimpleAdapter mMenuAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化popuwindow所要显示的数据
        initData();
        //初始化popuwindow控件
        initPopuwindow();

    }


    //初始化数据，popuwindow所需，一共有三个，所以要封装好三个数据，这里是假数据，真实数据从网上获取
    private void initData() {
        //创建一个存放popuwindow加载数据的大盒子，Map集合
        meunData1 = new ArrayList<>();
        //存放String字符串数组
        String[] menuStr1 = new String[]{"全部", "粮油", "衣服", "图书", "电子产品",
                "酒水饮料", "水果"};
        //创建一个小盒子，放编号和值
        Map<String,String> map1;
        for (int i = 0; i <menuStr1.length; i++) {
            map1=new HashMap<String,String>();
            map1.put("name",menuStr1[i]);
            meunData1.add(map1);
        }
        //创建一个存放popuwindow加载数据的大盒子，Map集合
        meunData2 = new ArrayList<>();
        //存放String字符串数组
        String[] menuStr2 = new String[]{"综合排序", "配送费最低"};
        //创建一个小盒子，放编号和值
        Map<String,String> map2;
        for (int i = 0; i <menuStr2.length; i++) {
            map2=new HashMap<String,String>();
            map2.put("name",menuStr2[i]);
            meunData2.add(map2);
        }
        //创建一个存放popuwindow加载数据的大盒子，Map集合
        meunData3 = new ArrayList<>();
        //存放String字符串数组
        String[] menuStr3 = new String[]{"优惠活动", "特价活动", "免配送费",
                "可在线支付"};
        //创建一个小盒子，放编号和值
        Map<String,String> map3;
        for (int i = 0; i <menuStr3.length; i++) {
            map3=new HashMap<String,String>();
            map3.put("name",menuStr3[i]);
            meunData3.add(map3);
        }


    }
    //初始化popuwindow
    private void initPopuwindow() {
        //把包裹listview布局的xml文件转换为view对象
        View popview = LayoutInflater.from(this).inflate(R.layout.popuwindow, null);
        //动态创建popuwindow对象,参数popuwindow要显示的布局，参数2.3定义宽和高
        mpopupWindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //设置popuwindow外部可以点击
        mpopupWindow.setOutsideTouchable(true);
        //设置popuwindow里面的listview有焦点
        mpopupWindow.setFocusable(true);
        //如果想让popwindow有动画效果，就必须有这句代码
        mpopupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置popuwindow结束时监听事件
        mpopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置TextView的颜色,把所有LinearLayout的文本颜色该为灰色
                mProductTv.setTextColor(Color.parseColor("#5a5959"));
                mSortTv.setTextColor(Color.parseColor("#5a5959"));
                mActivityTv.setTextColor(Color.parseColor("#5a5959"));

            }
        });

        //设置点击popuwindow以外的地方，使它消失
        LinearLayout list_ovttom = popview.findViewById(R.id.popwin_supplier_list_bottom);
        list_ovttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当点击到灰色区域时，会消失
                mpopupWindow.dismiss();
            }
        });
        //获取listview对象
        mpopListView = (ListView) popview.findViewById(R.id.popwin_supplier_list_lv);
        //创建simpleAdapter，一个listview安卓原生封装的适配器
         //参数：context:上下文 data:设置所有条目数，一个ArrayList大集合(包含所有条目信息)，里面又有一个map集合（通过map来设置一个条目数据）
        //resource:引用布局资源  for:String数组，装map条目内容的键  to:int数组，装控件id  二者具有映射关系，代表对应内容放到对应位置
        mMenuAdapter1 = new SimpleAdapter(this, meunData1, R.layout.item_listview_popwim,
                       new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        mMenuAdapter2 = new SimpleAdapter(this, meunData2, R.layout.item_listview_popwim,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        mMenuAdapter3 = new SimpleAdapter(this, meunData3, R.layout.item_listview_popwim,
                new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        //设置popuwindow里的listview点击事件，当点击listview里的item条目时，把item显示在第一个
        mpopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //首先让popuwindow消失
                mpopupWindow.dismiss();
                String currentProduct = meunData1.get(i).get("name");
                mProductTv.setText(currentProduct);
            }
        });
    }

    //三个popuwindow所依附的linearLayout,更改点击事件，做对应逻辑处理
    @OnClick({R.id.supplier_list_product, R.id.supplier_list_sort, R.id.supplier_list_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            //第一个popuwindow所执行的点击后的逻辑
            case R.id.supplier_list_product:
                //设置其TextView点击为绿色
                mProductTv.setTextColor(Color.parseColor("#39ac69"));
                //设置popuwindow里的listview适配器
                mpopListView.setAdapter(mMenuAdapter1);
                //让popuwindow显示出来 参数1.view对象，决定了popuwindow在那个控件下显示  参数2.3决定popuwindow的坐标，x轴，y轴
                mpopupWindow.showAsDropDown(mProduct,0,2);
                break;
            case R.id.supplier_list_sort:
                mSortTv.setTextColor(Color.parseColor("#39ac69"));
                //设置popuwindow里的listview适配器
                mpopListView.setAdapter(mMenuAdapter2);
                //让popuwindow显示出来 参数1.view对象，决定了popuwindow在那个控件下显示  参数2.3决定popuwindow的坐标，x轴，y轴
                mpopupWindow.showAsDropDown(mProduct,0,2);
                break;
            case R.id.supplier_list_activity:
                mActivityTv.setTextColor(Color.parseColor("#39ac69"));
                //设置popuwindow里的listview适配器
                mpopListView.setAdapter(mMenuAdapter3);
                //让popuwindow显示出来 参数1.view对象，决定了popuwindow在那个控件下显示  参数2.3决定popuwindow的坐标，x轴，y轴
                mpopupWindow.showAsDropDown(mProduct,0,2);
                break;
        }
    }

}
