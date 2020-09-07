package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import crud.StudentCrud;
import model.Student;

public class DLogovanje extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfUserName;
	private JTextField tfPassword;
	private Student s;

	public DLogovanje() {

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblKorisni = new JLabel("Korisni\u010Dko ime:");
		lblKorisni.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblKorisni.setBounds(34, 39, 91, 14);
		contentPanel.add(lblKorisni);

		tfUserName = new JTextField();
		tfUserName.setText("122/15");
		tfUserName.setBounds(135, 33, 124, 26);
		contentPanel.add(tfUserName);
		tfUserName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Lozinka:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(34, 77, 91, 14);
		contentPanel.add(lblNewLabel);

		tfPassword = new JTextField();
		tfPassword.setText("jova");
		tfPassword.setBounds(135, 69, 124, 26);
		contentPanel.add(tfPassword);
		tfPassword.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("jova");
		lblNewLabel_1.setBounds(79, 141, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("122/15");
		lblNewLabel_2.setBounds(34, 141, 46, 14);
		contentPanel.add(lblNewLabel_2);

		JLabel lblMisa = new JLabel("misa");
		lblMisa.setBounds(79, 166, 46, 14);
		contentPanel.add(lblMisa);

		JLabel label = new JLabel("341/15");
		label.setBounds(34, 166, 46, 14);
		contentPanel.add(label);

		JLabel lblOgi = new JLabel("ogi");
		lblOgi.setBounds(79, 191, 46, 14);
		contentPanel.add(lblOgi);

		JLabel label_1 = new JLabel("63/15");
		label_1.setBounds(34, 191, 46, 14);
		contentPanel.add(label_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Prijavi se");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StudentCrud sc = new StudentCrud();
						String brIndexa = tfUserName.getText();
						String password = tfPassword.getText();
						s = sc.logovanje(brIndexa, password);
						if (s != null) {
							setVisible(false);
							new GlavniProzor(s);
						} else
							JOptionPane.showMessageDialog(contentPanel, "Uneli ste pogrese podatke!");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Zatvori");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
