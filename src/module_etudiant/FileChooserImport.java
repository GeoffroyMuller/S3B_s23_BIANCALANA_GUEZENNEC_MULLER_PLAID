package module_etudiant;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooserImport extends JButton implements ActionListener {


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String filePath;

    public FileChooserImport(){
        this.setText("Sélection du fichier");
        this.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setDialogTitle("Choisissez un fichier Excel aux normes : ");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Documents Excel", "xlsx");
            fileChooser.addChoosableFileFilter(filter);
            int i=fileChooser.showOpenDialog(this);
            if(i==JFileChooser.APPROVE_OPTION){
                File f=fileChooser.getSelectedFile();
                 this.filePath=f.getPath();

            }
        }
    }

