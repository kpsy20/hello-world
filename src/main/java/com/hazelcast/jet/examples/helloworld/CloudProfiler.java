package com.hazelcast.jet.examples.helloworld;

public class CloudProfiler {

    static{
        try {
            System.load("/usr/lib/libcloud_profiler.so");
	   
	    System.load("/opt/squash/0.8/lib/libsquash0.8.so.0.8.0");
	    System.load("/home/namsick96_yonsei_ac_kr/cloud_profiler/build_rel/src/cp/config_server/libnet_conf.so");
	    System.load("/opt/boost/1.76.0/lib/libboost_system.so.1.76.0");
	    System.load("/opt/zmq/4.3.4/lib/libzmq.so.5");
	    System.load("/lib64/libstdc++.so.6");
	    System.load("/lib64/libm.so.6");
	    System.load("/lib64/libgcc_s.so.1");
	    System.load("/lib64/libc.so.6");
	    System.load("/lib64/libpthread.so.0");
	    System.load("/lib64/libdl.so.2");
	    System.load("/lib64/librt.so.1");
	    System.load("/lib64/ld-linux-x86-64.so.2");
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
