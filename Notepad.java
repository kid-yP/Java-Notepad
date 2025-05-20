import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class NotepadApp extends JFrame implements ActionListener {

    JTextArea textArea;
    JFileChooser fileChooser;

    public NotepadApp() {
        // Window settings
        setTitle("Java Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Text area with scroll
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // File chooser
        fileChooser = new JFileChooser();

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, "New");
        addMenuItem(fileMenu, "Open");
        addMenuItem(fileMenu, "Save");
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit");

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        addMenuItem(editMenu, "Cut");
        addMenuItem(editMenu, "Copy");
        addMenuItem(editMenu, "Paste");

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        addMenuItem(helpMenu, "About");

        // Add menus to bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void addMenuItem(JMenu menu, String title) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(this);
        menu.add(item);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                textArea.setText("");
                break;

            case "Open":
                int openOption = fileChooser.showOpenDialog(this);
                if (openOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        textArea.setText("");
                        String line;
                        while ((line = reader.readLine()) != null) {
                            textArea.append(line + "\n");
                        }
                    } catch (IOException ex) {
                        showError("Error reading file");
                    }
                }
                break;

            case "Save":
                int saveOption = fileChooser.showSaveDialog(this);
                if (saveOption == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(textArea.getText());
                    } catch (IOException ex) {
                        showError("Error saving file");
                    }
                }
                break;

            case "Exit":
                System.exit(0);
                break;

            case "Cut":
                textArea.cut();
                break;

            case "Copy":
                textArea.copy();
                break;

            case "Paste":
                textArea.paste();
                break;

            case "About":
                JOptionPane.showMessageDialog(this, "Notepad App\nBuilt with Java Swing\nBy Code GPT", "About", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotepadApp::new);
    }
}
