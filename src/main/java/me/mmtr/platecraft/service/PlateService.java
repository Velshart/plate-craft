package me.mmtr.platecraft.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class PlateService {

    private final File polishPlatesFile = new File("polish.txt");
    private final File englishPlatesFile = new File("english.txt");

    private final List<String> polishPlates;
    private final List<String> englishPlates;

    public PlateService() {
        this.polishPlates = new ArrayList<>();
        this.englishPlates = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        loadFromFile(polishPlatesFile, polishPlates);
        loadFromFile(englishPlatesFile, englishPlates);
    }

    public String getPolishPlate(String prefix) {
        Collections.shuffle(polishPlates);
        return polishPlates.stream()
                .filter(element -> element.length() <= 5 || element.startsWith(prefix))
                .findFirst().orElse(null);
    }

    public String getPolishPlate(String prefix, int length) {
        Collections.shuffle(polishPlates);
        return polishPlates.stream().filter(element -> element.length() == length ||
                        (element.length() > length &&
                                element.startsWith(prefix) &&
                                element.contains(" ") &&
                                element.substring(3).length() == length))
                .findFirst().orElse(null);
    }

    public String getEnglishPlate(String prefix) {
        Collections.shuffle(polishPlates);
        return englishPlates.stream()
                .filter(element -> element.length() <= 5 || element.startsWith(prefix))
                .findFirst().orElse(null);
    }

    public String getEnglishPlate(String prefix, int length) {
        Collections.shuffle(polishPlates);
        return englishPlates.stream().filter(element -> element.length() == length ||
                        (element.length() > length &&
                                element.startsWith(prefix) &&
                                element.contains(" ") &&
                                element.substring(3).length() == length))
                .findFirst().orElse(null);
    }

    private void loadFromFile(File file, List<String> list) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
