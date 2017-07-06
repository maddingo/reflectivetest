package no.maddin.test.dyninvoke;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.lang.model.type.PrimitiveType;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Verify that the Exception handling of the calcnodes is behaving as expected.
 */
@RunWith(Parameterized.class)
@SuppressWarnings("checkstyle:visibilitymodifier")
public class CalcNodeExceptionTest {

    @Parameterized.Parameters(name = "{index} {1}")
    public static List<Object[]> data() throws Exception {
        return testClasses("no.maddin.test.dyninvoke", Base.class).stream()
            .map(c -> new Object[] {c.getSimpleName(), c})
            .collect(Collectors.toList());
    }

    @Parameterized.Parameter(0)
    public String className;

    @Parameterized.Parameter(1)
    public Class<? extends Base> nodeClass;

    /**
     * If an exception is thrown, it should only be a CalculationException.
     * No exception is also good.
     */
    @Test
    public void eval() throws Exception {

        newInstanceOf(nodeClass).eval();
    }

    @SuppressWarnings("unchecked")
    private <T> T newInstanceOf(Class<T> aClass) throws Exception {
        if (aClass.isPrimitive()) {
            switch (aClass.getTypeName()) {
                case "boolean":
                    return (T) Boolean.FALSE;
                case "int":
                    return (T) Integer.valueOf(0);
                case "long":
                    return (T) Long.valueOf(0L);
                case "short":
                    return (T) Short.valueOf((short) 0);
                case "float":
                    return (T) Float.valueOf(0.0f);
                case "double":
                    return (T) Double.valueOf(0.0);
                case "char":
                    return (T) Character.valueOf('\0');
                case "byte":
                    return (T) Byte.valueOf((byte) '\0');
                default:
                    throw new IllegalArgumentException(aClass.getTypeName());
            }
        } else if (aClass.isArray()) {
            return (T) Array.newInstance(aClass.getComponentType(), 0);
        } else {
            final Constructor<?>[] constructors = aClass.getConstructors();

            // constructors sorted by number of arguments, least numbers first
            Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));

            Constructor<?> usedConstructor = constructors[0];
            int nParams = usedConstructor.getParameterCount();
            Object[] args = new Object[nParams];
            int i = 0;
            for (Class<?> argType : usedConstructor.getParameterTypes()) {
                args[i++] = newInstanceOf(argType);
            }
            @SuppressWarnings("unchecked")
            T inst = (T) usedConstructor.newInstance(args);
            return inst;
        }
    }

    /**
     * Get all the files from the given package.
     * Copied from https://stackoverflow.com/a/32828953/555366
     */
    public static <T> List<Class<?>> testClasses(final String pack, final Class<T> baseClass) throws Exception {
        final StandardJavaFileManager fileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);
        return StreamSupport.stream(fileManager.list(
            StandardLocation.CLASS_PATH,
            pack,
            Collections.singleton(JavaFileObject.Kind.CLASS),
            false
            )
            .spliterator(), false)
            .map(javaFileObject -> {
                try {
                    final String[] split = javaFileObject.getName()
                        .replace(".class", "")
                        .replace(")", "")
                        .split(Pattern.quote(File.separator));

                    final String fullClassName = pack + "." + split[split.length - 1];
                    return Class.forName(fullClassName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            })
            .filter(c -> baseClass.isAssignableFrom(c) && !c.isInterface())
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
