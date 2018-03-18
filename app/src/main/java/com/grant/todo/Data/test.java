package com.grant.todo.Data;

/**
 * Created by Grant on 3/18/18.
 */

public class test {
    private static final test ourInstance = new test();

    public static test getInstance() {
        return ourInstance;
    }

    private test() {
    }
}
