package com.honghe.livemanager.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * 对接i学接口
 * @author caoqian
 * @date 20181026
 */
public class HttpSearchAreaUtil {
    private static String API_URL="";
    private static final String SESSION_KEY ="androidclient";
    private static final String SESSION_VALUE ="9u8fjk3d02dv";
    private static final String SEARCH_AREA_URL="api/group/citys";
    private static final String SEARCH_SCHOOL_URL="api/group/schoolitems";
    private static HttpSearchAreaUtil initParamsUtil;
    public static HttpSearchAreaUtil getInstance(){
        if(null==initParamsUtil){
            synchronized (HttpSearchAreaUtil.class){
                if(null==initParamsUtil){
                    initParamsUtil=new HttpSearchAreaUtil();
                }
            }
        }
        return initParamsUtil;
    }
    static {
        API_URL=ConfigUtil.getInstance().getPropertyValue("API_URL");
    }
//    public static void main(String[] args) {
//        HttpSearchAreaUtil httpSearchAreaUtil=HttpSearchAreaUtil.getInstance();
//        String area=httpSearchAreaUtil.searchArea(534,3).toString();
//        System.out.println(area);
//    }

    /**
     * 查询地区
     * @param parentId  父级id
     * @param level     级别，1:省;2:市;3：区/县；4:学校
     * @return
     */
    public JSONArray searchArea(int parentId,int level){
        Map<String,String> params=new HashMap<>();
        params.put("pid",String.valueOf(parentId));
        params.put("level",String.valueOf(level));
        String areaUrl=API_URL+SEARCH_AREA_URL+"?"+initParams(params);
        JSONObject resultJson=HttpUtils.httpGet(areaUrl);
        JSONArray areaResult=new JSONArray();
        if(resultJson!=null && !resultJson.isEmpty() && Boolean.parseBoolean(resultJson.get("success").toString())){
            String data=resultJson.get("data").toString();
            if(!ParamUtil.isEmpty(data)) {
                JSONArray result= JSONArray.parseArray(data.replace("area_id","id"));
                for(Object o:result){
                    JSONObject json=JSONObject.parseObject(String.valueOf(o));
                    json.put("code",json.get("id"));
                    json.put("id",Integer.parseInt(String.valueOf(json.get("id"))));
                    json.put("parendId",parentId);
                    json.put("level",level);
                    areaResult.add(json);
                }
            }
        }
        return areaResult;
    }

    /**
     * 查询学校列表
     * @param areaId        地区id
     * @return
     */
    public JSONArray searchSchools(int areaId){
        return searchSchools(areaId,null);
    }
    /**
     * 查询学校列表
     * @param areaId        地区id
     * @param schoolName    学校名，关键字，非必需
     * @return
     */
    public JSONArray searchSchools(int areaId,String schoolName){
        Map<String,String> params=new HashMap<>();
        params.put("area",String.valueOf(areaId));
        if(!ParamUtil.isEmpty(schoolName)) {
            params.put("key", schoolName);
        }
        String areaUrl=API_URL+SEARCH_SCHOOL_URL+"?"+initParams(params);
        JSONObject resultJson=HttpUtils.httpGet(areaUrl);
        JSONArray areaResult=new JSONArray();
        if(resultJson!=null && !resultJson.isEmpty() && Boolean.parseBoolean(resultJson.get("success").toString())){
            String data=resultJson.get("data").toString();
            if(!ParamUtil.isEmpty(data)) {
                JSONArray result = JSONArray.parseArray(data);
                for(Object o:result){
                    JSONObject json=JSONObject.parseObject(String.valueOf(o));
                    JSONObject schoolDataJson=new JSONObject();
                    schoolDataJson.put("code",json.get("school_id"));
                    schoolDataJson.put("id",Integer.parseInt(String.valueOf(json.get("school_id"))));
                    schoolDataJson.put("parendId",areaId);
                    schoolDataJson.put("level",TipsMessage.SCHOOL_LEVEL);
                    schoolDataJson.put("name",json.get("s_name").toString());
                    areaResult.add(schoolDataJson);
                }

            }
        }
        return areaResult;
    }

    /**
     * 初始化i学参数
     *
     * @param params 参数
     * @return 拼接的字符串
     * @throws UnsupportedEncodingException 编码异常
     */
    private String initParams(Map<String, String> params){
        int pos = 0;
        StringBuilder tempParams = new StringBuilder();
        List<Params> paramses = setting(params);
        for (Params p : paramses) {
            if (pos > 0) {
                tempParams.append("&");
            }
            try {
                tempParams.append(String.format("%s=%s", p.getKey(), URLEncoder.encode(p.getValue(), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            pos++;
        }
        return tempParams.toString();
    }
    private List<Params> setting(Map<String, String> params) {
        params.put("sessionkey", SESSION_KEY);
        params.put("restauth", "_restauth");
        params.put("resttime", String.valueOf(new Date().getTime()).substring(0, 10));
        String urlParams = "";
        String[] keys = (String[]) params.keySet().toArray(new String[params.size()]);
        Arrays.sort(keys);
        for (String key : keys) {
            String value = (String) params.get(key);
            try {
                urlParams = urlParams + key + "=" + URLEncoder.encode(value, "UTF-8").replaceAll("\\*", "%2A") + "&";
            } catch (UnsupportedEncodingException e) {
                System.out.println("Un supported Encoding Exception");
            }
        }
        urlParams = urlParams + SESSION_VALUE;
        String _urlParams = urlParams.replaceAll("&restauth=_restauth", "").replaceAll("restauth=_restauth&", "");
        String md5_1 = MD5Util.getMD5(_urlParams.getBytes());
        List<Params> result = new ArrayList<Params>();
        String[] paramsArray = urlParams.replaceAll("&"+ SESSION_VALUE, "").replaceAll("_restauth", md5_1).split("&");
        for (String _paramsArray : paramsArray) {
            String[] temp = _paramsArray.split("=");
            if (temp.length == 1)
                result.add(new Params(temp[0], ""));
            else
                try {
                    result.add(new Params(temp[0], URLDecoder.decode(temp[1], "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Un supported Encoding Exception");
                }
        }
        return result;
    }
    private class Params {
        private String key;
        private String value;

        public Params(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
