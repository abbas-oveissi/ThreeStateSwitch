package ir.oveissi.threestateswitch.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.oveissi.threestateswitch.ThreeStateSwitch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ThreeStateSwitch threeState= (ThreeStateSwitch) findViewById(R.id.threeState);
        ThreeStateSwitch threeState1= (ThreeStateSwitch) findViewById(R.id.threeState1);

        threeState.setNormalTextTypeface(FontHelper.get(this,"vazir.ttf"));
        threeState.setSelectedTextTypeface(FontHelper.get(this,"vazir_b.ttf"));

        threeState1.setNormalTextTypeface(FontHelper.get(this,"vazir.ttf"));
        threeState1.setSelectedTextTypeface(FontHelper.get(this,"vazir_b.ttf"));
    }
}
