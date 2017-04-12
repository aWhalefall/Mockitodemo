package nuoyuan.com.mockitodmo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.mcxiaoke.packer.helper.PackerNg;

import javax.inject.Inject;

import nuoyuan.com.mockitodmo.annotation.ActivityComponent;
import nuoyuan.com.mockitodmo.annotation.DaggerActivityComponent;
import nuoyuan.com.mockitodmo.annotation.MainModule;


public class MainActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private TextView textView;
    private Button button;

    @Inject
    public LoginPreSenterImpl presenter;

    /**
     * 类似meishi模式
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进行注解


        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.loading_show);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        ActivityComponent component = DaggerActivityComponent.builder().mainModule(new MainModule(this)).
                build();
        component.inject(this);

        presenter.test(this);


    }

    @Override
    public void showLoading() {
        textView.setVisibility(View.VISIBLE);
        textView.setText("Loading..............................");
    }

    @Override
    public void hiddenLoading() {
        textView.setVisibility(View.GONE);
        textView.setText("Loading..............................");
    }

    @Override
    public void LoginError(String info) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(info);
    }

    @Override
    public void LoginSuccess(String info) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:  //进行登录
//                presenter.Login();
                String value = PackerNg.getMarket(MainActivity.this);
                Toast.makeText(MainActivity.this, "当前渠道" + value, 1000).show();
                break;

            default:
                break;
        }
    }
}
