package com.example.troknite.troknite.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@JsonSerialize(using = CustomMultipartFileSerializer.class)
@JsonDeserialize(using = CustomMultipartFileDeserializer.class)
public class CustomMultipartFile implements MultipartFile {

    private final byte[] bytes;
    private final String filename;
    private final InputStream inputStream;

    public CustomMultipartFile(byte[] bytes, String filename) {
        this.bytes = bytes;
        this.filename = filename;
        this.inputStream = new ByteArrayInputStream(bytes);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes != null ? bytes.length : 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
        // Not needed for this example
    }
}
