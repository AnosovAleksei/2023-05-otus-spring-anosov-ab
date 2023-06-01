package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@AllArgsConstructor
public class LoadDataImpl implements LoadDataDao {
    private String fileName;

    @Override
    public List<String[]> getData(){
        ClassLoader classLoader = LoadDataImpl.class.getClassLoader();
        InputStream fis = classLoader.getResourceAsStream(fileName);

        CSVReader csvReader = new CSVReader(new InputStreamReader(fis));
        try {
            return csvReader.readAll();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

}
