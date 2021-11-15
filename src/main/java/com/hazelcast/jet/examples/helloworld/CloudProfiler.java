package com.hazelcast.jet.examples.helloworld;

public class CloudProfiler {

    static{
        try {
            System.load("/usr/lib/libcloud_profiler.so");
	//    System.load("/usr/lib/libnet_conf.so");
	//    System.loadLibrary("cloud_profiler");
        }
        catch(UnsatisfiedLinkError e) {
            System.err.println("ERROR: \"cloud_profiler\" native code library failed to load.\n" + e);
            System.exit(1);
        }
        System.out.println("SUCCESS: \"cloud_profiler\" native code library successfully loaded.");
    }

//    public static void init() {
//        loadCloudProfilerLibrary();
//    }
}
