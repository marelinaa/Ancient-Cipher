package rgr2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestCheckerThread extends Thread {
    public File[] files;
    public static MainForm form;

    public TestCheckerThread(MainForm form) {
        this.form = form;
        this.files = form.files;
    }

    public static boolean isFileCorrect(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (file.length() == 0) {
                return false;
            }

            String firstLine = reader.readLine();
            if ((firstLine == null) || !firstLine.matches("[1-5]")) {
                return false;
            }

            int k = Integer.parseInt(firstLine);
            if (k < 1 || k > 5) {
                return false;
            }

            for (int i = 0; i < k; i++) {
                String line1 = reader.readLine();
                String line2 = reader.readLine();

                if (line1 == null || line2 == null || line1.length() > 100 || line2.length() > 100) {
                    return false;
                }
                if (!line1.matches("[A-Z]+") || !line2.matches("[A-Z]+")) {
                    return false;
                }

            }

            String extraLine = reader.readLine();
            if (extraLine != null && !extraLine.isEmpty()) {
                return false;
            }
            return true;

        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }

    public static void onlyCorrectTests(File[] files) {
        form.textArea.append("------Checking for the correctness of the tests:\n");
        for (File file : files) {
            if (isFileCorrect(file)) {
                synchronized (form.correctTests) {
                    form.correctTests.add(file);
                }
                form.textArea.append(file.getName() + " is correct\n");
            } else {
                form.textArea.append(file.getName() + " is incorrect\n");
                form.n--;
            }
        }
    }

    @Override
    public void run() {
        onlyCorrectTests(this.files);
        System.out.println("dklfjsdkfjkwjf");
    }
}
