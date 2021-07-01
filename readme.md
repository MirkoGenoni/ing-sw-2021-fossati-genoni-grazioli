# Software engineering project 2020-2021
The game realized is the digital version of a physical board game made by Cranio Creations and available [here](https://craniointernational.com/products/masters-of-renaissance/)


The game has been realized by the group 27 formed by:

- [Stefano Fossati](https://github.com/stefanofossati)
- [Mirko Genoni](https://github.com/MirkoGenoni)
- [Davide Grazioli](https://github.com/davidegrazioli)


## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Multiplayer rules | ![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| Singleplayer rules | ![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| Socket |![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| GUI | ![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| CLI |![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| Multiple games | ![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| Resilience to disconnection | ![GREEN](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Tick.png)
| Persistence | [![RED](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Cross.png)]() |
| Parameter Editor | [![RED](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Cross.png)]() |
| Resilience to disconnections | [![RED](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubresources/Cross.png)]() |

## How to run
In the [derivable](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/tree/main/deliverables) folder, inside the jar folder there will be two .jar files that needs to be downloaded:

- MasterOfRenaissance-Server.jar

- MasterOfRenaissance-Client.jar

This files have been created using the **maven assembly plugin** imported inside the pom and created inside Intellij, manually selecting inside the manifest the class that needed to be run.

The method to run this files depends on your operative system.

(the game has been fully tested on **mac os x** operative system, its use is suggested as other operative system may cause problem with the CLI)

 ### WINDOWS

 To run this file you need to install the **java sdk v.16** downloadable from [here](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html), selecting the version "Windows x64 Installer". Once the file .exe has been downloaded, double-click on it and go through the installation.

- #### **CLI**
To **run the CLI**, due to the missing of most of the utf-8 characters inside windows bash it will be necessary to activate a **wsl**, the game has been tested on a debian and ubuntu wsl. With this method it appears to be missing two characters and the terminal has problems with the escape code of the colors,
if possible it's suggested to use a machine with mac os x (the most stable and tested version) or a virtual machine with linux.


 Inside the wsl to run the program is necessary to run:


```shell
> java -jar /path-to-executable/MasterOfRenaissance-Server.jar

> java -jar /path-to-executable/MasterOfRenaissance-Client.jar
```

**Once the client has been launched type cli and press enter**

- #### **GUI**
To run the **gui** it's not needed anything else except the java sdk, open the **windows command terminal** searching inside the search bar "cmd" or through the start menu and run:


```shell
> java -jar /path-to-executable/MasterOfRenaissance-Client.jar
```

**Once the client has been launched type gui and press enter**

### MAC OS X
To run this file you need to install the **java sdk v.16** downloadable from [here](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html), selecting the version "macOS Installer". Once the file .dmg has been downloaded, double-click on it and go through the installation.

- #### **CLI/GUI**

The program is perfectly compatible and fully tested on this operative system, it doesn't need any further installation.

 To run the jar simply run:

```shell
> java -jar /path-to-executable/MasterOfRenaissance-Server.jar

> java -jar /path-to-executable/MasterOfRenaissance-Client.jar
```

**Launching the client jar will open up a terminal page where you can select to launch the CLI or the GUI**

- if the windows are too big it's suggested to:
  
```
    Mac os x menu bar > About This Mac > Displays > Displays preferences > Resolution: scaled > More Space
```
### LINUX
To run this file you need to install the **java sdk v.16** downloadable from [here](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html), selecting the version "Linux ARM 64 Compressed Archive". 

 Once the file .tar.gz has been downloaded, move it wherever you and extract it there using:

```shell
> tar zxvf jdk-16.<version-number>-x64_bin.tar.gz
```
- #### **CLI/GUI**

The program has been tested on debian using **x-term** and **mate-terminal**, on gnome desktop ambient there seems to be no problems, exactly as with the mac os x terminal, using kde it seems to be problems due to the system lock on terminal resizing, so it can be necessary  to manually resize the window. All the cli pages are 123x46 characters.
- You can now run the jar using:

```shell
> /path-where-you-extracted/java -jar /path-to-executable/MasterOfRenaissance-Server.jar

> /path-where-you-extracted/java -jar /path-to-executable/MasterOfRenaissance-Client.jar
```

**Launching the client jar will open up a terminal page where you can select to launch the CLI or the GUI**


## Test Cases

The tests cover the **100% of the model class** and the **98% of the lines**

Tested Class | Coverage (lines) |
------------------|:------------------------------------:|
| DevelopmentCardHandler | 100%
| LeaderCardHandler | 100%
| ResourceCardHandler | 100%
| Gameboard | 64% (9/14 lines)
| FaithTrack | 100%
| Market | 100%
| MultiPlayerGame | 100%
| SinglePlayerGame| 100%
| LorenzoIlMagnifico | 100%

## Software used

**Intellij** - IDE

**MagicDraw** - UML/Sequence Diagram





