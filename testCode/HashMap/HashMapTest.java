package test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cityhuntshou
 * @apiNote HashMap测试
 * @date 2022/2/20 20:45
 */
@BenchmarkMode(Mode.AverageTime) // 测试完成时间
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 2 轮，每次 1s
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 1s
@Fork(1) // fork 1 个线程
@State(Scope.Thread) // 每个测试线程一个实例
public class HashMapTest {

    static Map<Integer, String> map = new HashMap() {{
        for (int i = 0; i < 100; i++) {
            put(i, "val-" + i);
        }
    }};
    
    public static void main(String[] args) throws RunnerException {
        //Map<Integer, String> integerStringMap = getIntegerStringMap();
        //iteratorTest(integerStringMap);
        //keySetIterator(integerStringMap);
        //forEachEntryTest(integerStringMap);
        //forEachkeySetTest(integerStringMap);
        //lambdaTest(integerStringMap);
        //streamApiSingleThreadTest(integerStringMap);
        //streamApiMultiThread(integerStringMap);

        Options options = new OptionsBuilder()
                .include(HashMapTest.class.getName())
                .output("C:\\github\\testCode\\HashMap\\jmh-map.log")
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public static void EntrySetIteratorTest() {
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            Integer intKey = entry.getKey();
            String strValue = entry.getValue();
        }
    }

    @Benchmark
    public static void keySetIterator() {
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            String strValue = map.get(next);
        }
    }

    @Benchmark
    public static void forEachEntrySetTest() {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer intKey = entry.getKey();
            String strValue = entry.getValue();
        }
    }

    @Benchmark
    public static void forEachKeySetTest() {
        for (Integer integer : map.keySet()) {
            String strValue = map.get(integer);
        }
    }

    @Benchmark
    public static void lambdaTest() {
        map.forEach((key, value) -> {
            Integer intKey = key;
            String strValue = value;
        });
    }

    @Benchmark
    public static void streamApiSingleThreadTest() {
        map.entrySet().stream().forEach((entry) -> {
            Integer intKey = entry.getKey();
            String strValue =entry.getValue();
        });
    }

    public static void streamApiMultiThread() {
        map.entrySet().parallelStream().forEach((entry) -> {
            entry.getKey();
            entry.getValue();
        });
    }
}
