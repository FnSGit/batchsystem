package com.fengshuai.system.batch.process;

import java.io.File;
import java.util.List;

public interface ItemProcess {

     Object writeProcess(File file, List<String> rowValue);
}
