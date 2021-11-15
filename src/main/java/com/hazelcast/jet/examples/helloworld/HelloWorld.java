package com.hazelcast.jet.examples.helloworld;

import cp.swig.*;

public class HelloWorld {
    private static cloud_profilerJNI cpj = new cloud_profilerJNI();
    public static void main(String[] args) {
        try {
            Class.forName("com.hazelcast.jet.examples.helloworld.CloudProfiler");
        } catch (Exception e) {
            System.out.println(e);
        }
        long ch = cloud_profiler.openChannel("test", log_format.ASCII, handler_type.IDENTITY);
        cloud_profiler.logTS(ch, 0);
    }
}

