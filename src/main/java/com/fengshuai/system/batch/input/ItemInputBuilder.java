package com.fengshuai.system.batch.input;

import com.fengshuai.system.batch.output.ItemOutput;
import com.fengshuai.system.batch.process.ItemProcess;
import com.fengshuai.util.io.FileUtil;
import com.fengshuai.util.objectClass.ObjectUtil;

public class ItemInputBuilder {

    private ItemInput itemInput;

    public ItemInputBuilder(Class<? extends ItemInput> clazz) {
        try {
            itemInput=clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ItemInputBuilder(ItemInput itemInput) {
        this.itemInput=itemInput;
    }


    public ItemInputBuilder set(String fieldName, Object value) {
        ObjectUtil.setObjField(itemInput,fieldName,value);
        return this;
    }

    public ItemInputBuilder files(String filePath) {
        ObjectUtil.setObjField(itemInput,"files", FileUtil.getFiles(filePath));
        return this;
    }

    public ItemInputBuilder separator(String separator) {
        ObjectUtil.setObjField(itemInput,"separator", separator);
        return this;
    }
    public ItemInputBuilder itemProcess(ItemProcess itemProcess) {
        ObjectUtil.setObjField(itemInput,"itemProcess", itemProcess);
        return this;
    }
    public ItemInputBuilder itemOutput(ItemOutput itemOutput) {
        ObjectUtil.setObjField(itemInput,"itemOutput", itemOutput);
        return this;
    }
    public ItemInput build() {
        return itemInput;
    }

}
