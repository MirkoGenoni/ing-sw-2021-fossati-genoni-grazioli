# Software engineering project 2020-2021

[<img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/GameIcon.png" width="600">](https://craniointernational.com/products/masters-of-renaissance/)

## Presentation

The development of this project is part of the Software Engineering course at Politecnico di Milano.

The game realized is the digital version of the physical board game [Master Of Renaissance](https://craniointernational.com/products/masters-of-renaissance/) made by Cranio Creations.

The game has been realized by the group 27 formed by:

- [Stefano Fossati](https://github.com/stefanofossati)
- [Mirko Genoni](https://github.com/MirkoGenoni)
- [Davide Grazioli](https://github.com/davidegrazioli)

**The graphics are taken from the original game**
## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Multiplayer rules | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| Singleplayer rules | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| Socket | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| GUI | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| CLI | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| Multiple games | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| Resilience to disconnection | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Tick.png" width="30" heigth="30">
| Persistence | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Cross.png" width="30" heigth="30">
| Parameter Editor | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Cross.png" width="30" heigth="30">
| Local game | <img src="https://github.com/MirkoGenoni/ing-sw-2021-fossati-genoni-grazioli/blob/main/githubResources/Cross.png" width="30" heigth="30">

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

[**Intellij**](https://www.jetbrains.com/idea/) - IDE

[**MagicDraw**](https://www.3ds.com/products-services/catia/products/no-magic/magicdraw/) - UML/Sequence Diagram


## JavaDoc

The JavaDoc is available [here](https://mirkogenoni.github.io/ing-sw-2021-fossati-genoni-grazioli/index.html).

## License

The project is develop in collaboration with [Politecnico di Milano](https://www.polimi.it/) and [Cranio Creations](https://craniointernational.com/).
