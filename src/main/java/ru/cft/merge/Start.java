package ru.cft.merge;

public class Start {
    private final Options opt;

    public Start(Options opt) {
        this.opt = opt;
    }

    public void init() {
        FilesSort.sort(opt);
    }

    public static void main(String[] args) {
        Options opt = Options.of(args);
        new Start(opt).init();
        System.out.println("Sorting completed successfully!");
    }
}
