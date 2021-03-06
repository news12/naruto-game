package com.gutotech.narutogame.data.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.firebase.database.DatabaseReference;
import com.gutotech.narutogame.R;
import com.gutotech.narutogame.data.firebase.FirebaseConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Character extends BaseObservable implements Serializable {
    private String playerId;
    private String nick;
    private Ninja ninja;
    private int profile;
    private Village village;
    private Classe classe;
    private int level;
    private int exp;
    private int expUpar;
    private long ryous;
    private Graduation graduation;
    private Attributes attributes;
    private List<Jutsu> jutsus;
    private Bag bag;
    private CombatOverview combatOverview;
    private ResumeOfMissions resumeOfMissions;
    private ExtrasInformation extrasInformation;
    private String team;

    private boolean online;
    private String lastLogin;
    private int numberOfDaysPlayed;
    private int lastDayPlayed;

    private String title;
    private List<String> titles;

    private int score;
    private int npcDailyCombat;
    private int mapPosition;

    private boolean mission;
    private boolean battle;
    public String battleId;

    public Character() {
    }

    public Character(String playerId) {
        this.playerId = playerId;
        level = 1;
        ninja = Ninja.NARUTO;
        profile = 1;
        village = Village.FOLHA;
        graduation = Graduation.ESTUDANTE;
        setClasse(Classe.TAI);
        updateFormulas();
        full();
        ryous = 500;
        score = 1000;
        expUpar = 1200;
        combatOverview = new CombatOverview();
        resumeOfMissions = new ResumeOfMissions();
        extrasInformation = new ExtrasInformation();
        mapPosition = -1;

        bag = new Bag(new Ramen("nissin", R.string.ninja_snack,
                R.string.ninja_snack_description,
                null, 25, 5, 100));

        jutsus = new ArrayList<>(Arrays.asList(
                new Jutsu(JutsuInfo.DEFESA_MAO.toString(), 0, 5, 0,
                        10, 3),
                new Jutsu(JutsuInfo.DEFESA_ACROBATICA.toString(), 0, 8, 0,
                        15, 4),
                new Jutsu(JutsuInfo.SOCO.toString(), 0, 5, 1,
                        8, 1),
                new Jutsu(JutsuInfo.CHUTE.toString(), 8, 0, 0,
                        11, 2)
        ));
    }

    public void salvar() {
        DatabaseReference characterRef = FirebaseConfig.getDatabase()
                .child("characters")
                .child(nick);

        characterRef.setValue(this);
    }

    public String profilePath() {
        return ninja.getId() + "/" + profile;
    }

    public Formulas getFormulas() {
        return getAttributes().getFormulas();
    }

    public void updateFormulas() {
        getAttributes().updateFormulas(getClasse(), getLevel());
    }

    public void full() {
        getAttributes().getFormulas().full();
    }

    public void incrementExp(int expEarned) {
        int newTotalExp = getExp() + expEarned;

        while (newTotalExp >= getExpUpar()) {
            newTotalExp = newTotalExp - getExpUpar();

            setExpUpar(getExpUpar() + 1200);
            setLevel(getLevel() + 1);

            updateFormulas();
            full();
        }

        setExp(newTotalExp);
    }

    public void incrementScore(int earnedScore) {
        setScore(getScore() + earnedScore);
    }

    public void decrementScore(int lostScore) {
        setScore(getScore() - lostScore);
    }

    public void addRyous(long earnedRyous) {
        setRyous(getRyous() + earnedRyous);
    }

    public void subRyous(long ryousSpent) {
        setRyous(getRyous() - ryousSpent);
    }

    public ExtrasInformation getExtrasInformation() {
        return extrasInformation;
    }

    public void setExtrasInformation(ExtrasInformation extrasInformation) {
        this.extrasInformation = extrasInformation;
    }

    public Ninja getNinja() {
        return ninja;
    }

    public void setNinja(Ninja ninja) {
        this.ninja = ninja;
    }

    @Bindable
    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
        notifyPropertyChanged(BR.village);
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Bindable
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
        notifyPropertyChanged(BR.nick);
    }

    @Bindable
    public long getRyous() {
        return ryous;
    }

    public void setRyous(long ryous) {
        this.ryous = ryous;
        notifyPropertyChanged(BR.ryous);
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
        setAttributes(new Attributes(classe));
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Bindable
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        notifyPropertyChanged(BR.level);
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    @Bindable
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyPropertyChanged(BR.score);
    }

    public CombatOverview getCombatOverview() {
        return combatOverview;
    }

    public void setCombatOverview(CombatOverview combatOverview) {
        this.combatOverview = combatOverview;
    }

    public ResumeOfMissions getResumeOfMissions() {
        return resumeOfMissions;
    }

    public void setResumeOfMissions(ResumeOfMissions resumeOfMissions) {
        this.resumeOfMissions = resumeOfMissions;
    }

    @Bindable
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        notifyPropertyChanged(BR.exp);
    }

    @Bindable
    public int getExpUpar() {
        return expUpar;
    }

    public void setExpUpar(int expUpar) {
        this.expUpar = expUpar;
        notifyPropertyChanged(BR.expUpar);
    }

    public int getNpcDailyCombat() {
        return npcDailyCombat;
    }

    public void setNpcDailyCombat(int npcDailyCombat) {
        this.npcDailyCombat = npcDailyCombat;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Bindable
    public int getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(int mapPosition) {
        this.mapPosition = mapPosition;
//        notifyPropertyChanged(BR.mapPosition);
    }

    public Graduation getGraduation() {
        return graduation;
    }

    public void setGraduation(Graduation graduation) {
        this.graduation = graduation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<Jutsu> getJutsus() {
        return jutsus;
    }

    public void setJutsus(List<Jutsu> jutsus) {
        this.jutsus = jutsus;
    }

    public int getLastDayPlayed() {
        return lastDayPlayed;
    }

    public void setLastDayPlayed(int lastDayPlayed) {
        this.lastDayPlayed = lastDayPlayed;
    }

    @Bindable
    public int getNumberOfDaysPlayed() {
        return numberOfDaysPlayed;
    }

    public void setNumberOfDaysPlayed(int numberOfDaysPlayed) {
        this.numberOfDaysPlayed = numberOfDaysPlayed;
    }

    @Bindable
    public boolean isMission() {
        return mission;
    }

    public void setMission(boolean mission) {
        this.mission = mission;
        notifyPropertyChanged(BR.mission);
    }

    @Bindable
    public boolean isBattle() {
        return battle;
    }

    public void setBattle(boolean battle) {
        this.battle = battle;
        notifyPropertyChanged(BR.battle);
    }
}
