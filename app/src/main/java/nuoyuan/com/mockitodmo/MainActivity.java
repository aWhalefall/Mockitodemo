package nuoyuan.com.mockitodmo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private TextView textView;
    private Button button;
    private LoginPreSenterImpl presenter;

    /**
     * 类似meishi模式
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.loading_show);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        presenter = new LoginPreSenterImpl(this);

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
                presenter.Login();
                break;

            default:
                break;
        }
    }
}
