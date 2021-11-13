package com.hazelcast.jet.examples.helloworld;

public class CloudProfiler {

    private static void loadCloudProfilerLibrary() {
        try {
            System.load("/home/kpsy20_yonsei_ac_kr/hazelcast-jet-4.5.1/lib/libcloud_profiler.so");
	    System.load("/home/kpsy20_yonsei_ac_kr/hazelcast-jet-4.5.1/lib/libnet_conf.so");
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
