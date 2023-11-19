import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.File;
import java.io.FilenameFilter;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JButton chooseFile;
    private JTextField directoryTextField;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    JFileChooser fileChooser = new JFileChooser();
    File selectedDirectory;

    public MainForm(){
        setContentPane(mainPanel);
        setTitle("Ancient Cipher");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(600, 400);
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
                if (selectedDirectory == null) {
                    JOptionPane.showMessageDialog(null, "Directory wasn't chosen.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                File[] files = selectedDirectory.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".in");
                    }
                });

                if (files.length == 0 || files == null) {
                    JOptionPane.showMessageDialog(null, "There are no .in files in chosen directory.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (files != null) {
                    for (File file : files) {
                        boolean isFileCorrect = FileValidator.isFileCorrect(file);
                        if (isFileCorrect) {
                            textArea.append("File " + file.getName() + " is correct\n");
                        } else {
                            textArea.append("File " + file.getName() + " is incorrect\n");
                        }
                    }
                }
            }
        });


    }

    public static void main(String[] args) {
        new MainForm();
    }

}
