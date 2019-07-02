package com.fengshuai.system.batch.process.business.yizhi;

import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.system.batch.process.entity.yizhi.KdpyJbxinxObj;
import com.fengshuai.util.character.StringUtil;
import com.fengshuai.util.common.CommUtil;
import com.fengshuai.util.date.DateUtil;
import com.fengshuai.util.db.PrepareSql;
import com.fengshuai.util.parse.KeyUtil;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SqlGenFuzhaiToYizhi extends YizhiProcess implements ItemProcess {
    

    @Override
    public Object writeProcess(File file, List<String> rowValue) {

        String bianhao=rowValue.get(0);
        if (CommUtil.isNull(rowValue))
            return "";
        boolean isFaren = isFaren(bianhao, rowValue.get(1));
        String zhanghuyue = rowValue.get(10);

//        if (Double.parseDouble(zhanghuyue)>=50000)//法人社员最少存入5万


        String columns = "FARENDMA,ZHANGHAO,KEHUZHMC,CHAPBHAO,ZHUFLDM1,ZHUFLDM2,CUNKZLEI,HUOBDAIH,CHAOHUBZ,CUNQIIII,ZHKHJIGO,KAIHURIQ,KEHUZHAO,ZHHAOXUH,KEHUZHLX,ZHIXLILV,QIXIRIQI,JITILIXI,JIXILJYE,ZHDAOQIR,JIARCLFS,BJDAOQXX,XIANJNCR,XIANJZHQ,GLPINZBZ,TDUIBZHI,PNGZZLEI,PNGZPHAO,PNGZXHAO,ZHFUTOJN,KAIHRIQI,ZHHUZTAI,ZHJEDJBZ,ZHFBDJBZ,ZHZSBFBZ,ZHZFBSBZ,JKHUHAOO,ZHANGHYE,ZUIJGXRQ,XUNHDKBZ,JIESZHBZ,WAIHHCBZ,SFYZBZHI,YIZHZTAI,FENHBIOS,WEIHGUIY,WEIHJIGO,WEIHRIQI,SHIJCHUO,JILUZTAI,GUOBDAIM";

        KdpyJbxinxObj jbxinxObj = new KdpyJbxinxObj();

        jbxinxObj.setZhanghao(rowValue.get(3));//负债账号
        jbxinxObj.setKehuzhmc(rowValue.get(1));//账户名称

        /*
        产品号
         */
        String chanpinhao;
        /*
        账户分类代码
         */
        String fenleiDm1;
        String fenleiDm2;
        /*
        存款种类
         */
        String cunkZonl;
        /*
        客户号
         */
        String kehuhao = rowValue.get(0);
        kehuhao=kehuhao.substring(kehuhao.length() - 15);
        if (isFaren) {
            chanpinhao = "210003";
            fenleiDm1 = "0101";
            fenleiDm2 = "026";
            cunkZonl = "02";
            kehuhao = 2+kehuhao;
        } else {
            chanpinhao = "220010";
            fenleiDm1 = "XXX";
            fenleiDm2 = "XXX";
            cunkZonl = "25";
            kehuhao = 1+kehuhao;
        }

        jbxinxObj.setChapbhao(chanpinhao);
        jbxinxObj.setZhufldm1(fenleiDm1);
        jbxinxObj.setZhufldm2(fenleiDm2);
        jbxinxObj.setCunkzlei(cunkZonl);

        /*
        存期
         */
        String cunqi = rowValue.get(6);
        String[] cunqiUnitNm = {"年","月","天"};
        String[] cunqiUnit = {"Y","M","D"};
        String[] shuziNm = {"一","二","三","四","五","六","七","八","九"};
        String[] shuzi = {"1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < cunqiUnitNm.length; i++) {
            String unitNm = cunqiUnitNm[i];
            if (cunqi.contains(unitNm))
                cunqi = cunqi.replaceAll(unitNm, cunqiUnit[i]);
        }
        for (int i = 0; i < shuziNm.length; i++) {
            String shuzinm = shuziNm[i];
            cunqi=cunqi.replaceAll(shuzinm, shuzi[i]);
        }
        jbxinxObj.setCunqiiii(cunqi);

        /*
        开户日期
         */
        String kaihuriq = rowValue.get(2);
        kaihuriq = kaihuriq.replaceAll("-", "");
        jbxinxObj.setKaihuriq(kaihuriq);

        jbxinxObj.setKehuzhao(rowValue.get(3));

        /*
        执行利率
         */
        String lilv = rowValue.get(7);
        float flilv = Float.parseFloat(lilv);
        jbxinxObj.setZhixlilv(String.valueOf(flilv*100));

        /*
        到期日期
         */
        String daoqiriqi = rowValue.get(12);
        daoqiriqi = daoqiriqi.replaceAll("-", "");
        jbxinxObj.setZhdaoqir(daoqiriqi);

        /*
        计提利息
         */
        String startDate = "20190605";
        String endDate = "20190630";
        double jitililvhq=0;
        double jitililv=0;

        int dateDiffhq=0;
        int dateDiff=0;
        int daysOfMonth= DateUtil.getDaysOfMonth(startDate);
        if (daoqiriqi.compareTo(startDate) > 0&&daoqiriqi.compareTo(endDate) < 0) {

            dateDiffhq= DateUtil.getDaysDiff(daoqiriqi, endDate);
        } else if (daoqiriqi.compareTo(startDate) < 0) {

            dateDiffhq= DateUtil.getDaysDiff(daoqiriqi, endDate);
        } else if (daoqiriqi.compareTo(endDate) > 0) {
            if (kaihuriq.compareTo(endDate) > 0) {
                dateDiff= DateUtil.getDaysDiff(kaihuriq, endDate);
            } else {
                dateDiff = DateUtil.getDaysDiff(startDate, endDate);
            }
        }

        jitililv=flilv/12/daysOfMonth*dateDiff;//利率/12/当月天数*间隔天数
        jitililvhq=0.0038/365*dateDiffhq;//利率/365*间隔天数


        BigDecimal jitilixi = BigDecimal.valueOf(jitililv + jitililvhq).multiply(new BigDecimal(zhanghuyue)).setScale(7, RoundingMode.HALF_UP);
        jbxinxObj.setJitilixi(jitilixi.toString());


        jbxinxObj.setQixiriqi(kaihuriq);//起息日期


        /*
        凭证批号、序号
         */
        String pinzXuhao = rowValue.get(4);
        String pngzphao ;
        if (pinzXuhao.length() > 3) {

            pngzphao = pinzXuhao.substring(0, 3);
            pinzXuhao = pinzXuhao.substring(3);
            pinzXuhao = StringUtil.rFillStr(pinzXuhao, '0', 8);
        } else {
            pngzphao = "000";
            pinzXuhao = StringUtil.rFillStr(pinzXuhao, '0', 8);
        }
        jbxinxObj.setPngzphao(pngzphao);//凭证批号
        jbxinxObj.setPngzxhao(pinzXuhao);//凭证序号


        jbxinxObj.setKaihriqi(kaihuriq);

        jbxinxObj.setJkhuhaoo(kehuhao);

        jbxinxObj.setZhanghye(zhanghuyue);//账户余额

        jbxinxObj.setZuijgxrq(kaihuriq);

        String fileName=file.getName();
        fileName = fileName.substring(fileName.lastIndexOf("/"));
        String zhkhjigo = "9920";
        if (fileName.contains("厦门"))
            zhkhjigo = "9910";
        jbxinxObj.setZhkhjigo(zhkhjigo);//账户机构

        String zhhuztai = "A";
      /*  String zhhuztai = rowValue.get(9);
        if (zhhuztai.contains("正常")) {
            zhhuztai = "A";
        } else if (zhhuztai.contains("冻结")) {
            zhhuztai = "F";//E封闭冻结 F金额冻结
        } else {
            throw new IllegalArgumentException("未知账户状态，数据 " + zhhuztai);
        }*/
        jbxinxObj.setZhhuztai(zhhuztai);//账户状态
        dataPersistent(KeyUtil.parseKey("kdpy_jbxinx",jbxinxObj.getZhanghao()),jbxinxObj);
        return PrepareSql.insertSql(jbxinxObj, "kdpy_jbxinx");
    }
}
