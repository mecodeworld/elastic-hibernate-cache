package home.app.api.configuration;

import java.time.ZonedDateTime;

import javax.ws.rs.ext.ParamConverter;

import com.google.common.base.Strings;

public class ZonedDateTimeParamConverter implements ParamConverter<ZonedDateTime> {

    @Override
    public String toString(final ZonedDateTime value) {
        return value.toString();
    }

    @Override
    public ZonedDateTime fromString(final String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        return ZonedDateTime.parse(value);
    }
}
