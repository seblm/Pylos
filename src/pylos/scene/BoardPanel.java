package pylos.scene;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import pylos.game.Color;
import pylos.game.Column;
import pylos.game.Game;


public class BoardPanel extends Panel implements Observer {

	private static final long serialVersionUID = 1L;
	
	private Button[][] buttons;
	
	public void put(int x, int y, Color color) {
		buttons[x+3][y+3].setLabel(color == Color.WHITE ? "O" : "X");
	}

	public BoardPanel(final Game game) {
		super();
		game.addObserver(this);
		setLayout(new GridLayout(7, 7));
		buttons = new Button[7][7];
		Column[][] columns = new Column[7][7];
		Button button = null;
		for (int y = -3; y <= 3; y++) {
			for (int x = -3; x <= 3; x++) {
				if (Math.abs(x) % 2 == Math.abs(y) % 2) {
					button = new Button(" ");
					buttons[x + 3][y + 3] = (Button) add(button);
					final int x2 = x;
					final int y2 = y;
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							game.put(x2, y2);
						}});
					// deal with columns
					Column column = game.getColumn(x, y);
					column.addObserver(this);
					columns[x + 3][y + 3] = column;
				} else {
					add(new Label());
				}
			}
		}
	}

	public void update(Observable game, Object event) {
		Object[] eventTab = (Object[]) event;
		if ("put".equals(eventTab[0])) {
			put(((Integer) eventTab[1]).intValue(), ((Integer) eventTab[2]).intValue(), ((Game) game).getCurrentColor());
		}
	}

}
