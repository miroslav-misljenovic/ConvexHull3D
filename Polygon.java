import java.util.*;

public class Polygon {
  /** 
   * Poligon cini skup (vektor) cvorova.
   * Cvorovi moraju biti sacuvani u redosledu kako se obilazi poligon.
   * Za poligone u ravni, obilazak je u smeru kazaljke na satu, u odnosu na odgovarajucu normalu.
   */
  private Vector vertices;
  
  public Polygon() {
      vertices = new Vector();
    }

  public Polygon(Vector v) {
      vertices = v;
    }

  public Vector getVertices() {
      return vertices;
    }

  public String toString() {
	  
      String s = new String();

      for (Enumeration e = getVertices().elements(); e.hasMoreElements(); )	{
		  s += e.nextElement() + " - ";
	    }
		
      return s;
    }
}
