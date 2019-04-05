package module_etudiant;

import javax.swing.*;
import java.util.Vector;

public class ComboBoxModel extends DefaultComboBoxModel<String> {
        private boolean selectionAllowed;
public ComboBoxModel() {selectionAllowed=true;}
public ComboBoxModel(Vector<String> items) {
        super(items);
        selectionAllowed=true;
        }
@Override
public void setSelectedItem(Object item) {

        if(this.selectionAllowed){
                if(item.toString().equals("Selectionner un groupe")){
                        this.selectionAllowed=false;
                        super.setSelectedItem(item);
                }
        }else{
                if (item.toString().startsWith("----") || item.toString().equals("Selectionner un groupe"))
                        return;
                super.setSelectedItem(item);
        }


        };
}
