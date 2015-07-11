/*	Escuela Politecnica Nacional
	Ingenieria de Sistemas
	Computacion Distribuida
	Programa Cliente-Servidor
	Ortiz Leonardo
	Rodriguez Maritza			*/

package com.fis.communication;
//Permite borrar el nombre del paquete si se compila desde consola


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


//Clase que hereda de Thread para crear un hilo por cada conexion aceptada por el servidor
 
public class HiloComunicacion extends Thread
{
   	private DataInputStream streamDatosEntrada;
   	private DataOutputStream streamDatosSalida;
   	private String datosRecibidos;
   	private Socket socketCliente = null;
   	private String direccionCliente;
   	
 	public HiloComunicacion(Socket cliente)
 	{
   		this.socketCliente = cliente;
   		direccionCliente = this.socketCliente.getInetAddress().toString();
   	}
 	
 	// Metodo sobre escrito de la clase Thread

	public void run()
	{
   		try
   		{
   			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
  			streamDatosEntrada = new DataInputStream(socketCliente.getInputStream());
   			streamDatosSalida = new DataOutputStream(socketCliente.getOutputStream());
 			
   			// Si se recibe "Enter" se cierra la conexion.
   			do
 			{
   				// Datos de entrada

   				datosRecibidos = streamDatosEntrada.readUTF();
   				System.out.println("("+direccionCliente+") (Hilo "+this.getName()+") Informacion enviada, informacion recibida : " + datosRecibidos);
   				
   				// Datos de salida regresan al cliente

   				streamDatosSalida.writeUTF(datosRecibidos);

   				//streamDatosSalida.writeInt(datosRecibidos.length());
   			}
   			while(datosRecibidos.length()!=0);
   			
 			streamDatosEntrada.close();
   			socketCliente.close();
   			
   		}
   		catch(IOException e)
   		{
   			System.out.println(e.getMessage());
   			System.exit(1);
   		}
   		
   		System.out.println("El Cliente se encuentra desconectado: ("+direccionCliente+")");
   		
   		// Se termina el hilo creado en el servidor, si se crea uno nuevo, no se reusan
   		// los nombres de los hilos que se han destruido

		Thread.currentThread().stop();
	}
}