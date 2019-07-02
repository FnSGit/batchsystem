package com.fengshuai.system.batch.process.business.yizhi;

import com.fengshuai.system.batch.BatchLaunch;
import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.system.batch.process.entity.yizhi.KdpbDqfxdjObj;
import com.fengshuai.util.common.CommUtil;
import com.fengshuai.util.date.DateUtil;
import com.fengshuai.util.db.DataBase;
import com.fengshuai.util.db.PrepareSql;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class SqlGenDqfx implements ItemProcess {
    @Override
    public Object writeProcess(File file, List<String> rowValue) {

        String columns = "farendma,zhanghao,xuhaoooo,zhiqjnge,shijzqcs,shijzqje,xczzzqrq,leijijee,shcifxri,qixiriqi,fenhbios,weihguiy,weihjigo,weihriqi,weihshij,shijchuo,jiluztai";
        KdpbDqfxdjObj dqfxdjObj=new KdpbDqfxdjObj();
        try {

            String zhanghao = rowValue.get(0);
            dqfxdjObj.setZhanghao(zhanghao);

        /*
        金额
         */
            String shijzqje = rowValue.get(8);
            String leijijee = rowValue.get(8);

            dqfxdjObj.setShijzqje(shijzqje);
            dqfxdjObj.setLeijijee(leijijee);
        /*
        下次最早支取日期
         */

            String xczzzqrq;
            String sel_fuzXx = "select ZHDAOQIR,KAIHRIQI from kdpy_jbxinx where ZHANGHAO='" + zhanghao + "'";
            String[] fuzXx = DataBase.getValues(BatchLaunch.dbpool, sel_fuzXx, new String[]{"ZHDAOQIR", "KAIHRIQI"});
            if (CommUtil.isNull(fuzXx)) {
                System.out.println("支取利息的无效数据： "+rowValue);
                return "";
            }
            String daoqiri = fuzXx[0];
            String kaihriqi = fuzXx[1];
            dqfxdjObj.setQixiriqi(kaihriqi);//起息日期
            String xiacizhifuri = "20190831";
            if (xiacizhifuri.compareTo(daoqiri) < 0) {
                xczzzqrq = xiacizhifuri;
            } else {
                xczzzqrq = DateUtil.dateAdd(Calendar.DAY_OF_MONTH, -1, daoqiri);
            }
            dqfxdjObj.setXczzzqrq(xczzzqrq);
        } catch (Exception e) {
            System.err.println("解析失败文件["+file.getName()+"]    数据： "+rowValue);
            System.exit(-1);
        }
        return PrepareSql.insertSql(dqfxdjObj, "kdpb_dqfxdj");
    }
}
