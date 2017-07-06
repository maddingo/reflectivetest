package no.maddin.test.dyninvoke;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Data
@RequiredArgsConstructor
public class EClass implements Base {
    private final int p1;
    private final char p2;
    private final byte p3;
    private final String p4;
    private final float p5;
    private final double p6;
    private final long p7;
    private final short p8;

    @Override
    public void eval() {
        Objects.requireNonNull(p4);
    }
}
