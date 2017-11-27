package com.mvp.lt.andfixtest;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


/**
 * @author LiuTao
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {
    @BindView(R.id.verry_name)
    EditText mVerryName;
    @BindView(R.id.verry_pws)
    EditText mVerryPws;
    @BindView(R.id.verry_login)
    Button mVerryLogin;
    @BindView(R.id.verry_text1)
    TextView mVerryText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initState();
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindViewByRxBinding();
        getLoaderManager().initLoader(1, null, this);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String name = Thread.currentThread().getName();
                        mVerryText1.setText(name + ":wozai 子线程运行");
                    }
                }).start();
        //initState();
    }
    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void testBug(View view) {
        test();
    }

    private void test() {
        Toast.makeText(this, "新增加", Toast.LENGTH_LONG).show();
    }

    /**
     * 后台提供接口，有bug时，下载修复包 进行修复
     *
     * @param view
     */
    public void andFix(View view) {
        try {
            // 测试 目前暂且放在本地
            String patchFileString = Environment.getExternalStorageDirectory() + "/fix.apatch";
            Log.e("TAG", patchFileString);
            // 修复apatch，不需要重启可立即生效
            BaseApp.mPatchManager.addPatch(patchFileString);
            Toast.makeText(this, "Bug修复成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Bug修复失败", Toast.LENGTH_LONG).show();
        }
    }

    private void bindViewByRxBinding() {
        Observable<CharSequence> observerName = RxTextView.textChanges(mVerryName);
        Observable<CharSequence> observerPwd = RxTextView.textChanges(mVerryPws);
        Observable.combineLatest(observerName, observerPwd, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence name, CharSequence pwd) {
                return isNameValid(name.toString()) && isPasswordValid(pwd.toString());
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(Boolean aBoolean) {
                Toast.makeText(MainActivity.this, "boole++++++", Toast.LENGTH_SHORT).show();
                if (aBoolean) {
                    mVerryLogin.setEnabled(true);
                } else {
                    mVerryLogin.setEnabled(false);
                }
            }
        });



       io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

            }
        }).subscribeOn(io.reactivex.schedulers.Schedulers.io())
        .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });


        Snackbar.make(mVerryLogin,"",Snackbar.LENGTH_LONG);

    }

    private boolean isNameValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 5;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @OnClick(R.id.verry_login)
    public void onViewClicked() {
        rxJava();
        // Toast.makeText(this, "sdsad", Toast.LENGTH_SHORT).show();
    }

    private void rxJava() {

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < 100; i++) {

                        if (i == 20) {
                            int a = 2;
                            int b = 0;
                            int c = a / b;
                            subscriber.onNext("你好呀" + c);
                            continue;
                        }
                        subscriber.onNext("你好呀" + i);
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "错误下nxi:" + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {


        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
