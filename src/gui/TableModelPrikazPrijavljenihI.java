package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Ispit;

public class TableModelPrikazPrijavljenihI extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] kolone = { "Naziv predmeta", "Datum", "Ispitni rok" };
	private List<Ispit> ispiti;

	public TableModelPrikazPrijavljenihI(List<Ispit> ispiti) {
		this.ispiti = ispiti;
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		return ispiti.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Ispit i = ispiti.get(row);
		switch (column) {
		case 0:
			return i.getPredmet().getNaziv();
		case 1:
			return i.getDatum();
		case 2:
			return i.getIspitniRok();
		}
		return null;
	}

	@Override
	public String getColumnName(int arg0) {
		return kolone[arg0];
	}

	public void addRow(Ispit i) {
		ispiti.add(i);
		fireTableRowsInserted(0, 0);
	}
}
