/*
 * Created on 15 janv. 2005
 */
package fr.lemerdy.pylos.tmp;

//Java standard API
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
//Java 3d API
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import com.sun.j3d.utils.geometry.ColorCube; 

/**
 * @author banou
 */
public class Cube3D extends Frame implements WindowListener {

	private static final long serialVersionUID = 1L;
	
	public Cube3D() {
        super("- un cube vue de face -");
        this.addWindowListener(this);
        setLayout(new BorderLayout());
        // 1ere étape création du Canvas3d qui vas afficher votre univers virtuel avec une config prédéfinie
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center", canvas3D);
        // 2eme étape on crée notre scene (regroupement d'objet)
        BranchGroup scene = createSceneGraph();
        // on les compile pour optimiser les calculs
        scene.compile();
        
        // 3eme étape on creer l'univer qui va contenir notre scene 3d
        // utilise simpleUniverse qui simplifie le code (il crée un environemment minimal simple)
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        // on met le plan de projection en arriere par rapport a l'origine
        simpleU.getViewingPlatform().setNominalViewingTransform();
        // on place la scene dans l'univers simpleU
        simpleU.addBranchGraph(scene);
    }
	
	//crée un regroupement d'objet contenant un objet cube
	public BranchGroup createSceneGraph() {
		//on crée le Bg principal
		BranchGroup objRoot=new BranchGroup();
		
		// on creer un cube
		objRoot.addChild(new ColorCube(0.5));// de rayon 50 cm

		return objRoot;
	}
    
    public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	public void windowClosing(WindowEvent e) {
		System.exit(1);
	}
    	
	public static void main(String args[]) {
		Cube3D myApp=new Cube3D();
		myApp.setSize(300,300);
		myApp.setVisible(true);
	}
}
