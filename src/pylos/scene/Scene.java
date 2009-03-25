/*
 * Created on 15 janv. 2005
 */
package pylos.scene;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.BoundingSphere;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import pylos.game.Color;
import pylos.game.Game;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.ConfiguredUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * @author Sébastian Le Merdy <sebastian.lemerdy@gmail.com>
 */
public class Scene extends Frame implements WindowListener, Observer {
	
	private static final Logger logger = Logger.getLogger(Scene.class.getName());
	
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private SimpleUniverse universe;
	
	private TransformGroup transformGroup;
	
	private final Appearance white;
	
	private final Appearance black;
	
	public Scene() {
		super("Pylos");
		
		game = new Game();
		game.addObserver(this);
		
		setSize(260, 460);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setLayout(new BorderLayout());
		
		// Creates buttons controls
		Panel p = new Panel();
		BoardPanel buttons = new BoardPanel(game);
		p.add(buttons);
		add(BorderLayout.SOUTH, p);
		
		// Settings
		Canvas3D canvas3D = new Canvas3D(ConfiguredUniverse.getPreferredConfiguration());
		add(BorderLayout.CENTER, canvas3D);
		
		// Constructing scene
		BranchGroup scene = new BranchGroup();
		transformGroup = new TransformGroup();
		transformGroup.addChild(new Board());
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
		
		// Defining balls colors
		Color3f whiteColor = new Color3f(1f, 1f, .78f);
		Color3f blackColor = new Color3f(.1f, .1f, 0f);
		Material whiteMaterial = new Material();
		Material blackMaterial = new Material();
		whiteMaterial.setDiffuseColor(whiteColor);
		blackMaterial.setDiffuseColor(blackColor);
		white = new Appearance();
		black = new Appearance();
		white.setMaterial(whiteMaterial);
		black.setMaterial(blackMaterial);
		
		// Add capability for user to move scene
		MouseRotate mouseRotate = new MouseRotate();
		mouseRotate.setTransformGroup(transformGroup);
		transformGroup.addChild(mouseRotate);
		mouseRotate.setSchedulingBounds(new BoundingSphere());
		scene.addChild(transformGroup);
		
		// Creates light
		DirectionalLight directionalLight = new DirectionalLight();
		directionalLight.setInfluencingBounds(new BoundingSphere());
		directionalLight.setColor(new Color3f(1f, 1f, .78f));
		scene.addChild(directionalLight);
		
		scene.compile();
		universe = new SimpleUniverse(canvas3D);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);
	}
	
	public void put(int x, int y, int z, boolean color) {
		logger.entering(this.getClass().getName(), "put", new Object[] { x, y, z, (color?"white":"black") });
		BranchGroup ball = new BranchGroup();
		Transform3D transform3D = new Transform3D();
		transform3D.set(new Vector3f(.11f * x, .11f * y, .11f * z - 0.2f));
		TransformGroup transformGroup = new TransformGroup(transform3D);
		transformGroup.addChild(new Sphere(.1f, (color?white:black)));
		ball.addChild(transformGroup);
		ball.compile();
		this.transformGroup.addChild(ball);
	}
	
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent) { System.exit(0); }
	public void windowOpened(WindowEvent windowEvent) {}
	public void windowActivated(WindowEvent windowEvent) {}
	public void windowClosed(WindowEvent windowEvent) { System.exit(0); }
	public void windowDeactivated(WindowEvent windowEvent) {}

	public static void main(String[] args) throws IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
        	public void run() {
                new Scene().setVisible(true);
            }
        });
	}

	public void update(Observable game, Object event) {
		Object[] eventTab = (Object[]) event;
		if ("put".equals(eventTab[0])) {
			put(((Integer) eventTab[1]).intValue(), ((Integer) eventTab[2]).intValue(), ((Integer) eventTab[3]).intValue(), ((Game) game).getCurrentColor() == Color.WHITE);
		}
	}
	
}