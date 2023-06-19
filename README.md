# Bowling Project
Ten Pin Bowling score tracker

# Design

This app will keep track of scores in a game of ten pin bowling for a single player.

Each time the state of the game changes a new score board will be rendered to standard out.
The event that changes the state is a player performing a bowl, this is indicated by entering 
an integer via the command line interface. The integer will represent the amount of pins
that were knocked over during the bowl. This is the only input required to the app.

When a bowl is entered via the Player.addBowl() method the player will internally keep
track of the game frames and running score totals for each.

After Player.addBowl() is called the internal state is updated and so the player itself
can be passed to a renderer to be renderer and will display the current state of the game.
