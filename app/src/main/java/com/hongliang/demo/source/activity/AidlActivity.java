package com.hongliang.demo.source.activity;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hongliang.demo.R;
import com.hongliang.demo.StudentAidl;
import com.hongliang.demo.source.aidl.INumberManage;
import com.hongliang.demo.source.aidl.NumberManageImpl;

public class AidlActivity extends Activity implements View.OnClickListener {
    private ServiceConnectionImpl sci;
    private StudentAidl student;

    private INumberManage numberManage;

    private NumberImpl nip;
    /**
     * 发起通信
     */
    private Button mBtStart;
    /**
     * 绑定Server
     */
    private Button mBtBind;
    /**
     * 解除Server绑定
     */
    private Button mBtUnBind;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        initView();


    }


    public void initView() {
        mBtStart = (Button) findViewById(R.id.bt_start);
        mBtStart.setOnClickListener(this);
        mBtBind = (Button) findViewById(R.id.bt_bind);
        mBtBind.setOnClickListener(this);
        mBtUnBind = (Button) findViewById(R.id.bt_unBind);
        mBtUnBind.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bind:
                //   onBind();
                onBindNumberImple();
                break;

            case R.id.bt_start:
                //   queryOnClick();

                getNumber();
                break;
            case R.id.bt_unBind:
                // unbindService();

                break;

        }
    }


    /**
     * onBind() 后执行onCreate  onBind
     * 后再绑定不在再执行
     */
    private void onBind() {
        Intent intent = new Intent("com.hongliang.demo.StudentService");//Service所设置的action
        intent.setPackage("com.hongliang.demo");////Service所在的package名称
        sci = new ServiceConnectionImpl();
        bindService(intent, sci, Service.BIND_AUTO_CREATE);

    }


    private void onBindNumberImple() {
        Intent intent = new Intent("numberManageImpl");//Service所设置的action
        intent.setPackage("com.hongliang.demo");////Service所在的package名称
        nip = new NumberImpl();
        bindService(intent, nip, Service.BIND_AUTO_CREATE);
    }


    class ServiceConnectionImpl implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //将其转换为service实现类的对象
            student = StudentAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            student = null;
        }
    }


    class NumberImpl implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            numberManage = NumberManageImpl.adInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            numberManage = null;
        }
    }


    /**
     * unbindService() 后执行onUnbind onDestroy
     */
    private void unbindService() {
        if (sci != null) {
            unbindService(sci);
            sci = null;
        }
    }

    private void unbindService2() {
        if (nip != null) {
            unbindService(nip);
            nip = null;
        }
    }


    public void queryOnClick() {

        String name;
        try {
            if (student == null) {
                return;
            }
            name = student.getStudent(1);
            Toast.makeText(this, "sname" + name, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    public void getNumber() {

        int number;
        try {
            if (numberManage == null) {
                return;
            }
            number = numberManage.getNumber();
            Toast.makeText(this, "number" + number, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
        unbindService2();
    }

}
