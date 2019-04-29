package com.honghe.livemanager.service;

import com.honghe.livemanager.cloud.tencent.api.model.CTVodMedia;
import com.honghe.livemanager.common.pojo.model.Result;

/**
 * 点播业务处理
 * @author caoqian
 * @date 20190311
 */
public interface LiveVodMediaService {
    /**
     * 搜索媒体信息
     * @param vodMedia
     * @return
     */
    Result searchMedia(CTVodMedia vodMedia);
}
