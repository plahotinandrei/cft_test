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
    String inputFilePath6;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void initData() throws IOException {
        inputFilePath1 = folder.newFile("in1.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath1))) {
            write.write("1" + System.lineSeparator());
            write.write("4" + System.lineSeparator());
            write.write("9" + System.lineSeparator());
        }
        inputFilePath2 = folder.newFile("in2.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath2))) {
            write.write("1" + System.lineSeparator());
            write.write("8" + System.lineSeparator());
            write.write("27" + System.lineSeparator());
        }
        inputFilePath3 = folder.newFile("in3.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath3))) {
            write.write("1" + System.lineSeparator());
            write.write("2" + System.lineSeparator());
            write.write("3" + System.lineSeparator());
        }
        inputFilePath4 = folder.newFile("in4.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath4))) {
            write.write("1" + System.lineSeparator());
            write.write("15" + System.lineSeparator());
            write.write("3" + System.lineSeparator());
            write.write("4" + System.lineSeparator());
            write.write("16" + System.lineSeparator());
            write.write("18q" + System.lineSeparator());
        }
        inputFilePath5 = folder.newFile("in5.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath5))) {
            write.write("6" + System.lineSeparator());
            write.write("3" + System.lineSeparator());
            write.write("11" + System.lineSeparator());
            write.write("23" + System.lineSeparator());
            write.write(" 25" + System.lineSeparator());
            write.write("3 2" + System.lineSeparator());
        }
        inputFilePath6 = folder.newFile("in6.txt").getPath();
        try (BufferedWriter write = new BufferedWriter(new FileWriter(inputFilePath6))) {
            write.write(Integer.MIN_VALUE + System.lineSeparator());
            write.write("3" + System.lineSeparator());
            write.write("11" + System.lineSeparator());
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

    @Test
    public void whenSortIntAscNoIncorrectDataAndOneInputFile() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-i", "-a", outputFilePath, inputFilePath1};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = "149";
        Assert.assertEquals(expected, rsl.toString());
    }

    @Test
    public void whenSortIntAscNoIncorrectDataAndInputFileContainsMinValue() throws IOException {
        String outputFilePath = folder.newFile("out.txt").getPath();
        String[] args = new String[] {"-i", "-a", outputFilePath, inputFilePath1, inputFilePath6};
        Options opt = Options.of(args);
        FilesSort.sort(opt);
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(outputFilePath))) {
            in.lines().forEach(rsl::append);
        }
        String expected = Integer.MIN_VALUE + "134911";
        Assert.assertEquals(expected, rsl.toString());
    }
}