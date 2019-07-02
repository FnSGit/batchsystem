package com.fengshuai.system.batch.process.business.yizhi;


import com.fengshuai.system.batch.BatchLaunch;
import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.system.batch.process.entity.yizhi.KcfbYzdgjcObj;
import com.fengshuai.system.batch.process.entity.yizhi.KcfbYzdsjcObj;
import com.fengshuai.util.common.CommUtil;
import com.fengshuai.util.db.DataBase;
import com.fengshuai.util.db.PrepareSql;

import java.io.File;
import java.util.List;

public class SqlGenUdpSheyuan implements ItemProcess {


    @Override
    public Object writeProcess(File file,List<String> rowValue) {
        String sql = "";
        try {

            boolean isFaren=false;
            String kehuMc = rowValue.get(1);

            /*
            重名后5位处理
             */
            String weihao = kehuMc.substring(kehuMc.length() - 5);
            kehuMc = kehuMc.replace(weihao, "");


            String sel_duisi_kemuhao=String.format("select zhjhaoma from Kcfb_yzdsjc where KEHUZHWM like '%s';",kehuMc);
            String sel_duigo_kemuhao=String.format("select KEHUHAOO from kcfb_yzdgjc where KEHUZHWM like '%s'",kehuMc);

            List<String[]> zjlist = DataBase.getResultset(BatchLaunch.dbpool, sel_duisi_kemuhao, String[].class);
            List<String[]> khlist=null;
            if (CommUtil.isNull(zjlist)) {
                khlist = DataBase.getResultset(BatchLaunch.dbpool, sel_duigo_kemuhao, String[].class);
                if (khlist.size()>0)
                    isFaren=true;
            }
          /*  String[] kehuhao= DataBase.getValues(ExeGen.dbpool,sel_duisi_kemuhao,1);
            if (CommUtil.isNull(kehuhao)) {
                kehuhao = DataBase.getValues(ExeGen.dbpool, sel_duigo_kemuhao, 1);
                isFaren=true;
            }*/


            if (CommUtil.isNull(zjlist)&&CommUtil.isNull(khlist)) {
                System.err.println(kehuMc+" "+rowValue.get(0)+" "+rowValue.get(3));
            } else {
                String yongyou = rowValue.get(0);
                String zhujima = rowValue.get(3);
                if (isFaren) {
                    KcfbYzdgjcObj yzdgjcObj = new KcfbYzdgjcObj(true);
                    yzdgjcObj.setCengymch(yongyou);
                    yzdgjcObj.setYingwenm(zhujima);
                    yzdgjcObj.setKehuhaoo(khlist.get(0)[0]);
                    sql=PrepareSql.updateSql(yzdgjcObj, "kcfb_yzdgjc", "KEHUHAOO")+";\n";

                } else {
                    KcfbYzdsjcObj yzdsjcObj = new KcfbYzdsjcObj(true);
                    yzdsjcObj.setCengymch(yongyou);
                    yzdsjcObj.setYingwenm(zhujima);
                    String zjhao = "";
                    for (int i = 0; i < zjlist.size(); i++) {
                        String[] zj = zjlist.get(i);
                        if (zj[0].endsWith(weihao)) {
                            zjhao = zj[0];
                            break;
                        }
                    }
                    yzdsjcObj.setZhjhaoma(zjhao);
                    sql=PrepareSql.updateSql(yzdsjcObj, "Kcfb_yzdsjc", "zhjhaoma")+";\n";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("解析失败文件["+file.getName()+"]    数据： "+rowValue);
            System.exit(-1);
        }
        return sql;
    }
}
