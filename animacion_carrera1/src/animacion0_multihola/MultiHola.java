package animacion0_multihola;// Definimos unos sencillos hilos. Se detendr�n un rato
// antes de imprimir sus nombres y retardos

class TestTh extends Thread {
    private String nombre;
    private int retardo;

    // Constructor para almacenar un nombre y un retardo
    public TestTh( String s,int d ) {
        nombre = s;
        retardo = d;
        }

    // El metodo run() es similar al main(), pero para
    // threads. Cuando run() termina el thread muere
    public void run() {
        // Retasamos la ejecuci�n el tiempo especificado
        try {
            sleep( retardo );
        } catch( InterruptedException e ) {
            throw new RuntimeException(e);
        }

        // Ahora imprimimos el nombre
        System.out.println( "Hola Mundo! "+nombre+" "+retardo );
        }
    }

// Segunda clase. Cuando hay dos varias Clases en un fichero
// la que se va a ejecutar debe coincidir con el nombre del fichero.

public class MultiHola {
    public static void main( String args[] ) {
        TestTh t1,t2,t3;

        // Creamos los threads
        t1 = new TestTh( "Thread 1",(int)(Math.random()*2000) );
        t2 = new TestTh( "Thread 2",(int)(Math.random()*2000) );
        t3 = new TestTh( "Thread 3",(int)(Math.random()*2000) );

        // Arrancamos los threads, es decir, llamamos al m�todo run().
        t1.start();
        t2.start();
        t3.start();
        }
    }