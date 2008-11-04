/*
 * Created on 16 janv. 2005
 */
package fr.lemerdy.pylos.scene;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;


/**
 * @author banou
 */
public class Balls extends BranchGroup {
	
	public Balls() {
		
		super();
		
		// Defining 2 appearances of balls.
		Color3f whiteColor = new Color3f(1f, 1f, .78f);
		Color3f blackColor = new Color3f(.1f, .1f, 0f);
		Material whiteMaterial = new Material();
		Material blackMaterial = new Material();
		whiteMaterial.setDiffuseColor(whiteColor);
		blackMaterial.setDiffuseColor(blackColor);
		Appearance whiteAppearance = new Appearance();
		Appearance blackAppearance = new Appearance();
		whiteAppearance.setMaterial(whiteMaterial);
		blackAppearance.setMaterial(blackMaterial);
		
		// Creates balls.
		int level = 0;
		boolean currentColorIsBlack = true;
		for (float y = -.36f; y <= .36f; y += .12f) {
			for (float z = (3 - level) * -0.1f; z <= (3 - level) * 0.1f; z += .2f) {
				for (float x = (3 - level) * -0.1f; x <= (3 - level) * 0.1f; x += .2f) {
					Transform3D transform3D = new Transform3D();
					transform3D.set(new Vector3f(x, y, z));
					TransformGroup transformGroup = new TransformGroup(transform3D);
					transformGroup.addChild(new Sphere(.1f, (currentColorIsBlack?blackAppearance:whiteAppearance)));
					this.addChild(transformGroup);
					currentColorIsBlack = !currentColorIsBlack;
				}
			}
			level++;
		}
		
	}

	// Rotation
	/*
	Alpha rotationAlpha = new Alpha(1, 4000);
	RotationInterpolator rotator=new RotationInterpolator(rotationAlpha, balls);
	BoundingSphere bounds = new BoundingSphere();
	rotator.setSchedulingBounds(bounds);
	balls.addChild(rotator);
	
	Alpha rotationAlpha2 = new Alpha(2, 4000);
	RotationInterpolator rotator2 = new RotationInterpolator(rotationAlpha2, balls);
	Transform3D t = new Transform3D();
	t.set(new AxisAngle4f(new Vector3f(1f, 1f, 0f), (float)Math.PI/2));
	rotator2.setAxisOfRotation(t);
	rotator2.setSchedulingBounds(bounds);
	balls.addChild(rotator2);
	*/
	// End of rotation

}
