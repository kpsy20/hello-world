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
import com.hazelcast.jet.examples.helloworld.CustomSources;
<<<<<<< HEAD

import cp.swig.cloud_profiler;
import cp.swig.cloud_profilerJNI;
import cp.swig.log_format;
import cp.swig.handler_type;

=======
import com.hazelcast.jet.pipeline.file.FileSources;
//import cp.swig.cloud_profiler;
//import cp.swig.log_format;
//import cp.swig.handler_type;
//import cp.swig.cloud_profilerJNI;
import cp.swig.*;
>>>>>>> 58aee49f9826b7b9bb59069c02b0ace550df87c3
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import java.util.Properties;

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


        //p.readFrom(CustomSources.itemStream(1)).withIngestionTimestamps().writeTo(Sinks.logger());

        //long ch = cloud_profiler.openChannel("hazel-cast", log_format.ASCII, handler_type.IDENTITY);
        //cloud_profiler.logTS(1, 0);


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
	try{Class.forName("com.hazelcast.jet.examples.helloworld.CloudProfiler");
        }
        catch(Exception e)
        {
		//System.out.println(e);
		int a=0;
        }
        long ch = cloud_profiler.openChannel("test", log_format.ASCII, handler_type.IDENTITY);
        cloud_profiler.logTS(ch, 0);
        JetInstance jet = Jet.bootstrappedInstance();
/*
        Observable<List<Long>> observable = jet.getObservable(RESULTS);
        observable.addObserver(Observer.of(HelloWorld::printResults));
*/
        Pipeline p = buildPipeline();
<<<<<<< HEAD
=======
//	BatchSource<String> source = FileSources.files("/home/kpsy20_yonsei_ac_kr/in")
//                                        .build();
//	p.readFrom(source)
//		.writeTo(Sinks.logger());
//		Pipeline p = Pipeline.create();
	p.readFrom(Sources.buildNetworkSource());
//	 .withoutTimestamps()
//	 .peek()
//	 .writeTo(Sinks.logger());
//	CloudProfiler.init();
/*
	try{Class.forName("com.hazelcast.jet.examples.helloworld.CloudProfiler");
	}
	catch(Exception e)
	{System.out.println(e);
	}
	long ch = cloud_profiler.openChannel("test", log_format.ASCII, handler_type.IDENTITY);
	cloud_profiler.logTS(ch, 0);
*/
>>>>>>> 58aee49f9826b7b9bb59069c02b0ace550df87c3
        JobConfig config = new JobConfig();
        config.setName("hello-world");
        config.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
	config.addClass(cloud_profiler.class);
	config.addClass(cp.swig.cloud_profilerJNI.class);
	config.addClass(cp.swig.log_format.class);
	config.addClass(cp.swig.handler_type.class);
	
        jet.newJobIfAbsent(p, config).join();
        config.addClass(cloud_profiler.class);
        config.addClass(cp.swig.cloud_profilerJNI.class);

        config.addClass(cloud_profilerJNI.class);
    }
    private static void printResults(List<Long> topNumbers) {
        StringBuilder sb = new StringBuilder(String.format("\nTop %d random numbers in the latest window: ", TOP));
        for (int i = 0; i < topNumbers.size(); i++) {
            sb.append(String.format("\n\t%d. %,d", i + 1, topNumbers.get(i)));
        }
        System.out.println(sb.toString());
    }
}

class Sources {
    static StreamSource<String> buildNetworkSource() {
        return SourceBuilder
            .stream("network-source", ctx -> {
                int port = 11045;
                ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("HEHEHEHHHHHHHHHHHHHHHHH"); //okay
//		CloudProfiler.init();
//		long ch = cloud_profiler.openChannel("hazel-cast", log_format.ASCII, handler_type.IDENTITY);
//	        cloud_profiler.logTS(ch, 0);		
                ctx.logger().info(String.format("Waiting for connection on port %d ...", port));
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                ctx.logger().info(String.format("Data source connected on port %d.", port));
	
		System.out.println("LASTTTTTTTTTTTTTTTTTTTTTTTT");

                return new NetworkContext(reader, serverSocket);
            })
            .<String>fillBufferFn((context, buf) -> {
                BufferedReader reader = context.getReader();
		
                for (int i = 0; i < 128; i++) {
                    if (!reader.ready()) {
                        return;
                    }
                    String line = reader.readLine();
                    if (line == null) {
                        buf.close();
                        return;
                    }
                    buf.add(line);
                }
            })
            .destroyFn(context -> context.close())
            .build();
    }

    private static class NetworkContext {
        private final BufferedReader reader;
        private final ServerSocket serverSocket;

        NetworkContext(BufferedReader reader, ServerSocket serverSocket) {
            this.reader = reader;
            this.serverSocket = serverSocket;
<<<<<<< HEAD
=======
	    String value = System.getProperty("java.library.path");
	    System.out.println("RESULT: " + value);
//            CloudProfiler.init();
//	    try{            
//	    Class.forName("com.hazelcast.jet.examples.helloworld.CloudProfiler");
//	    }
//	    catch(Exception e)
//	    {System.out.println(e);
//	    }
//	    String value2 = System.getProperty("java.library.path");
//           System.out.println("RESULT: " + value2);
//	    long ch = cloud_profiler.openChannel("test", log_format.ASCII, handler_type.IDENTITY);
//	    cloud_profiler.logTS(ch, 0);
>>>>>>> 58aee49f9826b7b9bb59069c02b0ace550df87c3

        }

        BufferedReader getReader() {
            return reader;
        }

        void close() {
            try {
                reader.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}
