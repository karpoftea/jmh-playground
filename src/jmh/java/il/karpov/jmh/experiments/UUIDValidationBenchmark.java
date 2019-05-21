package il.karpov.jmh.experiments;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class UUIDValidationBenchmark {

    private static final Pattern UUID_PATTERN =
        Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    private static final String VALID_UUID = "d6ac4568-20a2-4338-90c7-516231dc0774";
    private static final String INVALID_UUID = "XXXXXXXX-YYYY-JJJJ-GGGG-SSSSSSSSSSSS";

    @Benchmark
    public boolean validateByRegExp_valid() {
        return validateByRegExp(VALID_UUID);
    }

    @Benchmark
    public boolean validateByRegExp_invalid() {
        return validateByRegExp(INVALID_UUID);
    }

    @Benchmark
    public boolean validateByTryCatch_valid() {
        return validateByTryCatch(VALID_UUID);
    }

    @Benchmark
    public boolean validateByTryCatch_invalid() {
        return validateByTryCatch(INVALID_UUID);
    }

    public static boolean validateByRegExp(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public static boolean validateByTryCatch(String uuid) {
        try {
            return UUID.fromString(uuid) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
