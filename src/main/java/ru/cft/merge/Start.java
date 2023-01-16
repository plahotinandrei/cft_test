package ru.cft.merge;

import java.io.IOException;

public class Start {
    private final Options opt;

    public Start(Options opt) {
        this.opt = opt;
    }

    public void init() throws IOException {
        FilesSort.sort(opt);
    }

    public static void main(String[] args) {
        try {
            Options opt = Options.of(args);
            new Start(opt).init();
            System.out.println("Sorting completed successfully!");
        } catch (IOException e) {
            System.out.println("Resource error");
        }
    }
}
