# Video Poker
*The project must include a README.md file, with (at least) the following contents. (Optionally, one or more of these can be in separate Markdown files, reachable via links in README.md.)*

### Aims
*Your aims or motivations for selecting the given topic for your app. That is, why did you choose to develop this particular app, and why is this (at least potentially) a useful or interesting app?* <br />
As a fan of casino video poker games, I've developed an interest in exploring the statistics and mathematics of the game. This is a mobile version of these games, however, this project is intended as more of a simulation of these games than a true "game" as I intend to offer the user plenty of opportunities to cheat. My stretch goals with this project are to allow for further experimentation, particularly an estimated value calculator that would find the optimal play strategy on a given hand, and that could eventually be used to find statistics based on a large number of optimal plays with a given paytable. The converter rules are potentially very flexible and could also allow for more customization in poker hand rules, and potentially even user customization. These stretch goals would allow for a very versatile app that would allow for an interesting exploration of the game. 

### Current State
*A description of the current state of completion/readiness of your app. This should include a “hit list” of deficiencies: any unimplemented/incomplete elements, and known bugs, that would have to be implemented or corrected for a usable prototype (i.e. one that could be given to a skilled user for testing and feedback), ordered with the most urgent items first.*
Currently the user is able to select a game (only three available at the moment) and play it with a given credit value. Each rule sequence has been tested in JUnit to ensure they are properly parsed. Paytable and Help display necessary content. The user can modify payouts from the Paytable activity and revert them to their defaults from the external CSV file. Unaware of any serious bugs. Incomplete elements, excluding stretch goals, are:
* User needs to be able to change a card in a hand on a long press.

### Versions
*A list of Android API versions and hardware (including emulators) on which you’ve tested the submitted version of your app, the minimum Android API required, and any other hardware/software/orientation restrictions that you’re aware of. (This includes restrictions on device language, orientation, etc.)*
Throughout development this project has been tested on:
* a physical Galaxy device on API level 26
* a Nexus device emulator on API level 28
* a Nexus device emulator on API level 24
* a physical device on API level 22
* a physical Amazon Fire device on API level ??
* a Galaxy Nexus emulator on API level 25
* a Galaxy Nexus emulator device on API level 26

The preferred test environments are currently the Galaxy Nexus API 26 emulator and the Nexus API 28 emulator. 

Past the launcher screen, the device only runs in landscape view. This is an intentional design choice. 

There is a significant compatibility issue on devices prior to API level 26 in which cards toggled as held are not changing color tint. Cards are held, but the user has no way of knowing which cards they have already selected. 

Image resource scaling varies depending on the device. 

### Third-party libraries
*A list of the 3rd-party libraries (i.e. anything beyond the Android standard and support libraries) used by your app.*
   * [Apache Commons CSV](https://commons.apache.org/proper/commons-csv/)
   * [Card images courtesy of American Contract Bridge League](http://acbl.mybigcommerce.com/52-playing-cards/)
   
### External services
*A list of the external services (including Google services such as Sign In, Calendar, Maps, etc.) consumed by your app.*
The only external services this app consumes are CSV files to read into a local persistence library. The files are stored on the device so the user can reset defaults if desired.

### Cosmetic stretch goals
*A list of aesthetic/cosmetic (not functional) improvements that you think would improve your app. This list should be ordered, with those that would give the most improvement (in your opinion) listed first.*
* Default button backgrounds are ugly.
* Timer to show the win accumulated rather than immediately changed. 
* Sound effects on win.
* Animation to show cards flip over individually on deal and draw.
* Borders on the payouts table.

### Functional stretch goals
*A list of functional stretch goals. These should be sorted either with those that would add the most utility at the top, or with those that would be the simplest to implement at the top.*
* An estimated value calculator to run on a given hand, in which the computer brute forces the possible combinations of hands and holds the cards that offers the highest EV. This may require serious optimization.
* A running simulation where the computer plays multiple games at the estimated value with the given paytable, returning odds for user analysis. Optimization is an even bigger concern.
* More rule sequence converter flexibility - OR operator, GREATER THAN operator
* Even more rule sequence converter flexibility - wildcard rank option, more suit variety?, card adjacent?
* With a proper UI, the user could even invent their own video poker variants with customizable hands.

### Wireframes and user stories
[User Stories](docs/UserStories.md) <br />
[Wireframe](docs/VidPokerWireframe.pdf) 

### ERD and DDL
[ERD](docs/ERD.pdf) <br />
[DDL](docs/DDL.sql)

### Javadocs
*A link to generated Javadoc HTML pages in your repository (see below).*
**NOT READY**

### Licenses
*Links to all applicable licenses.*
   * [LICENSE](LICENSE)
   * [Apache Commons License](licenses/ApacheSoftwareLicense.txt)
   
### Build instructions
[Build Instructions](docs/BuildInstructions.md)

### Usage instructions
[Usage Instructions](docs/UserInstructions.md)

(Note that the above should be formatted effectively with HTML or Markdown—i.e. using headings, bullet or numbered lists, links, etc. Please review the appearance of this document in GitHub Pages, to ensure that your content appears as intended.)
