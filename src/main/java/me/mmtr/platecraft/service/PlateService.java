package me.mmtr.platecraft.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Service
public class PlateService {

    private final File polishPlatesFile = new File("polish.txt");
    private final File englishPlatesFile = new File("english.txt");

    private Set<String> polishPlates;
    private Set<String> englishPlates;

    public PlateService() {
        this.polishPlates = new HashSet<>();
        this.englishPlates = new HashSet<>();
    }

    @PostConstruct
    public void init() {
    }
}
