package com.golden.raspberry.awards.useCases.fileManager;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvReader<T> implements FileReader<T> {

    private static final Logger LOG = LoggerFactory.getLogger(CsvReader.class);

    final Class<T> typeParameterClass;

    public CsvReader(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public List<T> read(String path) {
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            return read(reader);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<T> read(Reader reader) {
        try {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(typeParameterClass)
                    .withSeparator(';')
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }
}
