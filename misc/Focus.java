import javax.swing.*;
import java.awt.Color;
import java.util.Random;
public class Focus {
	static Random r = new Random();
	public static void main(String[] args) {
	while(true) {	
		try {
			Thread.sleep(500);
			createObject(r.nextInt(1000), r.nextInt(600));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	}

	public static void createObject(int x, int y) {
		JDialog testDialog = new JDialog();
		testDialog.setSize(50, 50);
		testDialog.setLocation(x, y);
		testDialog.setAlwaysOnTop(true);
		//testDialog.setUndecorated(true);
		//dtestDialog.setFocusable(false);
		//testDialog.setEnabled(false);
		testDialog.setVisible(true);
	}
}