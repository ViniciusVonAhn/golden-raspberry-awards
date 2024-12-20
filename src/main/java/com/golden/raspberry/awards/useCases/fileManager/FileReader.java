package com.golden.raspberry.awards.useCases.fileManager;

import java.io.Reader;
import java.util.List;

public interface FileReader<T> {
    List<T> read(String path);
    List<T> read(Reader reader);
}