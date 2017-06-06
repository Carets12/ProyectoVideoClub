/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import dao.ClubDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Sierra Raez
 */
public class ClienteDAO implements IClienteDAO {
    
    private Statement statement;
    private Connection connection = Conexion.getConnection();
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;
    private int rows;
    private List<Cliente> listaClientes;
    private Cliente c;
    
    @Override
    public List<Cliente> obtenerCliente() {
        listaClientes = new ArrayList<>();
        c = null;
        sql = "select * from cliente";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String dni = resultSet.getString("dni");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                String edad = resultSet.getString("edad");
                c = new Cliente(dni, nombre, apellidos, edad);
                listaClientes.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaClientes;
    }

    
    @Override
    public boolean anadirCliente(Cliente c) {
        boolean exito = false;
        sql = "Insert into cliente values (?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, c.getDni());
            preparedStatement.setString(2, c.getNombre());
            preparedStatement.setString(3, c.getApellidos());
            preparedStatement.setString(4, c.getEdad());
            rows = preparedStatement.executeUpdate();
            if( rows != 0 ){
               exito = true;
            } System.out.println("Cliente anadido");
        } catch (SQLException ex) {
            //System.out.println("El cliente no se ha podido anadir correctamente");
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }

    @Override
    public boolean borrarCliente(Cliente c) {
        boolean exito = false;
        sql = "Delete from cliente where dni = ?";
        try {
            preparedStatement.setString(1, c.getDni());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exito;
    }

    @Override
    public boolean actualizarCliente(Cliente c) {
         boolean exito = false;
        sql = "Update from cliente set nomobre = ?, apellidos = ?, edad = ?, "
                + "where dni = ?";
        try {
            preparedStatement.setString(1, c.getNombre());
            preparedStatement.setString(2, c.getApellidos());
            preparedStatement.setString(3, c.getEdad());
            preparedStatement.setString(4, c.getDni());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exito;
        
    }
    /*
      public void anadirClienteLista(Cliente c){
        listaClientes.add(c);
    }
    */
    
    public static void main (String [] args) {
        ClienteDAO cd = new ClienteDAO();
        Cliente c = new Cliente("4325235","Daniela","Seera Seea","15");
        cd.anadirCliente(c);
        System.out.println(c);
        
        
    }
}
