package rgr2;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JButton chooseFile;
    private JTextField directoryTextField;
    private JButton startButton;
    private JButton stopButton;
    public JTextArea textArea;
    private JScrollPane scrollPane;
    public File[] files;
    public ArrayList<File> correctTests;
    public int n;
    public File selectedDirectory;
    JFileChooser fileChooser = new JFileChooser();

    public MainForm(){
        setContentPane(mainPanel);
        setTitle("Ancient Cipher");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(600, 400);
        correctTests = new ArrayList<>();
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setLocationRelativeTo(null);
        setVisible(true);
        directoryTextField.setEditable(false);
        textArea.setEditable(false);
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = fileChooser.showDialog(null, "Choose directory");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    selectedDirectory = fileChooser.getSelectedFile();
                    String selectedDirectory = MainForm.this.selectedDirectory.getAbsolutePath();
                    directoryTextField.setText(selectedDirectory);
                    textArea.append("Chosen directory: " + selectedDirectory.toString() + "\n");
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                correctTests = new ArrayList<>();
                startProcessing();
            }
        });


    }

    public void startProcessing() {
        if (selectedDirectory == null) {
            JOptionPane.showMessageDialog(null, "Directory wasn't chosen.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        files = selectedDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".in"));

        if (files.length == 0 ) {
            JOptionPane.showMessageDialog(null, "There are no .in files in chosen directory.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        n = files.length;


        TestCheckerThread testCheckerThread = new TestCheckerThread(this);
        ProblemSolverThread problemSolverThread = new ProblemSolverThread(this);
        testCheckerThread.start();
        problemSolverThread.start();
        try {
            problemSolverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            testCheckerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        textArea.append(testCheckerThread.getName() + " ---> " + testCheckerThread.getState().toString()+ "\n\n");

//
//        // Ожидание завершения работы треда-чекера
//        try {
//            testCheckerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Запуск треда, решающего задачу, если проверка теста прошла успешно
//        if (testCheckerThread.isTestValid()) {
//            problemSolverThread.start();
//        } else {
//            System.out.println("Ошибка: неправильный тест");
//            return;
//        }
//
//        // Ожидание завершения работы треда, решающего задачу
//        try {
//            problemSolverThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Проверка наличия выходного файла, созданного тредом, решающим задачу
//        if (!problemSolverThread.isOutputFileCreated()) {
//            System.out.println("Ошибка: выходной файл не создан");
//            return;
//        }
//
//        // Запуск треда-чекера результатов
//        resultCheckerThread.start();
//
//        // Ожидание завершения работы треда-чекера результатов
//        try {
//            resultCheckerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Вывод результатов тестирования
//        printTestResults();
    }

}
