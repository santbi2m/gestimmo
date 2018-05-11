package com.ag2m.gestimmo.config;

import java.io.IOException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<LocalDateTime> {
	  
    /**
	 * 
	 */
	private DateTimeFormatter formatter 
      = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss");
 
 
     
    @Override
    public void serialize (LocalDateTime value, JsonGenerator gen, SerializerProvider arg2)
      throws IOException, JsonProcessingException {
        gen.writeString(value.toString(formatter));
    }

	
}