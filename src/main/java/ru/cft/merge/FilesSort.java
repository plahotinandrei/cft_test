package ru.cft.merge;

import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.*;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import static ru.cft.merge.Utils.*;

public class FilesSort {
    private final Options options;

    public FilesSort(Options options) {
        this.options = options;
    }

    public void sort() throws IOException {
        Path rsl = null;
        Queue<Path> queue = new LinkedList<>(options.getInputFiles());
        while (!queue.isEmpty()) {
            if (queue.size() == 1) {
                rsl = queue.poll();
                break;
            }
            Path p = merge(queue.poll(), queue.poll());
            queue.offer(p);
        }
        if (SortMode.ASC.equals(options.getSortMode())) {
            writeAsc(rsl, options.getOutputFile());
        } else {
            writeDesc(rsl, options.getOutputFile());
        }
    }

    private Path merge(Path src1, Path src2) throws IOException {
        File tmp = File.createTempFile(src1 + "_" + src2, ".txt");
        tmp.deleteOnExit();
        try (
                BufferedReader reader1 = new BufferedReader(new FileReader(src1.toFile()));
                BufferedReader reader2 = new BufferedReader(new FileReader(src2.toFile()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tmp))
        ) {
            String minValue = SortType.STRING.equals(options.getDataType()) ? "" : String.valueOf(Integer.MIN_VALUE);
            Comparator<String> comparator = SortType.STRING.equals(options.getDataType())
                    ? String::compareTo : Comparator.comparing(Integer::valueOf);
            String current1 = getNextValidValue(reader1, minValue);
            String current2 = getNextValidValue(reader2, minValue);
            while (current1 != null || current2 != null) {
                if (current1 != null && current2 != null) {
                    if (comparator.compare(current1, current2) < 0) {
                        writer.write(current1 + System.lineSeparator());
                        current1 = getNextValidValue(reader1, current1);
                    } else {
                        writer.write(current2 + System.lineSeparator());
                        current2 = getNextValidValue(reader2, current2);
                    }
                } else {
                    writer.write((current1 == null ? current2 : current1) + System.lineSeparator());
                    current1 = getNextValidValue(reader1, current1);
                    current2 = getNextValidValue(reader2, current2);
                }
            }
        }
        return tmp.toPath();
    }

    private String getNextValidValue(BufferedReader reader, String prevValue) throws IOException {
        Predicate<String> isIncorrect = SortType.STRING.equals(options.getDataType())
                ? (s) -> s.compareTo(prevValue) < 0 || !noSpace(s)
                : (s) -> isInteger(s) && Integer.parseInt(prevValue) > Integer.parseInt(s) || !isInteger(s);
        String line = reader.readLine();
        while (line != null && isIncorrect.test(line)) {
            line = reader.readLine();
        }
        return line;
    }

    private void writeAsc(Path src, Path dest) throws IOException {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(src.toFile()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(dest.toFile()))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }
        }
    }

    private void writeDesc(Path src, Path dest) throws IOException {
        try (
                ReversedLinesFileReader reader = new ReversedLinesFileReader(src.toFile());
                BufferedWriter writer = new BufferedWriter(new FileWriter(dest.toFile()))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Options opt = Options.of(args);
            new FilesSort(opt).sort();
            System.out.println("Sorting completed successfully!");
        } catch (IOException e) {
            System.out.println("Resource error, try restarting!");
        }
    }
}
