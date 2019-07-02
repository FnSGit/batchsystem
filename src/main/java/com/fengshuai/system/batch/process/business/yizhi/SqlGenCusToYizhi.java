package com.fengshuai.system.batch.process.business.yizhi;



import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.system.batch.process.entity.yizhi.KcfbYzdgjcObj;
import com.fengshuai.system.batch.process.entity.yizhi.KcfbYzdsjcObj;
import com.fengshuai.util.db.PrepareSql;
import com.fengshuai.util.parse.KeyUtil;

import java.io.File;
import java.util.List;

public class SqlGenCusToYizhi extends YizhiProcess implements ItemProcess {


    @Override
    public Object writeProcess(File file, List<String> rowValue) {
//        System.out.println("rowValue---"+rowValue);
        String sql = "";
        try {

            String sheyName = rowValue.get(1);

            if (isFaren(rowValue.get(0),sheyName)) {
                String columns = "FARENDMA,KEHUHAOO,KEHUZHWM,YINGWENM,CENGYMCH,KEHUZJZL,KEHUZHJH,FRDBDHHM,KEHUZTAI,GUISHJIG,DGJYDZHI,YZHIZTAI,FENHBIOS,WEIHGUIY,WEIHJIGO,WEIHRIQI,WEIHSHIJ,SHIJCHUO,JILUZTAI,GUOBDAIM";
                /*  insert into  kcfb_yzdgjc (FARENDMA,KEHUHAOO,KEHUZHWM,YINGWENM,CENGYMCH,KEHUZJZL,KEHUZHJH,FRDBDHHM,KEHUZTAI,GUISHJIG,DGJYDZHI,YZHIZTAI,FENHBIOS,WEIHGUIY,WEIHJIGO,WEIHRIQI,WEIHSHIJ,SHIJCHUO,JILUZTAI,GUOBDAIM) values ('8801','91520323322670648E','遵义康地房地产开发有限公司',null,null,'22','91520323322670648E','13350369388',null,null,null,null,'0','9901','贵州省遵义市绥阳县洋川镇天台村','0','99','88010000','9901','20190514','20190514','0','0','CHN');*/
                String[] columnArray = columns.split(",");
                KcfbYzdgjcObj yzdgjcObj = new KcfbYzdgjcObj();
            /*
            截取客户号
             */
                String kehuhao = rowValue.get(0);
                kehuhao = 2 + kehuhao.substring(kehuhao.length() - 15);
                yzdgjcObj.setKehuhaoo(kehuhao);

                yzdgjcObj.setKehuzhwm(rowValue.get(1));//客户名称

                yzdgjcObj.setYingwenm("");//英文名称，助记码
                yzdgjcObj.setCengymch("");//曾用名，用友客户号

                yzdgjcObj.setKehuzhjh(rowValue.get(0));//客户证件号
                yzdgjcObj.setFrdbdhhm(rowValue.get(6));//法人电话号码
                yzdgjcObj.setDgjydzhi(rowValue.get(4));//法人营业地址


                sql = PrepareSql.insertSql(yzdgjcObj, "kcfb_yzdgjc");

                dataPersistent(KeyUtil.parseKey("kcfb_yzdgjc",yzdgjcObj.getKehuhaoo()),yzdgjcObj);

//                RedisUtil.hmset("kcfb_yzdgjc:" + kehuhao, yzdgjcObj.toMap());
            } else {
                String columns = "FARENDMA,KEHUHAOO,KEHUZHWM,YINGWENM,CENGYMCH,XINGBIEE,ZHJIANZL,ZHJHAOMA,KEHUZTAI,YINGXJIG,YZHIZTAI,ZHZHAIDZ,LIANXFSH,LIANXIHM,FENHBIOS,WEIHGUIY,WEIHJIGO,WEIHRIQI,WEIHSHIJ,SHIJCHUO,JILUZTAI,GUOBDAIM";
                /*  insert into  Kcfb_yzdsjc (FARENDMA,KEHUHAOO,KEHUZHWM,YINGWENM,CENGYMCH,XINGBIEE,ZHJIANZL,ZHJHAOMA,YUESHOUR,NIANXINN,ZHFMIANJ,FANGCGUJ,GERZICZE,GERJDZEE,FDJTCYSM,JTNSHRZE,CHIGUSHU,KEHUZTAI,YINGXJIG,YZHIZTAI,ZHZHAIDZ,LIANXFSH,LIANXIHM,FENHBIOS,WEIHGUIY,WEIHJIGO,WEIHRIQI,WEIHSHIJ,SHIJCHUO,JILUZTAI,GUOBDAIM) values ('8801','522101196012242027','朱平',null,null,'03','021','522101196012242027',null,null,null,null,null,null,null,null,null,'0','9901','0','贵州省遵义市红花岗区中华南路幸福巷2号2单元','30','13618527178','99','88010000','9901','20190514','20190514','20190514','0','CHN');*/
                KcfbYzdsjcObj yzdsjcObj = new KcfbYzdsjcObj();
            /*
            截取客户号
             */
              /*
            截取客户号
             */
                String kehuhao = rowValue.get(0);
                kehuhao = 1 + kehuhao.substring(kehuhao.length() - 15);
                yzdsjcObj.setKehuhaoo(kehuhao);

                yzdsjcObj.setKehuzhwm(rowValue.get(1));//客户名称

                yzdsjcObj.setYingwenm("");//英文名称，助记码
                yzdsjcObj.setCengymch("");//曾用名，用友客户号

                yzdsjcObj.setZhjhaoma(rowValue.get(0)); //客户证件号
                yzdsjcObj.setLianxihm(rowValue.get(6));//法人电话号码
                yzdsjcObj.setZhzhaidz(rowValue.get(4));//法人营业地址
            /*
            获取社员性别
             */
                String gender = rowValue.get(2);
                if (gender.equals("男")) {
                    gender = "02";
                } else if (gender.equals("nv")) {
                    gender = "03";
                } else gender = "01";
                yzdsjcObj.setXingbiee(gender);



                sql = PrepareSql.insertSql(yzdsjcObj, "Kcfb_yzdsjc");

                dataPersistent(KeyUtil.parseKey("kcfb_yzdsjc",yzdsjcObj.getKehuhaoo()),yzdsjcObj);

//                RedisUtil.hmset("Kcfb_yzdsjc:" + kehuhao, yzdsjcObj.toMap());
            }
        } catch (Exception e) {
            System.err.println("解析失败文件["+file.getName()+"]    数据： "+rowValue);
            e.printStackTrace();
            System.exit(-1);
        }
        return sql;
    }
}
