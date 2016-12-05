# NinjaQuest
Ninja Quest is a game in the vein of Ninja Gaiden for SNES

Built using libgdx as a framework

Currently its just a shell of what it will become, now using an entity type of system, using SAT collision detection.

Logic Flow : 
 - Start: NinjaQuest.java -- This file sets up the core resources that get used across the project and once the setup is complete will set the screen to the desired screen; ie: intro screen -> menu screen -> game screen
 -  Screens:
  - Loading screen: will be initially displayed with modest animation while assets are loading
  - Menu screens: This will allow adjustment of variables to change things like sound, etc.
  - Game Screens: Loads the map, starts the player, and takes control of all the actual game action
 - Cameras: Mainly just the custom orthocamera which will be used to ensure that the player stays relatively central on the screen
 - Constants: A central location to store all the overall game constants which apply across the project and can be accessed from anywhere
 - Entities: The main game logic is performed by updating all updatable entities created
   - Base Entity: A base entity is ultimately just a shape with a position that will be tied to a sprite
   - Walking Char: A walking character is the expansion of the base entity that will be added to any entity that walks on the ground, the distinct element is that it will track the walking path and whether or not the entity is on the ground.... this will be used to control the specific walking direction for the character.  Todo: add the capacity that walking up hills will be slower than walking on flat terrain which will be slower than walking down hill.
 - World: The world is the object container for the map, all the entities, items, player, and ensure that everything gets updated each frame
  - Terrain: A base entity that blocks other entities from entering
  - Moveable terrain: A terrain entity whose position can be updated possibly even shape, we'll see if that's doable
  - Ladder: When the player is colliding with the ladder, can enter a climbing state, while in the climbing state the player can climb through terrain if the ladder is over.  This will need to be done either through the use of flags to determine if the player should go through the ladder terrain, or, by having the terrain split but have the ground line cross the gap so that there's no confusion.  Will need to determine which approach is easiest and less likely to cause grief.
      
      There's more to add...
    - Seeing how easily I can integrate the TweenEngine into the controls of the player.