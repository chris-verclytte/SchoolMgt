package mainframe;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewJDialog extends javax.swing.JDialog implements ActionListener {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JTextPane jTextPaneAboutSchoolMgt;
	private JButton jButtonOK;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				NewJDialog inst = new NewJDialog(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public NewJDialog(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
			getContentPane().setLayout(thisLayout);
			{
				jTextPaneAboutSchoolMgt = new JTextPane();
				getContentPane().add(jTextPaneAboutSchoolMgt);
				jTextPaneAboutSchoolMgt.setText("Réalisé par Christophe Verclytte et Clément Barbotin.\n\n"+
						"Ce programme est réalisé avec les" + 
						"composants suivants: \n" + 
						"- Gestion du model DAO avec OrmLite. \n" + 
						"- Gestion de la base de données avec HSQLDB. \n" + 
						"- Interface graphique avec Jigloo & Swing.");
				jTextPaneAboutSchoolMgt.setPreferredSize(new java.awt.Dimension(381, 171));
				jTextPaneAboutSchoolMgt.setOpaque(false);
			}
			{
				jButtonOK = new JButton();
				getContentPane().add(jButtonOK);
				jButtonOK.setText("OK");
				jButtonOK.addActionListener(this);
				jButtonOK.setAlignmentY(0.0f);
				jButtonOK.setBorderPainted(false);
				jButtonOK.setPreferredSize(new java.awt.Dimension(42, 28));
			}
			this.setSize(396, 248);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setVisible(false);
	}
	
}
