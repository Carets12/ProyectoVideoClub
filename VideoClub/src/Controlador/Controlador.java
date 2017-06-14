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
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private Cliente cl;
    private ClienteDAO cliente;
    private PeliculaDAO pelicula;
    private AlquilerDAO alquiler;
    private List<Cliente> listaClientes = new ClienteDAO().obtenerCliente();
    private List<Pelicula> listaPelis = new PeliculaDAO().obtenerPeli();
    private List<Alquiler> listaAlquiler = new AlquilerDAO().obtenerAlquiler();
    String camposVacios = "";  
    
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
            anadirCliente();
            anadirPelicula();
            anadirAlquiler();
            comprobarCamposObligatorios(camposVacios);
           
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
        //Accion del boton volcar 
        if(e.getActionCommand().equals("Volcar")){
            volcarAlquiler();
        }
        //Accion del boton activarAlquiler
        if(e.getActionCommand().equals("Activar")){
            activarAlquiler();
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
        vista.getjButton3VolcarAlquileres().addActionListener(escuchar);
        //Escucha del boton activarAlquiler
        vista.getjButtonACTIVARALQUILER().addActionListener(escuchar);
    }
    
    public void anadirCliente(){
    String dni = vista.getjTextFieldDNI().getText();
    String nombre = vista.getjTextFieldNOMBRE().getText();
    String apellidos = vista.getjTextFieldAPELLIDOS().getText();
    String edad = vista.getjTextFieldEDAD().getText();

        if(!dni.isEmpty()){         
            Cliente c = new Cliente(dni, nombre, apellidos, edad);   
            cliente.anadirCliente(c);      
        }
    }  
    
    public void borrarCliente(){
    String dni = vista.getjTextFieldDNI().getText();
           
        for (Cliente cl : listaClientes) {            
            if(dni.equals(cl.getDni())){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL CLIENTE?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  listaClientes.remove(cl);
                  cliente.borrarCliente(cl);
                  System.out.println("Cliente Borrado Correctamente");
                break;
                }
            }          
        }
    }  
           
    public void anadirPelicula(){
    String codigo = vista.getjTextFieldCODIGO().getText();
    String titulo = vista.getjTextFieldTITULO().getText();
    String director = vista.getjTextFieldDIRECTOR().getText();
    String anio = vista.getjTextFielANIO().getText();
    String genero = vista.getjTextFieldGENERO().getText();
           
        if(!codigo.isEmpty()){         
            Pelicula p = new Pelicula(codigo, titulo, director, anio, genero);
            pelicula.anadirPeli(p);
        }         
    }
    
    public void borrarPelicula(){
    String codigo = vista.getjTextFieldCODIGO().getText();
           
        for (Pelicula cl : listaPelis) {            
            if(codigo.equals(cl.getCodigo())){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR LA PELICULA?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  listaPelis.remove(cl);
                  pelicula.borrarPeli(cl);
                  System.out.println("Pelicula Borrada Correctamente");
                break;
                }
            }          
        }
    }  
    
    public void anadirAlquiler(){
    String fecha_alquilada = vista.getjTextFieldFECHAALQUILADA().getText();
    String fecha_devolucion = vista.getjTextFieldFECHADEVOLUCION().getText();
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText();

        if(!fecha_alquilada.isEmpty() && !dniCliente.isEmpty() && !codPelicula.isEmpty()){         
            Alquiler a = new Alquiler(fecha_alquilada, fecha_devolucion, dniCliente, codPelicula);
            alquiler.anadirAlquiler(a);
        }   
    }
    
    public void borrarAlquiler(){
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
    String codPelicula = vista.getjTextFieldCODPELICULA().getText();
    
        for (Alquiler cl : listaAlquiler) {            
            if((dniCliente.equals(cl.getDniCliente())) && (codPelicula.equals(cl.getCodPelicula()))){
               int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTÁ SEGURO DE BORRAR EL ALQUILER?","Borrar", YES_NO_OPTION, QUESTION_MESSAGE);
                if(respuesta == YES_OPTION){ 
                  listaPelis.remove(cl);
                  alquiler.borrarAlquiler(cl);
                  System.out.println("Alquiler Borrado Correctamente");
                break;
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

    public String comprobarCamposObligatorios(String camposVacios){
    String c = "", ca = "", p = "", pa = "", a = "", aa = "", v = "", dc = "", dcc = "";
       
    String dni = vista.getjTextFieldDNI().getText();  
    String codigo = vista.getjTextFieldCODIGO().getText();
    String dniCliente = vista.getjTextFieldDNICLIENTE().getText();
              
        if (dni.isEmpty()){
                c = "Cliente ";
                v = "vacíos: ";
        }else{ 
            ca = "Cliente añadido correctamente\n";
        }
        if (codigo.isEmpty()){
                p = "Pelicula ";
                v = "vacíos: ";
        }else{
            pa = "Pelicula añadida correctamente\n";
        }
        
        if (dniCliente.isEmpty()){
                dc = "Alquiler ";
               // v = "vacíos: ";
        }else{
            dcc = "Alquiler añadido correctamente\n";
        }

        if (!dni.isEmpty() && !codigo.isEmpty() && dniCliente.isEmpty()){
            
                camposVacios = ca + pa + "" + v + c + p;
            
        }else {
                camposVacios = ca + pa + "Campos obligatorios " + v + c + p ; 
        }
        
        if((dni.isEmpty() && codigo.isEmpty() && !dniCliente.isEmpty())) {
                
                camposVacios = dcc + "" + dc;
        }

        vista.getjTextArea1CAJA().setText(camposVacios);   
        return camposVacios;
    }
    
    public void activarAlquiler(){     
    String dni = vista.getjTextFieldDNI().getText();  
    String codigo = vista.getjTextFieldCODIGO().getText();
    
        if(dni.isEmpty() && codigo.isEmpty()){
           vista.getjTextFieldDNICLIENTE().setEnabled(false);
           vista.getjTextFieldCODPELICULA().setEnabled(false);
           vista.getjTextFieldFECHADEVOLUCION().setEnabled(false);

        }
        if (!dni.isEmpty() && !codigo.isEmpty()){
           vista.getjTextFieldDNICLIENTE().setEnabled(true);
           vista.getjTextFieldCODPELICULA().setEnabled(true);
           vista.getjTextFieldFECHADEVOLUCION().setEnabled(true);
           vista.getjTextFieldDNICLIENTE().setText(dni);
           vista.getjTextFieldCODPELICULA().setText(codigo);
           vista.getjTextFieldDNI().setText("");
           vista.getjTextFieldCODIGO().setText("");
           vista.getjTextFieldNOMBRE().setText("");
           vista.getjTextFieldAPELLIDOS().setText("");
           vista.getjTextFieldEDAD().setText("");
           vista.getjTextFieldCODIGO().setText("");
           vista.getjTextFieldTITULO().setText("");
           vista.getjTextFieldDIRECTOR().setText("");
           vista.getjTextFielANIO().setText("");
           vista.getjTextFieldGENERO().setText("");
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
        Cliente c;
        Pelicula p;
        Alquiler a;
        
        vista.getTabla().addColumn("DNI");
        vista.getTabla().addColumn("COD_PELI");
        vista.getTabla().addColumn("FECHA_ALQUILADA");
        vista.getTabla().addColumn("FECHA_DEVUELTA");
        
        vista.getjTable1().setModel(vista.getTabla());
        if (listaAlquiler.size() > 0){
          for (int i = 0; i < listaAlquiler.size(); i++) {
                Object fila[] = new Object[4];
              
                a = listaAlquiler.get(i);
                
                fila[0] = a.getDniCliente();
                fila[1] = a.getCodPelicula();
                fila[2] = a.getFecha_alquilada();
                fila[3] = a.getFecha_devolucion();
                
                vista.getTabla().addRow(fila);
                vista.getTabla().isCellEditable(i, 0);
            }
            
            vista.getjTable1().setModel(vista.getTabla());
            vista.getjTable1().setRowHeight(30);
        }
    }
    public void rellenaCliente(){
       List<Cliente> c = cliente.obtenerCliente();
       for (Cliente cliente1 : c) {
       vista.getL1().addElement(cliente1);
       }
       vista.getjListClientes().setModel(vista.getL1());
    }
    
    public void rellenaPelicula(){
       List <Pelicula> p = pelicula.obtenerPeli();
       for (Pelicula pelicula1 : p) {
         vista.getL2().addElement(pelicula1);            
       }
         vista.getjListPeliculas().setModel(vista.getL2());
    }
    
    public void volcarAlquiler(){
       List <Alquiler> a = alquiler.obtenerAlquiler();
       for (Alquiler alquiler1 : a) {
         vista.getL3().addElement(alquiler1);            
       }
         vista.getjListAlquileres().setModel(vista.getL3());
         vista.getjButton3VolcarAlquileres().setEnabled(false);
    }
    
    public void desactivarCamposAlquiler(){
    String dni = vista.getjTextFieldDNI().getText();  
    String codigo = vista.getjTextFieldCODIGO().getText();
    
        if(dni.isEmpty() && codigo.isEmpty()){
          vista.getjTextFieldDNICLIENTE().setEnabled(false);
          vista.getjTextFieldCODPELICULA().setEnabled(false);
          vista.getjTextFieldFECHADEVOLUCION().setEnabled(false);
        }
    }
}
