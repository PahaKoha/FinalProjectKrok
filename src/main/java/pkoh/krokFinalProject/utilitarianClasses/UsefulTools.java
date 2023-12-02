package pkoh.krokFinalProject.utilitarianClasses;

import java.util.Arrays;
import java.util.List;

public class UsefulTools {
    private UsefulTools() {

    }
    /**
     * Метод разбивает строку по различным знакам препинания
     * @param str - строка, которую хотим разбить
     * @return - список из элементов строки
     */
    public static List<String> getElementsFromString(String str) {
        String[] words = str.split("(?=[\\p{P}\\s])|(?<=[\\p{P}\\s])");
        return Arrays.asList(words);
    }

    /**
     * Метод, который разбивает строку по запятым (для CSV-файлов)
     * @param line - строка, которую хотим разбить
     * @return - список из элементов строки
     */
    public static List<String> getElementsFromCSVLine(String line) {
        return Arrays.stream(line.split(",", 5)).toList();
    }
}
