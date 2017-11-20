package com.chuangying.taohui.voice.view.activity;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.chuangying.taohui.voice.R;
import com.chuangying.taohui.voice.entity.OffLineEntity;
import com.chuangying.taohui.voice.entity.OnLineEntity;
import com.chuangying.taohui.voice.util.Config;
import com.chuangying.taohui.voice.util.Utils;
import com.chuangying.taohui.voice.view.custom.CircleProgress;
import com.google.gson.Gson;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechUnderstander;
import com.unisound.client.SpeechUnderstanderListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class CustomActivity extends AppCompatActivity {

    @BindView(R.id.custom_recycler)RecyclerView recyclerView;
    @BindView(R.id.progress)CircleProgress mProgressView;
    /**
     * 唤醒震动提示
     */
    private Vibrator mVibrator;

    private SpeechUnderstander mWakeUpRecognizer;
    private SpeechUnderstander mSpeechUnderstander;
    private SpeechUnderstander mUnderstander;


    private List<String> mList=new ArrayList<String>();
    private CommonAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_main);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.status_bar_main);
        ButterKnife.bind(this);

        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

        //加载动画
        recyclerView.setVisibility(View.INVISIBLE);
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.startAnim();
        mProgressView.setRadius(90.0f);

        mList.add("语音唤醒：");
        initData();
        //列表初始化
        setAdapter();
        //语音初始化
        initialization();

    }

    private void initData() {

    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new CommonAdapter<String>(this,R.layout.item_custom_recycler,mList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_custom_tv,s);
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    private void initialization() {
        mWakeUpRecognizer= new SpeechUnderstander(this, Config.appKey, null);
        mWakeUpRecognizer.setOption(SpeechConstants.ASR_SERVICE_MODE, SpeechConstants.ASR_SERVICE_MODE_LOCAL);
        mWakeUpRecognizer.setOption(SpeechConstants.WAKEUP_EVENT_SET_WAKEUPWORD_DONE,"你好百度");//设置唤醒词
        mWakeUpRecognizer.init("");
        mWakeUpRecognizer.setListener(new SpeechUnderstanderListener() {
            @Override
            public void onEvent(int i, int i1) {
                switch (i){
                    case SpeechConstants.WAKEUP_EVENT_RECOGNITION_SUCCESS:
                        Log.d("TAG", "WAKEUP_EVENT_RECOGNITION_SUCCESS");
                        mVibrator.vibrate(300);
                        displayList("(唤醒成功)");
                        selectMode();
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_START:
                        Log.d("TAG", "ASR_EVENT_RECORDING_START");
                        mProgressView.stopAnim();
                        displayList("语音唤醒已开始");
                        displayList("请说 [你好魔方] 唤醒");
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_STOP:
                        Log.d("TAG", "ASR_EVENT_RECORDING_STOP");
                        displayList("语音唤醒录音已停止");
                        break;
                    case SpeechConstants.ASR_EVENT_ENGINE_INIT_DONE:
                        Log.d("TAG", "ASR_EVENT_ENGINE_INIT_DONE");
                        displayList("引擎初始化完成");
                        wakeUpStart();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(int i, String errorMSG) {
                //失败反馈
                displayList("语音唤醒服务异常  异常信息：" + errorMSG);
            }

            @Override
            public void onResult(int i, String s) {
                Log.d("TAG", "返回："+s);
                //震动三秒
                mVibrator.vibrate(300);
            }
        });
    }
    /**
     * 目前离线识别支持的词表
     */
    private static final String mAsrFixWords = "请说以下命令：打开电视/关闭电视/打开空调/关闭空调/打开蓝牙/关闭蓝牙/增大音量/减小音量/播放音乐/停止播放";
    private static final String mGrammarTag = "main";
    //选择本地或网络
    private void selectMode() {
        if (Utils.isNetworkAvailable(this)){
            displayList("");
            displayList("开启本地语音识别：");
            displayList(mAsrFixWords);
            setAsrOffLine();
        }else {
            displayList("");
            displayList("开启在线语音识别：");
            setAsrOnLine();
        }
    }
    private SpeechSynthesizer mTTSPlayer;
    private void setAsrOnLine() {
        mUnderstander=new SpeechUnderstander(this, Config.appKey, Config.secret);
        // 开启可变结果
        mUnderstander.setOption(SpeechConstants.ASR_OPT_TEMP_RESULT_ENABLE, true);

        // 创建语音合成对象
        mTTSPlayer = new SpeechSynthesizer(this, Config.appKey, Config.secret);
        mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_NET);
        mTTSPlayer.init("");
        mUnderstander.setListener(new SpeechUnderstanderListener() {
            @Override
            public void onEvent(int type, int i1) {
                switch (type) {
                    case SpeechConstants.ASR_EVENT_NET_END:
                        statue = AsrStatus.idle;
                        break;
                    case SpeechConstants.ASR_EVENT_VOLUMECHANGE:
                        // 说话音量实时返回
                        break;
                    case SpeechConstants.ASR_EVENT_VAD_TIMEOUT:
                        // 说话音量实时返回
                        // 收到用户停止说话事件，停止录音
                        stopRecord();
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_STOP:
                        // 停止录音，请等待识别结果回调
                        statue = AsrStatus.recognizing;
                        displayList("结束");
                        displayList("正在识别中");
                        //需要输出东西
                        break;
                    case SpeechConstants.ASR_EVENT_SPEECH_DETECTED:
                        //用户开始说话
                        displayList("正在说话");
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_START:
                        //录音设备打开，开始识别，用户可以开始说话
                        displayList("请说话！");
                        statue = AsrStatus.recording;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(int i, String s) {
                displayList("识别失败:"+s);
            }

            @Override
            public void onResult(int i, String s) {
                displayList("识别结果：");
                parseData(2,s);
            }
        });
    }
    private AsrStatus statue = AsrStatus.idle;
    enum AsrStatus {
        idle, recording, recognizing
    }
    private void setAsrOffLine() {
        mSpeechUnderstander=new SpeechUnderstander(this,Config.appKey,Config.secret);
        mSpeechUnderstander.setOption(SpeechConstants.ASR_SERVICE_MODE,
                SpeechConstants.ASR_SERVICE_MODE_LOCAL);
        mSpeechUnderstander.setListener(new SpeechUnderstanderListener() {
            @Override
            public void onEvent(int i, int i1) {
                switch (i){
                    case SpeechConstants.ASR_EVENT_VAD_TIMEOUT:
                        stopRecord();
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_START:
                        displayList("开始识别");
                        break;
                    case SpeechConstants.ASR_EVENT_VOLUMECHANGE:
                        //音量事实返回
                        break;
                    case SpeechConstants.ASR_EVENT_LOCAL_END:
                        statue = AsrStatus.idle;
                        break;
                    default:break;
                }
            }

            @Override
            public void onError(int i, String s) {
                displayList("识别错误："+s);
            }

            @Override
            public void onResult(int type, String jsonResult) {
                if (type == SpeechConstants.ASR_RESULT_LOCAL) {
                    // 返回离线识别结果
                    parseData(1,jsonResult);
                }
            }
        });
    }

    //停止识别
    private void stopRecord() {
        displayList("正在识别");
        mSpeechUnderstander.stop();
    }

    //展示列表
    protected void displayList(String content){
       /* if (recyclerView.getVisibility()==View.INVISIBLE&&mProgressView.getVisibility()==View.VISIBLE){
            recyclerView.setVisibility(View.VISIBLE);
            mProgressView.setVisibility(View.GONE);
        }*/
        recyclerView.setVisibility(View.VISIBLE);
        mProgressView.setVisibility(View.GONE);
        mList.add(content);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    protected void parseData(int choose,String jsonStr){
        Gson gson=new Gson();
        switch (choose){
            case 1:
                OffLineEntity offEntity=gson.fromJson(jsonStr,OffLineEntity.class);
                String result=offEntity.getLocal_asr().get(0).getRecognition_result();
                displayList(result);
                break;
            case 2:
                OnLineEntity onEntity=gson.fromJson(jsonStr,OnLineEntity.class);
                String res=onEntity.getNet_nlu().get(0).getText();
                String answer=onEntity.getNet_nlu().get(0).getGeneral().getText();
                displayList("你："+res);
                displayList("我："+answer);
                break;
            default:break;
        }
    }
    /**
     * 启动语音唤醒
     */
    private static final String WAKEUP_TAG = "wakeup";
    protected void wakeUpStart() {

        /** ---设置唤醒命令词集合--- */
      /*  mTextViewResult.setText("");
        mTextViewTip.setText("");*/
        mWakeUpRecognizer.start(WAKEUP_TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 主动停止识别
        mWakeUpRecognizer.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mWakeUpRecognizer.start(WAKEUP_TAG);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mWakeUpRecognizer.cancel();
        mWakeUpRecognizer.release(SpeechConstants.ASR_RELEASE_ENGINE, "");
        super.onDestroy();
    }
}
