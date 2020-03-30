## Board
- 8x8 board
- divided to three lanes, each lane has a width of 2 cells. As graph show
- Type: non-accessible, Nexus(hero, monster), Plain cell(regular, bush - increase 10% dextrity, koulou - increase 10% of strength, cave - increase 10% of agility)
- Only influence hero's attribute, because monster do not have these attribute.
- In every cell there can be either no one, one hero, one monster or one hero and one monster but never two heroesor two monsters

## Quest
- Win condition: reach oppponents' nexus. When hero and monster reach nexus same round, hero win
- Turn game. All monsters make move, then all heros make move. Once all heroes have acted, the round is over and we move to a new round.

## Hero Moves
- Regain 10% hp and mana at the beginning of every round.
- Battle Operations: attack, spell, change weapon/armor, use potion
- Move: same as quest. Cannot pass behind a monster without killing it.
- Teleport. To any valid grid.(Not same lane, Not after a monster).
- Buy/Sell all items at nexus. Do not count as an action.
- Go back to nexus by using b. Hero get to nexus, effective at next round.

## Monster Moves
- Either attack a hero(If a hero is in range. Diagnal is also a neighbour)
- Or move forward towards hero's nexus. Cannot pass behind a hero without killing it.

## Initialization
- Choose 3 heros, one hero per lane, inital position is in nexus.
- In same round of hero's born, three equally leveled monster spawn in each lane

## Spawns of hero and monster
- Spawn 3 new monster in nexus every 8 rounds. One for each lane
- Hero respawn on nexus of its lane on next round when die.


## Question
- reward for killing a monster, penalty for dying.
- respwan rule. 50% hp or something?