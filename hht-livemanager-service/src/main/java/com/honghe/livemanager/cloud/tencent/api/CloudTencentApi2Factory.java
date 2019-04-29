package com.honghe.livemanager.cloud.tencent.api;

import com.honghe.livemanager.util.TencetLiveUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.vod.v20180717.VodClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯云3.0直播录制、点播api工厂
 * @author caoqian
 * @date 20190307
 */
public final class CloudTencentApi2Factory {
    private static Map<String,Object> entity=new HashMap<>();
    private final static String VOD_CLIENT_URL="vod.tencentcloudapi.com";
    private final static String LIVE_CLIENT_URL="live.tencentcloudapi.com";
    private static String SECRET_ID= "";
    private static String SECRET_KEY= "";

    static {
        SECRET_ID= TencetLiveUtil.CLOUD_TENCENT_SECRETID;
        SECRET_KEY=TencetLiveUtil.CLOUD_TENCENT_SECRETKEY;
        entity.put("vodClient",getVodClient());
        entity.put("liveClient",getLiveClient());
    }

    public static Object getClient(String key){
        return entity.get(key);
    }

    /**
     * 获取点播api实体
     * @return
     */
    private static VodClient getVodClient() {
        Credential cred = new Credential(SECRET_ID, SECRET_KEY);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(VOD_CLIENT_URL);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        VodClient  client = new VodClient (cred, "", clientProfile);
        return client;
    }

    /**
     * 获取录制api实体
     * @return
     */
    private static LiveClient getLiveClient() {
        Credential cred = new Credential(SECRET_ID, SECRET_KEY);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(LIVE_CLIENT_URL);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        LiveClient  client = new LiveClient (cred, "", clientProfile);
        return client;
    }
 /* public static void main(String [] args) {
        try{

            Credential cred = new Credential("AKIDoXq482sR8h4KOHyw32tpcmoezEJ86Mjy", "HOK93nQrHa6UgyBHCvSp13taRN9mDSWO");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("live.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            LiveClient client = new LiveClient(cred, "", clientProfile);

            String params = "{\"Action\":\"CreateLiveRecord\",\"StreamName\":\"妇女节快乐！！\",\"Highlight\":0,\"EndTime\":\"2019-03-12+17%3A30%3A00\",\"RecordType\":\"video\",\"DomainName\":\"2072.livepush.myqcloud.com\",\"VERSION\":\"2018-08-01\",\"StartTime\":\"2019-03-12+17%3A00%3A00\",\"MixStream\":0,\"FileFormat\":\"mp4\",\"AppName\":\"\"}";
            CreateLiveRecordRequest req = CreateLiveRecordRequest.fromJsonString(params, CreateLiveRecordRequest.class);

            CreateLiveRecordResponse resp = client.CreateLiveRecord(req);

            System.out.println(CreateLiveRecordRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }*/

}
