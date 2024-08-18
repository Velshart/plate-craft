package me.mmtr.platecraft.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Service
public class PlateService {

    private final File polishPlatesFile = new File("polish.txt");
    private final File englishPlatesFile = new File("english.txt");

    private final Set<String> polishPlates;
    private final Set<String> englishPlates;

    public PlateService() {
        this.polishPlates = new HashSet<>();
        this.englishPlates = new HashSet<>();
    }

    @PostConstruct
    public void init() {
        loadFromFile(polishPlatesFile, polishPlates);
        loadFromFile(englishPlatesFile, englishPlates);
    }

    public String getPolishPlate(String prefix) {
        return polishPlates.stream()
                .filter(element -> element.length() <= 5 || element.startsWith(prefix))
                .findFirst().orElse(null);
    }

    public String getPolishPlate(String prefix, int length) {
        return polishPlates.stream().filter(element -> element.length() == length ||
                        (element.length() > length &&
                                element.startsWith(prefix) &&
                                element.substring(2).length() == length))
                .findFirst().orElse(null);
    }

    public String getEnglishPlate(String prefix) {
        return englishPlates.stream()
                .filter(element -> element.length() <= 5 || element.startsWith(prefix))
                .findFirst().orElse(null);
    }

    public String getEnglishPlate(String prefix, int length) {
        return englishPlates.stream().filter(element -> element.length() == length ||
                        (element.length() > length &&
                                element.startsWith(prefix) &&
                                element.substring(2).length() == length))
                .findFirst().orElse(null);
    }

    private void loadFromFile(File file, Set<String> set) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                set.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
