
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Pila {
    //Varibles Globales
    ArrayList<String> Pila = new ArrayList<String>();
    
    
    //Método Push
    public void Push(String Dato){
        Pila.add(Dato);
    }
    
    //Método POP
    public void Pop(){
        if(Pila.size() == 0){
            JOptionPane.showMessageDialog(null, "La pila esta vacia");
        } else {
           int indice = Pila.size()-1; 
           Pila.remove(indice);
        }
    }
    
    //Obtener tope pila
    public String tope (){
        String dato = "";
        dato = Pila.get(Pila.size()-1);
        return dato;
    }
    
    //Imprimir pila
    public void Imprimir(){
        for (int i = 0; i < Pila.size(); i++) {
            System.out.println(" "+Pila.get(i)+" "+i);
        }
    }
    
}
