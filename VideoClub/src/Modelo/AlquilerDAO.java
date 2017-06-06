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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danic
 */
public class AlquilerDAO implements IAlquiler {
    
    private Statement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;
    private int rows;
    private List<Alquiler> listaAlquiler;
    private Alquiler a;

    public AlquilerDAO() throws SQLException {
        this.statement = statement;
        this.connection = connection;
        this.listaAlquiler = listaAlquiler;
    }
   
    @Override
    public List<Alquiler> obtenerAlquiler() {
       a = null;
        sql = "select * from alquila ";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String fecha_alquilada = resultSet.getString("fecha_alquilada");
                String fecha_devolucion = resultSet.getString("fecha_devolucion");
                String dniCliente = resultSet.getString("dniCliente");
                String codPelicula = resultSet.getString("codPelicula");
                a = new Alquiler(fecha_alquilada, fecha_devolucion, dniCliente, codPelicula);
                listaAlquiler.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAlquiler;
    }

    @Override
    public boolean anadirAlquiler(Alquiler a) {
        boolean exito = false;
        sql = "insert into alquila values = (?, ?, ?, ?)";
        try {
            preparedStatement.setString(1, a.getFecha_alquilada());
            preparedStatement.setString(2, a.getFecha_devolucion());
            preparedStatement.setString(3, a.getDniCliente());
            preparedStatement.setString(4, a.getCodPelicula());            
            rows = preparedStatement.executeUpdate();
            if( rows != 0 ){
               exito = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeliculaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }

    @Override
    public boolean borrarAlquiler(Alquiler a) {
         boolean exito = false;
        sql = "Delete from alquila where fecha_alquilada = ?";
        try {
            preparedStatement.setString(1, a.getFecha_alquilada());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exito;
    }

    @Override
    public boolean actualizarAlquiler(Alquiler a) {
        boolean exito = false;
        sql = "Update from alquila set fecha_devolucion = ?, dniCliente = ?, codPelicula = ?, "
                + "where fecha_alquilada = ?";

        try {
            preparedStatement.setString(1, a.getFecha_alquilada());
            preparedStatement.setString(2, a.getFecha_devolucion());
            preparedStatement.setString(3, a.getDniCliente());
            preparedStatement.setString(4, a.getCodPelicula());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exito;
        
    }
        
}