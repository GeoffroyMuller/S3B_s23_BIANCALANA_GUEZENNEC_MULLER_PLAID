package module_etudiant;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooserImport extends JButton implements ActionListener {

    /**
     * Chemin vers le fichier selectionné
     */
    private String filePath;

    /**
     * Nom du fichier sélectionné
     */
    private String fileName;


    /**
     * Permet la création d'un bouton permettant la sélection du fichier Excel
     */
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
                 DialogImportExcel.labChoixFichier.setText(this.filePath);
            }
        }



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


}

