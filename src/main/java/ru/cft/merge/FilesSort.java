package ru.cft.merge;

import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.*;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static ru.cft.merge.Utils.*;

public class FilesSort {

    public static void sort(Options opt) throws IOException {
        Path srcPath = merge(opt.getInputFiles(), opt.getDataType());
        Path destPath = opt.getOutputFile();
        if (SortMode.ASC.equals(opt.getSortMode())) {
            writeAsc(srcPath, destPath);
        } else {
            writeDesc(srcPath, destPath);
        }
    }

    private static Path merge(List<Path> inputFiles, SortType dataType) throws IOException {
        Path rsl = null;
        Queue<Path> queue = new LinkedList<>(inputFiles);
        while (!queue.isEmpty()) {
            if (queue.size() == 1) {
                rsl = queue.poll();
                break;
            }
            Path p = SortType.INT.equals(dataType)
                    ? mergeInt(queue.poll(), queue.poll())
                    : mergeStr(queue.poll(), queue.poll());
            queue.offer(p);
        }
        return rsl;
    }

    private static Path mergeInt(Path src1, Path src2) throws IOException {
        File tmp = File.createTempFile(src1 + "_" + src2, ".txt");
        tmp.deleteOnExit();
        try (
                BufferedReader read1 = new BufferedReader(new FileReader(src1.toFile()));
                BufferedReader read2 = new BufferedReader(new FileReader(src2.toFile()));
                BufferedWriter write = new BufferedWriter(new FileWriter(tmp))
        ) {
            Integer current1 = getNextValidNumber(read1, Integer.MIN_VALUE);
            Integer current2 = getNextValidNumber(read2, Integer.MIN_VALUE);
            while (current1 != null || current2 != null) {
                if (current1 != null && current2 != null) {
                    if (current1 < current2) {
                        write.write(current1 + System.lineSeparator());
                        current1 = getNextValidNumber(read1, current1);
                    } else {
                        write.write(current2 + System.lineSeparator());
                        current2 = getNextValidNumber(read2, current2);
                    }
                } else {
                    write.write((current1 == null ? current2 : current1) + System.lineSeparator());
                    current1 = getNextValidNumber(read1, current1);
                    current2 = getNextValidNumber(read2, current2);
                }
            }
        }
        return tmp.toPath();
    }

    private static Path mergeStr(Path src1, Path src2) throws IOException {
        File tmp = File.createTempFile(src1 + "_" + src2, ".txt");
        tmp.deleteOnExit();
        try (
                BufferedReader read1 = new BufferedReader(new FileReader(src1.toFile()));
                BufferedReader read2 = new BufferedReader(new FileReader(src2.toFile()));
                BufferedWriter write = new BufferedWriter(new FileWriter(tmp))
        ) {
            String current1 = getNextValidString(read1, "");
            String current2 = getNextValidString(read2, "");
            while (current1 != null || current2 != null) {
                if (current1 != null && current2 != null) {
                    if (current1.compareTo(current2) < 0) {
                        write.write(current1 + System.lineSeparator());
                        current1 = getNextValidString(read1, current1);
                    } else {
                        write.write(current2 + System.lineSeparator());
                        current2 = getNextValidString(read2, current2);
                    }
                } else {
                    write.write((current1 == null ? current2 : current1) + System.lineSeparator());
                    current1 = getNextValidString(read1, current1);
                    current2 = getNextValidString(read2, current2);
                }
            }
        }
        return tmp.toPath();
    }

    private static Integer getNextValidNumber(BufferedReader reader, Integer prevValue) throws IOException {
        String line = reader.readLine();
        while (isInteger(line) && prevValue > Integer.parseInt(line) || line != null && !isInteger(line)) {
            line = reader.readLine();
        }
        return line == null ? null : Integer.parseInt(line);
    }

    private static String getNextValidString(BufferedReader reader, String prevValue) throws IOException {
        String line = reader.readLine();
        while (line != null && (line.compareTo(prevValue) < 0 || !noSpace(line))) {
            line = reader.readLine();
        }
        return line;
    }

    private static void writeAsc(Path src, Path dest) throws IOException {
        try (
            BufferedReader read = new BufferedReader(new FileReader(src.toFile()));
            BufferedWriter write = new BufferedWriter(new FileWriter(dest.toFile()))
        ) {
            String line;
            while ((line = read.readLine()) != null) {
                write.write(line + System.lineSeparator());
            }
        }
    }

    private static void writeDesc(Path src, Path dest) throws IOException {
        try (
                ReversedLinesFileReader read = new ReversedLinesFileReader(src.toFile());
                BufferedWriter write = new BufferedWriter(new FileWriter(dest.toFile()))
        ) {
            String line;
            while ((line = read.readLine()) != null) {
                write.write(line + System.lineSeparator());
            }
        }
    }
}
