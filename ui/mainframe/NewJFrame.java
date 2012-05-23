package mainframe;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import src.client.*;

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
public class NewJFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 3691889102776644003L;
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private Client newClient;
	private JMenuBar barrePrincipale;
	private JMenu menuFichier;
	private JMenuItem menuAbout_AboutSchoolMgt;
	private JMenuItem menuFichier_Quitter;
	private JMenuItem menuFichier_Connexion;
	private JMenu menuAbout;
	private JDialog AboutSchoolMgt;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewJFrame inst = new NewJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setTitle("SchoolMgt");
				inst.setSize(300, 300);
			}
		});
	}
	
	public NewJFrame() {
		super();
		initGUI();
		setNewClient(new Client());
	}
	
	public Client getNewClient() {
		return newClient;
	}

	public void setNewClient(Client newClient) {
		this.newClient = newClient;
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				barrePrincipale = new JMenuBar();
				setJMenuBar(barrePrincipale);
				{
					menuFichier = new JMenu();
					barrePrincipale.add(menuFichier);
					menuFichier.setText("Fichier");
					{
						menuFichier_Connexion = new JMenuItem();
						menuFichier.add(menuFichier_Connexion);
						menuFichier_Connexion.setText("Connexion au serveur");
						menuFichier_Connexion.addActionListener(new FichierConnexionListener(this));
					}
					{
						menuFichier_Quitter = new JMenuItem();
						menuFichier.add(menuFichier_Quitter);
						menuFichier_Quitter.setText("Quitter");
					}
				}
				{
					menuAbout = new JMenu();
					barrePrincipale.add(menuAbout);
					menuAbout.setText("About");
					{
						menuAbout_AboutSchoolMgt = new JMenuItem();
						menuAbout.add(menuAbout_AboutSchoolMgt);
						menuAbout_AboutSchoolMgt.setText("About SchoolMgt");
						menuAbout_AboutSchoolMgt.addActionListener(this);
						{
						}
					}
				}
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("About SchoolMgt")) {
		}
	}
	
	/*    Listeners    */
	class AboutSchoolMgtListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			AboutSchoolMgt.setVisible(true);
		}
	}
	
	/* Fenêtres */
	private class FichierConnexionListener extends JDialog implements ActionListener {
			JDialogConnexion fenConnexion;
			NewJFrame parent;
			Client newClient;
			
		public FichierConnexionListener (NewJFrame parent){
			this.parent = parent;
			this.newClient = parent.getNewClient();
			}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			fenConnexion = new JDialogConnexion(parent, newClient);
			fenConnexion.setVisible(true);
		}
	}
	
	class AboutSchoolDialog extends JDialog implements ActionListener {

		private static final long serialVersionUID = -5637220497229462263L;

		public AboutSchoolDialog() {
			this.setModal(true);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));		
			JTextPane desc = new JTextPane();
			
			
			desc.setText("Réalisé par Christophe Verclytte et Clément Barbotin.\n\n"+
					"Ce programme est réalisé avec les" + 
					"composants suivants: \n" + 
					"- Gestion du model DAO avec OrmLite. \n" + 
					"- Gestion de la base de données avec HSQLDB. \n" + 
					"- Interface graphique avec Jigloo & Swing.");
			this.getContentPane().add(desc);
			JPanel panel = new JPanel();
			JButton buttonOK = new JButton("OK");
			buttonOK.addActionListener(this);
			panel.add(buttonOK);
			this.getContentPane().add(panel);
			pack();
			setVisible(false);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			this.setVisible(false);
			dispose();
		}
		public void main(String [] argv) {
			AboutSchoolDialog aboutSchoolDialog = new AboutSchoolDialog();
		}
		
	}

}
