package ru.cft.merge;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static ru.cft.merge.Utils.*;

public class FilesSort {
    public static void main(String[] args) throws IOException {
        Path p1 = Paths.get("in1.txt");
        Path p2 = Paths.get("in2.txt");
        Path p3 = Paths.get("in3.txt");
        Path p4 = Paths.get("in4.txt");
        Path p5 = Paths.get("in5.txt");
        Path out = merge(List.of(p1, p2, p3, p4, p5), "int");
        try (BufferedReader read = new BufferedReader(new FileReader(out.toFile()))) {
            String line;
            while ((line = read.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static Path merge(List<Path> inputFiles, String dataType) {
        Path rsl = null;
        Queue<Path> queue = new LinkedList<>(inputFiles);
        try {
            while (!queue.isEmpty()) {
                Path p = "int".equals(dataType)
                        ? mergeInt(queue.poll(), queue.poll())
                        : mergeStr(queue.poll(), queue.poll());
                queue.offer(p);
                if (queue.size() == 1) {
                    rsl = queue.poll();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Resource error", e);
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
            String current1 = read1.readLine();
            String current2 = read2.readLine();
            int prev1 = Integer.MIN_VALUE;
            int prev2 = Integer.MIN_VALUE;
            while (current1 != null || current2 != null) {
                while (isInteger(current1) && prev1 > Integer.parseInt(current1)) {
                    current1 = read1.readLine();
                }
                while (isInteger(current2) && prev2 > Integer.parseInt(current2)) {
                    current2 = read2.readLine();
                }
                if (isInteger(current1) && isInteger(current2)) {
                    if (Integer.parseInt(current1) < Integer.parseInt(current2)) {
                        write.write(current1 + "\n");
                        prev1 = Integer.parseInt(current1);
                        current1 = read1.readLine();
                    } else {
                        write.write(current2 + "\n");
                        prev2 = Integer.parseInt(current2);
                        current2 = read2.readLine();
                    }
                } else if (isInteger(current1)) {
                    write.write(current1 + "\n");
                    prev1 = Integer.parseInt(current1);
                    current1 = read1.readLine();
                } else if (isInteger(current2)) {
                    write.write(current2 + "\n");
                    prev2 = Integer.parseInt(current2);
                    current2 = read2.readLine();
                } else {
                    current1 = read1.readLine();
                    current2 = read2.readLine();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Resource error", e);
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
            String current1 = read1.readLine();
            String current2 = read2.readLine();
            String prev1 = "";
            String prev2 = "";
            while (current1 != null || current2 != null) {
                while (current1 != null && current1.compareTo(prev1) < 0) {
                    current1 = read1.readLine();
                }
                while (current2 != null && current2.compareTo(prev2) < 0) {
                    current2 = read2.readLine();
                }
                if (current1 != null && current2 != null) {
                    if (current1.compareTo(current2) < 0) {
                        write.write(current1 + "\n");
                        prev1 = current1;
                        current1 = read1.readLine();
                    } else {
                        write.write(current2 + "\n");
                        prev2 = current2;
                        current2 = read2.readLine();
                    }
                } else if (current1 != null) {
                    write.write(current1 + "\n");
                    prev1 = current1;
                    current1 = read1.readLine();
                } else if (current2 != null) {
                    write.write(current2 + "\n");
                    prev2 = current2;
                    current2 = read2.readLine();
                } else {
                    current1 = read1.readLine();
                    current2 = read2.readLine();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Resource error", e);
        }
        return tmp.toPath();
    }
}
