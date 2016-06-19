package com.maplesoft.enok.kaoqing;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton importBtn;
    private JButton outputBtn;

    private ImportExcel importExcel = new ImportExcel();

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        importBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    importExcel.load(chooseFile());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        outputBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    output();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void output() throws IOException, BiffException, WriteException {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(MainWindow.this);
        File file = chooser.getSelectedFile();
        if (file != null) {
            importExcel.output(file);
        }
    }

    private File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                if (f.getName().toLowerCase().endsWith(".xls")) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return ".xls";
            }
        });
        fileChooser.showOpenDialog(MainWindow.this);
        return fileChooser.getSelectedFile();
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
