package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import crud.StudentCrud;
import model.Ispit;
import model.Predmet;
import model.Student;
import model.Transakcija;
import utils.Podaci;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class GlavniProzor {

	private JFrame frmIsFakulteta;
	private Student s;
	private StudentCrud sc;
	private List<Ispit> polozeniIspiti;
	private List<Ispit> prijavljeniIspiti;
	private List<Predmet> slobodniPredmeti;
	private List<Transakcija> transakcije;
	private TableModelPrijavaIspita modelPrijavaIspita;
	private TableModelPolozeniIspiti modelPolozeni;
	private TableModelPrikazPrijavljenihI modelPrijavljeniIspiti;
	private TableModelFinansije modelFinansije;
	private JTable tablePrikazPolozenih;
	private JTable tablePrijavaIspita;
	private JTable tablePrikazPrijavljenihI;
	private JTable tableFinansije;
	private JLabel lblSaldo;
	private JLabel lblInfo;

	/**
	 * Create the application.
	 */
	public GlavniProzor(Student s) {
		sc = new StudentCrud();
		this.s = s;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIsFakulteta = new JFrame();
		frmIsFakulteta.setTitle("ePMF");
		frmIsFakulteta.setBounds(100, 100, 1033, 734);
		frmIsFakulteta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel PanelInfo = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) PanelInfo.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		frmIsFakulteta.getContentPane().add(PanelInfo, BorderLayout.SOUTH);
		
		lblInfo = new JLabel("");
		lblInfo.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfo.setText("Prijavljeni ste kao "+ s.getIme()+" "+s.getPrezime()+" "+s.getBrIndexa());
		PanelInfo.add(lblInfo);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmIsFakulteta.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		polozeniIspiti = sc.polozeniIspiti(s);

		modelPolozeni = new TableModelPolozeniIspiti(polozeniIspiti);

		JPanel panelPrijavaIspita = new JPanel();
		tabbedPane.addTab("Prijava ispita", null, panelPrijavaIspita, null);
		panelPrijavaIspita.setLayout(new BorderLayout(0, 0));
		JPanel panelSever = new JPanel();
		panelPrijavaIspita.add(panelSever, BorderLayout.NORTH);
		panelSever.setLayout(new BoxLayout(panelSever, BoxLayout.X_AXIS));
		JLabel lblIzborIspitnoRoka = new JLabel("Izbor ispitno roka:");
		panelSever.add(lblIzborIspitnoRoka);
		JComboBox<String> cmbIRok = new JComboBox<String>();

		cmbIRok.addItem("Septembarski ispitni rok");
		cmbIRok.addItem("Oktobarski ispitni rok");
		cmbIRok.addItem("Januarski ispitni rok");
		cmbIRok.addItem("Aprilski ispitni rok");
		cmbIRok.addItem("Junski ispitni rok");

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelSever.add(horizontalStrut);
		panelSever.add(cmbIRok);

		Component horizontalStrut_3 = Box.createHorizontalStrut(500);
		panelSever.add(horizontalStrut_3);

		JButton btnPrijaviIspit = new JButton("Prijavi");
		btnPrijaviIspit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		// btnPrijaviIspit.setPreferredSize(new Dimension(50, 20));
		// Prijava ispita
		btnPrijaviIspit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Predmet> prijavljeni = new ArrayList<Predmet>();
				float zaduzenje = 0.0f;
				boolean prijavljen = false;
				for (int i = 0; i < modelPrijavaIspita.getRowCount(); i++) {
					prijavljen = (Boolean) modelPrijavaIspita.getValueAt(i, 3);
					if (prijavljen) {
						Predmet p = modelPrijavaIspita.getPredmeti().get(i);
						prijavljeni.add(p);
						if (!s.isBudzet())
							zaduzenje += 200;
					}
				}
				float saldo = s.getSaldo();
				if (saldo - zaduzenje < 0.0) {
					JOptionPane.showMessageDialog(frmIsFakulteta, "Nemate dovoljno sredstava na računu!");
					return;
				}
				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				// zaduzujemo studenta ako nije na budzetu, 200 din prijava
				// ispita
				Transakcija t = null;
				if (!s.isBudzet()) {
					t = new Transakcija();
					t.setIznos(zaduzenje);
					t.setDatum(today);
					t.setOpis("Zaduzenje-Prijava ispita.");
					sc.zaduziStudenta(s, t);
				}
				if (t != null) {
					modelFinansije.dodajTransakciju(t);
					s = sc.findStudent((s.getStudentId()));
					lblSaldo.setText(s.getSaldo() + "");
				}
				for (Predmet p : prijavljeni) {
					Ispit ispit = new Ispit();
					ispit.setStudent(s);
					ispit.setPredmet(p);
					String ispitniRok = (String) cmbIRok.getSelectedItem();
					ispit.setIspitniRok(ispitniRok);
					ispit.setDatum(today.toString());
					sc.poveziStudentaIIspit(s, ispit);
					modelPrijavljeniIspiti.addRow(ispit);
				}

				for (Predmet p : prijavljeni) {
					modelPrijavaIspita.deleteRow(p);
				}
				if (prijavljeni.size() > 0)
					JOptionPane.showMessageDialog(frmIsFakulteta, "Uspešna prijava ispita.");
				else
					JOptionPane.showMessageDialog(frmIsFakulteta, "Morate oznaciti predmet!");
			}

		});
		panelSever.add(btnPrijaviIspit);

		JScrollPane scrollPrijavaIspita = new JScrollPane();
		panelPrijavaIspita.add(scrollPrijavaIspita, BorderLayout.CENTER);

		tablePrijavaIspita = new JTable();
		scrollPrijavaIspita.setViewportView(tablePrijavaIspita);
		slobodniPredmeti = sc.listSlobodniPredmeti(s);
		modelPrijavaIspita = new TableModelPrijavaIspita(slobodniPredmeti);
		tablePrijavaIspita.setModel(modelPrijavaIspita);

		JPanel panelPrikazPrijavjenihI = new JPanel();
		tabbedPane.addTab("Prikaz Prijavljneih Ispita", null, panelPrikazPrijavjenihI, null);
		panelPrikazPrijavjenihI.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPanePrijavljeniI = new JScrollPane();
		panelPrikazPrijavjenihI.add(scrollPanePrijavljeniI, BorderLayout.CENTER);

		tablePrikazPrijavljenihI = new JTable();

		scrollPanePrijavljeniI.setViewportView(tablePrikazPrijavljenihI);
		prijavljeniIspiti = sc.getPrijavljeniIspiti(s);
		modelPrijavljeniIspiti = new TableModelPrikazPrijavljenihI(prijavljeniIspiti);
		tablePrikazPrijavljenihI.setModel(modelPrijavljeniIspiti);

		JPanel panelFinansije = new JPanel();
		tabbedPane.addTab("Finansije", null, panelFinansije, null);
		panelFinansije.setLayout(new BorderLayout(0, 0));

		JPanel panelSaldo = new JPanel();
		panelFinansije.add(panelSaldo, BorderLayout.NORTH);

		JLabel lblSaldo_1 = new JLabel("SALDO:");
		panelSaldo.add(lblSaldo_1);

		lblSaldo = new JLabel(s.getSaldo() + "");
		panelSaldo.add(lblSaldo);
		lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPaneFinansije = new JScrollPane();
		panelFinansije.add(scrollPaneFinansije, BorderLayout.CENTER);

		tableFinansije = new JTable();
		scrollPaneFinansije.setViewportView(tableFinansije);
		transakcije = sc.listTransakcije(s);
		modelFinansije = new TableModelFinansije(transakcije);
		frmIsFakulteta.setExtendedState(JFrame.MAXIMIZED_BOTH);
		tableFinansije.setModel(modelFinansije);

		JPanel panelPolozeniIspiti = new JPanel();

		tabbedPane.addTab("Polozeni ispiti", null, panelPolozeniIspiti, null);
		panelPolozeniIspiti.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panelPolozeniIspiti.add(scrollPane, BorderLayout.CENTER);

		tablePrikazPolozenih = new JTable();
		tablePrikazPolozenih.setFillsViewportHeight(true);
		scrollPane.setViewportView(tablePrikazPolozenih);
		tablePrikazPolozenih.setModel(modelPolozeni);	
		
		
		JPanel panelKrajRada = new JPanel();
		tabbedPane.addTab("Kraj rada", null, panelKrajRada, null);
		panelKrajRada.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(15);
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelKrajRada.add(panel, BorderLayout.SOUTH);

		JButton btnKRajrada = new JButton("Kraj rada");
		btnKRajrada.setPreferredSize(new Dimension(80, 30));
		panel.add(btnKRajrada);

		JButton btnRestart = new JButton("Restart");
		btnRestart.setPreferredSize(new Dimension(80, 30));
		panel.add(btnRestart);
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmIsFakulteta.dispose();
				Podaci.restart(s);
			}
		});
		btnKRajrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frmIsFakulteta.setLocationRelativeTo(null);
		frmIsFakulteta.setVisible(true);
	}
}
