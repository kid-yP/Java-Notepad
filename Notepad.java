import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class Notepad {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}

class App {
    private JFrame frame;
    private JTextArea textArea;
    private File currentFile = null;

    public App() {
        frame = new JFrame("SwingPad");
        textArea = new JTextArea(25, 50);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu infoMenu = new JMenu("Info");

        JMenuItem newMenuItem = new JMenuItem("New File");
        JMenuItem openMenuItem = new JMenuItem("Open File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
        JMenuItem infoItem = new JMenuItem("About");

        // Add items to menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        infoMenu.add(infoItem);
        menuBar.add(fileMenu);
        menuBar.add(infoMenu);
        frame.setJMenuBar(menuBar);

        // Menu actions
        newMenuItem.addActionListener(e -> {
            textArea.setText("");
            currentFile = null;
            frame.setTitle("SwingPad - New File");
        });

        openMenuItem.addActionListener(e -> openFile());

        saveMenuItem.addActionListener(e -> saveFile());

        saveAsMenuItem.addActionListener(e -> saveAsFile());

        infoItem.addActionListener(e -> {
            JFrame infoFrame = new JFrame("Info");
            infoFrame.setLayout(new FlowLayout());
            infoFrame.add(new JLabel("SwingPad v1.4"));
            infoFrame.add(new JLabel("Created by SpaciousCoder78 @ GitHub"));
            infoFrame.setSize(300, 150);
            infoFrame.setLocationRelativeTo(frame);
            infoFrame.setVisible(true);
        });

        // Finalize window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Text File");
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
                currentFile = fileChooser.getSelectedFile();
                frame.setTitle("SwingPad - " + currentFile.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error opening file.");
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            writeToFile(currentFile);
        } else {
            saveAsFile(); // fallback to Save As if no current file
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            writeToFile(currentFile);
            frame.setTitle("SwingPad - " + currentFile.getName());
        }
    }

    private void writeToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(textArea.getText());
            JOptionPane.showMessageDialog(frame, "File saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving file.");
        }
    }
}
