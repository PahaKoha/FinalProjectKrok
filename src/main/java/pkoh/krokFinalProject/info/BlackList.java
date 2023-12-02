package pkoh.krokFinalProject.info;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, который хранит set с запрещенными словами
 */
public class BlackList {
    public static Set<String> blackWordsList = new HashSet<>();

    private BlackList() {

    }

    static {
        blackWordsList.add("плохое правление");
        blackWordsList.add("взятки");
        blackWordsList.add("неуважение к закону");
        blackWordsList.add("война");
        blackWordsList.add("угнетение");
    }
}
