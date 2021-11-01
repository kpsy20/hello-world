package com.hazelcast.jet.examples.helloworld;

public class CloudProfiler {

    private static void loadCloudProfilerLibrary() {
        try {
            System.loadLibrary("cloud_profiler");
        }
        catch(UnsatisfiedLinkError e) {
            System.err.println("ERROR: \"cloud_profiler\" native code library failed to load.\n" + e);
            System.exit(1);
        }
        System.out.println("SUCCESS: \"cloud_profiler\" native code library successfully loaded.");
    }

    public static void init() {
        loadCloudProfilerLibrary();
    }
}
