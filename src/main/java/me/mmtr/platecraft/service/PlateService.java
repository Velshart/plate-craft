package me.mmtr.platecraft.service;

import jakarta.annotation.PostConstruct;
import me.mmtr.platecraft.record.Plate;
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

    private final int AMOUNT_OF_PLATES_TO_DRAW = 10;

    public PlateService() {
        this.polishPlates = new ArrayList<>();
        this.englishPlates = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        loadPlatesFromFile(polishPlatesFile, polishPlates);
        loadPlatesFromFile(englishPlatesFile, englishPlates);
    }

    public List<Plate> getPolishPlates(String prefix) {
        return getPlates(prefix, polishPlates);
    }

    public List<Plate> getPolishPlates(String prefix, int length) {
        return getPlates(prefix, polishPlates, length);
    }

    public List<Plate> getEnglishPlates(String prefix) {
        return getPlates(prefix, englishPlates);
    }

    public List<Plate> getEnglishPlates(String prefix, int length) {
        return getPlates(prefix, englishPlates, length);
    }

    private List<Plate> getPlates(String prefix, List<String> platesCollection) {
        Collections.shuffle(platesCollection);

        return platesCollection.stream()
                .filter(element -> element.length() <= 5 || element.startsWith(prefix))
                .map(Plate::new)
                .limit(AMOUNT_OF_PLATES_TO_DRAW)
                .toList();
    }

    private List<Plate> getPlates(String prefix, List<String> platesCollection, int length) {
        Collections.shuffle(platesCollection);

        return platesCollection.stream().filter(element -> element.length() == length ||
                        (element.length() > length &&
                                element.startsWith(prefix) &&
                                element.contains(" ") &&
                                element.substring(3).length() == length))
                .map(Plate::new)
                .limit(AMOUNT_OF_PLATES_TO_DRAW)
                .toList();
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
