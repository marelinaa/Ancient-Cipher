import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileValidator {
    public static boolean isFileCorrect(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (file.length() == 0)
                return false;

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
                if (!line1.matches("[a-z]+") || !line2.matches("[a-z]+")){
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
}