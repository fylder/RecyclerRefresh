package fylder.recycler.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.main_demo_btn)
    void demo() {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_fish_btn)
    void fish() {
        Intent intent = new Intent(this, FishActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_grid_btn)
    void grid() {
        Intent intent = new Intent(this, GridActivity.class);
        startActivity(intent);
    }
}
