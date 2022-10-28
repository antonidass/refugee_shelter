package com.example.refugeeshelter.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public class FilterRoomsObject {
    private static FilterRoomsObject INSTANCE;
    private FilterRoomsObject() {
    }

    public static FilterRoomsObject getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FilterRoomsObject();
        }
        return INSTANCE;
    }

    public MappingJacksonValue getMappingJacksonValue(Object object) {
        MappingJacksonValue mappingJacksonValue;

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept("user");


        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("roomsFilter", simpleBeanPropertyFilter);

        mappingJacksonValue = new MappingJacksonValue(object);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
}