package com.hazelcast.jet.examples.helloworld;

import com.hazelcast.jet.annotation.EvolvingApi;
import com.hazelcast.jet.core.ProcessorMetaSupplier;
import com.hazelcast.jet.impl.util.Util;
import com.hazelcast.jet.pipeline.BatchSource;
import com.hazelcast.jet.pipeline.SourceBuilder;
import com.hazelcast.jet.pipeline.Sources;
import com.hazelcast.jet.pipeline.StreamSource;
import com.hazelcast.jet.pipeline.SourceBuilder.TimestampedSourceBuffer;
import com.hazelcast.jet.pipeline.test.GeneratorFunction;

import com.hazelcast.jet.pipeline.test.SimpleEvent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

@EvolvingApi
public final class CustomSources {
    private CustomSources() {
    }

    @Nonnull
    public static <T> BatchSource<T> items(@Nonnull Iterable<? extends T> items) {
        Objects.requireNonNull(items, "items");
        return (BatchSource<T>) SourceBuilder.batch("items", (ctx) -> {
            return null;
        }).fillBufferFn((ignored, buf) -> {
            items.forEach(buf::add);
            buf.close();
        }).build();
    }

    @Nonnull
    public static <T> BatchSource<T> items(@Nonnull T... items) {
        Objects.requireNonNull(items, "items");
        return items((Iterable)Arrays.asList(items));
    }

    @Nonnull
    public static <T> BatchSource<T> itemsDistributed(@Nonnull Iterable<? extends T> items) {
        Objects.requireNonNull(items, "items");
        return (BatchSource<T>) SourceBuilder.batch("items", (ctx) -> {
            return ctx;
        }).fillBufferFn((ctx, buf) -> {
            Iterator<? extends T> iterator = items.iterator();

            for(int i = 0; iterator.hasNext(); ++i) {
                T item = iterator.next();
                if (i % ctx.totalParallelism() == ctx.globalProcessorIndex()) {
                    buf.add(item);
                }
            }

            buf.close();
        }).distributed(1).build();
    }

    @Nonnull
    public static <T> BatchSource<T> itemsDistributed(@Nonnull T... items) {
        Objects.requireNonNull(items, "items");
        return itemsDistributed((Iterable)Arrays.asList(items));
    }

    @EvolvingApi
    @Nonnull
    public static StreamSource<SimpleEvent> itemStream(int itemsPerSecond) {
        return itemStream(itemsPerSecond, SimpleEvent::new);
    }

    @EvolvingApi
    @Nonnull
    public static <T> StreamSource<T> itemStream(int itemsPerSecond, @Nonnull GeneratorFunction<? extends T> generatorFn) {
        Objects.requireNonNull(generatorFn, "generatorFn");
        Util.checkSerializable(generatorFn, "generatorFn");
        return (StreamSource<T>) SourceBuilder.timestampedStream("itemStream", (ctx) -> {
            return new ItemStreamSource(itemsPerSecond, generatorFn);
        }).fillBufferFn(ItemStreamSource::fillBuffer).build();

        //return SourceBuilder.timestampedStream("itemStream", (ctx) -> {
        //    return new com.hazelcast.jet.pipeline.test.TestSources.ItemStreamSource(itemsPerSecond, generatorFn);
        //}).fillBufferFn(com.hazelcast.jet.pipeline.test.TestSources.ItemStreamSource::fillBuffer).build();
    }
/*
    @Nonnull
    public static StreamSource<Long> longStream(long eventsPerSecond, long initialDelayMillis) {
        return Sources.streamFromProcessorWithWatermarks("longStream", true, (eventTimePolicy) -> {
            long startTime = System.currentTimeMillis() + initialDelayMillis;
            return ProcessorMetaSupplier.of(() -> {
                return new LongStreamSourceP(startTime, eventsPerSecond, eventTimePolicy);
            });
        });
    }
*/
    private static final class ItemStreamSource<T> {
        private static final int MAX_BATCH_SIZE = 1024;
        private final GeneratorFunction<? extends T> generator;
        private final long periodNanos;
        private long emitSchedule;
        private long sequence;

        private ItemStreamSource(int itemsPerSecond, GeneratorFunction<? extends T> generator) {
            this.periodNanos = TimeUnit.SECONDS.toNanos(1L) / (long)itemsPerSecond;
            this.generator = generator;
        }

        void fillBuffer(TimestampedSourceBuffer<T> buf) throws Exception {
            long nowNs = System.nanoTime();
            if (this.emitSchedule == 0L) {
                this.emitSchedule = nowNs;
            }

            long tsNanos = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
            long ts = TimeUnit.NANOSECONDS.toMillis(tsNanos - tsNanos % this.periodNanos);

            for(int i = 0; i < 1024 && nowNs >= this.emitSchedule; ++i) {
                T item = this.generator.generate(ts, (long)(this.sequence++));
                buf.add(item, ts);
                this.emitSchedule += this.periodNanos;
            }
        }
    }
}
