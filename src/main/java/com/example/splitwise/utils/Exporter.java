package com.example.splitwise.utils;

import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.util.List;

public interface Exporter<T> {
    ByteArrayResource export(List<T> data) throws IOException;

    String getExportedPath();
}
