package com.fengshuai.system.batch.input;

import com.fengshuai.system.batch.output.ItemOutput;
import com.fengshuai.system.batch.process.ConsoleProcess;
import com.fengshuai.system.batch.process.ItemProcess;
import lombok.Data;

import java.io.File;

@Data
public abstract class ItemInput {

    protected  String columns ;
    protected  String valueNum ;
    protected  File[] files;
    protected  String sqlType;
    protected  String separator;
    protected  int startRow;
    protected ItemProcess itemProcess =new ConsoleProcess();
    protected ItemOutput itemOutput ;

    public ItemInput() {
    }



    public abstract void process(File file) throws Exception;


}
