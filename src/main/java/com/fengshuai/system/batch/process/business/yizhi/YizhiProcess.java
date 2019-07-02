package com.fengshuai.system.batch.process.business.yizhi;

import com.fengshuai.system.batch.process.entity.yizhi.YizhiEntity;
import com.fengshuai.util.character.StringUtil;
import com.fengshuai.util.date.DateUtil;
import com.fengshuai.util.middleware.GeodeUtil;
import com.fengshuai.util.middleware.RedisUtil;
import com.fengshuai.util.parse.KeyUtil;

import java.sql.SQLException;

public class YizhiProcess {

    protected   boolean isFaren(String number,String name) {
        boolean isFaren = false;
        String duigBiaoZ = "公司,合作社,房地产,服务社,供销社,基金,幼儿园,协会";
        for (String biaozhi : duigBiaoZ.split(",")) {
            if (name.contains(biaozhi))
                return true;
        }

        String kehuhao = StringUtil.matchStr(number, "\\d+");//去除末尾X、Y
        String lastStr = number.substring(number.length() - 1);
        if (lastStr.equalsIgnoreCase("Y") || lastStr.equalsIgnoreCase("X")) {
            if (kehuhao.length()!=17)
                return true;
        } else if (kehuhao.length() != 18) {
            return true;
        }
        String birthday = kehuhao.substring(6, 14);
        char thousand = kehuhao.charAt(6);
//        System.out.println(birthday);

        if (!DateUtil.checkDate(birthday))
            return true;

        if (thousand<'1'||thousand>'2')
            return true;

        return isFaren;
    }

    private void dataToGeode(String table, String key, Object data) throws SQLException {
        GeodeUtil.insert(table.toLowerCase(), key, data);
    }

    protected void dataPersistent(String key, YizhiEntity data) {
            String[] keyNodes = KeyUtil.toArrayKey(key);
            try {
                dataToGeode(keyNodes[0], KeyUtil.delKeyNode(keyNodes, 1), data);
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
}
