package fr.kestrel.duilgmc.skills;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Skill {

    private SkillType type;
    private double exp;
    private int level;
    private Map<Integer, Double> exps = new HashMap<>();

    public Skill(SkillType type, int level, double exp) {
        this.type = type;
        this.level = level;
        this.exp = exp;
        exps.put(1, 50.0);
        exps.put(2, 175.0);
        exps.put(3, 375.0);
        exps.put(4, 675.0);
        exps.put(5, 1175.0);
        exps.put(6, 1925.0);
        exps.put(7, 2925.0);
        exps.put(8, 4425.0);
        exps.put(9, 6425.0);
        exps.put(10, 9925.0);
        exps.put(11, 14925.0);
        exps.put(12, 22425.0);
        exps.put(13, 32425.0);
        exps.put(14, 47425.0);
        exps.put(15, 67425.0);
        exps.put(16, 97425.0);
        exps.put(17, 147425.0);
        exps.put(18, 222425.0);
        exps.put(19, 322425.0);
        exps.put(20, 522425.0);
    }
    public SkillType getType() {
        return type;
    }
    public void setType(SkillType type) {
        this.type = type;
    }
    public double getExp() {
        return exp;
    }
    public void setExp(double exp) {
        this.exp = exp;
    }
    public void addExp(double amount){
        this.setExp(this.getExp()+amount);
    }
    public void addExp(double amount, Player p){
        this.setExp(this.getExp()+amount);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.AQUA+"+"+amount+" exp"));
    }
    public double getExpToNextLevel(){
        double bE = getExp();
        double uE = exps.get(getLevel()+1);
        return (uE - bE);
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
