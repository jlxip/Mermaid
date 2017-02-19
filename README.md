# M E R M A I D
_Generate customized and undetectable exploits for Metasploit_

<h1 align='center'><img width='400' src='http://i.imgur.com/taEiE54.png'><br><br></h1>

## What?
Mermaid is a program (Java) which can generate customized exploits (with a lot of options) for windows machines.<br>
There are three types of exploits so far:<br>
### VeilEvasion's c/meterpreter/reverse_tcp
The common exploit, which connects to Metasploit server. Courtesy of Veil Evasion.<br>
An IP and port is required in order to use it.
### MerBin
An improved exploit, which requires a previously generated shellcode (\x00), and executes it in memory.<br>
### MerCodeBin
The best one, requires a shellcode (\x00), and uploads it to Pastebin. Then, when the exploit is running, downloads the shellcode from there and executes it in runtime.<br>

## The program
### Interface
The main interface is minimal.<br>
![Main](http://i.imgur.com/Pv3sQ8I.png)<br>
The first time you open Mermaid, a file ("profiles.dat") is created in the same directory, which will contain the configuration of all exploits you create.<br>
The buttons located in the right, do pretty much what they seem to do (lol).

### Let's create an exploit
![Options](http://i.imgur.com/Gv0SCe7.png)<br>
First of all, you must assign a name to the current profile.<br>
Then, as you can see, there are four tabs: 'Exploit', 'Connection', 'Installation' and 'Persistance and Extras'. Let's see what they contain.
#### Exploit
![Exploit](http://i.imgur.com/cWapJjL.png)<br>
Here, you can select one of the three types of exploits you've read before.
#### Connection
![Connection](http://i.imgur.com/zxnU195.png)<br>
In the 'Connection' tab, you can select the connection details, such as IP and port.<br>
As the notice below says, it's only necessary to change the values if you are using Veil Evasion's exploit.
#### Installation
![Installation](http://i.imgur.com/S4eBhgt.png)<br>
In 'Installation' tab, you can select where your exploit is going to be saved in the target's machine. You can choose any directory you want, but watch out, some of them might need Administrator Privileges.<br>
There is also the option of using an 'Adittional Folder' which is a directory that will be created inside of the path selected.<br>
It is necessary to set a name for the installation file, and it has to end with '.exe'.
#### Persistance and Extras
![P&E](http://i.imgur.com/4LEHQwx.png)<br>
In this tab, you can set some values to the exploit.<br>
The most important might be attaching to the registry, this way it will execute every time the machine is started, and you can select whether you want to attach to HKCU or HKLM.<br>
There are some extra options, such as disabling UAC, disable Firewall or add a Firewall exception (these three need Administrator Privileges).<br>
Down there, there is a 'melt' option, which I recommend not to use, as it might not work.

### Building the exploit
![Build](http://i.imgur.com/63MpLJM.png)<br>
This is the window you need, 'Build'. You can select whether you like to use UPX and compress the final executable.<br>
Double click in the '...' button, and select a name for the final file. Hit 'BUILD', and enjoy.