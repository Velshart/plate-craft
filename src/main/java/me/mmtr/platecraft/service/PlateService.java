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

    private final File POLISH_PLATES_FILE = new File("polish.txt");
    private final File ENGLISH_PLATES_FILE = new File("english.txt");

    private final List<String> POLISH_PLATES;
    private final List<String> ENGLISH_PLATES;

    private final int AMOUNT_OF_PLATES_TO_DRAW = 10;

    public PlateService() {
        this.POLISH_PLATES = new ArrayList<>();
        this.ENGLISH_PLATES = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        loadPlatesFromFile(POLISH_PLATES_FILE, POLISH_PLATES);
        loadPlatesFromFile(ENGLISH_PLATES_FILE, ENGLISH_PLATES);
    }

    public List<Plate> getPolishPlates(String prefix) {
        return getPlates(prefix, POLISH_PLATES);
    }

    public List<Plate> getPolishPlates(String prefix, int length) {
        return getPlates(prefix, POLISH_PLATES, length);
    }

    public List<Plate> getEnglishPlates(String prefix) {
        return getPlates(prefix, ENGLISH_PLATES);
    }

    public List<Plate> getEnglishPlates(String prefix, int length) {
        return getPlates(prefix, ENGLISH_PLATES, length);
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
