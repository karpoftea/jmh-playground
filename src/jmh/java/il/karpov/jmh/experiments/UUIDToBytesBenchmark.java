package il.karpov.jmh.experiments;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

/*
Benchmark                           Mode  Cnt        Score       Error  Units
UUIDToBytesBenchmark.asBytes       thrpt   50  2011178,561 ± 53003,688  ops/s
UUIDToBytesBenchmark.asUuidString  thrpt   50  3247594,074 ± 43608,785  ops/s
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class UUIDToBytesBenchmark {

    private static final String TEST_UUID = UUID.randomUUID().toString();
    private static final byte[] TEST_UUID_BYTES = asBytes(TEST_UUID);

    @Benchmark
    public byte[] asBytes() {
        return asBytes(TEST_UUID);
    }

    @Benchmark
    public String asUuidString() {
        return asUuidString(TEST_UUID_BYTES);
    }

    private static String asUuidString(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong).toString();
    }

    private static byte[] asBytes(String uuid) {
        UUID id = UUID.fromString(uuid);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(id.getMostSignificantBits());
        bb.putLong(id.getLeastSignificantBits());
        return bb.array();
    }
}
