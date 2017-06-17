/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alquiler;
import Modelo.AlquilerDAO;
import Modelo.Cliente;
import Modelo.ClienteDAO;
import Modelo.Pelicula;
import Modelo.PeliculaDAO;
import Modelo.View;
import Modelo.ViewDAO;
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

/**
 *
 * @author Daniel Sierra Raez
 */
public class Controlador implements ActionListener{
    private Vista vista;
   // private Cliente cl;
    private ClienteDAO cliente;
    private PeliculaDAO pelicula;
    private AlquilerDAO alquiler;
    private ViewDAO view;
    private List<Cliente> listaClientes;
    private List<Pelicula> listaPelis;
    private List<Alquiler> listaAlquiler;
    private List<View> listaView;
    String camposVacios = "";
    int controlExistenciaCliente = 0, controlExistenciaPelicula = 0, controlExistenciaAlquiler = 0, controlExistenciaAlquilerBorrar = 0;
    
    public Controlador(Vista vista, ClienteDAO cliente, PeliculaDAO pelicula, AlquilerDAO alquiler) {
        this.vista = vista;
        this.cliente = cliente;
        this.pelicula = pelicula;
        this.alquiler = alquiler;
        actionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        //Accion del boton anadir
        if(e.getActionCommand().equals("Añadir")){
            compruebaClienteBD(controlExistenciaCliente);
            compruebaPeliculaBD(controlExistenciaPelicula);
            compruebaAlquilerBorrarBD(controlExistenciaAlquilerBorrar);
            anadirCliente();
            anadirPelicula();
            anadirAlquiler();
            comprobarCamposObligatoriosAnadir(camposVacios); 
        }
        //Accion del boton limpiar
        if(e.getActionCommand().equals("Limpiar")){
            limpiarTexto();
        }
        //Accion del boton borrar
        if(e.getActionCommand().equals("Borrar")){
            borrarCliente();
            borrarPelicula();
            borrarAlquiler();
        }
        //Accion del boton borrar de la Tabla
        if(e.getActionCommand().equals("Borrar ")){
            borrarCampoTabla();
        }  
        //Accion del boton volcar 
        if(e.getActionCommand().equals("Volcar")){
            volcarAlquilerOpcional();
        }
        //Accion del boton activarAlquiler
        if(e.getActionCommand().equals("Activar")){
            compruebaClienteBD(controlExistenciaCliente);
            compruebaPeliculaBD(controlExistenciaPelicula);
            activarAlquiler();
        }
        //Accion del boton actualizarOpcional
        if(e.getActionCommand().equals("Actualizar")){
            actualizarListaAlquiler();
        }
        //Accion al pulsar Activar creditos, menu ABOUT
        if(e.getActionCommand().equals("ON Creditos")){
            creditosActivados();
        }
        //Accion al pulsar Desactivar creditos, menu ABOUT
        if(e.getActionCommand().equals("OFF Creditos")){
            creditosDesactivados();
        } 
        //Accion del boton BorrarCliente en la lista
        if(e.getActionCommand().equals("Borrar Cliente")){
            borrarClienteLista();
        } 
        //Accion del boton BorrarPelicula en la lista
        if(e.getActionCommand().equals("Borrar Pelicula")){
            borrarPeliculaLista();
        } 
    }
    
    public void actionListener(ActionListener escuchar){
        //Escucha del boton anadir
        vista.getjButtonANADIR().addActionListener(escuchar);
        //Escucha del boton limpiar
        vista.getjButtonLIMPIAR().addActionListener(escuchar);
        //Escucha del boton borrar
        vista.getjButtonBORRAR().addActionListener(escuchar);
        //Escucha del boton volcar
        vista.getjButtonVolcarAlquileres().addActionListener(escuchar);
        //Escucha del boton activarAlquiler
        vista.getjButtonACTIVARALQUILER().addActionListener(escuchar);
        //Escucha del boton actualizar
        vista.getjButtonActualizarL().addActionListener(escuchar);
        //Escucha del listener de los creditos
        vista.getjMenuItemActivarCreditos().addActionListener(escuchar);
        vista.getjMenuItem1DesactivarCreditos().addActionListener(escuchar);
        //Escucha del boton borrar de la Tabla
        vista.getjButtonBorrarT().addActionListener(escuchar);
        //Ecucha de los botones borrar de las listas
        vista.getjButtonBorrarClienteLista().addActionListener(escuchar);
        vista.getjButtonBorrarPeliculaLista().addActionListener(escuchar);
    }

    public void anadirCliente(){ 
    String dni = vista.getjTextFieldDNI().getText();
    String nombre = vista.getjTextFieldNOMBRE().getText();
    String apellidos = vista.getjTextFieldAPELLIDOS().getText();
    String edad = vista.getjTextFieldEDAD().getText();
    Cliente c = new Cliente(dni, nombre, apellidos, edad); 

        if(!dni.isEmpty()){         
            listaClientes = new ClienteDAO().obtenerCliente();
            cliente.anadirCliente(c);  
            listaClientes.removeAll(listaClientes);
            vista.getL1().removeAllElements();
            rellenaClienteOpcional();  
        }  
    }  
     
    public void borrarCliente(){
    listaClientes = new ClienteDAO().obtenerCliente();
    String dni = vista.getjTextFieldDNI().getText();
    
        for (Cliente cl : listaClientes) {            
            if(dni.equals(cl.getDni())){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL CLIENTE?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  listaClientes.remove(cl);
                  cliente.borrarCliente(cl);
                  vista.getjTextArea1CAJA().setText("Cliente borrado correctamente\n");
                  vista.getL1().removeAllElements();
                  rellenaClienteOpcional();  
                break;
                }
            }          
        }
    }  
               
    public void anadirPelicula(){
    listaPelis = new PeliculaDAO().obtenerPeli();
    String codigo = vista.getjTextFieldCODIGO().getText();
    String titulo = vista.getjTextFieldTITULO().getText();
    String director = vista.getjTextFieldDIRECTOR().getText();
    String anio = vista.getjTextFielANIO().getText();
    String genero = vista.getjTextFieldGENERO().getText();
           
        if(!codigo.isEmpty()){         
            Pelicula p = new Pelicula(codigo, titulo, director, anio, genero);
            pelicula.anadirPeli(p);
            listaPelis.removeAll(listaPelis);
            vista.getL2().removeAllElements();
            rellenaPeliculaOpcional();
        }         
    }
    
    public void borrarPelicula(){
    listaPelis = new PeliculaDAO().obtenerPeli();
    String codigo = vista.getjTextFieldCODIGO().getText();
           
        for (Pelicula cl : listaPelis) {            
            if(codigo.equals(cl.getCodigo())){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR LA PELICULA?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  listaPelis.remove(cl);
                  pelicula.borrarPeli(cl);
                  vista.getjTextArea1CAJA().setText("Pelicula borrada correctamente\n");
                  vista.getL2().removeAllElements();
                  rellenaPeliculaOpcional();
                  for (int i = 0; i < vista.getjTable1().getRowCount(); i++) {
                    vista.getTabla().removeRow(i);
                    i-=1;
                    }
                break;
                }
            }          
        }
    }  
    
    public void anadirAlquiler(){
    listaAlquiler = new AlquilerDAO().obtenerAlquiler();
   
    String fecha_alquilada = vista.getjTextFieldFECHAALQUILADA().getText();
    String fecha_devolucion = vista.getjTextFieldFECHADEVOLUCION().getText();
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText(); 

    Alquiler a = new Alquiler(fecha_alquilada, fecha_devolucion, dniCliente, codPelicula);
  
        compruebaClienteBD(controlExistenciaCliente);
        compruebaPeliculaBD(controlExistenciaPelicula);
        compruebaAlquilerBorrarBD(controlExistenciaAlquilerBorrar);
         
        if(!dniCliente.isEmpty() && !codPelicula.isEmpty()){
            if((compruebaClienteBD(controlExistenciaCliente) == 1 && compruebaPeliculaBD(controlExistenciaPelicula) == 1)
                    && compruebaAlquilerBorrarBD(controlExistenciaAlquilerBorrar) == 0){
                alquiler.anadirAlquiler(a);
                listaAlquiler.removeAll(listaAlquiler);
                listaView.removeAll(listaView);
                vista.getjTextFieldFECHADEVOLUCION().setEnabled(false);
                // Borra la tabla que habia por defecto
                for (int i = 0; i < vista.getjTable1().getRowCount(); i++) {
                  vista.getTabla().removeRow(i);
                  i-=1;
                }
                //Carga la lista de la VISTA     
                listaView = new ViewDAO().obtenerView();
            }
            //Crea una nueva Tabla con valores actualizados de la Lista VIEW
            View v;
            for (int i = 0; i < listaView.size(); i++) { 
                Object fila[] = new Object[7];
                v = listaView.get(i);
                
                fila[0] = v.getDniCliente();
                fila[1] = v.getNombre();
                fila[2] = v.getApellidos();
                fila[3] = v.getCodPelicula();
                fila[4] = v.getTitulo();
                fila[5] = v.getFecha_alquilada();
                fila[6] = v.getFecha_devolucion();
                
                vista.getTabla().addRow(fila);
            }
        }
    }
    
    public void borrarAlquiler(){
    //listaAlquiler = new AlquilerDAO().obtenerAlquiler();
    listaView = new ViewDAO().obtenerView();
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText();

        for (Alquiler cl : listaAlquiler) {            
            if((dniCliente.equals(cl.getDniCliente())) && (codPelicula.equals(cl.getCodPelicula()))){
                if(compruebaAlquilerBorrarBD(controlExistenciaAlquilerBorrar) == 1){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL ALQUILER?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                    if(respuesta == YES_OPTION){ 
                      listaAlquiler.remove(cl);
                      alquiler.borrarAlquiler(cl);
                      System.out.println("Alquiler Borrado Correctamente");
                      break;
                    }
                }
            }          
        }
    }
    
    public void fechaActual(){
      String fechaActual = "";
      String cero = "0";
      Calendar fecha = new GregorianCalendar();
      int anio = fecha.get(Calendar.YEAR);
      int mes = fecha.get(Calendar.MONTH);
      int dia = fecha.get(Calendar.DAY_OF_MONTH);

        if (dia < 10 || mes < 10){

            if (dia < 10 && mes > 10){
                fechaActual = cero + dia + "/" +(mes+1) + "/" + anio;   
            }

            if (mes < 10 && dia > 10){
                fechaActual = dia + "/" + cero +(mes+1) + "/" + anio;   
            }

            if (dia < 10 && mes < 10){
                fechaActual = cero + dia + "/" + cero +(mes+1) + "/" + anio;   
            }

        } else{
            fechaActual = dia + "/" +(mes+1) + "/" + anio;   
          }
     
      vista.getjTextFieldFECHAALQUILADA().setText(fechaActual);
      vista.getjTextFieldFECHAALQUILADA().setEnabled(false);
    }

    public String comprobarCamposObligatoriosAnadir(String camposVacios){
    String c = "", ca = "", p = "", pa = "", v = "", dc = "", dcc = "";
       
    String dni = vista.getjTextFieldDNI().getText();  
    String codigo = vista.getjTextFieldCODIGO().getText();
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText();
          
        if (dni.isEmpty()){
                c = "Cliente ";
                v = "Campos obligatorios vacíos: ";
        }else{ 
            ca = "Cliente añadido correctamente\n";
        }

        if (codigo.isEmpty()){
                p = "Pelicula ";
                v = "Campos obligatorios vacíos: ";
        }else{
            pa = "Pelicula añadida correctamente\n";
        }
        
        if (dniCliente.isEmpty() && codPelicula.isEmpty()){
                dc = "Alquiler ";
        }else{/*
           if (compruebaAlquiler(controlExistenciaAlquiler) == 0) {
                dcc = "Alquiler añadido correctamente\n";
            }
            if (compruebaAlquilercontrolExistencia) == 1) {
                dcc = "ERROR: Ya esta añadido este alquiler\n";
            }*/
        }

        if ((!dni.isEmpty() && !codigo.isEmpty()) && (dniCliente.isEmpty() && codPelicula.isEmpty())){
            
                camposVacios = ca + pa + "" + v + c + p;
        }else {
                camposVacios = ca + pa + "" + v + c + p ; 
        }
        
        if((dni.isEmpty() && codigo.isEmpty()) && (!dniCliente.isEmpty() && !codPelicula.isEmpty())) {
                
                camposVacios = dcc + "" + dc;
        }

        vista.getjTextArea1CAJA().setText(camposVacios);   
        return camposVacios;
    }
    
    public int compruebaAlquilerBorrarBD(int controlExistenciaAlquilerBorrar){
    listaAlquiler = new AlquilerDAO().obtenerAlquiler();
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText();
     for (Alquiler cl : listaAlquiler) {   
        if((dniCliente.equals(cl.getDniCliente())) && (codPelicula.equals(cl.getCodPelicula()))){
            controlExistenciaAlquilerBorrar = 1;  
            break;
           
        }
        controlExistenciaAlquilerBorrar = 0;
     } 
    System.out.println("El alquiler esxiste si es 1 -- "+controlExistenciaAlquilerBorrar);
     return controlExistenciaAlquilerBorrar;
    }
   /*     
    public int compruebaAlquilerBD(int controlExistenciaAlquiler){
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText();
     for (Alquiler cl : listaAlquiler) {   
        if((dniCliente.equals(cl.getDniCliente())) && (codPelicula.equals(cl.getCodPelicula()))){
            controlExistenciaAlquiler = 1;
            break;
        }
        controlExistenciaAlquiler = 0;
     } 
     return controlExistenciaAlquiler;
    }*/
    
    //Metodo que comprueba si el cliente introducido en "Cliente: DNI" esta registrado en la base de datos
    public int compruebaClienteBD(int controlExistenciaCliente){
    listaClientes = new ClienteDAO().obtenerCliente();
    String dni = vista.getjTextFieldDNI().getText();
    String codigo = vista.getjTextFieldCODIGO().getText();
     for (Cliente cl : listaClientes) {   
            if((dni.equals(cl.getDni())) ){
                controlExistenciaCliente = 1;
                break;
            }
            controlExistenciaCliente = 0;
        }
    //System.out.println("El Cliente esxiste si es 1 "+compruebaClienteBD(controlExistenciaCliente));
   System.out.println("CLIENTE Existe si es 1 :----  "+controlExistenciaCliente);
     return controlExistenciaCliente;   
    }
    
    //Metodo que comprueba si la pelicula introducida en "Pelicula: CODIGO" esta registrado en la base de datos
    public int compruebaPeliculaBD(int controlExistenciaPelicula){
    listaPelis = new PeliculaDAO().obtenerPeli();
    String codigo = vista.getjTextFieldCODIGO().getText();
     for (Pelicula cl : listaPelis) {   
        if((codigo.equals(cl.getCodigo()))){
            controlExistenciaPelicula = 1;
            break;
        }
        controlExistenciaPelicula = 0;
     }
      System.out.println("PELICULA Existe si es 1 :----  "+controlExistenciaPelicula);
     return controlExistenciaPelicula;
        
    }
          
    public void activarAlquiler(){
    //Variables que guardan las cajas de texto del FORMULARIO "cliente" y "pelicula"
    String dni = vista.getjTextFieldDNI().getText();  
    String codigo = vista.getjTextFieldCODIGO().getText();
    //Metodos que comprueban si existen Clientes y Peliculas
        
        //Si el (DNI y CODIGO están vacios) Y ("cliente" DNI y "pelicula" CODIGO NO existen en la base de datos)
        //DESACTIVA EL BOTON ACTIVAR "alquiler"
        if((dni.isEmpty() && codigo.isEmpty()) && (compruebaClienteBD(controlExistenciaCliente) == 0 &&
                compruebaPeliculaBD(controlExistenciaPelicula) == 0)){
           vista.getjTextFieldDNICLIENTE().setEnabled(false);
           vista.getjTextFieldCODPELICULA().setEnabled(false);
           vista.getjTextFieldFECHADEVOLUCION().setEnabled(false);
        }
        
        //Si el (DNI y CODIGO no están vacios) Y ("cliente" DNI y "pelicula" CODIGO existen en la base de datos)
        //ACTIVA EL BOTON ACTIVAR "alquiler"
        if ((!dni.isEmpty() && !codigo.isEmpty()) && (compruebaClienteBD(controlExistenciaCliente) == 1 && 
                compruebaPeliculaBD(controlExistenciaPelicula) == 1)){
          // vista.getjTextFieldDNICLIENTE().setEnabled(true);
         //  vista.getjTextFieldCODPELICULA().setEnabled(true);
           vista.getjTextFieldFECHADEVOLUCION().setEnabled(true);
         //  vista.getjTextFieldFECHADEVOLUCION().setEnabled(true);
           vista.getjTextFieldDNICLIENTE().setText(dni);
           vista.getjTextFieldCODPELICULA().setText(codigo);
          // vista.getjTextFieldDNI().setText("");
          // vista.getjTextFieldCODIGO().setText("");
          // vista.getjTextFieldNOMBRE().setText("");
          // vista.getjTextFieldAPELLIDOS().setText("");
          // vista.getjTextFieldEDAD().setText("");
          // vista.getjTextFieldCODIGO().setText("");
          // vista.getjTextFieldTITULO().setText("");
          // vista.getjTextFieldDIRECTOR().setText("");
          // vista.getjTextFielANIO().setText("");
          // vista.getjTextFieldGENERO().setText("");
        }
    }
     
    public void limpiarTexto(){
        vista.getjTextFieldDNI().setText("");
        vista.getjTextFieldNOMBRE().setText("");
        vista.getjTextFieldAPELLIDOS().setText("");
        vista.getjTextFieldEDAD().setText("");
        vista.getjTextFieldCODIGO().setText("");
        vista.getjTextFieldTITULO().setText("");
        vista.getjTextFieldDIRECTOR().setText("");
        vista.getjTextFielANIO().setText("");
        vista.getjTextFieldGENERO().setText("");
        vista.getjTextFieldFECHADEVOLUCION().setText("");
        vista.getjTextFieldDNICLIENTE().setText("");
        vista.getjTextFieldCODPELICULA().setText("");
        vista.getjTextArea1CAJA().setText("");
    }
    
    public void tabla (){
       listaView = new ViewDAO().obtenerView();
       listaAlquiler = new AlquilerDAO().obtenerAlquiler();
        View v;

        vista.getTabla().addColumn("DNI");
        vista.getTabla().addColumn("NOMBRE");
        vista.getTabla().addColumn("APELLIDOS");
        vista.getTabla().addColumn("COD_PELI");
        vista.getTabla().addColumn("TITULO");
        vista.getTabla().addColumn("FECHA_ALQUILADA");
        vista.getTabla().addColumn("FECHA_DEVUELTA");
        
        vista.getjTable1().setModel(vista.getTabla());
        if (listaView.size() > 0){
          for (int i = 0; i < listaView.size(); i++) { 
                Object fila[] = new Object[7];
                v = listaView.get(i);
                
                fila[0] = v.getDniCliente();
                fila[1] = v.getNombre();
                fila[2] = v.getApellidos();
                fila[3] = v.getCodPelicula();
                fila[4] = v.getTitulo();
                fila[5] = v.getFecha_alquilada();
                fila[6] = v.getFecha_devolucion();
                
                vista.getTabla().addRow(fila);
            }
            vista.getjTable1().setModel(vista.getTabla());
            vista.getjTable1().setRowHeight(30);
        }  
       
    }
    public void rellenaClienteOpcional(){
       List<Cliente> c = cliente.obtenerCliente();
       for (Cliente cliente1 : c) {
       vista.getL1().addElement(cliente1);
       }
       vista.getjListClientes().setModel(vista.getL1());
      
    }
    
    public void rellenaPeliculaOpcional(){
       List <Pelicula> p = pelicula.obtenerPeli();
       for (Pelicula pelicula1 : p) {
         vista.getL2().addElement(pelicula1);            
       }
         vista.getjListPeliculas().setModel(vista.getL2());
         
    }
    
    public void actualizarListaAlquiler(){
    boolean activado = vista.getjButtonVolcarAlquileres().isEnabled();
        if(activado == true){
           vista.getjButtonActualizarL().setEnabled(false);
        
        }else{
            vista.getjButtonActualizarL().setEnabled(true);
            vista.getL3().removeAllElements();
            volcarAlquilerOpcional();
        }
    }
    
    public void volcarAlquilerOpcional(){
       vista.getjButtonActualizarL().setEnabled(true);
       List <Alquiler> a = alquiler.obtenerAlquiler();
       for (Alquiler alquiler1 : a) {
         vista.getL3().addElement(alquiler1);            
       }
         vista.getjListAlquileres().setModel(vista.getL3());
         vista.getjButtonVolcarAlquileres().setEnabled(false);
    }
    
    public void desactivarCamposAlquiler(){
    String dni = vista.getjTextFieldDNI().getText();  
    String codigo = vista.getjTextFieldCODIGO().getText();
    
        if(dni.isEmpty() && codigo.isEmpty()){
          vista.getjTextFieldDNICLIENTE().setEnabled(false);
          vista.getjTextFieldCODPELICULA().setEnabled(false);
          vista.getjTextFieldFECHADEVOLUCION().setEnabled(false);
          
        }
        vista.getjTextFieldDniClienteT().setEnabled(false);
        vista.getjTextFieldNombreT().setEnabled(false);
        vista.getjTextFieldApellidosT().setEnabled(false);
        vista.getjTextFieldCodPeliculaT().setEnabled(false);
        vista.getjTextFieldTituloT().setEnabled(false);
        vista.getjTextFieldFecha_AlquiladaT().setEnabled(false);
        vista.getjTextFieldFecha_DevueltaT().setEnabled(false);
        vista.getjTextFieldNOMBRELISTA().setVisible(false);
        vista.getjTextFieldTITULOLISTA().setVisible(false);
    }
    
    public void creditosDesactivados(){
        vista.getjTextFieldCreditos1().setVisible(false);
        vista.getjTextFieldCreditos2().setVisible(false);
        vista.getjTextFieldCreditos3().setVisible(false);
    }
    public void creditosActivados(){
        vista.getjTextFieldCreditos1().setVisible(true);
        vista.getjTextFieldCreditos2().setVisible(true);
        vista.getjTextFieldCreditos3().setVisible(true);
    }
    /*
    public void rellenaSeleccionTabla(){
        String dniT = vista.getjTextFieldDniClienteT().getText();
        String codT = vista.getjTextFieldCodPeliculaT().getText();
        
    }*/
    
    public void borrarCampoTabla(){
    int filaSeleccionada = vista.getjTable1().getSelectedRow();
    String dniClienteT = vista.getjTextFieldDniClienteT().getText();
    String codPeliculaT = vista.getjTextFieldCodPeliculaT().getText();
        for (Alquiler cl : listaAlquiler) {            
          if((dniClienteT.equals(cl.getDniCliente())) && (codPeliculaT.equals(cl.getCodPelicula()))){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL ALQUILER?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
            if(respuesta == YES_OPTION){ 
                  alquiler.borrarAlquiler(cl);
                  vista.getTabla().removeRow(filaSeleccionada);
                break;
            }
          }          
        }
    }
    
    public void borrarClienteLista(){
    listaClientes = new ClienteDAO().obtenerCliente();
    int fila = vista.getjListClientes().getSelectedIndex();
    String nombreLista = vista.getjTextFieldNOMBRELISTA().getText().toString();

        for (Cliente cl : listaClientes) {  
            if(nombreLista.equals(cl.toString())){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL CLIENTE?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  cliente.borrarCliente(cl);
                  vista.getL1().removeElementAt(fila);
                  break;
                }
            }          
        }
    }
    public void borrarPeliculaLista(){   
    listaPelis = new PeliculaDAO().obtenerPeli();
    int fila = vista.getjListPeliculas().getSelectedIndex();
    String tituloLista = vista.getjTextFieldTITULOLISTA().getText().toString();

        for (Pelicula cl : listaPelis) {  
            if(tituloLista.equals(cl.toString())){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL CLIENTE?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  pelicula.borrarPeli(cl);
                  vista.getL2().removeElementAt(fila);
                  break;
                }
            }          
        } 
    }
}