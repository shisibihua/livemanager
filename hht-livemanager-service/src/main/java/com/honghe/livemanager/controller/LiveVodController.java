package com.honghe.livemanager.controller;

import com.honghe.livemanager.cloud.tencent.api.model.CTVodMedia;
import com.honghe.livemanager.common.pojo.model.Result;
import com.honghe.livemanager.service.LiveVodMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 直播点播控制层
 * @author caoqian
 * @date 20190311
 */
@CrossOrigin
@RestController("liveVodController")
@RequestMapping("liveVod")
public class LiveVodController {
    @Autowired
    private LiveVodMediaService vodMediaService;

    /**
     * 搜索媒体信息
     * @param vodMedia 点播信息
     * @return
     */
    @RequestMapping(value = "searchMedia",method = RequestMethod.POST)
    public Result searchMedia(@RequestBody CTVodMedia vodMedia){
        return vodMediaService.searchMedia(vodMedia);
    }
}
