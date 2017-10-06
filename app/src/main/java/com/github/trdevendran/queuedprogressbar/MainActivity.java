package com.github.trdevendran.queuedprogressbar;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.github.trdevendran.queuedprogressbar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, com.github.trdevendran.queuedprogressbar.R.layout.activity_main);

        mBinding.ballCountControllerSb.setOnSeekBarChangeListener(this);
        mBinding.radiusControllerSb.setOnSeekBarChangeListener(this);
        mBinding.speedControllerSb.setOnSeekBarChangeListener(this);

        mBinding.defaultColorRb.setOnCheckedChangeListener(this);
        mBinding.greenColorRb.setOnCheckedChangeListener(this);
        mBinding.blueColorRb.setOnCheckedChangeListener(this);
        mBinding.redColorRb.setOnCheckedChangeListener(this);

        mBinding.ballCountControllerSb.setProgress(5);
        mBinding.radiusControllerSb.setProgress(5);
        mBinding.speedControllerSb.setProgress(5);
        mBinding.defaultColorRb.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {

            switch (buttonView.getId()) {
                case R.id.red_color_rb:
                    mBinding.testQueuedProgressbar.setBallColor(Color.RED);
                    break;
                case R.id.green_color_rb:
                    mBinding.testQueuedProgressbar.setBallColor(Color.GREEN);
                    break;
                case R.id.blue_color_rb:
                    mBinding.testQueuedProgressbar.setBallColor(Color.BLUE);
                    break;
                default:
                    mBinding.testQueuedProgressbar.setBallColor(0);
                    break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()) {
            case R.id.ball_count_controller_sb:
                mBinding.ballCountControllerTv.setText(getResources().getString(R.string.ball_count_title) + " " + progress);
                mBinding.testQueuedProgressbar.setTotalBalls(progress);
                break;
            case R.id.radius_controller_sb:
                mBinding.radiusControllerTv.setText(getResources().getString(R.string.radius_title) + " " + progress);
                mBinding.testQueuedProgressbar.setBallRadius(progress);
                break;
            case R.id.speed_controller_sb:
                mBinding.speedControllerTv.setText(getResources().getString(R.string.speed_title) + " " + progress);
                mBinding.testQueuedProgressbar.setIntervalValue(progress);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}