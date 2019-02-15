import java.io.*;
import java.util.*;


/**
 * Main program za konstrukciju konveksnog omotaca.
 * 
 */
public class Hull {
  /**
   * Glavni program koji cita listu 3D tacaka zadatku kao ulazni fajl.
   * Konstruise konveksni omotac nad tackama.
   * Ispisuje ga u OFF formatu.
   *
   * usage: Hull infile outfile
   *
   */
  public static void main(String[] args) throws IOException, ConvexHullException {
	  
	if (args.length != 2) {
	    System.out.println("usage: Hull <infile> <outfile>");
	  }
	else
	  {	  	
	    BufferedReader r = new BufferedReader(new FileReader(args[0]));
	    StreamTokenizer st = new StreamTokenizer(r);
	    Vector vertices = new Vector();

	    /* Ucitavanje cvorova */
	    while ( st.nextToken() != st.TT_EOF ) {
			
			int x = 0, y = 0, z = 0;

			if (st.ttype == st.TT_NUMBER) {
				x = (int)st.nval;
			  }
			else {
				System.out.println("Error reading " + args[0]);
				System.exit(-1);
			  }
			  
			if (st.nextToken() == st.TT_NUMBER) {
				y = (int)st.nval;
			  }
			else {
				System.out.println("Error reading " + args[0]);
				System.exit(-1);
			  }
			if (st.nextToken() == st.TT_NUMBER) {
				z = (int)st.nval;
			  }	
			else {
				System.out.println("Error reading " + args[0]);
				System.exit(-1);
			  }
			  
			vertices.addElement(new Vertex(x, y, z));
	      }
		  
	    r.close();
	    
	    
	    long timerBegin, timerEnd;
	    
	    timerBegin = System.currentTimeMillis();
	    //System.out.println(timerBegin + "\n");

	    ConvexHull hull = new ConvexHull(vertices);
	    
	    timerEnd = System.currentTimeMillis();
	    System.out.println(timerEnd - timerBegin + "\n");
	    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
	    
		hull.writeOFF(pw);
		
		pw.close();
	  }
    }
}
