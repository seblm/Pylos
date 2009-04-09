package pylos.scene;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import pylos.game.BallPosition;
import pylos.game.Color;
import pylos.game.Game;

import com.sun.j3d.utils.geometry.Sphere;

public class Ball extends TransformGroup implements Observer {
	
	private static final Logger logger = Logger.getLogger(Ball.class.getName());
	
	private final Sphere sphere;
	
	private static final Appearance transparent;
	
	private static final Appearance white;
	
	private static final Appearance black;
	
	private static final Material whiteMaterial;
	
	private static final Material blackMaterial;
	
	static {
		// Defining balls colors
		Color3f whiteColor = new Color3f(1f, 1f, .78f);
		Color3f blackColor = new Color3f(.1f, .1f, 0f);
		whiteMaterial = new Material();
		blackMaterial = new Material();
		whiteMaterial.setDiffuseColor(whiteColor);
		blackMaterial.setDiffuseColor(blackColor);
		transparent = new Appearance();
		white = new Appearance();
		black = new Appearance();
		transparent.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST, 1f));
		white.setMaterial(whiteMaterial);
		black.setMaterial(blackMaterial);
	}
	
	public Ball(final int x, final int y, final int z, final Game game) {
		Transform3D transform3D = new Transform3D();
		transform3D.set(new Vector3f(.11f * x, .11f * y, .11f * z - 0.2f));
		setTransform(transform3D);
		sphere = new Sphere(.1f, transparent);
		sphere.setCapability(Sphere.ENABLE_APPEARANCE_MODIFY);
		sphere.getShape().setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		addChild(sphere);
		setUserData(game.getBallPosition(x, y, z));
		((BallPosition) getUserData()).addObserver(this);
	}

	public void update(Observable o, Object arg) {
		logger.entering(this.getClass().getName(), "update", new Object[] { o, arg });
		if (o instanceof BallPosition) {
			Color color = ((BallPosition) o).getColor();
			if (color == null) {
				sphere.setAppearance(transparent);
			} else {
				if (color.equals(Color.BLACK)) {
					sphere.setAppearance(black);
				} else {
					sphere.setAppearance(white);
				}
			}
		}
	}

}
