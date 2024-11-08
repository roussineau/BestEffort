package aed;
import java.util.ArrayList;

public class Heap {
    
    ArrayList<Integer> elems;
    int longitud;

    public Heap() {
        this.elems = new ArrayList();
        this.longitud = 0;
    }

    public Integer max() {
        return elems.get(0);
   }

   public void encolar() {
    
   }
}
