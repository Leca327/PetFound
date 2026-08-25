
package starter;
import javax.swing.ImageIcon;

import java.awt.Toolkit;

public class icon extends javax.swing.JFrame{

    public icon() {
        setIcon();
    }
    
    
    
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/testelogo.png")));
    }
}
