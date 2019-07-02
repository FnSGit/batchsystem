package com.fengshuai.system.batch.input;

import com.fengshuai.system.batch.input.excell.ExcellInput;
import com.fengshuai.util.io.FileUtil;
import org.junit.Test;

import java.io.File;

public class ExcellInputTest {

    @Test
    public void readTest() throws Exception {
        ExcellInput excellInpu = (ExcellInput) new ItemInputBuilder(ExcellInput.class)
                .set("files", FileUtil.getFiles("file/开户行-银行卡号-1.xls"))
                .set("startRow",1)
                .build();
        for (File f:excellInpu.files)
            excellInpu.process(f);
    }
}
