import java.util.*;
import java.io.*;

public class Surface {
  /**
   * Lista poligonalnih strana koje cine povrs.
   */
  private Vector faces;

  public Surface() {
      faces = new Vector();
    }

  public Surface(Vector f) {
      faces = f;
    }

  public Vector getFaces() {
      return faces;
    }

  public void setFaces(Vector f) {
      faces = f;
    }

  public Vector getVertices() {
	  
      Vector vertices = new Vector();

      // Uzimanje cvorova svake strane
      for (Enumeration e = faces.elements(); e.hasMoreElements(); ) {
		
		Vector face_verts = ((Polygon)e.nextElement()).getVertices();
		
		for (Enumeration f = face_verts.elements(); f.hasMoreElements(); ) {
			 
			  Vertex vertex = (Vertex)f.nextElement();
			  if (vertices.indexOf(vertex) == -1) {
					vertices.addElement(vertex);
				}
			}
	  }
      return vertices;
    }

  /**
   * Metod ispisuje OFF fajl koji opisuje povrs.
   * OFF fajlovi se mogu videti koriscenjem Geomview softvera
   * http://www.geom.umn.edu/software/download/geomview.html
   */
  public void writeOFF(PrintWriter pw) throws IOException {
	  
	/* Zaglavlje izlaznog fajla */
	pw.println("OFF");
	Vector vertices = getVertices();
	pw.println(vertices.size() + " " + faces.size() + " " + (3 * faces.size()));

	/* Lista cvorova */
	for (Enumeration v = vertices.elements(); v.hasMoreElements(); ) {
	    int[] c = ((Vertex)v.nextElement()).getCoords();
	    pw.println(c[0] + " " + c[1] + " " + c[2]);
	  }

	/* Lista poligona */
	for (Enumeration f = faces.elements(); f.hasMoreElements(); ) {
	    Vector pV = ((Polygon)f.nextElement()).getVertices();
	    pw.print(pV.size());
	    for (Enumeration v = pV.elements(); v.hasMoreElements(); ) {
			pw.print(" " + vertices.indexOf((Vertex)v.nextElement()));
	    }		
	    pw.println();
	}
  }

  public String toString() {
      String s = new String();
	  
	  for (Enumeration e = faces.elements(); e.hasMoreElements(); ) {
		s += (Polygon)e.nextElement() + "\n";
	  }
	  
      return s;
    }
}