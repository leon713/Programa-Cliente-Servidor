/*	Escuela Politecnica Nacional
	Ingenieria de Sistemas
	Computacion Distribuida
	Programa Cliente-Servidor
	Ortiz Leonardo
	Rodriguez Maritza			*/

package com.fis.communication;
//Permite borrar el nombre del paquete si se compila desde consola

import java.io.*;
import java.net.*; 

//Clase que crea un servidor concurrente
 
public class Servidor
{
   	public static void main(String[] args)
   	{
   		ServerSocket socketServidor = null;
   		Socket socketCliente = null;
   		int puerto = 10000;

//valida el puerto para evitar errrores al ingreso del mismo y se esto ocurre se utiliza el puerto por defecto
   		
   		try
   		{
   			if (args.length > 0)
   				puerto = Integer.parseInt(args[0]);
   		}
   		catch(NumberFormatException ex)
   		{
   			System.out.println("Error en argumento, se usara el puerto por defecto");
   		}
   		
   		boolean estaServidorEscuchando = true;
   		
 		try
 		{
   			socketServidor = new ServerSocket(puerto);
   			System.out.println("El Servidor se encuentra escuchando en puerto " + puerto);
 			
   			while(estaServidorEscuchando)
 			{
   				// Conexion de cliente al servidor
   				socketCliente = socketServidor.accept();
   				// Se crea un thread por cada conexion aceptada
   				HiloComunicacion hilo = new HiloComunicacion(socketCliente);
   				hilo.start();
   				
   				System.out.println("Cliente conectado: " + socketCliente.getInetAddress().toString() + ":"+socketCliente.getPort());
   				System.out.println("Se ha creado el hilo para la conección "+ hilo.getName() + " para este cliente");
   			}
	 		socketServidor.close();
   		}
 		catch (IOException e)
 		{
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
   	}
}
