package com.fengshuai.system.batch.process.business.yizhi;

import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.system.batch.process.entity.yizhi.KdpaKehuzhObj;
import com.fengshuai.util.common.CommUtil;
import com.fengshuai.util.db.PrepareSql;

import java.util.List;

public class SqlGenUdpYinHInfo  implements ItemProcess {


    @Override
    public Object writeProcess(List<String> rowValue) {

        if (CommUtil.isNull(rowValue))
            return null;
        KdpaKehuzhObj kehuzhObj = new KdpaKehuzhObj(true);


        kehuzhObj.setShijyhzh(rowValue.get(1));
        kehuzhObj.setKaihhngm(rowValue.get(3));
        kehuzhObj.setKehuzhao(rowValue.get(5));


        String sql = "";
        try {
            sql = PrepareSql.updateSql(kehuzhObj, "kdpa_kehuzh", "kehuzhao");
        } catch (Exception e) {
            System.err.println("错误数据： "+rowValue);
            e.printStackTrace();
            System.exit(-1);
        }

        return sql;
    }
}
