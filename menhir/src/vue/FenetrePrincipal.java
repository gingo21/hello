package vue;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Classe qui va permettre de cr�er la vueJeu
 * 
 *
 */
public class FenetrePrincipal extends JFrame {
	
	private static final long serialVersionUID = 213498785226L;

	public FenetrePrincipal(){
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setVisible(true);
	    this.setResizable(false);
	    this.setFocusable(true);  
	    this.setSize(new Dimension(1200,725));
	    this.setLocationRelativeTo(null);
	
	}
}
