<h1>M E R M A I D</h1><br>

<h3>English descrption of the program, scroll down for the Spanish one.</h3><br>

<h2>Introduction</h2>
Mermaid is a program which generates metasploit "windows/meterpreter/reverse_tcp" exploit.<br>
It's written in multi-platform java, so it doesn't matter if it's runned on Windows, Linux or MAC OS X
(obviously taget machine HAS to be Windows).<br>
I am absolutely not responsible about the use you give to the program.<br><br>

<h2>How does it work?</h2>
The program lets you generate a customized exploit with some options, like:<br>
- Obviously select IP/DNS and connection port.<br>
- The path where exploit will be installed in the taget machine.<br>
- The possibility of adding to the registry (HKCU and/or HKLM).<br>
- Disable UAC.<br>
- Disable Firewall.<br>
- Add Firewall exception.<br>
- Melt the executable.<br><br>

The exploit will be written in C language and it's compiled with the integrated compiler TCC.<br>
Mermaid gives you the option to pass the compiled executable through UPX for substract file size.<br><br>

<h2>More information about the exploit</h2>
The exploit is generated with the intention of being as silent as possible, this means:<br>
it's compiled with TCC (which generates REALLY tiny executables, in this case the awesome size of 8.5kb),<br>
it's possible to use UPX (for losing size, with it you can get a just 5kb exe file),<br>
it's undetectable.<br><br><br>


<h3>Spanish description.</h3><br>

<h2>Introducción</h2>
Mermaid es un programa que genera exploits "windows/meterpreter/reverse_tcp" de metasploit.<br>
Está escrito en java multi-plataforma, de este modo no importa si es ejecutado en Windows, Linux o MAC OS X (aunque la máquina objetivo tiene que ser Windows).<br>
No me hago en absoluto responsable del uso que se le pueda dar al programa.<br><br>

<h2>¿Cómo funciona?</h2>
El programa te permite generar un exploit customizado con algunas opciones, como:<br>
- Obviamente introducir el IP/DNS y el puerto de conexión.<br>
- La ruta donde el exploit será instalado en la máquina objetivo.<br>
- La posibilidad de añadirse al registro (HKCU y/o HKLM).<br>
- Deshabilitar UAC.<br>
- Deshabilitar el Firewall.<br>
- Añadir una excepción al Firewall.<br>
- Auto-eliminar el ejecutable.<br><br>

El exploit será escrito en el lenguaje C y compilado con el compilador integrado TCC.<br>
Mermaid te ofrece la posibilidad de pasar el ejecutable compilado por UPX para reducir el peso final.<br><br>

<h2>Más información sobre el exploit</h2>
El exploit es generado con la intención de ser tan silencioso como sea posible, esto quiere decir:<br>
es compilado con TCC (programa que genera ejecutables muy livianos, en este caso el asombroso peso de 8.5kb),<br>
es posible usar UPX (para perder peso, el cual te dejará el ejecutable con un peso final de tan solo 5kb),<br>
es indetectable.