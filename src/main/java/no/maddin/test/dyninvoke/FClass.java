package no.maddin.test.dyninvoke;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import static java.util.Objects.requireNonNull;

@Data
@RequiredArgsConstructor
public class FClass implements Base {
    private final int[] p1;
    private final char[] p2;
    private final byte[] p3;
    private final String[] p4;
    private final float[] p5;
    private final double[] p6;
    private final long[] p7;
    private final short[] p8;
    private final BClass[] p9;

    @Override
    public void eval() {
        requireNonNull(p1);
        requireNonNull(p2);
        requireNonNull(p3);
        requireNonNull(p4);
        requireNonNull(p5);
        requireNonNull(p6);
        requireNonNull(p7);
        requireNonNull(p8);
    }
}
