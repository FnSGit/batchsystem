package com.fengshuai.system.batch.input.reader;

import com.fengshuai.system.batch.input.InputReader;
import com.fengshuai.system.batch.input.ItemInput;
import com.fengshuai.util.io.IOUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.*;
import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
public class TextReader {

    private InputReader inputReader;
    private ItemInput itemInput;

    public TextReader(ItemInput itemInput,InputReader inputReader) {
        this.itemInput=itemInput;
        this.inputReader = inputReader;

    }

    public void readFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        long row=0;
        while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(itemInput.getSeparator());
                inputReader.getRows( row,Arrays.asList(array));

            ++row;
        }

        bufferedReader.close();
        fileReader.close();
    }
}
