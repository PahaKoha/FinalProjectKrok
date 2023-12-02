package pkoh.krokFinalProject.utilitarianClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CSVHandler {
    private CSVHandler() {

    }

    /**
     * Метод разбивает файл со всеми участниками на строки, каждая из которых содержит информацию о кандидате
     * @param path - путь к файлу
     * @return - строки с информацией о кандидатах
     */
    public static Set<String> getCandidatesInfoFromCSV (String path) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(path))) {
            Set<String> listWithLineFromCSVFile = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                listWithLineFromCSVFile.add(line);
            }
            return listWithLineFromCSVFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
