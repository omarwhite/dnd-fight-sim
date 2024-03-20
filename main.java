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
        combatantObject Renn = new combatantObject(76, rennAC, 5, 0, 3, 0, -1, 3, false);//declaring renn

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
            }
            else {
                //Renn behavior
            }
            turn = !turn;
        }
    }

    //basic attack function
    public void Attack(int hitMod, int dmgMod, int src1Dice, int src1Die, int src2Dice, int src2Die, combatantObject target, int adv){
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
            case 0://normal attack
                toHit = hitMod + rand1;
            case 1://advantage
                if(rand1 > rand2){
                    toHit = hitMod + rand1;
                }
                else{
                    toHit = hitMod + rand2;
                }
        }
        

        if(toHit > target.getAC() && toHit - hitMod != 20){ //if attack roll hits before shield and is not a crit
            if(toHit < target.getAC() + 5 && target.isReaction()){ //if shield would block the attack and the target has a reaction
                if(target.isRennOrEva() && target.getSlots() > 0){//if target is eva and has spell slots

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

                    target.setAC(target.getAC() + 5);
                    target.setReaction(false);
                }

                else if (!target.isRennOrEva() && target.getSpellPoints() >= 2 && target.isReaction()) {//if target is renn and has reaction and enough spell points for shield
                    target.setSpellPoints(target.getSpellPoints() - 2);//subtract 2 spell points
                    target.setAC(target.getAC() + 5);//increase AC by 5 (remember to reset this at beginning of next round)
                    target.setReaction(false);//set reaction to false (remember to reset this at beginning of next round)
                }
            }
            else{
                int dmgTotal = dmgMod;//number for storing all the damage
                for (int i = 0; i < src1Dice; i++){
                    dmgTotal += rand.nextInt(src1Die - 1) + 1;//rolling damage for damage source 1 and adding it to the total
                }
                for (int i = 0; i < src2Dice; i++){
                    dmgTotal += rand.nextInt(src2Die - 1) + 1;//rolling damage for damage source 2 and adding it to the total
                }
                target.setHp(target.getHp() - dmgTotal);
            }
        }
    }
}