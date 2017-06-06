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
 * @author Daniel Sierra Raez
 */
public class PeliculaDAO implements IPeliculaDAO {
   
    private Statement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String sql;
    private int rows;
    private List<Pelicula> listaPelis;
    private Pelicula p;

    public PeliculaDAO() {
        this.statement = statement;
        this.connection = connection;
        this.listaPelis = listaPelis;
    }
    
    @Override
    public List<Pelicula> obtenerPeli() {
        p = null;
        sql = "select * from pelicula";
        try {
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                String codigo = resultSet.getString("codigo");
                String titulo = resultSet.getString("titulo");
                String director = resultSet.getString("director");
                String anio = resultSet.getString("anio");
                String genero = resultSet.getString("genero");
                p = new Pelicula(codigo, titulo, director, anio, genero);
                listaPelis.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPelis;
    }
        
    @Override
    public boolean anadirPeli(Pelicula p) {
        boolean exito = false;
        sql = "insert into pelicula values = (?, ?, ?, ?, ?)";
        try {
            preparedStatement.setString(1, p.getCodigo());
            preparedStatement.setString(2, p.getTitulo());
            preparedStatement.setString(3, p.getDirector());
            preparedStatement.setString(4, p.getAnio());
            preparedStatement.setString(5, p.getGenero());
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
    public boolean borrarPeli(Pelicula p) {
        boolean exito = false;
        sql = "Delete from pelicula where codigo = ?";
        try {
            preparedStatement.setString(1, p.getCodigo());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exito;
    }

    @Override
    public boolean actualizarPeli(Pelicula p) {
        boolean exito = false;
        sql = "Update from pelicula set titulo = ?, director = ?, anio = ?, "
                + "where codigo = ?";
        try {
            preparedStatement.setString(1, p.getTitulo());
            preparedStatement.setString(2, p.getDirector());
            preparedStatement.setString(3, p.getAnio());
            preparedStatement.setString(4, p.getCodigo());
            rows = preparedStatement.executeUpdate();
            if ( rows != 0 )
                exito = true;
        } catch (SQLException ex) {
            Logger.getLogger(ClubDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exito;
        
    }
}
