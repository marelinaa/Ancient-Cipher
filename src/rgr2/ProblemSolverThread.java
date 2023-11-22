package rgr2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProblemSolverThread extends Thread {
    public static MainForm form;
    public ArrayList<File> tests;

    public ProblemSolverThread(MainForm form) {
        synchronized (form) {
            this.form = form;
        }
        synchronized (form.correctTests) {
            this.tests = form.correctTests;
        }
    }

    public static int countDifferentLetters(String str) {
        Set<Character> distinctLetters = new HashSet<>();

        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                distinctLetters.add(Character.toLowerCase(c));
            } else return -1;
        }

        return distinctLetters.size();
    }

    public static void solveTest(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String filePath = file.getAbsolutePath();
            String outputPath = filePath.substring(0, filePath.lastIndexOf('.')) + ".out";
            //form.textArea.append(outputPath +"\n");
            File outputFile = new File(outputPath);
            FileWriter fileWriter = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String firstLine = reader.readLine();
            int k = Integer.parseInt(firstLine);
            for (int i = 0; i < k; i++) {
                String line1 = reader.readLine();
                String line2 = reader.readLine();
                int diff1 = countDifferentLetters(line1);
                int diff2 = countDifferentLetters(line2);
                if (diff1 == diff2) {
                    bufferedWriter.write("YES\n");
                } else {
                    bufferedWriter.write("NO\n");
                }
            }

            bufferedWriter.close();
            //form.textArea.append("Результаты записаны в файлы с расширением .out\n");

        } catch (IOException | NumberFormatException e) {
            form.textArea.append("Ошибка при обработке файла: " + file.getName() + "\n");
        }
    }

    public static void solveAllTests(ArrayList<File> tests) {
        for (int i = 0; i < form.n; ) {
            if (i < tests.size()) {
                solveTest(tests.get(i));
                ++i;
            }
        }
    }

    @Override
    public void run() {
        solveAllTests(this.tests);
    }

}
