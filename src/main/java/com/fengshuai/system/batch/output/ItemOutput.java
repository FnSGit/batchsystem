package com.fengshuai.system.batch.output;

public abstract class ItemOutput {

    protected int commitSize=-1;
    protected String task;


    public abstract void outputProcess(Object object);

    public abstract void commit();

    public abstract void end();
}
