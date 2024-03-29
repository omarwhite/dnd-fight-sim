import java.util.Random;
import java.io.*;

public class main {
    public static void main(String[] args) {
        int startingDist = 60;//starting distance value
        int distance = startingDist;//setting starting distance
        boolean turn; //true=eva, false=renn

        //setting default values for beginning of turn reset
        int evaAC = 15;
        int rennAC = 20;
        
        combatantObject Eva = new combatantObject(84, evaAC, 0, 2, 4, 5, 1, 1, true);//declaring eva
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

        while (Eva.getHp() > 0 && Renn.getHp() > 0){
            if(turn) {
                //Eva behavior
                //reset
            }
            else {
                //Renn behavior
                //reset
            }
            turn = !turn;
        }
    }

    //basic attack function
    public boolean Attack(int hitMod, 
                        int dmgMod, 
                        int src1Dice, 
                        int src1Die, 
                        int src2Dice, 
                        int src2Die, 
                        combatantObject attacker, 
                        combatantObject target, 
                        int adv,
                        int slotUsed){
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
        

        if (toHit > target.getAC() && toHit - hitMod != 20){ //if attack roll hits before shield and is not a crit
            if(toHit < target.getAC() + 5 && target.isReaction()){ //if shield would block the attack and the target has a reaction
                if(target.getSlots() > 0 || target.getSpellPoints() >= 2){//if target has either the slots or the points for shield
                    Shield(target.isRennOrEva(), target);         
                    return false;           
                }
            }
            else{
                damageCalc(dmgMod, src1Dice, src1Die, src2Dice, src2Die, attacker, target, slotUsed);
                return true;
            }
        }
        else if (toHit > target.getAC() && toHit - hitMod == 20 && target.isRennOrEva()) {
            damageCalc(dmgMod, 2 * src1Dice, src1Die, 2 * src2Dice, src2Die, attacker, target, slotUsed);
            return true;
        }
        return false;
    }

    //method for calculating damage of a successful attack
    public int damageCalc(int dmgMod, 
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
        return dmgTotal;
    }

    public void Shield(boolean evaOrRenn, combatantObject target) {
        if (evaOrRenn){
            if (target.getL1Slots() > 0) {//use lv 1 slots if available
                target.setL1Slots(target.getL1Slots() - 1);
            }
            else if (target.getL2Slots() > 0) {//use lv 2 slots if available
                target.setL2Slots(target.getL2Slots() - 1);
            }
            else if (target.getL3Slots() > 0) {//use lv 3 slots if available
                target.setL3Slots(target.getL3Slots() - 1);
            }
            else if (target.getL4Slots() > 0) {//use lv 4 slots if available
                target.setL4Slots(target.getL4Slots() - 1);
            }

            target.setAC(target.getAC() + 5);//increase AC by 5 (remember to reset this at beginning of next round)
            target.setReaction(false);//set reaction to false (remember to reset this at beginning of next round)
        }
        else{
            target.setSpellPoints(target.getSpellPoints() - 2);//subtract 2 spell points
            target.setAC(target.getAC() + 5);//increase AC by 5 (remember to reset this at beginning of next round)
            target.setReaction(false);//set reaction to false (remember to reset this at beginning of next round)
        }
    }

    //if Eva makes an opp attack
    public void evaReaction(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        attacker.setReaction(false);//use reaction
        Attack(5, 2, 1, 4, 0, 0, attacker, target, adv, slotUsed);//call attack method
    }

    //Sapping Sting
    public void sappingSting(combatantObject attacker, combatantObject target){
        attacker.setAction(false);//use action
        Random rand = new Random();//initializing rng
        if(rand.nextInt(19) + 1 + target.getCon() + target.getCha() < attacker.getSaveDC()){//if Renn fails a con save. formula is d20 + con mod + aura of protection
            target.setHp(target.getHp() - (rand.nextInt(3) + rand.nextInt(3) + 2));//subtract hp from target
            target.setMovement(target.getMovement() - 15);//target gets knocked prone, so subtract half movement
        }
    }

    //Blood Shot
    public void bloodShot(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        attacker.setAction(false);//use action
        switch (slotUsed){//detrmine which level spell slot was used and subtract it
            case 2://if lv 2 slot used
                attacker.setL2Slots(attacker.getL2Slots() - 1);
                break;
            case 3://if lv 3 slot used
                attacker.setL3Slots(attacker.getL3Slots() - 1);
                break;
            case 4://if lv 4 slot used
                attacker.setL4Slots(attacker.getL4Slots() - 1);
                break;
            default://this should never be triggered
                System.out.println("Something broke in bloodShot");
        }
        Attack(9, 0, 2 + (slotUsed - 2), 10, 2, 8, attacker, target, adv, slotUsed);//trigger attack method. adds damage dice based on slot level used
    }

    //Inflict Wounds
    public void inflictWounds(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        attacker.setAction(false);//use action
        switch (slotUsed){//determine which level spell slot was used and subtract it
            case 1://if lv 1 slot used
                attacker.setL1Slots(attacker.getL1Slots() - 1);
                break;
            case 2://if lv 2 slot used
                attacker.setL2Slots(attacker.getL2Slots() - 1);
                break;
            case 3://if lv 3 slot used
                attacker.setL3Slots(attacker.getL3Slots() - 1);
                break;
            case 4://if lv 4 slot used
                attacker.setL4Slots(attacker.getL4Slots() - 1);
                break;
            default://this should never be triggered
                System.out.println("Something broke in inflictWounds");
        }
        Attack(9, 0, 3 + (slotUsed - 1), 10, 0, 0, attacker, target, adv, slotUsed);//trigger attack method. adds damage dice based on slot level used
    }

    public void rayOfSickness(combatantObject attacker, combatantObject target, int adv, int slotUsed){
        attacker.setAction(false);//use action
        switch (slotUsed) {//deduct spell slot
            case 1:
                attacker.setL1Slots(attacker.getL1Slots() - 1);
                break;
            case 2:
                attacker.setL2Slots(attacker.getL2Slots() - 1);
                break;
            case 3:
                attacker.setL3Slots(attacker.getL3Slots() - 1);
                break;
            case 4:
                attacker.setL4Slots(attacker.getL4Slots() - 1);
                break;
        }
        if (Attack(9, 0, 2 + (slotUsed - 1), 8, 0, 0, attacker, target, adv, slotUsed)){//if attack hits. also, attack made with slot usage accounted for
            Random rand = new Random();//initializing RNG
            if(rand.nextInt(19) + 1 + target.getCon() + target.getCha() < attacker.getSaveDC()){//rolling d20 + con mod + cha mod for aura of protection, if roll fails then it triggers the effect
                target.setPoisoned(true);//target is poisoned if save fails
            }
        }
    }

    public void hungerOfHadar(combatantObject attacker, combatantObject target, int slotUsed){
        attacker.setAction(false);//use action

        Random rand = new Random();//initialize RNG
        int dmgTotal = 0;//initialize dmgTotal
        dmgTotal += rand.nextInt(5) + rand.nextInt(5) + 2;//dmgTotal = 2d6

        target.setHp(target.getHp() - dmgTotal);//subtract dmgTotal from target's hp
        target.setHadar(true);//account for target now being in the hunger of hadar
    }

    public void tollTheDead(combatantObject attacker, combatantObject target){
        attacker.setAction(false);//use action
        Random rand = new Random();//initializing rng
        if(rand.nextInt(19) + 1 + target.getWis() + target.getCha() < attacker.getSaveDC()){//if Renn fails a con save. formula is d20 + con mod + aura of protection
            target.setHp(target.getHp() - (rand.nextInt(11) + rand.nextInt(11) + 2));//subtract hp from target
        }
    }

    public void rennReaction(combatantObject attacker, combatantObject target, int pointsUsed, int adv){
        attacker.setReaction(false);//use reaction

        if(Attack(9, 11, 1, 6, pointsUsed, 8, attacker, target, adv, 0)) {//make attack roll. pointsUsed determines how many d8s will be used in the damage roll
            attacker.setSpellPoints(attacker.getSpellPoints() - pointsUsed);//deduct spell points used if any, but only if attack lands
        }
    }

    public void rennAttack(combatantObject attacker, combatantObject target, int pointsUsed, int adv){
        attacker.setAction(false);//use action

        for(int i = 0; i < 2; i++){//do it twice
            if(Attack(9, 11, 1, 6, pointsUsed, 8, attacker, target, adv, 0)) {//make attack roll. pointsUsed determines how many d8s will be used in the damage roll
                attacker.setSpellPoints(attacker.getSpellPoints() - pointsUsed);//deduct spell points used if any, but only if attack lands
            }
        }
    }

    public void rennRangedAttack(combatantObject attacker, combatantObject target, int adv){
        attacker.setAction(false);
        attacker.setJavelins(attacker.getJavelins() - 2);

        for(int i = 0; i < 2; i++){//do it twice
            Attack(8, 10, 1, 6, 0, 0, attacker, target, adv, 0);//make attack roll.
        }
    }

    public void rennBonusAttack(combatantObject attacker, combatantObject target, int pointsUsed, int adv){
        attacker.setBonusAction(false);//use bonus action

        if(Attack(9, 11, 1, 4, pointsUsed, 8, attacker, target, adv, 0)) {//make attack roll. pointsUsed determines how many d8s will be used in the damage roll
            attacker.setSpellPoints(attacker.getSpellPoints() - pointsUsed);//deduct spell points used if any, but only if attack lands
        }
    }

    public void layOnHands(combatantObject caster){
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

    public int feyStep(combatantObject caster, combatantObject target, int distance){
        caster.setBonusAction(false);//use bonus action
        caster.setFeyStep(caster.getFeyStep() - 1);//use feystep

        if (distance < 30){
            distance = 5;
            target.setHp(target.getHp() - 3);
            return distance;
        }
        else {
            distance -= 30;
            return distance;
        }
    }
}