# Quest II structrue

## Main idea
Quest II mainly differs in the respective of rules. So most modification is related to Quest class. Beside Quest class, we also need to modify some ohter class due to some extra settings.

Changes in each class are list below:
- Borad
    - different way to print board. Need to show the type of entry.

- BoardEntry
    - better to have a reference of monster and hero in it, so that printBoard can be implement easily.
    - more types now
    - some grid influence hero's attribute
    - Need to consider something like: two hero wants to enter same entry.

- Quest
    - Now have a clear ending condition
        - Reach enemy's nexus
        - if reach in same round, consider it as hero win
    - Different initialization for board
    - Different act flow.
    - move W/A/S/D has restrction.
    - Teleport, 'b'
    - Show buy/sell only in nexus. Print different message instrction.
    - respawn for hero and monster.

- QuestCombat
    - there are no seperate combat now, this part will be combined into Quest
    - attack is a kind of move now
    - There could have two monster in range, need a extra target choose process
    - Gain something when hero kill a monster. Cooresponding to previous combat win
    - Monster attacks when possible, otherwise move forward.

- GameCharacter
    - record position
    - getTarget() return a list of possible target base on "this" type.
- Hero
    - need a function to handle respawn
- Monster
    - a static funciton handle respawn new monster

- Other Optimization
    - change all println to calling OutputTools.java
    - Optimize the printList 
## Frame

All game realated rules
- QuestII
    - Member
        - board. the board we play game on
        - List&lt;Hero&gt; a list of hero chosen by hero
        - List&lt;Monster&gt; a list of monster
    - Method
        - start()
        - isEnd()
        - end()
        - run()
        - makeMove(MainCharacter h) (Maybe rename to GameCharacter)


A board to play on.
- RectangualrRPGGameBoard
    - Member
        - Type: Inaccessible, Nexus, Regular, Bush, Koulou, Cave
        - Hero hero. reference to hero in this grid. Set null if has no hero
        - Monster m. reference to monster in this grid. Set null if hero has no hero
    - Method
        - init board.
            - define nexus, inaccessible grid, plain grid
            - designate redanom type to plain grid
        - printBoard.
            - print board as shown on requirement.
                - hero and monster's postion
                - grid's type
        - getter and setters
- 

Hero and Monster most parts remains same
- Hero extends
    - Memeber
        - Attribute
        - type. type of hero, influence the upgrad
        - Position. hero's position on board
        - equipedArmor, equipedWeapon
        - List&lt;Weapon&gt;, List&lt;Armor&gt;, List&lt;Spell&gt;, List&lt;Potion&gt;
    - Method
        - Move(Board, source, destination)
### Quest class
- Initalize game
    - set up board
    - set up hero: let user choose
- call run
    - get instruction
    - call relative function move, attack, buy item, use item



&nbsp;
&lt;
&gt;