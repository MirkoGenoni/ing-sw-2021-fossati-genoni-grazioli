# Software engineering project 2020-2021
The game realized is the digital version of a physical board game made by Cranio Creations and available [here](https://craniointernational.com/products/masters-of-renaissance/)


The game has been realized by the group 27 formed by:

- [Stefano Fossati](https://github.com/stefanofossati)
- [Mirko Genoni](https://github.com/MirkoGenoni)
- [Davide Grazioli](https://github.com/davidegrazioli)
- [JavaDoc](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/docs/index.html)


## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Multiplayer rules | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| Singleplayer rules | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| Socket | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| GUI | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| CLI | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| Multiple games | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| Resilience to disconnection | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Tick.png" width="50" heigth="50">
| Persistence | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Cross.png" width="50" heigth="50">
| Parameter Editor | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Cross.png" width="50" heigth="50">
| Local game | <img src="https://github.com/MirkoGenoni/ResourceToGithub/blob/master/Icon/Cross.png" width="50" heigth="50">

## How to run
In the [derivable](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/tree/main/deliverables) folder, inside the jar folder there will be two .jar files that needs to be downloaded:

- MasterOfRenaissance-Server.jar

- MasterOfRenaissance-Client.jar

This files have been created using the **maven assembly plugin** imported inside the pom and created inside Intellij, manually selecting inside the manifest the class that needed to be run:

The method to run this files depends on your operative system:

- [Windows](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/wiki/Windows)
- [Linux](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/wiki/Linux)
- [MacOsX](https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/wiki/Mac-Os-X)

(the game has been fully tested on **mac os x** operative system, its use is suggested as other operative system may cause problem with the CLI)

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

## Javadoc

The javadoc is available [here](https://mirkogenoni.github.io/ResourceToGithub/allclasses-index.html)





