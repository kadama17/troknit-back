package com.example.troknite.troknite.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomMultipartFileDeserializer extends JsonDeserializer<CustomMultipartFile> {

    @Override
    public CustomMultipartFile deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // Implement deserialization logic here
        // For example, you can read the byte array from the JSON and construct a CustomMultipartFile object
        byte[] bytes = jsonParser.getBinaryValue();
        String filename = "example-filename"; // Provide the appropriate filename
        return new CustomMultipartFile(bytes, filename);
    }
}
