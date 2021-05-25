package home.app.api.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * Provide the converter for java LocalDate -to- REST parameter (pathParameter, QueryParameter etc).
 * <p>
 * NOTE that each converter need its own provider. A generic provider that provides converter by classtype did not work.
 * 
 * @see LocalDateParamConverter
 * @author mberckem
 *
 */
@Provider
@Singleton
public class LocalDateConverterProvider implements ParamConverterProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (!rawType.isAssignableFrom(LocalDate.class))
            return null;
        // we could provide also the converter as a (anonymous)innerclass here ..
        return (ParamConverter<T>) new LocalDateParamConverter();
    }

}
