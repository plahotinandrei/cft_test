package ru.cft.merge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.*;

public class FilesSortTest {
    String inputFilePath1;
    String inputFilePath2;
    String inputFilePath3;
    String inputFilePath4;
    String inputFilePath5;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void initData() throws IOException {
        inputFilePath1 = folder.newFile("in1.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath1))) {
            write.write("1\n");
            write.write("4\n");
            write.write("9\n");
        }
        inputFilePath2 = folder.newFile("in2.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath2))) {
            write.write("1\n");
            write.write("8\n");
            write.write("27\n");
        }
        inputFilePath3 = folder.newFile("in3.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath3))) {
            write.write("1\n");
            write.write("2\n");
            write.write("3\n");
        }
        inputFilePath4 = folder.newFile("in4.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath4))) {
            write.write("1\n");
            write.write("15\n");
            write.write("3\n");
            write.write("4\n");
            write.write("16\n");
            write.write("18q\n");
        }
        inputFilePath5 = folder.newFile("in5.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath5))) {
            write.write("6\n");
            write.write("3\n");
            write.write("11\n");
            write.write("23\n");
            write.write(" 25\n");
            write.write("3 2\n");
        }
    }

    @Test
    public void whenSortIntAscNoIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-i", "-a", outputFilePath, inputFilePath1, inputFilePath2, inputFilePath3};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "1112348927";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortIntDescNoIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-i", "-d", outputFilePath, inputFilePath1, inputFilePath2, inputFilePath3};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "2798432111";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortStrAscNoIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-s", outputFilePath, inputFilePath1, inputFilePath3};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "112349";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortStrDescNoIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-s", "-d", outputFilePath, inputFilePath1, inputFilePath3};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "943211";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortIntAscWithIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-i", "-a", outputFilePath, inputFilePath3, inputFilePath4, inputFilePath5};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "1123611151623";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortIntDescWithIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-i", "-d", outputFilePath, inputFilePath3, inputFilePath4, inputFilePath5};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "2316151163211";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortStrAscWithIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-s", "-a", outputFilePath, inputFilePath3, inputFilePath5};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "1236";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortStrDescWithIncorrectData() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-s", "-d", outputFilePath, inputFilePath4, inputFilePath5};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "643151";
        Assert.assertEquals(expected, rsl.toString());
    }
}