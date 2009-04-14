package pylos.scene;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;

public class VerticalBoardRotationInterpolator extends RotationInterpolator {

	private final WakeupOnAWTEvent keyCriterion = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
	
	public VerticalBoardRotationInterpolator(Alpha alpha, TransformGroup target) {
		super(alpha, target);
		
		Transform3D transformAxis = new Transform3D();
		
		transformAxis.rotZ(Math.PI / 2);
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
					case KeyEvent.VK_DOWN :
						setMaximumAngle(getMaximumAngle() + (float) Math.PI / 2);
						break;
					case KeyEvent.VK_UP :
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
