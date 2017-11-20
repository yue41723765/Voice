package com.chuangying.taohui.voice.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class OffLineEntity {

    private List<LocalAsrBean> local_asr;

    public List<LocalAsrBean> getLocal_asr() {
        return local_asr;
    }

    public void setLocal_asr(List<LocalAsrBean> local_asr) {
        this.local_asr = local_asr;
    }

    public static class LocalAsrBean {
        /**
         * engine_mode : local
         * result_type : full
         * recognition_result :   关闭蓝牙
         * score : -17.42
         */

        private String engine_mode;
        private String result_type;
        private String recognition_result;
        private double score;

        public String getEngine_mode() {
            return engine_mode;
        }

        public void setEngine_mode(String engine_mode) {
            this.engine_mode = engine_mode;
        }

        public String getResult_type() {
            return result_type;
        }

        public void setResult_type(String result_type) {
            this.result_type = result_type;
        }

        public String getRecognition_result() {
            return recognition_result;
        }

        public void setRecognition_result(String recognition_result) {
            this.recognition_result = recognition_result;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
