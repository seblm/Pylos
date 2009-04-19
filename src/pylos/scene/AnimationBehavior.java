package pylos.scene;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.j3d.Alpha;
import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.media.j3d.WakeupOnElapsedFrames;

public class AnimationBehavior extends Behavior {
	
	private final TransformGroup transformGroup;
	
	private final Transform3D transform3D;
	
	private final Transform3D transform3DZ;
	
	private final Transform3D transform3DX;
	
	private final Alpha alpha;
	
	private double angleZ;
	
	private double minimumAngleZ;
	
	private double maximumAngleZ;
	
	private double angleX;
	
	private final WakeupCriterion wakeupCriterion;
	
	private final WakeupCriterion wakeupFrame;
	
	private final static Logger logger = Logger.getLogger(AnimationBehavior.class.getName());
	
	/**
	 * 
	 * 
	 * @param transformGroup
	 */
	public AnimationBehavior(final TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
		this.transform3D = new Transform3D();
		this.transform3DX = new Transform3D();
		this.transform3DZ = new Transform3D();
		this.wakeupCriterion = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		this.wakeupFrame = new WakeupOnElapsedFrames(0);
		this.alpha = new Alpha(1, 0, 0, 500, 250, 0);
		this.minimumAngleZ = 0;
		this.maximumAngleZ = 0;
	}

	@Override
	public void initialize() {
		wakeupOn(wakeupCriterion);
	}

	@Override
	public void processStimulus(Enumeration criteria) {
		WakeupCriterion criterion = (WakeupCriterion) criteria.nextElement();
		WakeupCriterion nextCriterion = wakeupCriterion;
		logger.log(Level.FINER, angleX + "\t" + angleZ);
		if (criterion instanceof WakeupOnAWTEvent) {
			AWTEvent[] awtEvents = ((WakeupOnAWTEvent) criterion).getAWTEvent();
			if (awtEvents != null) {
				if (awtEvents[0] instanceof KeyEvent) {
					KeyEvent keyEvent = (KeyEvent) awtEvents[0];
					switch (keyEvent.getKeyCode()) {
					case KeyEvent.VK_DOWN :
						if (angleX < 0) {
							angleX += .1;
						}
						break;
					case KeyEvent.VK_UP :
						if (angleX > -Math.PI / 2) {
							angleX -= .1;
						}
						break;
					case KeyEvent.VK_LEFT :
						maximumAngleZ = maximumAngleZ + Math.PI / 2;
						nextCriterion = wakeupFrame;
						alpha.setStartTime(System.currentTimeMillis());
						break;
					case KeyEvent.VK_RIGHT :
						maximumAngleZ = maximumAngleZ - Math.PI / 2;
						nextCriterion = wakeupFrame;
						alpha.setStartTime(System.currentTimeMillis());
					}
					transform3DX.rotX(angleX);
					transform3DZ.rotZ(angleZ);
					transform3D.mul(transform3DX, transform3DZ);
					transformGroup.setTransform(transform3D);
				}
			}
		} else if (alpha.finished()) {
			minimumAngleZ = maximumAngleZ;
		} else if (criterion instanceof WakeupOnElapsedFrames) {
			float value = alpha.value();
			angleZ = (1.0 - value) * minimumAngleZ + value * maximumAngleZ;
			transform3DX.rotX(angleX);
			transform3DZ.rotZ(angleZ);
			transform3D.mul(transform3DX, transform3DZ);
			transformGroup.setTransform(transform3D);
			nextCriterion = wakeupFrame;
		} 
		wakeupOn(nextCriterion);
	}

}
