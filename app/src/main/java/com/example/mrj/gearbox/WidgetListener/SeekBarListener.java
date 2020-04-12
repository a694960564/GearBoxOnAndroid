package com.example.mrj.gearbox.WidgetListener;

/**
 * Created by Mr.J on 2020/4/12.
 */
import android.util.Log;
import android.widget.SeekBar;

import com.example.mrj.gearbox.object.GearBoxObject;

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener{
        private GearBoxObject gearBox;
        public SeekBarListener(GearBoxObject Gear){
                this.gearBox = Gear;
        }
        @Override
        /**
         * seekBar改变出发的监听器
         * fromUser :   是否是用户手动操作的
         */
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               gearBox.SetValue(progress-100);
        }

        @Override
        /**
         * 用户开始拖拽的时候出发的监听器
         */
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        /**
         * 用户停止拖拽的时候出发的监听器
         */
        public void onStopTrackingTouch(SeekBar seekBar) {
            System.out.println("SeekBar--> progress = " + seekBar.getProgress());
        }
}
