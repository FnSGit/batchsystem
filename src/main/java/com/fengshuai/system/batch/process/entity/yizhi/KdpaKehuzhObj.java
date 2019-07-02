package com.fengshuai.system.batch.process.entity.yizhi;

import lombok.Data;

@Data
public class KdpaKehuzhObj extends YizhiEntity {

    private String kehuzhao;
    private String shijyhzh;
    private String kaihhngm;

    public KdpaKehuzhObj(boolean isEmpty) {
        super(isEmpty);
    }

    @Override
    void defaultValueInit() {


    }
}
