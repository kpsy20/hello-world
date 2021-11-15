package com.hazelcast.jet.examples.helloworld;

import cp.swig.*;

public class HelloWorld {
    public static void main(String[] args) {
        try {
            Class.forName("com.hazelcast.jet.examples.helloworld.CloudProfiler");
        } catch (Exception e) {
            System.out.println(e);
        }
        long ch = cloud_profiler.openChannel("test", log_format.ASCII, handler_type.IDENTITY);
        cloud_profiler.logTS(ch, 0);
/*
	JetInstance jet = Jet.bootstrappedInstance();

        Pipeline p = Pipeline.create();
	p.readFrom(Sources.buildNetworkSource());
	 .withoutTimestamps()
	 .peek()
	 .writeTo(Sinks.logger());

        JobConfig config = new JobConfig();
        config.setName("hello-world");
        config.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
	config.addClass(cloud_profiler.class);
	config.addClass(cp.swig.cloud_profilerJNI.class);
	config.addClass(cp.swig.log_format.class);
	config.addClass(cp.swig.handler_type.class);
	
        jet.newJobIfAbsent(p, config).join();
*/
    }
/*
class Sources {
    static StreamSource<String> buildNetworkSource() {
        return SourceBuilder
            .stream("network-source", ctx -> {
                int port = 11045;
                ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("port open");		
                ctx.logger().info(String.format("Waiting for connection on port %d ...", port));
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                ctx.logger().info(String.format("Data source connected on port %d.", port));
		System.out.println("data in");
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
	    String value = System.getProperty("java.library.path");
	    System.out.println("RESULT: " + value);
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
    }*/
}

