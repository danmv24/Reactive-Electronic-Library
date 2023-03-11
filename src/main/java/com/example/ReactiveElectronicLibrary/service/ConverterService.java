package com.example.ReactiveElectronicLibrary.service;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;

public interface ConverterService {
    InputStream getInputStreamFromFluxDataBuffer(Flux<DataBuffer> data) throws IOException;
}
