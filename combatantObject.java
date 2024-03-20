public class combatantObject {
    private int hp;
    private int AC;
    private int str;
    private int dex;
    private int con;
    private int intl;
    private int wis;
    private int cha;
    private boolean action;
    private boolean bonusAction;
    private boolean reaction;
    private int spellPoints;
    private int l1Slots;
    private int l2Slots;
    private int l3Slots;
    private int l4Slots;
    private boolean concentration;
    private int saveDC;
    private int movement = 30;
    private boolean canSee = true;
    private boolean rennOrEva; // true=eva, false=renn
    private int feyStep = 0;
    private int layOnHands = 0;
    private int javelins = 0;
    private int mutSuff = 0;
    private int sanBur = 0;

    // Constructor
    public combatantObject( int hp,
                            int AC,
                            int str,
                            int dex,
                            int con,
                            int intl,
                            int wis,
                            int cha,
                            boolean rennOrEva){
        this.hp = hp;
        this.AC = AC;
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.intl = intl;
        this.wis = wis;
        this.cha = cha;

        this.concentration = false;

        if (rennOrEva){
            this.l1Slots = 4;
            this.l2Slots = 3;
            this.l3Slots = 3;
            this.l4Slots = 2;
            this.mutSuff = 1;
            this.sanBur = 5;
        }
        else{
            this.spellPoints = 17;
            this.feyStep = 3;
            this.layOnHands = 1;
            this.javelins = 5;
        }
    }
    // Getters
    public int getHp() {
        return hp;
    }

    public int getAC() {
        return AC;
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getCon() {
        return con;
    }

    public int getIntl() {
        return intl;
    }

    public int getWis() {
        return wis;
    }

    public int getCha() {
        return cha;
    }

    public boolean isAction() {
        return action;
    }

    public boolean isBonusAction() {
        return bonusAction;
    }

    public boolean isReaction() {
        return reaction;
    }

    public int getSpellPoints() {
        return spellPoints;
    }

    public int getL1Slots() {
        return l1Slots;
    }

    public int getL2Slots() {
        return l2Slots;
    }

    public int getL3Slots() {
        return l3Slots;
    }

    public int getL4Slots() {
        return l4Slots;
    }

    public int getSlots() {
        return l1Slots + l2Slots + l3Slots + l4Slots;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public int getSaveDC() {
        return saveDC;
    }

    public int getMovement() {
        return movement;
    }

    public boolean isCanSee() {
        return canSee;
    }

    public boolean isRennOrEva() {
        return rennOrEva;
    }

    public int getFeyStep() {
        return feyStep;
    }

    public int getLayOnHands() {
        return layOnHands;
    }

    public int getJavelins() {
        return javelins;
    }

    public int getMutSuff() {
        return mutSuff;
    }

    public int getSanBur() {
        return sanBur;
    }

    // Setters
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public void setIntl(int intl) {
        this.intl = intl;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public void setCha(int cha) {
        this.cha = cha;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setBonusAction(boolean bonusAction) {
        this.bonusAction = bonusAction;
    }

    public void setReaction(boolean reaction) {
        this.reaction = reaction;
    }

    public void setSpellPoints(int spellPoints) {
        this.spellPoints = spellPoints;
    }

    public void setL1Slots(int l1Slots) {
        this.l1Slots = l1Slots;
    }

    public void setL2Slots(int l2Slots) {
        this.l2Slots = l2Slots;
    }

    public void setL3Slots(int l3Slots) {
        this.l3Slots = l3Slots;
    }

    public void setL4Slots(int l4Slots) {
        this.l4Slots = l4Slots;
    }

    public void setConcentration(boolean concentration) {
        this.concentration = concentration;
    }

    public void setSaveDC(int saveDC) {
        this.saveDC = saveDC;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void setCanSee(boolean canSee) {
        this.canSee = canSee;
    }

    public void setRennOrEva(boolean rennOrEva) {
        this.rennOrEva = rennOrEva;
    }

    public void setFeyStep(int feyStep) {
        this.feyStep = feyStep;
    }

    public void setLayOnHands(int layOnHands) {
        this.layOnHands = layOnHands;
    }

    public void setJavelins(int javelins) {
        this.javelins = javelins;
    }

    public void setMutSuff(int mutSuff) {
        this.mutSuff = mutSuff;
    }

    public void setSanBur(int sanBur) {
        this.sanBur = sanBur;
    }
}