package ru.cft.merge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OptionsTest {
    String outputFilePath;
    String inputFilePath1;
    String inputFilePath2;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void initData() throws IOException {
        outputFilePath = folder.newFile("out.txt").getPath();
        inputFilePath1 = folder.newFile("in1.txt").getPath();
        inputFilePath2 = folder.newFile("-in2.txt").getPath();
    }

    @Test
    public void whenAllArguments() {
        String[] args = new String[] {"-i", "-a", outputFilePath, inputFilePath1};
        Options rsl = Options.of(args);
        Options expected = new Options();
        expected.setSortMode("ASC");
        expected.setDataType("int");
        expected.setOutputFile(Paths.get(outputFilePath));
        List<Path> inputFiles = List.of(Paths.get(inputFilePath1));
        expected.setInputFiles(inputFiles);
        Assert.assertEquals(expected, rsl);
    }

    @Test
    public void whenSortModeDescDataTypeStringAndAllArguments() {
        String[] args = new String[] {"-s", "-d", outputFilePath, inputFilePath1, inputFilePath2};
        Options rsl = Options.of(args);
        Options expected = new Options();
        expected.setSortMode("DESC");
        expected.setDataType("String");
        expected.setOutputFile(Paths.get(outputFilePath));
        List<Path> inputFiles = List.of(Paths.get(inputFilePath1), Paths.get(inputFilePath2));
        expected.setInputFiles(inputFiles);
        Assert.assertEquals(expected, rsl);
    }

    @Test
    public void whenNotSortModeDataTypeIntAndAllArguments() {
        String[] args = new String[] {"-i", outputFilePath, inputFilePath1};
        Options rsl = Options.of(args);
        Options expected = new Options();
        expected.setSortMode("ASC");
        expected.setDataType("int");
        expected.setOutputFile(Paths.get(outputFilePath));
        List<Path> inputFiles = List.of(Paths.get(inputFilePath1));
        expected.setInputFiles(inputFiles);
        Assert.assertEquals(expected, rsl);
    }

    @Test
    public void whenNotSortModeDataTypeStringAndAllArguments() {
        String[] args = new String[] {"-s", outputFilePath, inputFilePath1};
        Options rsl = Options.of(args);
        Options expected = new Options();
        expected.setSortMode("ASC");
        expected.setDataType("String");
        expected.setOutputFile(Paths.get(outputFilePath));
        List<Path> inputFiles = List.of(Paths.get(inputFilePath1));
        expected.setInputFiles(inputFiles);
        Assert.assertEquals(expected, rsl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInputFileNotExist() {
        String[] args = new String[] {"-s", outputFilePath, "in.txt"};
        Options.of(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotArgument() {
        String[] args = new String[] {};
        Options.of(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotDataType() {
        String[] args = new String[] {"-a", outputFilePath, inputFilePath1, inputFilePath2};
        Options.of(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotInputFiles() {
        String[] args = new String[] {"-s", outputFilePath};
        Options.of(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotOutputFileAndInputFiles() {
        String[] args = new String[] {"-s", "-d"};
        Options.of(args);
    }
}