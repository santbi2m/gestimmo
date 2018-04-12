package com.ag2m.gestimmo.webapp.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class HibernateAwareObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 1829235503334236302L;

	public HibernateAwareObjectMapper() {

		registerModule(new Hibernate5Module());
	}

	
}
