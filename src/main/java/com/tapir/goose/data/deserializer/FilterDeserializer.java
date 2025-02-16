package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.FilterDTO;
import com.tapir.goose.data.dto.SymbolDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterDeserializer implements JsonbDeserializer<FilterDTO> {

    @Override
    public FilterDTO deserialize(JsonParser parser,
                                 DeserializationContext ctx,
                                 Type type) {
        String key = null;
        String filterType = "";
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("filterType".equals(key)) {
                        filterType = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                }
                default -> {
                }
            }
        }
        return new FilterDTO(filterType);
    }
}
