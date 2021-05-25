package home.app.api.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.ParamConverter;

/**
 * For (De-)serialization REST_URL -to- JAVA (request-param and query-param) the following rules exists
 * <p>
 * From <a href="https://jersey.github.io/documentation/latest/jaxrs-resources.html#d0e2271">jersey docu:</a> <br>
 * In general the Java type of the method parameter may:
 * <ol>
 * <li>Be a primitive type;
 * <li>Have a constructor that accepts a single String argument;
 * <li>Have a static method named valueOf or fromString that accepts a single String argument (see, for example, Integer.valueOf(String) and
 * java.util.UUID.fromString(String));
 * <li>Have a registered implementation of javax.ws.rs.ext.ParamConverterProvider JAX-RS extension SPI that returns a javax.ws.rs.ext.ParamConverter instance
 * capable of a "from string" conversion for the type. or
 * <li>Be List<T>, Set<T> or SortedSet<T>, where T satisfies 2 or 3 above. The resulting collection is read-only.
 * </ol>
 * <p>
 * 
 * Here the option 4 is chosen for java LocalDate. This converter must be provided (e.g. by a javax.ws.rs.ext.Provider annotated class).
 * 
 * @see LocalDateConverterProvider
 * @author mberckem
 *
 */
public class LocalDateParamConverter implements ParamConverter<LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    @Override
    public LocalDate fromString(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("LocalDate value must not be null (uuuu-MM-dd)");
        }
        return LocalDate.parse(value, formatter);
    }

    @Override
    public String toString(final LocalDate value) {
        return value.toString();
    }
}
