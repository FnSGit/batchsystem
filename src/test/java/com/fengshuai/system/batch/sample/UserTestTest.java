package com.fengshuai.system.batch.sample;

import com.fengshuai.system.batch.input.ItemInputBuilder;
import com.fengshuai.system.batch.input.ItemInput;
import com.fengshuai.system.batch.input.text.TextInput;
import com.fengshuai.system.batch.BatchLaunch;
import org.junit.Test;

public class UserTestTest {
    @Test
    public void sampleTest() {
        ItemInput itemInput = new ItemInputBuilder(TextInput.builder().build())
                .files("file/user")
                .separator(",")
                .build();

        BatchLaunch process= BatchLaunch.builder()
                .itemInput(itemInput)
                .itemProcess(new UserTest())
                .build();

        process.launch();
    }
}
