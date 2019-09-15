# Easy ISO Project

## How to use it

1. Clone this project.
2. Import the folder to your eclipse framework.

## Project Structure
1. **Autorizador**
> * Has the Mastercard ISO8583 (V1) message parser  

2. **CommonInterfaces**
> * Interfaces between all modules.  

3. **EasyISO**
> * Has the main class and classes that are GUI (_grafical user interface_).  

4. **FormataCECI**
> * It is a formater to use in CECI tool to start an transaction on Mainframes (CICS).  

5. **FormatedTextField**
> * Some user's text fields created to interpret specific format in GUI.  

6. **TCPIP**
> * Some classes to handle Tcp/IP protocols and messages.  

7. **TcpIpServerTest**
> * Some tests created to act has an Server echoing an request. It doensn't process an transaction, only gives an echo back.  

8. **Uteis**
> * Some utilities classes used in the project. For example: EBCDIC to ASCII converter.  


## Screen Shots

![easy iso 0](https://raw.githubusercontent.com/jrperin/easy_iso/master/Docs/screenshots/0_eclipse_project_structure.png)

![easy iso 1](https://raw.githubusercontent.com/jrperin/easy_iso/master/Docs/screenshots/6_easyiso.png)

![easy iso 2](https://raw.githubusercontent.com/jrperin/easy_iso/master/Docs/screenshots/4_easyiso.png)




