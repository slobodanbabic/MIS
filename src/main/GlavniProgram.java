package main;

import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.DLogovanje;
import utils.Podaci;

public class GlavniProgram {

	public static void main(String[] args) throws SQLException {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Podaci.pokretanjeH2Servera();		
		Podaci.inicijalizacija();
		new DLogovanje();
	}

}
