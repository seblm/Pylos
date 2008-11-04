/*
 * Created on 15 janv. 2005
 */
package fr.lemerdy.pylos.scene;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.ConfiguredUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * @author banou
 */
public class Scene extends Frame implements WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	private BranchGroup scene;
	
	private final Appearance white;
	
	private final Appearance black;
	
	public Scene() {
		// Creates awt properties
		super("Pylos");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		
		// Setting 
		Canvas3D canvas3D = new Canvas3D(ConfiguredUniverse.getPreferredConfiguration());
		this.add("Center", canvas3D);
		
		// Constructing scene
		scene = new BranchGroup();
		TransformGroup transformGroup = new TransformGroup();
		// transformGroup.addChild(new Balls());
		transformGroup.addChild(new Board());
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		// defining balls colors
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
		
		MouseRotate mouseRotate = new MouseRotate();
		mouseRotate.setTransformGroup(transformGroup);
		transformGroup.addChild(mouseRotate);
		mouseRotate.setSchedulingBounds(new BoundingSphere());
		
		scene.addChild(transformGroup);
		DirectionalLight directionalLight = new DirectionalLight();
		directionalLight.setInfluencingBounds(new BoundingSphere());
		directionalLight.setColor(new Color3f(1f, 1f, .78f));
		scene.addChild(directionalLight);
		scene.compile();
		SimpleUniverse simpleUniverse = new SimpleUniverse(canvas3D);
		simpleUniverse.getViewingPlatform().setNominalViewingTransform();
		simpleUniverse.addBranchGraph(scene);
	}
	
	public void put(int x, int y, int z, boolean color) {
		Transform3D transform3D = new Transform3D();
		transform3D.set(new Vector3f(.12f * x, .1f * y, .1f * z));
		TransformGroup transformGroup = new TransformGroup(transform3D);
		transformGroup.addChild(new Sphere(.1f, (color?white:black)));
		scene.addChild(transformGroup);
	}
	
	public static void main(String args[]) {
		Scene scene = new Scene();
		scene.setSize(640, 480);
		scene.setVisible(true);
		int level = 0;
		boolean colorIsWhite = true;
		for (int y = -3; y <= 3; y += 2) {
			for (int z = level - 3; z <= 3 - level; z += 2) {
				for (int x = level - 3; x <= 3 - level; x += 2) {
					scene.put(x, y, z, colorIsWhite);
					colorIsWhite = !colorIsWhite;
				}
			}
			level++;
		}
	}
	
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent) { System.exit(0); }
	public void windowOpened(WindowEvent windowEvent) {}
	public void windowActivated(WindowEvent windowEvent) {}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowDeactivated(WindowEvent windowEvent) {}

}
