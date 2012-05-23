package mainframe;
import com.jgoodies.forms.layout.FormLayout;
import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.swing.DebugGraphics;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import src.client.Client;


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
public class JDialogConnexion extends JDialog implements ActionListener{
	private JPanel jPanel1;
	private JLabel jLabelPort;
	private JTextField jTextFieldpwd;
	private JTextField jTextFieldLogin;
	private JLabel jLabelPwd;
	private JLabel jLabelLogin;
	private JTextField jTextFieldPort;
	private JTextField jTextFieldHost;
	private JLabel jLabelHost;
	private NewJFrame parent;
	private Client newClient;
	
	public JDialogConnexion(NewJFrame parent, Client newClient) {
		super(parent, "Connexion au serveur", true);
		this.parent = parent;
		this.newClient = newClient;
		initGUI(parent);
		JPanel buttonPane = new JPanel();
		FlowLayout buttonPaneLayout = new FlowLayout();
		buttonPane.setLayout(buttonPaneLayout);
		JButton buttonOK = new JButton("OK");
		JButton buttonCancel = new JButton("Cancel");
		buttonPane.add(buttonOK);
		buttonPane.add(buttonCancel);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setPreferredSize(new java.awt.Dimension(385, 49));
		{
			jPanel1 = new JPanel();
			getContentPane().add(jPanel1, BorderLayout.CENTER);
			jPanel1.setBackground(null);
			TableLayout jPanel1Layout = new TableLayout(new double[][] {{155.0, 84.0, 44.0, 70.0}, {15.0, 35.0, 35.0, 35.0, 40.0}});
			jPanel1Layout.setHGap(5);
			jPanel1Layout.setVGap(5);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1.setPreferredSize(new java.awt.Dimension(385, 168));
			jPanel1.setSize(385, 150);
			jPanel1.setPreferredSize(new java.awt.Dimension(parent.getSize().width, parent.getSize().height));
			{
				jLabelHost = new JLabel();
				jPanel1.add(jLabelHost, "0, 1, r, f");
				jLabelHost.setText("Adresse du serveur : ");
			}
			{
				jTextFieldHost = new JTextField();
				jPanel1.add(jTextFieldHost, "1, 1, f, c");
				jTextFieldHost.setText("localhost");
				jTextFieldHost.setSize(80, 30);
				jTextFieldHost.setPreferredSize(new java.awt.Dimension(84, 30));
			}
			{
				jLabelPort = new JLabel();
				jPanel1.add(jLabelPort, "2, 1, r, f");
				jLabelPort.setText("Port : ");
			}
			{
				jTextFieldPort = new JTextField();
				jPanel1.add(jTextFieldPort, "3, 1, f, c");
				jTextFieldPort.setText("6789");
			}
			{
				jLabelLogin = new JLabel();
				jPanel1.add(jLabelLogin, "0, 2, r, f");
				jLabelLogin.setText("Nom d'utilisateur : ");
			}
			{
				jLabelPwd = new JLabel();
				jPanel1.add(jLabelPwd, "0, 3, r, f");
				jLabelPwd.setText("Mot de passe : ");
			}
			{
				jTextFieldLogin = new JTextField();
				jPanel1.add(jTextFieldLogin, "1, 2, f, c");
			}
			{
				jTextFieldpwd = new JPasswordField();
				jPanel1.add(jTextFieldpwd, "1, 3, f, c");
			}
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "OK") {
			newClient.setAuthInformation(jTextFieldLogin.getText(), jTextFieldpwd.getText());
			try {
				newClient.setAdresseServeur(InetAddress.getByName(jTextFieldHost.getText()));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			newClient.setPortServer(Integer.parseInt(jTextFieldPort.getText()));
		}
		setVisible(false);
		dispose();
	}

	private void initGUI(JFrame parent) {
		try {
			if (parent != null) 
				setLocation(parent.getSize().width/2, parent.getSize().height);	
			else
				setLocation(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
