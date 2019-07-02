package com.fengshuai.system.batch.process;

import java.util.List;

public class ConsoleProcess implements ItemProcess {

    @Override
    public Object writeProcess(List<String> rowValue){
        System.out.println(rowValue);
        return null;
    }
}
