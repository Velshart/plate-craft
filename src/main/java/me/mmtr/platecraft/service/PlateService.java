package me.mmtr.platecraft.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        loadPlatesFromFile(polishPlatesFile, polishPlates);
        loadPlatesFromFile(englishPlatesFile, englishPlates);
    }

    public String getPolishPlate(String prefix) {
        return getPlates(prefix, polishPlates);
    }

    public String getPolishPlate(String prefix, int length) {
        return getPlates(prefix, polishPlates, length);
    }

    public String getEnglishPlate(String prefix) {
        return getPlates(prefix, englishPlates);
    }

    public String getEnglishPlate(String prefix, int length) {
        return getPlates(prefix, englishPlates, length);
    }

    private String getPlates(String prefix, List<String> platesCollection) {
        Collections.shuffle(platesCollection);

        return platesCollection.stream()
                .filter(element -> element.length() <= 5 || element.startsWith(prefix))
                .findFirst().orElse(null);
    }

    private String getPlates(String prefix, List<String> platesCollection, int length) {
        Collections.shuffle(platesCollection);

        return platesCollection.stream().filter(element -> element.length() == length ||
                        (element.length() > length &&
                                element.startsWith(prefix) &&
                                element.contains(" ") &&
                                element.substring(3).length() == length))
                .findFirst().orElse(null);
    }

    private void loadPlatesFromFile(File file, List<String> list) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
