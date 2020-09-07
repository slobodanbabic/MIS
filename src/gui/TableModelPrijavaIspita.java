package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Predmet;

public class TableModelPrijavaIspita extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] kolone = { "Naziv predmeta", "ESP", "Profesor", "Prijava" };
	private List<Predmet> predmeti;

	public TableModelPrijavaIspita(List<Predmet> predmeti) {
		this.predmeti = predmeti;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> klasa = String.class;
		if (columnIndex == 3)
			klasa = Boolean.class;
		return klasa;

	}

	public void deleteRow(Predmet p) {
		predmeti.remove(p);
		fireTableRowsDeleted(0, 0);
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		return predmeti.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Predmet p = predmeti.get(row);
		switch (column) {
		case 0:
			return p.getNaziv();
		case 1:
			return p.getEsp();
		case 2:
			return p.getNastavnik();
		case 3:
			return p.isPrijavljen();
		}
		return null;

	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		Predmet p = predmeti.get(row);
		if (column == 3) {
			if (p.isPrijavljen())
				p.setPrijavljen(false);
			else
				p.setPrijavljen(true);
		}

	}

	@Override
	public String getColumnName(int arg0) {
		return kolone[arg0];
	}

	public List<Predmet> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(List<Predmet> predmeti) {
		this.predmeti = predmeti;
	}

}
