package com.example.troknite.troknite.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomMultipartFileSerializer extends JsonSerializer<CustomMultipartFile> {

    @Override
    public void serialize(CustomMultipartFile customMultipartFile, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // Implement serialization logic here
        // For example, you can serialize the byte array or any other necessary representation
        jsonGenerator.writeBinary(customMultipartFile.getBytes());
    }
}
