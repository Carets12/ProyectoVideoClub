/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.List;

/**
 *
 * @author Daniel Sierra Raez
 */
public interface IPeliculaDAO {
    
    List<Pelicula> obtenerPeli();
    boolean anadirPeli(Pelicula p);
    boolean borrarPeli(Pelicula p);
    boolean actualizarPeli(Pelicula p);
    
}