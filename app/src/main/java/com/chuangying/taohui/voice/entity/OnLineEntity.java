package com.chuangying.taohui.voice.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class OnLineEntity {
    private List<NetAsrBean> net_asr;
    private List<NetNluBean> net_nlu;

    public List<NetAsrBean> getNet_asr() {
        return net_asr;
    }

    public void setNet_asr(List<NetAsrBean> net_asr) {
        this.net_asr = net_asr;
    }

    public List<NetNluBean> getNet_nlu() {
        return net_nlu;
    }

    public void setNet_nlu(List<NetNluBean> net_nlu) {
        this.net_nlu = net_nlu;
    }

    public static class NetAsrBean {
        /**
         * engine_mode : net
         * result_type : full
         * last_result : true
         * recognition_result : 你好呀。
         * sessionID : 9481cc310beb9fb9ca06a6417f05a140
         */

        private String engine_mode;
        private String result_type;
        private boolean last_result;
        private String recognition_result;
        private String sessionID;

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

        public boolean isLast_result() {
            return last_result;
        }

        public void setLast_result(boolean last_result) {
            this.last_result = last_result;
        }

        public String getRecognition_result() {
            return recognition_result;
        }

        public void setRecognition_result(String recognition_result) {
            this.recognition_result = recognition_result;
        }

        public String getSessionID() {
            return sessionID;
        }

        public void setSessionID(String sessionID) {
            this.sessionID = sessionID;
        }
    }

    public static class NetNluBean {
        /**
         * nluProcessTime : 53
         * retTag : nlu
         * rc : 5
         * text : 你好呀
         * service : cn.yunzhisheng.error
         * code : ANSWER
         * general : {"type":"T","text":"很抱歉，我还不理解您说的话。"}
         * history : cn.yunzhisheng.error
         * responseId : c04f8a5afbc64873a45768ce308f33bc
         */

        private String nluProcessTime;
        private String retTag;
        private int rc;
        private String text;
        private String service;
        private String code;
        private GeneralBean general;
        private String history;
        private String responseId;

        public String getNluProcessTime() {
            return nluProcessTime;
        }

        public void setNluProcessTime(String nluProcessTime) {
            this.nluProcessTime = nluProcessTime;
        }

        public String getRetTag() {
            return retTag;
        }

        public void setRetTag(String retTag) {
            this.retTag = retTag;
        }

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public GeneralBean getGeneral() {
            return general;
        }

        public void setGeneral(GeneralBean general) {
            this.general = general;
        }

        public String getHistory() {
            return history;
        }

        public void setHistory(String history) {
            this.history = history;
        }

        public String getResponseId() {
            return responseId;
        }

        public void setResponseId(String responseId) {
            this.responseId = responseId;
        }

        public static class GeneralBean {
            /**
             * type : T
             * text : 很抱歉，我还不理解您说的话。
             */

            private String type;
            private String text;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
