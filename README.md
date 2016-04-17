# PadUp3
This is an app I am currently (04/08/16) making for fun that could possibly help the community of Puzzle and Dragons players.

In short, the app allows players looking to play multiplayer mode to communicate with each other. Users can host a room with a room ID as well as monsters they are using, and other users can see this information and use the room ID to join them in the actual PAD app.

Functionality as of 04/17/16:
This app is about finished. Like before, Create A Room is fully up and running. It web scrapes PADx for a list of multiplayer dungeons, and allows players to choose select by the category and dungeon they wish to run. After selecthing that, there is an activity to input the room ID as well as an AutoCompleteTextView that lists all monsters in the game, also scraped from PADx. The category, dungeon, room ID, and all monster data for the team is saved to a Firebase Database.

Join a Room is also about finished. Currently retrieves data from a Firebase Database, and displays all available categories, as well as dungeons of that category in the following activity. After selecting the dungeon, the Room ID and the team of the person hosting the dungeon is displayed. The monster images are loaded from the web (a png) through an Asynchronous task, which allows the activity to be displayed even if the images aren't fully loaded yet. If not, the activity stalls for several seconds before it can open. Rooms are deleted based off of a TIME_TO_DELETE variable which is currently set to 3.6*10^9 ms. Ideally it'd probably delete every 5-10 minutes or so, but since the app isn't super popular I don't want to repopulate the data to test things each time.

Future work on this app could include some sort of authentication + security to have users log in to be able to use this app. This also means I have no real way of limiting the amount of requests a user could make, so someone could seriously troll and run things. Lastly, there's a few things I messed up on. If you do not select a monster from the AutoCompleteTextView I have not yet implemented any feature to output a dummy image. This should be an easy if-else statement fix. I also have a spot for a requested leader monster in the CreateRoom part of the app, but I didn't include that in the JoinRoom part. 

These things may or may not be implemented later. If I publish the app I definitely will.