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


//Clase para conectar un cliente al servidor concurrente


public class Cliente
{
   	public static void main(String[] args)
   	{
   		Socket servidor = null;
   		DataOutputStream streamDatosEnviadosServidor;
   		BufferedReader streamDatosRecibidosTeclado;
   		DataInputStream streamDatosRecibidosDesdeServidor;
   		String datos;
   		int puerto = 10000;

//valida el puerto para evitar errrores al ingreso del mismo y se esto ocurre se utiliza el puerto por defecto
   		
   		try
   		{
   			if (args.length > 1)
   			{
   				puerto = Integer.parseInt(args[1]);
   			}		
   		}
   		catch(NumberFormatException ex)
   		{
   			System.out.println("Error en argumento, se usara el puerto por defecto");
   		}
   
   		try
   		{
   			try
   			{
   				servidor = new Socket(args[0], puerto);
   			}
   			catch (UnknownHostException e)
   			{
   				System.out.println(e.getMessage());
   				System.exit(1);
   			} 
   			
   			System.out.println("Conectado al Servidor");
   
   			streamDatosRecibidosTeclado = new BufferedReader(new InputStreamReader(System.in));
   			streamDatosEnviadosServidor = new DataOutputStream(servidor.getOutputStream());
   			streamDatosRecibidosDesdeServidor = new DataInputStream(servidor.getInputStream());
   
  	 		do
  	 		{
   				System.out.print("Ingrese un mensaje ");
				System.out.print("Presione Enter para salir ");
   				datos = streamDatosRecibidosTeclado.readLine();
   				streamDatosEnviadosServidor.writeUTF(datos);
   				System.out.println("El mensaje del servidor enviado = "+streamDatosRecibidosDesdeServidor.readUTF()); 
   			}
  	 		while(datos.length()!=0);
   
   			System.out.println("La conexion con el servidor a sido finalizada");
   
   			streamDatosRecibidosDesdeServidor.close();
   			streamDatosRecibidosTeclado.close();
   			streamDatosEnviadosServidor.close();
   			servidor.close();
   
   		}
   		catch (IOException e)
   		{
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
   	}
}