package animacion1_caracoles;

public class Carrera {
   public static void main( String args[] )
   {
      Caracol[] caracol = new Caracol[4];
      for (int i=0; i<caracol.length; i++) {
         caracol[i] = new Caracol(i+1);
      }
      System.err.println( "\nIniciando carrera" );
      Caracol.iniciarCarrrera();
      for (Caracol caracol1 : caracol) {
          caracol1.start();
      }
      System.out.println("Preparados, listos ...");
      System.out.print("Caracol     ");
      for (int i=0; i<caracol.length; i++) {
         System.out.printf("%5d ", (i+1));
      }
      System.out.print("\n");
      System.out.print("----------------------------------");
      while (!Caracol.haTerminado()) {          
        System.out.print("\n");
        System.out.print("Distancia  ");
        // Desdecometa el sleep para una salida sin tantos datos
//        try {
//          Thread.sleep(100);
//        } catch (InterruptedException ex) {
//          Logger.getLogger(Carrera.class.getName()).log(Level.SEVERE, null, ex);
//        }
        for (Caracol caracol1 : caracol) {
            System.out.printf("%5d ", caracol1.getDistancia());
        }
          
      }
      System.out.print("\r");
      System.out.print("Dist. final");
      for (Caracol caracol1 : caracol) {
          System.out.printf("%5d ", caracol1.getDistancia());
      }
      System.out.println("\n");
      System.out.println("----------------------------------");
      System.out.println("EL GANADOR ES EL " + Caracol.getGanagor());
   }
}






