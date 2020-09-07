package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Transakcija;

public class TableModelFinansije extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] kolone = { "Opis", "Iznos", "Datum" };
	private List<Transakcija> transakcije;

	public TableModelFinansije(List<Transakcija> transakcije) {
		this.transakcije = transakcije;
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		return transakcije.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Transakcija t = transakcije.get(row);
		switch (column) {
		case 0:
			return t.getOpis();
		case 1: {
			float iznos = t.getIznos();
			if (t.getOpis().contains("Zaduzenje"))
				return "- " + iznos;

			return iznos;
		}
		case 2:
			return t.getDatum();
		}
		return null;
	}

	@Override
	public String getColumnName(int arg0) {
		return kolone[arg0];
	}

	public void dodajTransakciju(Transakcija t) {
		transakcije.add(t);
		fireTableRowsInserted(0, 0);
	}

}
