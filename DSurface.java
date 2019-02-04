import java.util.*;

/**
 * Dinamicka povrs.
 */
public class DSurface extends Surface {

public void addFace(Polygon p) {	
    getFaces().addElement(p);
  }

public void addFaces(Vector v) {
    for (Enumeration e = v.elements(); e.hasMoreElements(); ) {
		addFace((Polygon)e.nextElement());
      }
  }

public boolean deleteFace(Polygon p) {
    return getFaces().removeElement(p);
  }
}