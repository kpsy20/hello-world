package com.hazelcast.jet.examples.helloworld;
import com.hazelcast.function.ComparatorEx;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Observable;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.config.ProcessingGuarantee;
import com.hazelcast.jet.datamodel.WindowResult;
import com.hazelcast.jet.function.Observer;
import com.hazelcast.jet.pipeline.*;
import com.hazelcast.jet.pipeline.file.FileSources;
import com.hazelcast.jet.pipeline.test.TestSources;
//import com.hazelcast.jet.examples.helloworld.CustomSources;

import cp.swig.*;
import cp.swig.log_format;
import cp.swig.handler_type;
public class HelloWorld {
    public static void main(String[] args) {
        try {
//	ClassLoaderUtil.newInstance(null, "com.hazelcast.jet.examples.helloworld.CloudProfiler");
            Class.forName("com.hazelcast.jet.examples.helloworld.CloudProfiler");
        } catch (Exception e) {
            System.out.println(e);
        }
        long ch = cloud_profiler.openChannel("test", log_format.ASCII, handler_type.IDENTITY);
        cloud_profiler.logTS(ch, 0);
    }
}

