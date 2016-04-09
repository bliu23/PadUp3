# PadUp3
This is an app I am currently (04/08/16) making for fun that could possibly help the community of Puzzle and Dragons players.

In short, the app allows players looking to play multiplayer mode to communicate with each other. Users can host a room with a room ID as well as monsters they are using, and other users can see this information and use the room ID to join them in the actual PAD app.

Functionalities as of 04/08/16:
Create A Room is fully up and running. Web scrapes PADx for a list of multiplayer dungeons, and allows players to choose the category and dungeon they wish to run. There is an activity to input the room ID as well as an AutoCompleteTextView that lists all monsters in the game, also scraped from PADx. Currently it's packed up as a JSON object to be sent to the backend. Need to implement an HTTP POST request.

Join A Room is not quite ready since it's waiting for the server side to be built. But it has the basic skeleton. All it needs now is to make an HTTP GET request to get dungeon information, and another GET request to retrieve monsters+monster images (png) based off of the room selected.

The server side is not built. Trying to work on it now though.
