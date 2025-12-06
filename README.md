## ABOUT

This is a zombie projectmade by Joseph Carter, Aaron Raycone, Bennett, and Gabe. 
The project is supposed to be a simple "fight-fest" style simulation of a zombie apocalypse. 
The events that can happen per supposed day are based off neighbor interaction between entities. 

The project is made in Java with the ini4j and lanterna libraries.

## Entities
- Humans: humans can engage in battles with zombies, move around, and reproduce with other close humans.
- Zombies: zombies can engage in battles with humans and may infect on victory. 

## Configuration parameters
Located in the "src/main/java/resources" directory, the following parameters are available for configuration:

### World
- world_width and world_height: since the project has a grid-like representation, the width and height of the grid can be considered.
- human_spawn_rate and zombie_spawn_rate: during generation and visiting, the chance of a human spawning is integral for unique results.
- super_creature_spawn_rate: sometimes, humans and zombies may spawn as "super", which gives them a higher chance of winning battles.
- visitor_rate: when each current creature is being processed, a visitor may join the world!
- max_creatures: the amount of creatures allowed in the world
- world_norm_constant: the normalization constant is the maximum random value used for calculating chance in world

### Human

- human_reproduce_rate: to humans fall in love and try to reproduce! Maybe human civilization can be saved if this rate was set high
- human_battle_rate: when zombies and humans engage in a fight, this dictates the chances of the human winning
- human_move_rate: this dictates the chances of a human moving to a different tile
- human_norm_constant: just like world_norm_constnat, this parameter is the maximum random value for calculating chance for human events.
- human_super_offset: if a human spawns as super, this configurable offset allows you to decide how super they really are.
- human_engage_rate: will they engage in a fight or not? this rate decides the frequencies of battles actually happening


### Zombie
- zombie_infect_rate: this parameter decides how frequent infections happen with respect to zombie_norm_constant
- zombie_death_ticker: to prevent zombies from overflowing the board, they have a death ticker decrements every time they make a turn. If its 0, the zombie dies
- zombie_battle_rate:  when zombies and humans engage in a fight, this dictates the chances of the zombie winning
- zombie_move_rate: this configurable parameter is responsible for how frequent zombies move
- zombie_norm_constant: this is responsible for calculating chance by serving as the maximum random value for zombie events.
- zombie_super_offset: if zombies are super, then this adds to their battle rate.
- zombie_engage_rate: decides the rate of battle engagement for zombies
