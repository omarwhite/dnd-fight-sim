# dnd-fight-sim
Simulating PVP combats between two specific DND characters

Notes:
- Things that must be modeled
	- Distance
		- In melee or in ranged combat
			- This will be an enum
		- Distance will be Numbers
	- Stats
		- 6 stats
		- Saves
		- Ability checks
		- Armor Class
		- Health
		- Bonuses to damage
	- Action economy
		- Action, Bonus Action, Reaction
	- Different kinds of attacks
		- Determine default attacks
			- Write cases where one would deviate
		- Critical hits
		- Smites
		- Conditionals
	- Spell Slots
		- Each level of spell slot will be a resource
	- Initiative
	- Eva's damage rerolls
- Things that will be configured per simulation
	- Preparations
	- Distance at beginning
	- Spells prepared
- Things that will be assumed
	- No tricky terrain
	- No summon undead
	- Renn in Summer mode
	- Daylight
		- Shadow of Moil still imposes disadvantage
- Coding Concepts
	- Combatant Object
		- Health
		- AC
			- Renn
				- 20
			- Eva
				- 15
		- 6 Stat Mods and Saves
			- Str
			- Dex
			- Con
			- Int
			- Wis
			- Cha
		- Ability Checks
			- Do as necessary
		- Action economy
			- Action
			- Bonus action
			- Reaction 
		- Attacks sorted by priority
		- Actions sorted by priority 
		- Concentration
			- Concentration tests happen immediately
				- `if (damage/2) > 10 {`
					  `DC = damage/2`
				  `}`
				  `else {`
					  `DC = 10`
				  `}`
		- Spell slots
			- Renn
				- Spell points - 17
					- 1st Level spell cost - 2
					- 2nd Level spell cost - 3
			- Eva
				- 1st Level - 4
				- 2nd Level - 3
				- 3rd Level - 3
				- 4th Level - 2
		- Spell Save DC
			- Renn
				- 14
			- Eva
				- 17
		- Movement
			- 30 feet for both characters
			- Dash gives extra 30 feet
				- In exchange for an action
			- Feystep gives extra 30 feet
				- In exchange for an action
		- Can see
			- For case of Hunger of Hadar to prevent AI from feystepping out of it
		- Extra resources
			- Renn
				- Feystep
					- 3
				- Channel Divinity
					- Irrelevant, don't model 
				- Lay on Hands
					- 1
				- Javelins
					- 5
			- Eva
				- Mutual Suffering
					- 1
				- Sanguine Burst
					- 5
		- Passive modifiers
			- Just hard code them in
	- Initiative Phase
		- At beginning, both characters roll with bonuses applied
			- Higher roll goes first
	- Turn enum
		- 1 for first in initiative
		- 2 for second in initiative
	- Turn phases
		- Immediate
		- Beginning
		- Middle
		- End
	- Distance
		- Distance between combatants
	- Attack functions
		- Mod to hit
		- Damage dice
			- Damage type
				- Elemental or Non-elemental
					- Number that ranges from 0 to 1
						- 0 is non-elemental
						- 1 is elemental
		- Damage bonus
		- Advantage/Disadvantage
			- Number attached to `Attack Functions`
			- Determined by external factors
			- Number that ranges from -1 to 1
				- 1 is advantage
					- Call the roll twice and take highest
				- 0 is normal
					- Call the roll once
				- -1 is disadvantage
					- Call the roll twice and take lowest
		- Range
			- Distance minimums must be met for melee attacks to work
		- Cost
		- Number of attacks
		- Trigger
		- List
			- Renn
				- `PM` (Polearm Master) `Reaction`
					- `+9`
					- `1d6`
					- `+11`
					- `5 ft`
					- `1 Reaction`
					- `1`
					- When `Distance == 5 ft` after not being so at `beginnng phase`
					- Options for Smite on succesful attack roll
						- If `Lv 1 slot` is expended, `+ 2d8` damage
						- If `Lv 2 slot` is expended, `+ 3d8` damage
				- `Melee Action Attack`
					- `+9`
					- `1d6`
					- `+11`
					- `5 ft`
					- `1 action`
					- `2`
					- At will if `Distance == 5 ft`
					- Options for Smite per successful attack roll
						- If `Lv 1 slot` is expended, `+ 2d8` damage
						- If `Lv 2 slot` is expended, `+ 3d8` damage
				- `Ranged Action Attack`
					- `+8`
					- `1d6`
					- `+11`
					- `120 ft`
						- Apply `Disadvantage` if `Distance > 30 ft`
					- `1 action`
					- `2`
					- At will if `Distance <= 120 ft`
				- `PM Bonus Action`
					- `+9`
					- `1d4`
					- `+11`
					- `5 ft`
					- `1 Bonus Action`
					- `1`
					- At will if `Melee Attack Action` is taken and `Distance == 5 ft`
					- Options for Smite
						- If `Lv 1 slot` is expended, `+ 2d8` damage
						- If `Lv 2 slot` is expended, `+ 3d8` damage
			- Eva
				- `Melee Reaction Attack`
					- `+5`
					- `1d4`
					- `+2`
					- `5 ft`
					- `1 reaction`
					- `1`
					- When `Distance > 5 ft` after being `5 ft` at `Beginning Phase`
				- `Sapping Sting`
					- `Con Save`
					- `2d4`
					- `0`
					- `30 ft`
					- `1 action`
					- `1`
				- Blood Shot
					- `+9`
					- `2d10 Elemental + 2d8 Non-Elemental`
					- `0`
					- `30 ft`
					- `1 action and 1 spell slot Lv 2-4`
					- `1`
					- If upcasted, `+1d10 Elemental` for every level above `2nd Lv`
				- Inflict Wounds
					- `+9`
					- `3d10` 
					- `0`
					- `5 ft`
					- `1 action and 1 spell slot Lv 1-4`
					- If upcasted, `+1d10` for every level above `1st Lv`
				- Ray of Sickness
					- `+9`
					- `2d8`
					- `0`
					- `60 ft`
					- `1 action and 1 spell slot Lv 1-4`
					- If upcasted, `+1d8` for every level above `1st Lv`
					- `Con Save`
						- `Poisoned` upon failure until end of your next turn
							- `Disadvantage` on `attack rolls` and `ability checks`
				- Hunger of Hadar
					- `Dex Save`
					- `2d6 Elemental`
					- `0`
					- `150 ft`
					- `1 action and 1 3rd Lv spell slot`
					- Move will eat an `Action` to represent leaving it
						- If Renn has `Feystep`, she'll use it to close distance on Eva
				- Legion
					- `+9`
					- `3d8`
					- `0`
					- `5 ft`
					- `1 action and 1 3rd Lv spell slot`
	- Bonus array
		- Every roll gets a bonus array
		- 2d array
		- Two kinds of bonuses
			- Permanent
			- Perishable
		- Every element has
			- Bonus number
			- 0-2
				- 0 is Permanent
				- 1 is One-Time
				- 2 is Concentration
	- Action functions
		- Effect on use
		- Cost
		- Trigger time
		- List
			- Renn
				- Lay on Hands
					- Restores missing `HP` up to 40
					- `1 action and 1 Lay on Hands usage`
					- `Middle`
				- Feystep
					- Reduces `distance` up to `30 ft` and deals `3 Elemental Damage` if `distance` reduced to `5 ft`
					- `1 Bonus Action and 1 Feystep`
					- `Middle`
				- ~~Absorb Elements~~
					- ~~Halves `Elemental Damage` and gives `+1d6 Elemental Damage` on next `Melee Attack`~~
					- ~~`1 Reaction and 1 Lv 1 Spell Slot`~~
					- ~~`Immediate`~~
				- Anticipate Weakness
					- Gives `Advantage` to all `Attacks` on this round
					- `1 Bonus Action and 1 Lv 1 Spell Slot`
					- `Middle`
				- See Invisibility
					- Removes `Invisibility`
					- `1 Action and 1 Lv 2 Spell Slot`
					- `Middle`
				- Shield
					- `+5 AC` until `Beginning` of `Next Turn`
					- `1 Reaction and 1 Lv 1 Spell Slot`
					- `Immediate`
			- Eva
				- Shadow of Moil
					- Imposes `Disadvantage` on `Attacks` targeting Eva. Deals `2d8` to enemy if `Attack Roll` succeeds when `Distance <= 10 ft`
					- `1 Action and 1 Lv 4 Spell Slot`
					- `Immediate`
					- Deals `3d10 Damage` to Eva
				- Shield
					- `+5 AC` until `Beginning` of `Next Turn`
					- `1 Reaction and 1 Lv 1 Spell Slot`
					- `Immediate`
				- Invisibility
					- Gives `Invisibility` to Eva
					- `1 Action`
					- `Middle`
				- Sanguine Burst
					- Rerolls a Damage Die for a 1st Level or higher Spell Attack
	- Preparation settings
		- Effect on use
		- Cost
		- Trigger time
		- List
			- Renn
				- ~~Armor of Agathys~~
					- ~~`+10 HP`. If a `Melee Attack` succeeds against Renn while `76/81 HP` (depending on if Aid has been used), attacker takes `10 Elemental Damage`~~
					- ~~`1 Level 2 Spell Slot`~~
					- ~~`Immediate`~~
				- Gift of Alacrity
					- Adds `1d8` to `Initiative Roll`
					- `1 Level 1 Spell Slot`
					- `Immediate`
				- ~~Shield of Faith~~
					- ~~Adds `+2 AC` while `Concentrated`~~
					- ~~`1 Level 1 Spell Slot`~~
					- ~~`Immediate`~~
				- See Invisibility
					- Sets `Invisibility` to Negative
					- `1 Level 2 Spell Slot`
					- `Middle`
				- Bless
					- Gives additional `1d4` to `attack rolls`
					- `Concentration`
					- `1 Level 1 Spell Slot`
					- `Immediate`
			- Eva
				- False Life
					- Gives `1d4 + 4 + 5 per Upcast Level past 1st Level`
					- Free or `1 Level 2 - 4 Spell Slot`
					- `Middle`
	- Amount of trials
		- How many combats we run
	- Optimal Behavior
		- Default case
		- Cases at certain ranges
		- Cases depending on amount of resources 
