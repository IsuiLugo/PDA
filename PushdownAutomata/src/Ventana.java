/*
* Tecnológico Nacional de México campus Pachuca
* Asignatura: Lenguajes y Autómatas II
* Catedrático: Ing. Roberto Hernandéz Pérez
* Equipo: 
*   Lugo Martinez Saul Isui
*   Tomás Hernández Dulce Alejandra
*
* Automa de Pila (PDA) con el lenguaje
* L = {w | w is a palindorme of a's and b's}
* M = {Q,Σ,𝚪,𝛿,q0,Z0,F}
* Q = {q0, q1, q2}
* Σ = {a,b}
* 𝚪 = {R,G,B}
* q0 = estado inicial
* Z0 = R | R pertenene a 𝚪
* F = {q2}
*/
public class Ventana extends javax.swing.JFrame {
    //Variables globales
    Pila objeto = new Pila();
    Definicion define = new Definicion();
    //Constructor
    public Ventana() {
        initComponents();
        objeto.Push("R");
        setLocationRelativeTo(null);
    }          
    
    public int cambiaEstado( ){
        String w = jTextField1.getText();
        int retorna = 0;
        String tipo = "";
        float opera;
        //Comprueba si la cadena es par o impar
        //Envia el estado donde debe cambiar el automata
        if(w.length()%2 == 0){
            //Es par
            opera = w.length()/2;
            tipo  = "par";
        } else {
            //Es impar
            opera = ((w.length()/2)+(1/2)+1);
            tipo  = "impar";
        }
        retorna = (int)opera;
        return retorna;
    }
    
     //Funcion de transición iterativa para Delta
    public String deltaTransicion(String q, char a, int posicionSymbol) {
        String p = "";
        String w = jTextField1.getText();
        String topePila = objeto.tope();
        int epsilon = (cambiaEstado()-1);
        int posicion = posicionSymbol;
        try {
            //Compara cada estado con la entrada que recibe
            if (q.equals("q0") && a == 'a'&& topePila.equals("R")) {
                if (posicion == epsilon) {
                    p = "q1";
                    objeto.Pop();
                } else {
                    p = "q0";
                    objeto.Push("G");
                }
            } else if (q.equals("q0") && a == 'b' && topePila.equals("R")) {
                if (posicion == epsilon) {
                    p = "q1";
                    objeto.Pop();
                } else {
                    p = "q0";
                    objeto.Push("B");
                }
            } else if (q.equals("q0") && a == 'a' && topePila.equals("G")) {
                if (posicion == epsilon) {
                    p = "q1";
                    objeto.Pop();
                } else {
                    p = "q0";
                    objeto.Push("G");
                }

            } else if (q.equals("q0") && a == 'a' && topePila.equals("B")) {
                if (posicion == epsilon) {
                    p = "q1";
                    objeto.Pop();
                } else {
                    p = "q0";
                    objeto.Push("G");
                }
            } else if (q.equals("q0") && a == 'b' && topePila.equals("G")) {
                if (posicionSymbol == epsilon) {
                    p = "q1";
                    objeto.Pop();
                } else {
                    p = "q0";
                    objeto.Push("B");
                }
            } else if (q.equals("q0") && a == 'b' && topePila.equals("B")) {
                if (posicion == epsilon) {
                    p = "q1";
                    objeto.Pop();
                } else {
                    p = "q0";
                    objeto.Push("B");
                }
            } else if (q.equals("q1") && a == 'a' && topePila.equals("G")) {
                if(posicion == w.length()){
                    p = "q2";
                } else {
                    p = "q1";
                    objeto.Pop();                
                }
            } else if (q.equals("q1") && a == 'b' && topePila.equals("B")) {
                if(posicion == w.length()){
                    p = "q2";
                } else {
                    p = "q1";
                    objeto.Pop();                
                }
            } else if (q.equals("q1") && topePila.equals("R")) {
                p = "q2";
                //objeto.Pop();
                //objeto.Push("R");
            } else if(q.equals("q2")){
                p = "q2";
            }
            // La cadena no es correcta, pero es leida completamente
            else if(q.equals("q1") && a == 'a' && topePila.equals("B")) {
                p = "qx";
            } else if(q.equals("q1") && a == 'b' && topePila.equals("G")){
                p = "qx";
            } else if(q.equals("qx")){
                p = "qx";
            }
            
        } catch (Exception e) {
            System.out.println(""+e);
            jTextArea1.setText("Exepcion : " + w
                    + "\nNo es VÁLIDA");
        }
        topePila = objeto.tope();
        return p;
    }
    
    //Funcion iterativa para Delta
    public String Delta(String estado, String cadena){
        String p = "";
        int Ciclos =  cadena.length();
        int i = 0;

        while (i<Ciclos){
            //Llama al metodo delta para que obtengamos
            //el estado con el simbolo actual de la cadena
            p = deltaTransicion(estado, cadena.charAt(i),i);
            estado = p;
            i++;
        }
        return p;
    }
    
    //Valida que el ultimo simbolo de la cadena se encuetre
    //en el estado final
    public boolean Valida(String w){
        boolean val = false;
        String p = Delta("q0",w);
        try{
        if (p.equals("q2")){
            val = true;
            jTextField2.setText("Estado final: "+p);
        } else {
            val = false;
            jTextField2.setText("Estado final: q1");
        }    
        }
        catch(Exception e){
            System.out.println(""+e);
        }
        
        return val;
    }
    
    //Imprime el resultado del método valida
    //Si la cadena es válida o no
    public void Comprueba(String w){
        String top = objeto.tope();
        if (Valida(w)){
            objeto.Pop();
            jTextArea1.setText("La cadena : "+w+
                               "\nEs un palindromo"
                                +"\nTope de la pila: "+top);
        } else {
            
            jTextArea1.setText("La cadena : "+w+
                               "\nNo es válida "+top);
        }
    }
    
    //Metodo para validar que los simbolos pertenescan al lenguaje
    public void ValidaAlfa(String w) {
        boolean valida = false;
        //variable para que para el simbolo que no pertence al alfabeto
        String p = null;
        for (int i = 0; i < w.length(); i++) {
            char a = w.charAt(i);
            String q = String.valueOf(a);
            //Comparamos cada simbolo
            if (q.equals("a") || q.equals("b")) {
                valida = true;
            } else {
                valida = false;
                p = q;
                /*break termina las iteraciones
                /*puesto que la cadena no podra ser leida
                /*por contener un simbolo que no pertence al alfabeto*/
                break;
            }
        }
        if (valida) {
            //Si todos los simbolos son aceptados puede entrar al automata
            Comprueba(w);
            //objeto.Push("R");
        } else {
            //La cadena contiene simbolos que no son aceptados para el alfabeto
            //p es el simbolo que no pertenece al alfabeto
            NoPerteneceAlfa(p);
        }
    }
    
    //Método para imprimir el simbolo que no pertenece al alfabeto
    public void NoPerteneceAlfa(String q){
        jTextArea1.setText("Simbolo : "+q+""
                         + "\nNo pertenece al alfabeto");
    }
    
    //Método para lipiar los campos
    public void Limpiar(){
    jTextField1.setText(null);
    jTextArea1.setText(null);
    jTextField2.setText(null);
    }
    
    //Método que lee la cadena del campo, escrita por el usuario
    public void Ingresa(){
        //Limpiar();
        //String w = "";
        String w = jTextField1.getText();
        //Llama al metodo que valida el alfabeto
        ValidaAlfa(w);
    }
   
          
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N

        jLabel1.setText("Ingresa la cadena porfavor:");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Limpiar campos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consola", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 14))); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jTextField2.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/DefinicionM.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("L = {w | w es un palindromo de a's y b's}");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Programa PDA | Equipo 6");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/EncabezadoLAII2.png"))); // NOI18N

        jButton3.setText("Definición de M");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField2)
                            .addComponent(jTextField1)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       this.Ingresa();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.Limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        define.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
