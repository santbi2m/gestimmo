package com.ag2m.gestimmo.metier.utils;

import java.io.IOException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateJsonDeserializer extends JsonDeserializer<LocalDateTime> {
	  
    /**
	 * Default formatter
	 */
	private final DateTimeFormatter formatter 
      = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss");
 
 
     
			public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				String date = p.getText();
				return formatter.parseLocalDateTime(date);
			}
}