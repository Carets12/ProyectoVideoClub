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
public interface IClienteDAO {
   
    List<Cliente> obtenerCliente();
    boolean anadirCliente(Cliente c);
    boolean borrarCliente(Cliente c);
    boolean actualizarCliente(Cliente c);
}
