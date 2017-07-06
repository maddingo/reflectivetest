package no.maddin.test.dyninvoke;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Data
public class BClass implements Base {
    private final String par1;
    private final AClass par2;

    public void eval() {
        requireNonNull(par1);
        requireNonNull(par2);
        par2.eval();
    }
}
