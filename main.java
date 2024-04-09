import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class main {
    public static void main(String[] args) {
        int startingDist = 60;//starting distance value
        int distance = startingDist;//setting starting distance
        boolean turn; //true=eva, false=renn

        //setting default values for beginning of turn reset
        int evaAC = 20;
        int rennAC = 20;
        
        Scanner input = new Scanner(System.in);
        System.out.println("How many rounds would you like to run?");
        int matches = input.nextInt();

        int rennWins = 0;
        int evaWins = 0;

        for(int i = 0; i < matches; i++){
            combatantObject Eva = new combatantObject(112, evaAC, 0, 2, 4, 5, 1, 1, true);//declaring eva
            Eva.isRennOrEva();
            combatantObject Renn = new combatantObject(76, rennAC, 5, 0, 3, 0, 2, 3, false);//declaring renn

            Random rand = new Random();//initializing rng
            int rennInit = (rand.nextInt(19) + 1) + Renn.getDex() + (rand.nextInt(7) + 1);//rolling initiative for renn
            int evaInit = (rand.nextInt(19) + 1) + Eva.getDex() + (rand.nextInt(7) + 1);//rolling initiative for eva

            if(rennInit > evaInit){//if renn rolls higher, she goes first
                turn = false;
                System.out.println("Renn wins initiative.");
            }
            else{//if eva rolls higher, she goes first
                turn = true;
                System.out.println("Eva wins initiative.");
            }

            int round = 0;
            
            while (Eva.getHp() > 0 && Renn.getHp() > 0){
                //reset
                Eva.setAC(evaAC);
                Eva.setAction(true);
                Eva.setBonusAction(true);
                Eva.setReaction(true);
                Eva.setMovement(30);
                Renn.setAC(rennAC);
                Renn.setAction(true);
                Renn.setBonusAction(true);
                Renn.setReaction(true);
                Renn.setMovement(30);

                if(turn) {
                    System.out.println("Eva's Turn");
                    Eva.isRennOrEva();
                    round++;
                    //Eva behavior
                    if (round == 1){
                        //first round, eva goes invis and moves away 30 ft, her max movement
                        invis(Eva);
                        Eva.setMovement(0);
                        distance = 30;
                    }
                    else{
                        if(distance == 5 && !Renn.isReaction()){
                            Eva.setMovement(5);
                            distance = 30;
                            if(Eva.getSlots() - Eva.getL1Slots() > 0){
                                if(Eva.getL4Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 4);
                                }
                                else if(Eva.getL3Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 3);
                                }
                                else if(Eva.getL2Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 2);
                                }
                            }
                        }
                        else if(distance == 5 && Renn.isReaction()){
                            if(Eva.getL4Slots() > 0 && !Eva.isSom()){
                                shadowOfMoil(Eva);
                                Eva.setConcentration(true);
                            }
                            else if(Eva.getSlots() > 0){
                                if (Eva.getL4Slots() > 0) {
                                    inflictWounds(Eva, Renn, 0, 4);
                                }
                                else if (Eva.getL3Slots() > 0){
                                    inflictWounds(Eva, Renn, 0, 3);
                                }
                                else if (Eva.getL2Slots() > 0){
                                    inflictWounds(Eva, Renn, 0, 2);
                                }
                                else {
                                    inflictWounds(Eva, Renn, 0, 1);
                                }
                            }
                        }
                        else if(distance == 5 && !Renn.isReaction()){
                            Eva.setMovement(5);
                            distance = 30;
                            if(Eva.getSlots() - Eva.getL1Slots() > 0){
                                if(Eva.getL4Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 4);
                                }
                                else if(Eva.getL3Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 3);
                                }
                                else if(Eva.getL2Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 2);
                                }
                            }
                            else if (Eva.getL1Slots() > 0){
                                Eva.setMovement(0);
                                distance = 35;
                                rayOfSickness(Eva, Renn, 0, 1);
                            }
                            else{
                                Eva.setMovement(0);
                                distance = 35;
                                tollTheDead(Eva, Renn);
                            }
                        }
                        else if(distance <= 30){
                            if(Eva.getSlots() - Eva.getL1Slots() > 0){
                                if(Eva.getL4Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 4);
                                }
                                else if(Eva.getL3Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 3);
                                }
                                else if(Eva.getL2Slots() > 0){
                                    bloodShot(Eva, Renn, 0, 2);
                                }
                            }
                            else if (Eva.getL1Slots() > 0){
                                rayOfSickness(Eva, Renn, 0, 1);
                            }
                            else{
                                tollTheDead(Eva, Renn);
                            }
                            Eva.setMovement(0);
                            distance += 30;
                        }
                        else if(distance <= 60){
                            if(Eva.getSlots() > 0){
                                if(Eva.getL4Slots() > 0){
                                    rayOfSickness(Eva, Renn, 0, 4);
                                }
                                else if(Eva.getL3Slots() > 0){
                                    rayOfSickness(Eva, Renn, 0, 3);
                                }
                                else if(Eva.getL2Slots() > 0){
                                    rayOfSickness(Eva, Renn, 0, 2);
                                }
                                else{
                                    rayOfSickness(Eva, Renn, 0, 1);
                                }
                            }
                            else{
                                tollTheDead(Eva, Renn);
                            }
                            Eva.setMovement(0);
                            distance += 30;
                        }
                        else{
                            if(Eva.getSlots() - Eva.getL1Slots() - Eva.getL2Slots() > 0){
                                if(Eva.getL3Slots() > 0){
                                    hungerOfHadar(Eva, Renn, 3);
                                }
                                else{
                                    hungerOfHadar(Eva, Renn, 4);
                                }
                                Eva.setMovement(0);
                                distance += 30;
                            }
                            else{
                                if (distance > 60 + Eva.getMovement()){
                                    Eva.setMovement(0);
                                    distance += 30;
                                }
                                else{
                                    Eva.setMovement(Eva.getMovement() - (distance - 60));
                                    if (Eva.getSlots() > 0){
                                        if(Eva.getL2Slots() > 0){
                                            rayOfSickness(Eva, Renn, 0, 2);
                                        }
                                        else{
                                            rayOfSickness(Eva, Renn, 0, 1);
                                        }
                                    }
                                    else{
                                        tollTheDead(Eva, Renn);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    //Renn behavior
                    if(Eva.isInvis()){
                        seeInvis(Renn, Eva);
                        if(distance > 65){
                            Renn.setMovement(0);
                            distance -= 30;
                            feyStep(Renn, Eva, distance);
                            
                        }
                        else if(distance <= 65 && distance > 35){
                            Renn.setMovement(0);
                            distance -= 30;
                            feyStep(Renn, Eva, distance);
                            rennReaction(Renn, Eva, 0, 0);
                        }
                        else{
                            Renn.setMovement(Renn.getMovement() - (distance - 5));
                            distance = 5;
                        }
                    }
                    else{
                        if(Eva.isSom() && Renn.getHp() < 10){
                            if(distance == 5){
                                Renn.setMovement(Renn.getMovement() - 10);
                                distance += 10;
                                rennRangedAttack(Renn, Eva, -1);
                            }
                            else{
                                rennRangedAttack(Renn, Eva, -1);
                            }
                        }
                        else if(Eva.isSom()){
                            if(distance > 65){
                                Renn.setMovement(0);
                                distance -= 30;
                                feyStep(Renn, Eva, distance);
                                
                            }
                            else if(distance <= 65 && distance > 35){
                                Renn.setMovement(0);
                                distance -= 30;
                                feyStep(Renn, Eva, distance);
                                rennReaction(Renn, Eva, 0, 0);
                            }
                            else{
                                Renn.setMovement(Renn.getMovement() - (distance - 5));
                                distance = 5;
                                anticipateWeakness(Renn);

                                if(Renn.getSpellPoints() >= 3){
                                    rennReaction(Renn, Eva, 3, 0);
                                }
                                else if(Renn.getSpellPoints() == 2){
                                    rennReaction(Renn, Eva, 2, 0);
                                }
                                else{
                                    rennReaction(Renn, Eva, 0, 0);
                                }

                                if(Renn.getSpellPoints() >= 6){
                                    rennAttack(Renn, Eva, 3, 0);
                                }
                                else if(Renn.getSpellPoints() == 4){
                                    rennAttack(Renn, Eva, 2, 0);
                                }
                                else{
                                    rennAttack(Renn, Eva, 0, 0);
                                }
                            }
                        }
                        else{
                            if(Renn.getHp() < 40){
                                layOnHands(Renn);
                                if(distance > 65){
                                    Renn.setMovement(0);
                                    distance -= 30;
                                    feyStep(Renn, Eva, distance);
                                    
                                }
                                else if(distance <= 65 && distance > 35){
                                    Renn.setMovement(0);
                                    distance -= 30;
                                    feyStep(Renn, Eva, distance);
                                    rennReaction(Renn, Eva, 0, 0);
                                }
                                else{
                                    Renn.setMovement(Renn.getMovement() - (distance - 5));
                                    distance = 5;
                                }
                            }
                            if(distance > 65){
                                Renn.setMovement(0);
                                distance -= 30;
                                feyStep(Renn, Eva, distance);
                                if(distance > 30){
                                    rennRangedAttack(Renn, Eva, -1);
                                }
                                else{
                                    rennRangedAttack(Renn, Eva, 0);
                                }
                                
                            }
                            else if(distance <= 65 && distance > 35){
                                Renn.setMovement(0);
                                distance -= 30;
                                feyStep(Renn, Eva, distance);
                                rennReaction(Renn, Eva, 0, 0);

                            }
                            else if(Eva.getHp() < 30){
                                Renn.setMovement(Renn.getMovement() - (distance - 5));
                                distance = 5;
                                if(Renn.getSpellPoints() >= 3){
                                    rennReaction(Renn, Eva, 3, 0);
                                }
                                else{
                                    rennReaction(Renn, Eva, 0, 0);
                                }

                                if(Renn.getSpellPoints() >= 3){
                                    rennAttack(Renn, Eva, 3, 0);
                                }
                                else{
                                    rennAttack(Renn, Eva, 0, 0);
                                }

                                if(Renn.getSpellPoints() >= 3){
                                    rennBonusAttack(Renn, Eva, 3, 0);
                                }
                                else{
                                    rennBonusAttack(Renn, Eva, 0, 0);
                                }

                            }
                            else{
                                Renn.setMovement(Renn.getMovement() - (distance - 5));
                                distance = 5;
                                rennAttack(Renn, Eva, 0, 0);
                                rennBonusAttack(Renn, Eva, 0, 0);
                            }
                        }
                    }
                }
                if(Renn.getHp() <= 0 && Eva.getHp() > 0){
                    System.out.println("Eva Wins");
                    evaWins++;
                }
                if(Eva.getHp() <= 0 && Renn.getHp() > 0){
                    System.out.println("Renn Wins");
                    rennWins++;
                }
                turn = !turn;
            }
        }
        System.out.println("Renn won " + rennWins + " times.");
        System.out.println("Eva won " + evaWins + " times.");
    }

    //basic attack function
    public static boolean Attack(int hitMod, 
                        int dmgMod, 
                        int src1Dice, 
                        int src1Die, 
                        int src2Dice, 
                        int src2Die, 
                        combatantObject attacker, 
                        combatantObject target, 
                        int adv,
                        int slotUsed){
        attacker.isRennOrEva();
        Random rand = new Random();//initializing rng
        int rand1 = (rand.nextInt(19) + 1);
        int rand2 = (rand.nextInt(19) + 1);//two rolls, in case advantage or disadvantage is involved
        int toHit = 0;

        switch (adv) {
            case -1://disadvantage
                if(rand1 < rand2){
                    toHit = hitMod + rand1;
                }
                else{
                    toHit = hitMod + rand2;
                }
                break;
            case 0://normal attack
                toHit = hitMod + rand1;
                break;
            case 1://advantage
                if(rand1 > rand2){
                    toHit = hitMod + rand1;
                }
                else{
                    toHit = hitMod + rand2;
                }
                break;
            default:
                System.out.println("Something broke in Attack");
                break;
        }
        
        if(toHit > target.getAC()){
            if(toHit < target.getAC() + 5 && (target.getSlots() > 0 || target.getSpellPoints() >= 2)){
                Shield(target);
                System.out.println(target.toString());
                return false;
            }
            else if(toHit - hitMod == 20 && !target.isRennOrEva()){
                damageCalc(dmgMod, 2 * src1Dice, src1Die, 2 * src2Dice, src2Die, attacker, target, slotUsed);
                return true;
            }
        }
        else{
            damageCalc(dmgMod, src1Dice, src1Die, src2Dice, src2Die, attacker, target, slotUsed);
            return true;
        }
        return false;
    }

    //method for calculating damage of a successful attack
    public static int damageCalc(int dmgMod, 
                            int src1Dice, 
                            int src1Die, 
                            int src2Dice, 
                            int src2Die, 
                            combatantObject attacker, 
                            combatantObject target,
                            int slotUsed) {
        Random rand = new Random();//initializing rng

        int dmgTotal = dmgMod;//number for storing all the damage
        for (int i = 0; i < src1Dice; i++){
            int dmgDie = rand.nextInt(src1Die - 1) + 1;
            if (dmgDie <= (src1Die / 2) - 1 && attacker.getSanBur() > 0 && slotUsed > 0){//if damage number rolled is less than half - 1 of the damage die, apply sanguine burst if character has it and the attack uses a spell slot
                dmgDie = rand.nextInt(src1Die - 1) + 1;//reroll the damage die
                attacker.setSanBur(attacker.getSanBur() - 1);//subtract one use of sanguine burst
                attacker.setHp(attacker.getHp() - slotUsed);//subtract the level of slot used from health
            }
            dmgTotal += dmgDie;//rolling damage for damage source 1 and adding it to the total
        }
        for (int i = 0; i < src2Dice; i++){
            int dmgDie = rand.nextInt(src1Die - 1) + 1;
            if (dmgDie <= (src1Die / 2) - 1 && attacker.getSanBur() > 0 && slotUsed > 0){//if damage number rolled is less than half - 1 of the damage die, apply sanguine burst if character has it and the attack uses a spell slot
                dmgDie = rand.nextInt(src1Die - 1) + 1;//reroll the damage die
                attacker.setSanBur(attacker.getSanBur() - 1);//subtract one use of sanguine burst
                attacker.setHp(attacker.getHp() - slotUsed);//subtract the level of slot used from health
            }
            if (target.isSom()) {//unclean way of doing this, but if target has shadow of moil active they will have resistance to what should be radiant damage. Assumed that only Eva will have this active and Renn can only deal radiant damage as secondary damage
                dmgTotal += dmgDie / 2;//add only half damage due to radiant resistance
            }
            else {
                dmgTotal += dmgDie;//rolling damage for damage source 2 and adding it to the total
            }
            
        }
        target.setHp(target.getHp() - dmgTotal);//subtracting damage total from target's health
        if(target.isConcentration()){//if target takes damage while having concentration
            int conSave = 0;//initialize con save
            if(target.isRennOrEva()){//eva makes con save normally
                conSave = rand.nextInt(19) + 1 + target.getCon();
            }
            else{//renn makes con save with con mod and cha mod bc aura of protection
                conSave = rand.nextInt(19) + 1 + target.getCon() + target.getCha();
            }

            if(dmgTotal > 20){//if damage is higher than 20, DC is half of damage taken
                if(conSave < dmgTotal/2){
                    target.loseConcentration();//lose concentration if conSave is lower than DC
                }
            }
            else{//otherwise, DC is 10
                if(conSave < 10){
                    target.loseConcentration();//lose concentration if conSave is lower than DC
                }
            }
        }

        if(target.isRennOrEva() && dmgTotal >= 20){
            attacker.setHp(attacker.getHp() - dmgTotal);
            target.setMutSuff(0);
        }

        System.out.println("The attack deals " + dmgTotal + " damage");//printing damage for debugging purposes
        return dmgTotal;//return dmgTotal
    }

    public static void Shield(combatantObject caster) {
        if (caster.isRennOrEva()){
            if (caster.getL1Slots() > 0) {//use lv 1 slots if available
                caster.setL1Slots(caster.getL1Slots() - 1);
            }
            else if (caster.getL2Slots() > 0) {//use lv 2 slots if available
                caster.setL2Slots(caster.getL2Slots() - 1);
            }
            else if (caster.getL3Slots() > 0) {//use lv 3 slots if available
                caster.setL3Slots(caster.getL3Slots() - 1);
            }
            else if (caster.getL4Slots() > 0) {//use lv 4 slots if available
                caster.setL4Slots(caster.getL4Slots() - 1);
            }

            caster.setAC(caster.getAC() + 5);//increase AC by 5 (remember to reset this at beginning of next round)
            caster.setReaction(false);//set reaction to false (remember to reset this at beginning of next round)
            System.out.println("Eva uses shield");//debug message
        }
        else{
            caster.setSpellPoints(caster.getSpellPoints() - 2);//subtract 2 spell points
            caster.setAC(caster.getAC() + 5);//increase AC by 5 (remember to reset this at beginning of next round)
            caster.setReaction(false);//set reaction to false (remember to reset this at beginning of next round)
            System.out.println("Renn uses shield");//debug message
        }
    }

    //if Eva makes an opp attack
    public static void evaReaction(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        System.out.println("Eva uses her reaction to attack");
        attacker.setReaction(false);//use reaction
        if(Attack(5, 2, 1, 4, 0, 0, attacker, target, adv, slotUsed)){//call attack method
            System.out.println("Eva hits Renn with her reaction");
        }
        else{
            System.out.println("Eva misses");
        }
    }

    //Sapping Sting
    public static void sappingSting(combatantObject attacker, combatantObject target){
        System.out.println("Eva uses Sapping Sting");
        attacker.setAction(false);//use action
        Random rand = new Random();//initializing rng
        if(rand.nextInt(19) + 1 + target.getCon() + target.getCha() < attacker.getSaveDC()){//if Renn fails a con save. formula is d20 + con mod + aura of protection
            int dmgTotal = target.getHp() - (rand.nextInt(3) + rand.nextInt(3) + 2);
            System.out.println("Renn fails the save, takes " + dmgTotal + " damage, and is knocked prone");
            target.setHp(dmgTotal);//subtract hp from target
            target.setMovement(target.getMovement() - 15);//target gets knocked prone, so subtract half movement
        }
    }

    //Blood Shot
    public static void bloodShot(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        System.out.println("Eva uses Blood Shot");
        attacker.setAction(false);//use action
        switch (slotUsed){//detrmine which level spell slot was used and subtract it
            case 2://if lv 2 slot used
                System.out.println("At 2nd Level");
                attacker.setL2Slots(attacker.getL2Slots() - 1);
                break;
            case 3://if lv 3 slot used
                System.out.println("At 3rd Level");
                attacker.setL3Slots(attacker.getL3Slots() - 1);
                break;
            case 4://if lv 4 slot used
                System.out.println("At 4th Level");
                attacker.setL4Slots(attacker.getL4Slots() - 1);
                break;
            default://this should never be triggered
                System.out.println("Something broke in bloodShot");
        }
        if(Attack(9, 0, 2 + (slotUsed - 2), 10, 2, 8, attacker, target, adv, slotUsed)){//trigger attack method. adds damage dice based on slot level used
            System.out.println("Eva hits Renn with Blood Shot");
        }
        else{
            System.out.println("Eva misses");
        }
    }

    //Inflict Wounds
    public static void inflictWounds(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        System.out.println("Eva uses Inflict Wounds");
        attacker.setAction(false);//use action
        switch (slotUsed){//determine which level spell slot was used and subtract it
            case 1://if lv 1 slot used
                System.out.println("At 1st Level");
                attacker.setL1Slots(attacker.getL1Slots() - 1);
                break;
            case 2://if lv 2 slot used
                System.out.println("At 2nd Level");
                attacker.setL2Slots(attacker.getL2Slots() - 1);
                break;
            case 3://if lv 3 slot used
                System.out.println("At 3rd Level");
                attacker.setL3Slots(attacker.getL3Slots() - 1);
                break;
            case 4://if lv 4 slot used
                System.out.println("At 4th Level");
                attacker.setL4Slots(attacker.getL4Slots() - 1);
                break;
            default://this should never be triggered
                System.out.println("Something broke in inflictWounds");
        }
        if(Attack(9, 0, 3 + (slotUsed - 1), 10, 0, 0, attacker, target, adv, slotUsed)){//trigger attack method. adds damage dice based on slot level used
            System.out.println("Eva hits Renn with Inflict Wounds");
        }
        else{
            System.out.println("Eva misses");
        }
    }

    public static void rayOfSickness(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        System.out.println("Eva uses Ray of Sickness");
        attacker.setAction(false);//use action
        switch (slotUsed) {//deduct spell slot
            case 1://if lv 1 slot used
                System.out.println("At 1st Level");
                attacker.setL1Slots(attacker.getL1Slots() - 1);
                break;
            case 2://if lv 2 slot used
                System.out.println("At 2nd Level");
                attacker.setL2Slots(attacker.getL2Slots() - 1);
                break;
            case 3://if lv 3 slot used
                System.out.println("At 3rd Level");
                attacker.setL3Slots(attacker.getL3Slots() - 1);
                break;
            case 4://if lv 4 slot used
                System.out.println("At 4th Level");
                attacker.setL4Slots(attacker.getL4Slots() - 1);
                break;
        }
        if (Attack(9, 0, 2 + (slotUsed - 1), 8, 0, 0, attacker, target, adv, slotUsed)){//if attack hits. also, attack made with slot usage accounted for
            System.out.println("Eva hits Renn with Ray of Sickness");
            Random rand = new Random();//initializing RNG
            if(rand.nextInt(19) + 1 + target.getCon() + target.getCha() < attacker.getSaveDC()){//rolling d20 + con mod + cha mod for aura of protection, if roll fails then it triggers the effect
                target.setPoisoned(true);//target is poisoned if save fails
                System.out.println("Renn fails the save and is poisoned");
            }
            else{
                System.out.println("Renn passes the Ray of Sickness save");
            }
        }
        else{
            System.out.println("Eva misses");
        }
    }

    public static void hungerOfHadar(combatantObject attacker, combatantObject target, int slotUsed){
        System.out.println("Eva uses Hunger of Hadar");
        attacker.setAction(false);//use action

        Random rand = new Random();//initialize RNG
        int dmgTotal = 0;//initialize dmgTotal
        dmgTotal += rand.nextInt(5) + rand.nextInt(5) + 2;//dmgTotal = 2d6

        target.setHp(target.getHp() - dmgTotal);//subtract dmgTotal from target's hp
        System.out.println("It deals " + dmgTotal + " damage");
        target.setHadar(true);//account for target now being in the hunger of hadar
    }

    public static void tollTheDead(combatantObject attacker, combatantObject target){
        System.out.println("Eva uses Toll the Dead");
        attacker.setAction(false);//use action
        Random rand = new Random();//initializing rng
        if(rand.nextInt(19) + 1 + target.getWis() + target.getCha() < attacker.getSaveDC()){//if Renn fails a con save. formula is d20 + con mod + aura of protection
            int dmgTotal = rand.nextInt(11) + rand.nextInt(11) + 2;
            target.setHp(target.getHp() - dmgTotal);//subtract hp from target
            System.out.println("Renn fails the save and takes " + dmgTotal + " damage");
        }
        else{
            System.out.println("Renn passes the save");
        }
    }

    public static void shadowOfMoil(combatantObject caster){
        System.out.println("Eva casts Shadow of Moil");//debug message
        caster.setAction(false);//use action
        caster.setL4Slots(caster.getL4Slots() - 1);//use level 4 slot
        caster.setSom(true);//set shadow of moil status to true
        
        Random rand = new Random();
        int selfDmg = rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + 3;
        caster.setHp(caster.getHp() - selfDmg);
    }

    public static void somDamage(combatantObject target){
        Random rand = new Random();//initializing rng
        int dmgTotal = rand.nextInt(7) + rand.nextInt(7) + 2;//roll 2d8 damage
        target.setHp(target.getHp() - dmgTotal);//subtract dmgTotal from hp
        System.out.println("Renn takes " + dmgTotal + " damage from Shadow of Moil");//debug message
    }

    public static void invis(combatantObject caster){
        System.out.println("Eva casts Invisibility");
        caster.setAction(false);
        caster.setInvis(true);
    }

    public static void rennReaction(combatantObject attacker, combatantObject target, int pointsUsed, int adv){
        System.out.println("Renn uses her reaction to attack");
        attacker.setReaction(false);//use reaction

        if(Attack(9, 11, 1, 6, pointsUsed, 8, attacker, target, adv, 0)) {//make attack roll. pointsUsed determines how many d8s will be used in the damage roll
            System.out.println("Renn hits and uses " + pointsUsed + " Spell Points");
            attacker.setSpellPoints(attacker.getSpellPoints() - pointsUsed);//deduct spell points used if any, but only if attack lands

            if(target.isSom()){
                somDamage(attacker);//deal damage to renn if shadow of moil is active
            }
        }
        else{
            System.out.println("Renn misses");
        }
    }

    public static void rennAttack(combatantObject attacker, combatantObject target, int pointsUsed, int adv){
        System.out.println("Renn uses her action to attack");
        attacker.setAction(false);//use action

        for(int i = 0; i < 2; i++){//do it twice
            if(Attack(9, 11, 1, 6, pointsUsed, 8, attacker, target, adv, 0)) {//make attack roll. pointsUsed determines how many d8s will be used in the damage roll
                System.out.println("Renn hits and uses " + pointsUsed + " Spell Points");
                attacker.setSpellPoints(attacker.getSpellPoints() - pointsUsed);//deduct spell points used if any, but only if attack lands

                if(target.isSom()){
                    somDamage(attacker);//deal damage to renn if shadow of moil is active
                }
            }
            else{
                System.out.println("Renn misses");
            }
        }

        
    }

    public static void rennRangedAttack(combatantObject attacker, combatantObject target, int adv){
        System.out.println("Renn uses her javelins to attack");
        attacker.setAction(false);
        attacker.setJavelins(attacker.getJavelins() - 2);

        for(int i = 0; i < 2; i++){//do it twice
            if(Attack(8, 10, 1, 6, 0, 0, attacker, target, adv, 0)){//make attack roll.
                System.out.println("Renn hits with a javelin");
            }
            else{
                System.out.println("Renn misses");
            }
        }
    }

    public static void rennBonusAttack(combatantObject attacker, combatantObject target, int pointsUsed, int adv){
        System.out.println("Renn uses her bonus action to attack");
        attacker.setBonusAction(false);//use bonus action

        if(Attack(9, 11, 1, 4, pointsUsed, 8, attacker, target, adv, 0)) {//make attack roll. pointsUsed determines how many d8s will be used in the damage roll
            System.out.println("Renn hits and uses " + pointsUsed + " Spell Points");
            attacker.setSpellPoints(attacker.getSpellPoints() - pointsUsed);//deduct spell points used if any, but only if attack lands

            if(target.isSom()){
                somDamage(attacker);//deal damage to renn if shadow of moil is active
            }
        }
        else{
            System.out.println("Renn misses");
        }
    }

    public static void layOnHands(combatantObject caster){
        System.out.println("Renn uses Lay on Hands");
        caster.setAction(false);//use action
        if(caster.getHp() <= 36){//if renn's hp is less than or equal to 36
            caster.setHp(caster.getHp() + 40);//use all 40 lay on hands points
            caster.setLayOnHands(0);//set layonhands to 0
        }
        else{
            caster.setLayOnHands(0);;//otherwise, set layonhands to 0 and renn's hp to 76
            caster.setHp(76);
        }
    }

    public static int feyStep(combatantObject caster, combatantObject target, int distance){
        System.out.println("Renn uses Feystep");
        caster.setBonusAction(false);//use bonus action
        caster.setFeyStep(caster.getFeyStep() - 1);//use feystep

        if (distance < 30){//if distance is less than 30, aka max range of feystep
            distance = 5;//Renn moves 5 ft away from Eva
            target.setHp(target.getHp() - 3);//Eva takes 3 damage
            return distance;//return distance
        }
        else {
            distance -= 30;//otherwise, move max distance
            return distance;//return distance
        }
    }

    public static int anticipateWeakness(combatantObject caster){
        System.out.println("Renn uses Anticipate Weakness");
        caster.setBonusAction(false);//use bonus action
        caster.setSpellPoints(caster.getSpellPoints() - 2);//subtract 2 spell points for a lv 1 spell
        
        return 1;//return 1 for advantage
    }

    public static void seeInvis(combatantObject caster, combatantObject target){
        System.out.println("Renn uses See Invisibility");
        caster.setAction(false);
        caster.setSpellPoints(caster.getSpellPoints() - 3);

        target.setInvis(false);
    }
}