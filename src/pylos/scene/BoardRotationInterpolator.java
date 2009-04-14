package pylos.scene;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

public class BoardRotationInterpolator extends RotationInterpolator {
	
	private final Logger logger = Logger.getLogger(BoardRotationInterpolator.class.getName());
	
	private final WakeupOnAWTEvent keyCriterion = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
    public BoardRotationInterpolator(Alpha alpha, TransformGroup transformGroup) {
		super(alpha, transformGroup);
		
		Transform3D transformAxis = new Transform3D();
		
		transformAxis.rotX(Math.PI / 2);
		setTransformAxis(transformAxis);
		setMaximumAngle(0);
	}
	
	@Override
	public void initialize() {
		wakeupOn(keyCriterion);
	}
	
	@Override
	public void processStimulus(Enumeration criteria) {
		WakeupCriterion criterion = (WakeupCriterion) criteria.nextElement();
		if (keyCriterion.equals(criterion)) {
			// a key has been pressed
			boolean matchResponsiveKeys = true;
			AWTEvent[] awtEvents = keyCriterion.getAWTEvent();
			if (awtEvents != null) {
				if (awtEvents[0] instanceof KeyEvent) {
					KeyEvent keyEvent = (KeyEvent) awtEvents[0];
					switch (keyEvent.getKeyCode()) {
					case KeyEvent.VK_LEFT :
						setMaximumAngle(getMaximumAngle() + (float) Math.PI / 2);
						break;
					case KeyEvent.VK_RIGHT :
						setMaximumAngle(getMaximumAngle() - (float) Math.PI / 2);
						break;
					default : matchResponsiveKeys = false;
					}
				}
			}
			if (matchResponsiveKeys) {
				getAlpha().setStartTime(System.currentTimeMillis());
			}
			wakeupOn(defaultWakeupCriterion);
		} else if (getAlpha().finished()) { // criterion is frame refresh
			// alpha loop is finished : restart to listen for a key
			setMinimumAngle(getMaximumAngle());
			wakeupOn(keyCriterion);
		} else {
			// process until the end of alpha loop
			super.processStimulus(criteria);
		}
	}

}
