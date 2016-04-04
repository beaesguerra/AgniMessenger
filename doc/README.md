# Agni
Agni, a lightweight purely command line instant messenger for developers.
The name comes from the Hindu deity Agni who was a swift messenger and keeper of divine knowledge.
Agni will support multiple users in the same chat, real-time updates of peers, file transfer,security with encryption, and high customizability in messages.
This repository comes bundled with both the client and the server.

# Requirements 

Java 8 JDK, gnu-make, and eclipse with junit. And other Mac OS X, or Linux operating systems.

Homebrew on Mac OS X

## Setup

- Open the project in eclipse
- Include everything in the lib folder into the class path
- Compile the chavra shared library and include in the build folder (more information under compile)
- Export the client and server folders as runnable jars into the build/ directory
- Run the server with the port number (i.e. 3000)
- Run the client with the server ip and port number (i.e. localhost and 3000)

## Compile

It is required that the chavra shared library is compiled on the client side. To do so,
download the source at https://sourceforge.net/projects/charva/ .
Unzip the contents then go to c/src, open the Makefile for your OS and append the following to the directories necessary from your java install. 

EXAMPLE FOR LINUX
-I/usr/lib/jvm/java-8-oracle/include/ -I/usr/lib/jvm/java-8-oracle/include/linux/

Then run make -f Makefile.your_os.txt
Replacing your_os with the respective makefile for your OS.

You may need install the dev packages on linux for ncurses. 
sudo apt-get install libncurses5-dev

The resulting shared library will be in c/lib/

Copy the just compiled share library file into the build/ directory in this project.

## Styleguide

Spaces not tabs, 4 spaces for indents


Methods, parameters, variables should be `camelCase`


Class names should be `CapCase`


Constants should be `ALL_CAPS`


Space before opening braces


Braces should be on the same line
```java
class Agni {
    ...
}
```


`else` statements on the same line as the previous closing brace
```java
if(true) {\n
    ...\n
} else if (false) {\n
    ...\n
} else {
    ...
}
```


Spaces after commas
```java
method(arg, arg);
```


Spaces around operators
```java
x = 5 - y;
```
