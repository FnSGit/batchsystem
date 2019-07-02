package com.fengshuai.system.batch.input.excell;

import com.fengshuai.system.batch.input.ItemInput;
import com.fengshuai.util.common.CommUtil;
import com.fengshuai.util.excell.ExcellReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class ExcellInput extends ItemInput {
//    private  String sheet ;
    private  int sheetIndxe ;//按sheet页读取仅支持xlsx

    public ExcellInput() {
    }

    @Override
    public void process(File file) throws Exception {
        ExcellReader reader;
        if (CommUtil.isNull(sheetIndxe)) {

            reader = new ExcellReader() {
                @Override
                public void getRows(int i, int i1, List<String> list) {
                    try {
                        if (i1 >= startRow) {
                            Object object= itemProcess.writeProcess(file,list);
                            if (itemOutput!=null)
                                itemOutput.outputProcess(object);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            reader.readExcel(file.getAbsolutePath());
        } else {
            reader = new ExcellReader() {
                @Override
                public void getRows(int i, int i1, List<String> list) {
                    if (i1 >= startRow) {
                        Object object= itemProcess.writeProcess(file,list);
                        if (itemOutput!=null)
                            itemOutput.outputProcess(object);
                    }

                }
            };
            reader.readExcel(file.getAbsolutePath(),sheetIndxe);
        }

    }



}
