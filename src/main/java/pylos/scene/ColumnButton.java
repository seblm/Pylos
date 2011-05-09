package pylos.scene;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import pylos.game.Color;
import pylos.game.Column;
import pylos.game.Game;

public class ColumnButton extends Button implements Observer {
	
	private static final long serialVersionUID = 1L;

	protected ColumnButton(final Game game, final Column column) {
		super();
		column.addObserver(this);
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.put(column);
			}});
		setLabel(" ");
		setEnabled(column.isCanAcceptBall());
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof Column) {
			Column column = (Column) arg0;
			Color columnColor = column.getColor();
			if (columnColor == null) {
				setLabel(" ");
			} else if (columnColor.equals(Color.BLACK)){
				setLabel("X");
			} else {
				setLabel("O");
			}
			setEnabled(column.isCanAcceptBall());
		}
	}

}
