package com.fengshuai.system.batch.output;

import com.fengshuai.util.objectClass.ObjectUtil;

public class ItemOutputBuilder {

    private ItemOutput itemOutput;

    public ItemOutputBuilder(Class<? extends ItemOutput> itemClass) {
        try {
            itemOutput=itemClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ItemOutputBuilder(ItemOutput itemOutput) {
        this.itemOutput = itemOutput;
    }

    public ItemOutputBuilder set(String fieldName, Object value) {
        ObjectUtil.setObjField(itemOutput,fieldName,value);
        return this;
    }


    public ItemOutputBuilder commitSize(int commitSize) {
        ObjectUtil.setObjField(itemOutput,"commitSize",commitSize);
        return this;
    }


    public ItemOutput build() {
        return itemOutput;
    }
}
