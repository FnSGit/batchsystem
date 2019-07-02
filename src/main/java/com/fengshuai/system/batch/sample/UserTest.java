package com.fengshuai.system.batch.sample;

import com.fengshuai.system.batch.process.ItemProcess;

import java.util.List;

public class UserTest extends ItemProcess {

    @Override
    public void writeProcess(List<String> rowValue) {
        User user = new User();
        user.setName(rowValue.get(0));
        user.setAge(Integer.parseInt(rowValue.get(1)));
//        System.out.println(user);

        System.out.println(rowValue);
    }
}
