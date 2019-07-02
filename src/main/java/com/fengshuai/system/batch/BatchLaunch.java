package com.fengshuai.system.batch;

import com.fengshuai.system.batch.input.ItemInput;
import com.fengshuai.system.batch.output.ItemOutput;
import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.util.config.ConfigUtil;
import com.fengshuai.util.db.DataBase;
import com.fengshuai.util.log.FsLogger;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Builder
@AllArgsConstructor

public class BatchLaunch {
    private ItemInput itemInput;
    private ItemProcess itemProcess;
    private ItemOutput itemOutput;

    FsLogger logger = FsLogger.getLogger(this.getClass().getName());

    public static  String dbpool ;
    public BatchLaunch() {
        if (itemProcess !=null)
            itemInput.setItemProcess(itemProcess);
        Properties properties= null;
        try {
            properties = ConfigUtil.getNormProperties("config/Jdbc.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbpool = properties.getProperty("yizhi");
    }

    public void launch() {
        for (int i = 0; i < itemInput.getFiles().length; i++) {
            File file = itemInput.getFiles()[i];
            try {
                itemInput.process(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemOutput.end();

    }


}
