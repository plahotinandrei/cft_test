package ru.cft.merge;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Options {
    private String sortMode = "ASC";
    private String dataType;
    private Path outputFile;
    private List<Path> inputFiles = new ArrayList<>();

    public String getSortMode() {
        return sortMode;
    }

    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(Path outputFile) {
        this.outputFile = outputFile;
    }

    public List<Path> getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(List<Path> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public static Options of(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not found all required arguments");
        }
        Options options = new Options();
        options.parse(args);
        return options;
    }

    private void parse(String[] args) {
        Set<String> modes = Arrays.stream(args)
                .filter((s) -> s.startsWith("-")).collect(Collectors.toSet());
        if (modes.contains("-d")) {
            setSortMode("DESC");
        }
        if (modes.contains("-s")) {
            setDataType("String");
        } else if (modes.contains("-i")) {
            setDataType("int");
        } else {
            throw new IllegalArgumentException("Data type argument not found");
        }
        Arrays.stream(args)
                .filter((s) -> Pattern.matches("(.*)\\.(.+)$", s))
                .forEach((s) -> {
                    if (outputFile == null) {
                        setOutputFile(Paths.get(s));
                    } else {
                        Path p = Paths.get(s);
                        if (Files.exists(p)) {
                            inputFiles.add(p);
                        } else {
                            throw new IllegalArgumentException(String.format("File %s does not exist", p));
                        }
                    }
                });
        if (outputFile == null) {
            throw new IllegalArgumentException("Output file argument not found");
        }
        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("Input files argument not found");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Options options = (Options) o;
        return sortMode.equals(options.sortMode)
                && dataType.equals(options.dataType)
                && outputFile.equals(options.outputFile)
                && inputFiles.equals(options.inputFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortMode, dataType, outputFile, inputFiles);
    }
}
