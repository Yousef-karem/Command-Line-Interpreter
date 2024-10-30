package cli.commands;

import java.io.*;

public class ShowFileContentCommand implements command {
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("cat: missing argument");
            return;
        }

        boolean redirectOutput = false;
        String outputFileName = null;

        if (args.length >= 3 && args[args.length - 2].equals(">")) {
            redirectOutput = true;
            outputFileName = args[args.length - 1];
        } else if (args.length >= 3 && args[args.length - 2].equals(">>")) {
            redirectOutput = true;
            outputFileName = args[args.length - 1];
            appendToFile(args);
            return;
        }

        if (redirectOutput) {
            File outputFile = new File(outputFileName);
            if (!outputFile.exists()) {
                createFile(outputFileName);
            } else {
                concatenateFiles(args, args.length - 2, outputFileName);
            }
        } else {
            // Display multiple files
            for (String fileName : args) {
                displayFile(fileName);
            }
        }
    }

    private void displayFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("cat: " + e.getMessage());
            }
        } else {
            System.out.println("cat: no such file: " + fileName);
        }
    }

    public void createFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            System.out.println("Creating new file: " + fileName + ". Type text (type 'Save' to save):");

            while (true) {
                line = reader.readLine();
                if ("Save".equalsIgnoreCase(line)) {
                    break;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("cat: " + e.getMessage());
        }
    }



    private void concatenateFiles(String[] args, int endIndex, String outputFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (int i = 0; i < endIndex; i++) {
                File file = new File(args[i]);
                if (file.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                    } catch (IOException e) {
                        System.out.println("cat: " + e.getMessage());
                    }
                } else {
                    System.out.println("cat: no such file: " + args[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("cat: " + e.getMessage());
        }
    }


    private void appendToFile(String[] args) {
        if (args.length < 3) {
            System.out.println("cat: missing arguments for append");
            return;
        }

        String targetFileName = args[args.length - 1];
        boolean targetFileExists = new File(targetFileName).exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFileName, true))) {

            if (targetFileExists) {
                writer.newLine();
            }

            for (int i = 0; i < args.length - 2; i++) {
                File sourceFile = new File(args[i]);
                if (sourceFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                    } catch (IOException e) {
                        System.out.println("cat: " + e.getMessage());
                    }
                } else {
                    System.out.println("cat: no such file: " + args[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("cat: " + e.getMessage());
        }
    }

}

