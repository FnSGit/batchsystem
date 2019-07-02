package com.fengshuai.system.batch.output.Jdbc;


import com.fengshuai.system.batch.BatchLaunch;
import com.fengshuai.system.batch.output.ItemOutput;
import com.fengshuai.system.batch.output.Jdbc.dbtask.ExeSqlDao;
import com.fengshuai.util.character.StringUtil;
import com.fengshuai.util.config.ConfigUtil;
import com.fengshuai.util.db.DataBase;
import com.fengshuai.util.io.IOUtil;
import com.fengshuai.util.log.FsLogger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

@AllArgsConstructor
@Builder
@Data
public class JdbcOutput extends ItemOutput {

    private String dbpool;
    private FsLogger logger ;
    private String table;
    private int rowNum;

    public JdbcOutput() {
        logger= FsLogger.getLogger(this.getClass().getName());
        logger.setLogPath("logs/"+task);
        dbpool = BatchLaunch.dbpool;
    }


    public void ExeBatchSql(String filePath){
        logger.info("-----------------------------------------------任务[{}]执行开始！--------------------------------------------",task);
        File file = new File(filePath);
        ArrayList<File> files = new ArrayList();

        if (file.isDirectory()) {
            File[] filsTmp = file.listFiles();
            if (filsTmp!=null){
                for (File file1 : filsTmp) {
                    if (file1.isFile())
                        files.add(file1);
                }
            }
        }else if (file.isFile()) {
            files.add(file);
        }

        ExeSqlDao dao = new ExeSqlDao(dbpool,logger);
        Iterator i$ = files.iterator();
        long size=0;
        File obj;
        while(i$.hasNext()) {
            obj = (File)i$.next();
            logger.info("####################################开始执行文件：{}",obj.getName());
            BufferedReader bufferedReader = IOUtil.getBufferReader(obj);
            String line;
            try {
                while((line = bufferedReader.readLine()) != null) {
                    if (line.endsWith(";")) {
                        line = line.substring(0, line.length() - 1).trim();
                    }
                    if (!line.startsWith("--") && !StringUtil.isBlank(line)) {
                       long row= dao.exeUpdate(line);
                       logger.debug("执行影响行数：{},sql：{}",row,line);
                       ++size;
                       if (commitSize!=-1&&size%commitSize==0) {
                           DataBase.commit(dbpool);
                           logger.debug("已提交sql：{}条。",size);
                           System.out.println("已提交sql："+size+"条。");
                       }
                    }
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
               logger.error("执行错误文件：{}",obj);
               System.err.println("执行出错文件："+obj);
               return;
            }
            logger.info("#####################################结束执行文件：{}",obj.getName());
            System.out.println("#####################################结束执行文件："+obj.getName());
        }

        DataBase.commit(dbpool);
        logger.debug("提交总sql：{}条。",size);
        logger.info("-----------------------------------------------任务[{}]执行结束！--------------------------------------------",task);
        System.out.println("提交总sql："+size+"条。");
        System.out.println("所有文件执行成功！");
    }

    @Override
    public void outputProcess(Object object) {
        String sql;
        if (object instanceof String) {
             sql = (String) object;
        } else throw new IllegalArgumentException("output object is not suitable. object: "+object);
        ExeSqlDao dao = new ExeSqlDao(dbpool,logger);
        try {
            int row;
            row = dao.exeUpdate(sql);
            logger.debug("执行影响行数：{},sql：{}",row,sql);
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
            System.err.println("执行出错sql："+sql);
            return;
        }

        if (commitSize != -1 && rowNum % commitSize == 0) {
            commit();
        }


        System.out.println("提交总sql："+rowNum+"条。");
        System.out.println("所有文件执行成功！");
        ++rowNum;
    }

    @Override
    public void commit() {
        DataBase.commit(dbpool);
        logger.debug("已提交sql：{}条。",rowNum);
        System.out.println("已提交sql："+rowNum+"条。");
    }

    @Override
    public void end() {
        DataBase.commit(dbpool);
        logger.debug("提交总sql：{}条。",rowNum);
        logger.info("-----------------------------------------------任务[{}]执行结束！--------------------------------------------",task);
        System.out.println("提交总sql："+rowNum+"条。");
    }


}
