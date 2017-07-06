package no.maddin.test.dyninvoke;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Data
public class CClass implements Base {
    private final AClass par1;
    private final String par2;
    private final boolean par3;
    private final BClass par4;

    @Override
    public void eval() {
        requireNonNull(par1);
        requireNonNull(par2);
        requireNonNull(par3);
        requireNonNull(par4);
        par1.eval();
        par4.eval();
    }
}
