package fr.lemerdy.pylos.scene;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import fr.lemerdy.pylos.game.Color;

public class BoardPanel extends Panel {

	private static final long serialVersionUID = 1L;
	
	private Button[][] buttons;
	
	public void put(int x, int y, Color color) {
		buttons[x+3][y+3].setLabel(color == Color.WHITE ? "O" : "X");
	}

	public BoardPanel() {
		super();
		setLayout(new GridLayout(7, 7));
		buttons = new Button[7][7];
		for (int y = -3; y <= 3; y++) {
			for (int x = -3; x <= 3; x++) {
				if (Math.abs(x) % 2 == Math.abs(y) % 2) {
					buttons[x + 3][y + 3] = (Button) add(new Button(" "));
				} else {
					add(new Label());
				}
			}
		}
	}

}
