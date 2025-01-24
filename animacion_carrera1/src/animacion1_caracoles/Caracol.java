/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animacion1_caracoles;

class Caracol extends Thread {
   private static boolean finalCarrera = false;
   private static final int longitudCarrera = 5;
   private static String ganador = "";
   private int distancia;
   private String nombre = "";

   public Caracol(int id) {
      nombre = "CARACOL NÚMERO " + id;
      distancia = 0;
   }

   public static void iniciarCarrrera() { finalCarrera = false; }
   public static boolean haTerminado() { return finalCarrera; }
   public static String getGanagor() { return ganador; }
   public int getDistancia() { return distancia; }

   @Override
   public void run() {
      while (!finalCarrera) {
         try {
            // Dormimos el hilo. Si tenemos una m�quina muy r�pida poner poco tiempo durmiento
            Thread.sleep((int)(Math.random() * 200));
            if (++distancia >= longitudCarrera) finalCarrera = true;
         } catch (InterruptedException e) {
            System.err.println(e);
         }
      }
      if (distancia == longitudCarrera){
          ganador = nombre;
//          Para probar como los otros hilos siguen corriendo descomenta la línea de debajo y poner el sleep muy bajo, <5
//          System.out.println(" "+nombre);
      }
   }
}
