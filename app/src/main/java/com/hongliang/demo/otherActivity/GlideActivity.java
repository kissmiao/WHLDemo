package com.hongliang.demo.otherActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.text.PrecomputedTextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hongliang.demo.R;
import com.hongliang.demo.util.BitmapUtil;

import java.io.File;

import me.zhouzhuo810.cameracardcrop.BitmapUtils;
import me.zhouzhuo810.cameracardcrop.CameraConfig;
import me.zhouzhuo810.cameracardcrop.CropActivity;

public class GlideActivity extends Activity {

    private ImageView mIvGlide;
    private String url = "http://pic.netbian.com/uploads/allimg/180315/110404-1521083044b19d.jpg";

    String dir_JPG = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image.JPG";
    String dir_PNG = Environment.getExternalStorageDirectory().getAbsolutePath() + "/p_image.PNG";
    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image.JPG";
        Log.i("LOG", "dir" + dir);

        findViewById(R.id.bt_touch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlideActivity.this, CropActivity.class);
                intent.putExtra(CameraConfig.RATIO_WIDTH, 855);
                intent.putExtra(CameraConfig.RATIO_HEIGHT, 541);
                intent.putExtra(CameraConfig.PERCENT_LARGE, 0.8f);
                intent.putExtra(CameraConfig.MASK_COLOR, 0x2f000000);
                intent.putExtra(CameraConfig.RECT_CORNER_COLOR, 0xff00ff00);
                intent.putExtra(CameraConfig.TEXT_COLOR, 0xffffffff);
                intent.putExtra(CameraConfig.HINT_TEXT, "请将方框对准证件拍照");
                intent.putExtra(CameraConfig.IMAGE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "/CameraCardCrop/" + System.currentTimeMillis() + ".jpg");
                startActivityForResult(intent, 0x01);
            }
        });


        MyTask mTask = new MyTask();
        mTask.execute();


    }


    private void initView() {
        mIvGlide = (ImageView) findViewById(R.id.iv_glide);

        iv_image = findViewById(R.id.iv_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x01) {
                String path = data.getStringExtra(CameraConfig.IMAGE_PATH);
                iv_image.setImageURI(Uri.parse("file://" + path));
            }
        }
    }


    private class MyTask extends AsyncTask<String, Integer, Bitmap> {


        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
            //   text.setText("加载中");
            // 执行前显示提示
        }


        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected Bitmap doInBackground(String... strings) {
            return BitmapUtil.getBitmapByUrl(url);
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //     progressBar.setProgress(progresses[0]);
            //    text.setText("loading..." + progresses[0] + "%");
        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(Bitmap result) {
            // 执行完毕后，则更新UI
            //  text.setText("加载完毕");

            ;
            ;

            Log.i("LOG", "========JPG设置2000*1000分辨率后内存" + BitmapUtil.getBitmapSize(BitmapUtil.bitmapFactory(dir_JPG, 2000, 1000)));//8294400
            Log.i("LOG", "========JPG设置1000*500分辨率后内存" + BitmapUtil.getBitmapSize(BitmapUtil.bitmapFactory(dir_JPG, 1000, 500)));//2073600

            Log.i("LOG", "========PNG设置2000*1000分辨率后内存" + BitmapUtil.getBitmapSize(BitmapUtil.bitmapFactory(dir_PNG, 2000, 1000)));//17766672
            Log.i("LOG", "========PNG设置1000*500分辨率后内存" + BitmapUtil.getBitmapSize(BitmapUtil.bitmapFactory(dir_PNG, 1000, 500)));// 4439160


            BitmapUtil.saveImage(BitmapUtil.bitmapFactory(dir_JPG, 2000, 1000), Bitmap.CompressFormat.JPEG, 100, "j_image", ".JPG");//1920*1080 1M
            BitmapUtil.saveImage(BitmapUtil.bitmapFactory(dir_JPG, 1000, 500), Bitmap.CompressFormat.JPEG, 100, "j2_image", ".JPG");//960*540 253.4kb

            BitmapUtil.saveImage(BitmapUtil.bitmapFactory(dir_PNG, 2000, 1000), Bitmap.CompressFormat.JPEG, 100, "p_image", ".PNG");//2508*1771 361.7kb
            BitmapUtil.saveImage(BitmapUtil.bitmapFactory(dir_PNG, 1000, 500), Bitmap.CompressFormat.JPEG, 100, "p2_image", ".PNG");//1254*885 131kb


            iv_image.setImageBitmap(BitmapUtil.getBitmapByPhone(dir_JPG));
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {
            //   text.setText("已取消");
            //   progressBar.setProgress(0);

        }
    }

}
