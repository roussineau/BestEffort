package aed;

 
 public class Ciudad {

    private int identificador;  
    private int ganancia ;
    private int superavit;

   public Ciudad (int identificador){
     this.identificador = identificador;
     this.superavit = 0;
     this.ganancia = 0;
   }

   public int ganancia (){
    return ganancia;
   }

   public int superavit (){
    return superavit;
   }

   public int identificador (){
    return identificador;
   }

 }
