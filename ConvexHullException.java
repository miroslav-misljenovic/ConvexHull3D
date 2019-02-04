import java.lang.*;

/**
 * Klasa koja se moze uhvatiti kao exception prilikom konstrukcije konveksnog omotaca,
 * npr. u situaciji kada su svi cvorovi koplanarni.
 */
public class ConvexHullException extends Exception {
  public ConvexHullException() {
      super();
    }
 
  public ConvexHullException(String s) {
      super(s);
    }
}