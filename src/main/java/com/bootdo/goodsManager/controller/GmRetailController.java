package com.bootdo.goodsManager.controller;

import com.bootdo.common.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 零售表
 *
 * @author xyy
 * @email 615131288@qq.com
 * @date 2019-04-04 16:01:15
 */
@Controller
@RequestMapping("/goodsManager/retail")
public class GmRetailController {

    @RequestMapping("/toRetail")
    @ResponseBody
    public R toRetail(){
        return R.ok();
    }

}
