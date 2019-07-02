package com.fengshuai.system.batch.input.text;

import com.fengshuai.system.batch.input.InputReader;
import com.fengshuai.system.batch.input.ItemInput;
import com.fengshuai.system.batch.input.reader.TextReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class TextInput extends ItemInput {


    @Override
    public void process(File file) throws Exception {
        TextReader reader = new TextReader(this,new InputReader() {
            @Override
            public void getRows(long r,List<String> var3) {
                if (r>=startRow)
                    itemProcess.writeProcess(file,var3);
            }
        });

        reader.readFile(file);
    }




}
