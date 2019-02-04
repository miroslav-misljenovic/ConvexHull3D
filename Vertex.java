public class Vertex {
  /**
   * 3D koordinate (x, y, z) cvora.
   */
  private int x, y, z;

  public Vertex() {
      x = 0;
      y = 0;
      z = 0;
    }

  public Vertex(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
  }

  public int[] getCoords() {
	  
      int[] c = new int[3];
      c[0] = x;
      c[1] = y;
      c[2] = z;
      return c;
    }

  public static boolean sameVertex(Vertex v1, Vertex v2) {
      return v1 == v2 ||
			(v1.x == v2.x &&
			 v1.y == v2.y &&
			 v1.z == v2.z);
    }

  public static boolean collinear(Vertex v1, Vertex v2, Vertex v3) {
      long x1 = v1.x, y1 = v1.y, z1 = v1.z;
      long x2 = v2.x, y2 = v2.y, z2 = v2.z;
      long x3 = v3.x, y3 = v3.y, z3 = v3.z;
	  
      return (z3 - z1) * (y2 - y1) - (z2 - z1) * (y3 - y1) == 0 &&
			 (z2 - z1) * (x3 - x1) - (x2 - x1) * (z3 - z1) == 0 &&
			 (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1) == 0;
    }

  public String toString() {
      return ("(" + x + ", " + y + ", " + z + ")");
    }


}