**Tower of Terror plugin layout**

**Comments**
Ride is complex compared to first two attractions designed. Block naming convection is a bit different from standard. See enums for description

**Enums**
- Block 1a [Alpha Lift] (Bottom | Boiler Room | Hallway | Dim): clear (at bottom), restraintsunlocked, restraintsdoorsunlocked, restraintslocked, dispatcha (up), dispatchb (out of lift) 
- Block 1b [Bravo Lift] (Bottom | Boiler Room | Hallway | Dim): clear (at bottom), restraintsunlocked, restraintsdoorsunlocked, dispatcha (up), dispatchb (out of lift)
- Block 2a [Alpha Dim Enter]: clear, occupied, holding
- Block 2b [Bravo Dim Enter]: clear, occupied, holding
- Block 3 [Dim End]: clear, occupied, holding
- Block 4 [Echo Lift] (Bottom | Mid2 | Mid1 | Dim | 4 | 3 | 2 | 1): clear, occupied, holding 
- Block 5 [Unload Enter]: clear, occupied, holding
- Block 6 [Unload]: ckear, occupied, restraints, dispatch, holding
- Block 99 [Return]: clear, occupied, holding

  Notes 
  - Some blocks have to get looked at for feasibility of holding state
  - Dim is Dem in game
  - Block 99 is new notation for last block in the ride
  
**Classes**
TOTBlocks
- Management for blocks and ride functions
- Declaration for ride start blocks
- Spawn/Killing trains
- Open/Close doors and restraints
- Dispatch function and other block functions
TOTCommandManager
- Mangages all ingame commands
- EX: changeblockstate
TOTManger
- Brain of TOT
TOTTimers
- All timers for ride
- aLoad
- bLoad
- aLift
- bLift
- unLoad
- eLift (expansion not first release)

  **Dependencies**
  - DHSRideCommand
  - Main

