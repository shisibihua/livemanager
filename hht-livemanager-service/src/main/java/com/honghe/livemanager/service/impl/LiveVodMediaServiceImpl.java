package com.honghe.livemanager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.honghe.livemanager.cloud.tencent.api.CloudTencentApi2;
import com.honghe.livemanager.cloud.tencent.api.model.CTVodMedia;
import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.service.LiveVodMediaService;
import com.honghe.livemanager.util.ConvertResult;
import jodd.typeconverter.Convert;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LiveVodMediaServiceImpl implements LiveVodMediaService {
    private Logger logger= LoggerFactory.getLogger(LiveVodMediaService.class);
    @Autowired
    private CloudTencentApi2 cloudTencentApi2;
    @Override
    public Result searchMedia(CTVodMedia vodMedia) {
        if(StringUtil.isEmpty(vodMedia.getStreamId())){
            return ConvertResult.getParamErrorResult("流id不能为空");
        }
        try{
            Map<String,Object> result = cloudTencentApi2.searchMedia(vodMedia);
            Result resultValue=new Result(Result.Code.Success.value());
            Map<String,Object> map=new HashMap<>();
            map.put("totalCount",result.get("totalCount"));
            if(!"".equals(result.get("mediaInfoSet").toString())){
                List<Map<String,String>> mediaUrlList=new ArrayList<>();
                JSONArray mediaInfo=(JSONArray) result.get("mediaInfoSet");
                for(Object o:mediaInfo){
                    JSONObject json=JSONObject.parseObject(o.toString());
                    if(json!=null && !json.isEmpty()) {
                        JSONObject basicInfo = JSONObject.parseObject(json.getString("BasicInfo"));
                        if(basicInfo!=null && !basicInfo.isEmpty()){
                            Map<String,String> mediaUrl=new HashMap<>();
                            mediaUrl.put("mediaUrl",basicInfo.getString("MediaUrl"));
                            mediaUrlList.add(mediaUrl);
                        }else{
                            map.put("mediaUrlSet","");
                        }
                    }else{
                        map.put("mediaUrlSet","");
                    }
                }
                if(!mediaUrlList.isEmpty()){
                    map.put("mediaUrlSet",mediaUrlList);
                }else{
                    map.put("mediaUrlSet","");
                }
            }else{
                map.put("mediaUrlSet","");
            }
            resultValue.setResult(map);
            return resultValue;
        }catch (Exception e) {
            logger.error("搜索媒体信息异常",e);
            return ConvertResult.getErrorResult("搜索媒体信息失败");
        }
    }
}
