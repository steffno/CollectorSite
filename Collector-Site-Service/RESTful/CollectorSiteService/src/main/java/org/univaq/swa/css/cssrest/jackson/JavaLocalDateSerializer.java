package org.univaq.swa.css.cssrest.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author MastroGibbs
 */
public class JavaLocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException 
    {
       jsonGenerator.writeString(localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth());
    }
}
