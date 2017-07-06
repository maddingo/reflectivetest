package no.maddin.test.dyninvoke;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DClass {
    private final BClass par1;
    private boolean calledWrongConstructor;

    /**
     * This constructor should not have been called since there is a constructor with fewer parameters.
     */
    public DClass(BClass p1, CClass p2) {
        throw new UnsupportedOperationException();
    }
}
