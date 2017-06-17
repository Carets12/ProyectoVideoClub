/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Sierra Raez
 */
public class ViewDAO implements IView{
    private Statement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;
    private int rows;
    private List<View> listaView;
    private View v;
    
    public ViewDAO() {
        this.connection = Conexion.getConnection();
    }

    @Override
    public List<View> obtenerView() {
        listaView = new ArrayList<>();
        v = null;
        sql = "select * from vistaAlquiler ";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String dniCliente = resultSet.getString("dniCliente");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                String codPelicula = resultSet.getString("codPelicula");
                String titulo = resultSet.getString("titulo");
                String fecha_alquilada = resultSet.getString("fecha_alquilada");
                String fecha_devolucion = resultSet.getString("fecha_devolucion");

                v = new View(dniCliente, nombre, apellidos, codPelicula, titulo, fecha_alquilada, fecha_devolucion);
                listaView.add(v);
            }
        } catch (SQLException ex) {
            System.out.println("Error en la sentencia SQL: Obtener : VIEW");
        }
        return listaView;
    }

  /*  @Override
    public boolean anadirView(View v) {
         boolean exito = false;
        sql = "insert into vistaAlquiler values (?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, v.getDniCliente());
            preparedStatement.setString(2, v.getNombre());
            preparedStatement.setString(3, v.getApellidos());
            preparedStatement.setString(4, v.getCodPelicula());   
            preparedStatement.setString(5, v.getTitulo()); 
            preparedStatement.setString(6, v.getFecha_alquilada()); 
            preparedStatement.setString(7, v.getFecha_devolucion()); 
            rows = preparedStatement.executeUpdate();
            if( rows != 0 ){
               exito = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error en la sentencia SQL: Añadir : VIEW");
        }
        return exito;
    }

    @Override
    public boolean borrarView(View v) {
         boolean exito = false;
        sql = "Delete from vistaAlquiler where dniCliente = ? and codPelicula = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, v.getDniCliente());
            preparedStatement.setString(2, v.getCodPelicula());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            System.out.println("Error en la sentencia SQL: Borrar : VIEW");
        }
        
        return exito;
       
    }
    
    public static void main (String [] args) {
        View v = new View("11111", "federerico", "sass", "12123", "sssasd", "12312321", "21313");
        ViewDAO pl = new ViewDAO();
        pl.anadirView(v);
        //Pelicula14 p = new Pelicula14("4sd32hh235","Daniela","Seera Seea","15","sad");
        System.out.println(pl.obtenerView());
     }
    */
}
