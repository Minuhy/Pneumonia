package top.minuy.feiyan;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.minuy.feiyan.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "主页面";
    final static int SHARE = 231;
    final static int REFRESH_URL = 321;
    final static int REFRESH_DATA = 221;
    final static String ABOUT_STR = "信息来源：\n" +
            "腾讯新闻：https://news.qq.com/zt2020/page/feiyan.htm\n" +
            "丁香医生：https://3g.dxy.cn/newh5/view/pneumonia\n" +
            "网易新闻：http://news.163.com/special/epidemic\n" +
            "夸克浏览器：https://broccoli.uc.cn/apps/pneumonia/routes/index\n" +
            "\n" +
            "其他：\n" +
            "梅斯医学：http://m.medsci.cn/wh.asp\n" +
            "新浪新闻：https://news.sina.cn/zt_d/yiqing0121\n" +
            "搜狗搜索：http://sa.sogou.com/new-weball/page/sgs/epidemic\n" +
            "凤凰新闻：https://news.ifeng.com/c/special/7tPlDSzDgVk\n" +
            "人民网：http://health.people.com.cn/GB/26466/431463/431576/index.html\n" +
            "知乎：https://www.zhihu.com/special/19681091\n" +
            "第一财经：https://m.yicai.com/news/100476965.html\n" +
            "百度：https://voice.baidu.com/act/newpneumonia/newpneumonia\n" +
            "快资讯：https://www.360kuai.com/mob/subject/400\n" +
            "\n" +
            "特别：\n" +
            "老人版：https://mp.weixin.qq.com/s/jXf94aQoMG-MFArEZhW5Jg\n" +
            "佛教版：https://mp.weixin.qq.com/s/e4ruQmt91sQ46BR65OH4og\n" +
            "道教版：https://mp.weixin.qq.com/s/1YDU8PDF-RMRd7i8tAiHFA\n" +
            "基督教版：https://mp.weixin.qq.com/s/KcKj1nReRhk3qH24spoGcQ";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHARE:
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                            //没有权限则申请权限
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("需要读写SD卡的权限，继续吗？");
                            builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            builder.show();
                        }else {
                            //有权限直接执行,docode()不用做处理
                            Share();
                        }
                    }else {
                        //小于6.0，不用申请权限，直接执行
                        Share();
                    }
                    tvTitle.setText("2020全国实时疫情播报");
                    break;
                case REFRESH_URL:
//                    Map<String,WebBearn> url1 = url4web.getU4().getUrl();
//                    for (int i=1;i<url1.size()+1;i++){
//                        Log.i(TAG,url1.get(String.valueOf(i)).getTitle());
//                        Log.i(TAG,url1.get(String.valueOf(i)).getUrl());
//                    }
//                    Log.i(TAG,"已更新");
                    url4web u41 = (url4web) msg.obj;
                    url4web.setU4(u41);
//                    for (int i=1;i<u41.getUrl().size()+1;i++){
//                        Log.i(TAG,u41.getUrl().get(String.valueOf(i)).getTitle());
//                        Log.i(TAG,u41.getUrl().get(String.valueOf(i)).getUrl());
//                    }
//
//                    Log.i(TAG,"页面数据");
//                    url4web u44 = url4web.getU4();
//
//                    Map<String,WebBearn> url = u44.getUrl();
//                    for (int i=1;i<url.size()+1;i++){
//                        Log.i(TAG,url.get(String.valueOf(i)).getTitle());
//                        Log.i(TAG,url.get(String.valueOf(i)).getUrl());
//                    }

                    sectionsPagerAdapter.notifyDataSetChanged();
                    //Toast.makeText(getBaseContext(),"已更新",Toast.LENGTH_SHORT).show();
                    break;
                case REFRESH_DATA:
                    finish();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    TextView tvTitle;
    ImageButton ibAbout;
    ViewPager viewPager;
    TabLayout tabs;
    SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        final FloatingActionButton fab = findViewById(R.id.fab);

        SPsave.getSp().InitSP(getBaseContext());

        if(SPsave.getSp().getTabJson().equals("")){
            url4web uf = JSON.parseObject(SPsave.getSp().getTabJson(),url4web.class);
            if(uf != null){
                url4web.setU4(uf);
                sectionsPagerAdapter.notifyDataSetChanged();
            }
        }

        // android 7.0系统解决分享本地照片文件闪退的问题（app through ClipData.Item.getUri()）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        ibAbout = findViewById(R.id.imageButton);
        tvTitle = findViewById(R.id.title);

        tvTitle.setText("2020全国实时疫情播报");

        ibAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("关于");
                builder.setMessage(ABOUT_STR);
                builder.setPositiveButton("更多", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("http://minuy.top/app/feiyan/");
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = new Date();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                tvTitle.setText("实时疫情"+format.format(date));

                Snackbar.make(view, "截图分享"+format.format(date), Snackbar.LENGTH_LONG)
                        .setAction("分享", null).show();

//                String name = System.currentTimeMillis()+".jpg";
//                saveBitmapToFile(name,shotPic);//保存
//                AlbumScan(name);//更新
                new Thread(new Runnable() {
                    public void run() {
                        //sleep设置的是时长
                        try {
                            Thread.sleep(500);
                            Message msg = new Message();
                            msg.what = SHARE;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        Refresh();
    }

    //刷新，5分钟数据，一次URL
    private void Refresh() {
        new Thread(){
            @Override
            public void run() {
                //刷新界面选项卡
                String jsonStr = getWeb();
                SPsave.getSp().setTabJson(jsonStr);
                if(jsonStr!=null){
                    url4web uf = JSON.parseObject(jsonStr,url4web.class);
                    if(uf != null){
                        Message msg = new Message();
                        msg.what = REFRESH_URL;
                        msg.obj = uf;
                        handler.sendMessage(msg);
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                //定时刷新网页
                while(true){
                    try {
                        sleep(300000);
                        Message msg = new Message();
                        msg.what = REFRESH_DATA;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void Share(){
        Bitmap shotPic = shotActivityNoBar(MainActivity.this);
        SharePic(shotPic);
    }

    private void SharePic(Bitmap bgimg0){
        /** * 分享图片 */
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("image/*");  //设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_STREAM, saveBitmap(bgimg0,String.valueOf(System.currentTimeMillis())));
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "分享实时疫情截图");
        startActivity(share_intent);
    }

    public void AlbumScan(String fileName) {
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{fileName}, new String[]{"image/jpeg"}, null);
    }

    /** * 将图片存到本地 */
    private static Uri saveBitmap(Bitmap bitmap, String fileName) {
        if (TextUtils.isEmpty(fileName) || bitmap == null) return null;
        try {
            Date date = new Date();

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

            String dir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Minuy/feiyan2020/"+"fy"+format.format(date)+System.currentTimeMillis()%1000+".jpg";
            File f = new File(dir);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Uri uri = Uri.fromFile(f);
            return uri;
        } catch (FileNotFoundException e) {
            Log.i("ScreenShotUtil", "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("ScreenShotUtil", "保存失败");
            e.printStackTrace();
        }
        return null;
    }

    public void saveBitmapToFile(String fileName, Bitmap bitmap) {
        if (TextUtils.isEmpty(fileName) || bitmap == null) return;
        try {
            File f = new File(fileName);
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            Log.i("ScreenShotUtil", "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Bitmap shotActivityNoBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //执行代码,这里是已经申请权限成功了,可以不用做处理
                    Share();
                }else{
                    Toast.makeText(MainActivity.this,"权限申请失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static String getWeb() {
        //创建连接客户端
        OkHttpClient client = new OkHttpClient();
        //设置提交参数(post)
        FormBody form = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://minuy.top/app/feiyan/web.json")
                .post(form)//给post设置参数;
                .build();
        //创建"调用" 对象
        Call call = client.newCall(request);
        try {
            Response response = call.execute();//执行
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}