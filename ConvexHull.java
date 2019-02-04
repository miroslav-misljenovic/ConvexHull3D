// Copyright (C) Chris Pudney, The University of Western Australia, 1998.
// All rights reserved.
//  
// Permission to use, copy, modify and distribute this software and its
// documentation only for the purposes of teaching and research is hereby
// granted without fee, provided that the above copyright notice and this
// permission notice appear in all copies of this software/documentation
// and that you do not sell the software.  No commercial use or
// distribution of the software is permitted without the consent of the
// copyright owners.  Commercial licensing is available by contacting the
// author(s).
// 
// THIS SOFTWARE/DOCUMENTATION IS PROVIDED WITH NO WARRANTY, EXPRESS OR
// IMPLIED, INCLUDING, WITHOUT LIMITATION, WARRANTY OF MERCHANTABILITY OR
// FITNESS FOR A PARTICULAR PURPOSE.

import java.util.*;

/**
 * Klasa koja prikazuje konveksni omotac skupa tacaka.
 */
public class ConvexHull extends DSurface {
  /**
   * Dopustivi opseg koordinata.
   */
  private static final int COORD_RANGE = 1000000;

  public ConvexHull() {
      super();
  }

  /**
   * Konstruktor konveksnog omotaca na osnovu skupa cvorova.
   *
   */
  public ConvexHull(Vector vertices) throws ConvexHullException {
	super();

	if (vertices.size() < 4)
	  {	    
	    throw new ConvexHullException
	      ("Too few (" + vertices.size() + ") vertices to form hull");
	  }
	  
	Enumeration e = vertices.elements();
	if (e.hasMoreElements()) {
	    Vertex v1 = (Vertex)e.nextElement();
	    Vertex v2 = null;
	    for (; e.hasMoreElements(); ){
			v2 = (Vertex)e.nextElement();
			if (!Vertex.sameVertex(v1, v2)) {
				break;
			  }
		}

	    Vertex v3 = null;
	    Triangle t = null;
	    Vector coVerts = new Vector();
	    for (; e.hasMoreElements(); ) {
			v3 = (Vertex)e.nextElement();
			if (Vertex.collinear(v1, v2, v3)) {
				coVerts.addElement(v3);
			}
			else {
				t = new Triangle(v1, v2, v3);
				break;
			}
	    }
	    
	    Vertex v4 = null;
	    for (; e.hasMoreElements(); ) {
			v4 = (Vertex)e.nextElement();
			
			int volSign = t.volumeSign(v4);
			if (volSign == 0) {
				coVerts.addElement(v4);
		    }
			else {
				addFace(t);
				triToTet(t, v4, volSign);
				break;
			}
	    }

	    checkVertex(v1);
	    checkVertex(v2);
	    checkVertex(v3);
	    checkVertex(v4);

	    int i = 0;

	    // Dodavanje novog cvora u konveksni omotac.
	    for (; e.hasMoreElements(); ) {
			addVertex((Vertex)e.nextElement());
	    }

	    // Ponovna obrada prethodno pronadjenih kolinearnih/koplanarnih cvorova.
	    if (getFaces().size() > 0) {
			for (e = coVerts.elements(); e.hasMoreElements(); ) {
				addVertex((Vertex)e.nextElement());
			}		
	    }
	    else {
			throw new ConvexHullException("Vertices coplanar");
	    }
	}
  }
  
  private static boolean checkVertex(Vertex vertex)
    {
      int[] c = vertex.getCoords();
      if (Math.abs(c[0]) > COORD_RANGE ||
		  Math.abs(c[1]) > COORD_RANGE ||
		  Math.abs(c[2]) > COORD_RANGE)
		{
	  System.out.println
	    ("Warning: vertex coordinates > " + COORD_RANGE + " or < " +
	     -COORD_RANGE + " may create problems");
	  return false;
		}
      return true;
    }


  /**
   * Formiranje tetraedra na osnovu cvora 
   * i postojeceg omotaca sastavljenog od trouglova.
   *
   * Parametar vol oznacava (za tetraedar) vidljivost strane u odnosu na cvor.
   */
  private void triToTet(Polygon face, Vertex vertex, int vol) {
      Vector v = face.getVertices();
      Vertex v1 = (Vertex)v.elementAt(0);
      Vertex v2 = (Vertex)v.elementAt(1);
      Vertex v3 = (Vertex)v.elementAt(2);
      
      // Cuvanje cvorova u redosledu obrnutom od smera kazaljke na satu.
      if (vol < 0 ) {
		  v.setElementAt(v3, 0);
		  v.setElementAt(v1, 2);
		  Vertex tv = v1;
		  v1 = v3;
		  v3 = tv;
		}
	  
      addFace(new Triangle(v3, v2, vertex));
      addFace(new Triangle(v2, v1, vertex));
      addFace(new Triangle(v1, v3, vertex));
    }

  /**
   * Dodaj cvor u konveksni omotac.
   * Odredi sve vidljive strana iz cvora.
   * Ako nijedna strana nije vidljiva, oznaci cvor kao unutrasnji za omotac.
   * Obrisi vidljive strane i konstruisi strane izmedju cvora i ivica horizonta.
   *
   */
  private void addVertex(Vertex vertex) {
	  
      Vector visEdges = new Vector();
      Vector visFaces = new Vector();

      checkVertex(vertex);

      // Odredjivanje vidljivih strana
      for (Enumeration e = getFaces().elements(); e.hasMoreElements(); ) {
		
		Triangle face = (Triangle)e.nextElement();
		if (face.volumeSign(vertex) < 0) {
	      visFaces.addElement(face);
	      // System.out.println(vertex + " visible from " + face);
	    }
	  // else
	  // {
	  // System.out.println(vertex + " NOT visible from " + face);
	  // }
		}
		
      // Brisanje vidljivih strana i konstruisanje liste ivica horizonta	  
      for (Enumeration e = visFaces.elements(); e.hasMoreElements();) {
		
		Polygon face = (Polygon)e.nextElement();
		deleteVisibleFace(face, visEdges);
		}
      
      // System.out.println("Visible edges: " + visEdges);
      
      // Konstruisanje novih strana na osnovu ivica horizonta
      for ( Enumeration f = visEdges.elements(); f.hasMoreElements(); ) {
		  Edge edge = (Edge)f.nextElement();
		  Vertex ends[] = edge.getVertices();
		  addFace(new Triangle(ends[0], ends[1], vertex));
		}
    }


  /**
   * Brisanje vidljive strane iz konveksnog omotaca.
   * Azuriranje liste vidljivih strana.
   *
   * face - strana vidljiva iz cvora koju treba obrisati
   * visibleEdges - lista ivica omotaca vidljivih iz cvora (horizont)
   */
  private void deleteVisibleFace(Polygon face, Vector visibleEdges) {
	  
      Vector v = face.getVertices();
      Vertex v1 = (Vertex)v.elementAt(0);
      Vertex v2 = (Vertex)v.elementAt(1);
      Vertex v3 = (Vertex)v.elementAt(2);
      Edge e1 = new Edge(v1, v2);
      Edge e2 = new Edge(v2, v3);
      Edge e3 = new Edge(v3, v1);
      updateVisibleEdges(e1, visibleEdges);
      updateVisibleEdges(e2, visibleEdges);
      updateVisibleEdges(e3, visibleEdges);
      deleteFace(face);
    }


  /**
   * Azuriranje liste vidljivih ivica (horizonta).
   * Ako ivica e nije u listi, dodaj je,
   * ako jeste, obrisi je.
   *
   */
  private void updateVisibleEdges(Edge e, Vector visibleEdges) {
	  
      Enumeration f;
      boolean same = false;

      for (f = visibleEdges.elements(); f.hasMoreElements(); ) {
		Edge edge = (Edge)f.nextElement();

			if (Edge.sameEdge(e, edge)) {
				  same = true;
				  e = edge;
				  break;
			}
		}
		
	  if (same) {
		visibleEdges.removeElement(e);
		}
      else {
		visibleEdges.addElement(e);
		}
    }
}
