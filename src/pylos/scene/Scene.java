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
import java.io.InputStream;
import java.util.logging.LogManager;

import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.BoundingSphere;
import javax.vecmath.Color3f;

import pylos.game.Game;

import com.sun.j3d.utils.universe.ConfiguredUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author Sébastian Le Merdy <sebastian.lemerdy@gmail.com>
 */
public class Scene extends Frame {
	
	static {
		// configure logging from configuration file
		InputStream logging = Scene.class.getResourceAsStream("/logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(logging);
		} catch (SecurityException e) {
		} catch (IOException e) {
		}
	}
	
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private SimpleUniverse universe;
	
	public Scene() {
		super("Pylos");
		
		game = new Game();
		
		setSize(260, 460);
		setLocationRelativeTo(null);
		addWindowListener(new WindowListener() {
			public void windowIconified(WindowEvent windowEvent) {}
			public void windowDeiconified(WindowEvent windowEvent) {}
			public void windowClosing(WindowEvent windowEvent) { System.exit(0); }
			public void windowOpened(WindowEvent windowEvent) {}
			public void windowActivated(WindowEvent windowEvent) {}
			public void windowClosed(WindowEvent windowEvent) { System.exit(0); }
			public void windowDeactivated(WindowEvent windowEvent) {}
		});
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
		
		TransformGroup transformGroup = new TransformGroup();
		transformGroup.addChild(new Board());
		for (int level = 0 ; level <= 3 ; level++) {
			for (int x = -3 + level ; x <= 3 - level ; x += 2) {
				for (int y = -3 + level ; y <= 3 - level ; y += 2) {
					transformGroup.addChild(new Ball(x, y, level, game));
				}
			}
		}
		
		// Add capability for user to move scene
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Behavior animationBehavior = new AnimationBehavior(transformGroup);
		transformGroup.addChild(animationBehavior);
		animationBehavior.setSchedulingBounds(new BoundingSphere());

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
	
	public static void main(String[] args) throws IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
        	public void run() {
                new Scene().setVisible(true);
            }
        });
	}

}
