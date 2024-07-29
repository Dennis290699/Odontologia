package App;

import javax.swing.SwingUtilities;

import vistas.JFPrincipalScreen;

public class Principal {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFPrincipalScreen frame = new JFPrincipalScreen();
				frame.setVisible(true);
			}
		});
	}
}
