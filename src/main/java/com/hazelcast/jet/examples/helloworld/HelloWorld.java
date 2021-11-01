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
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.WindowDefinition;
import com.hazelcast.jet.pipeline.test.TestSources;

import cp.swig.cloud_profiler;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HelloWorld {

    public static final int TOP = 10;
    private static final String RESULTS = "top10_results";
    public static long count = 0;


    private static Pipeline buildPipeline() {
        Pipeline p = Pipeline.create();
/*
	    p.readFrom(TestSources.items("the", "quick", "brown", "fox"))
		.map(item -> item.toUpperCase())
		.writeTo(Sinks.logger());
*/



        CloudProfiler.init();//add Cloudprofiler
        p.readFrom(TestSources.itemStream(1)).withIngestionTimestamps().writeTo(Sinks.logger());

/*
 *
        p.readFrom(TestSources.itemStream(100, (ts, seq) -> nextRandomNumber()))
                .withIngestionTimestamps()
                .window(WindowDefinition.tumbling(1000))
                .aggregate(AggregateOperations.topN(TOP, ComparatorEx.comparingLong(l -> l)))
                .map(WindowResult::result)
                .writeTo(Sinks.observable(RESULTS));
*/
        return p;
    }

    private static long counting() {
	count++;
        return count;
    }

    public static void main(String[] args) {
        JetInstance jet = Jet.bootstrappedInstance();
/*
        Observable<List<Long>> observable = jet.getObservable(RESULTS);
        observable.addObserver(Observer.of(HelloWorld::printResults));
*/
        Pipeline p = buildPipeline();

        JobConfig config = new JobConfig();
        config.setName("hello-world");
        config.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
        jet.newJobIfAbsent(p, config).join();
    }

    private static void printResults(List<Long> topNumbers) {
        StringBuilder sb = new StringBuilder(String.format("\nTop %d random numbers in the latest window: ", TOP));
        for (int i = 0; i < topNumbers.size(); i++) {
            sb.append(String.format("\n\t%d. %,d", i + 1, topNumbers.get(i)));
        }
        System.out.println(sb.toString());
    }

}
