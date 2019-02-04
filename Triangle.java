import java.util.*;

public class Triangle extends Polygon {

  public Triangle (Vertex v1, Vertex v2, Vertex v3) {
      super();
      Vector v = getVertices();
      v.addElement(v1);
      v.addElement(v2);
      v.addElement(v3);
    }

  /**
   * Metod vraca znak zapremine tetraedra, formiranog od trougla i cvora.
   * Znak zapremine je pozitivan akko je cvor na negativnoj strani trougla
   * (pozitivna strana je definisana pravilom desne sake).
   * Dakle, znak zapremine je pozitivan
   * ako pri obilasku trougla u smeru suprotnom od kazaljke na satu
   * normala pokazuje na spoljasnost tetraedra.
   * Zavrsno izracunavanje je uradjeno po formuli Boba Vilijamsona,
   * sa manjim brojem mnozenja.
   * 
   * Metod vraca -1/1 ako je cvor na negativnoj/pozitivnoj strani trougla
   * ili 0 ako su cvorovi koplanarni.
   */
  public int volumeSign(Vertex v) {
	  
      Vector vertices = getVertices();
	  
      int[] v1 = ((Vertex)vertices.firstElement()).getCoords();
      int[] v2 = ((Vertex)vertices.elementAt(1)).getCoords();
      int[] v3 = ((Vertex)vertices.lastElement()).getCoords();
	      
	  int[] v4 = v.getCoords();
	  
      long ax = v1[0] - v4[0];
      long ay = v1[1] - v4[1];
      long az = v1[2] - v4[2];
	  
      long bx = v2[0] - v4[0];
      long by = v2[1] - v4[1];
      long bz = v2[2] - v4[2];
	  
      long cx = v3[0] - v4[0];
      long cy = v3[1] - v4[1];
      long cz = v3[2] - v4[2];

      long vol = ax * (by * cz - bz * cy) + ay * (bz * cx - bx * cz) + az * (bx * cy - by * cx);
      
      /* Zapremina mora biti celobrojna. */
      if (vol > 0) {
		return 1;
	  }
      else if (vol < 0) {
		return -1;
	  }		
      else {
		return 0;
	  }
    }
}