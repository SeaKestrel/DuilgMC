package fr.kestrel.duilgmc.skills;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class SkillProfile {

    private UUID uuid;
    private Map<SkillType ,Skill> skills;

    public SkillProfile(UUID uuid){
        this.uuid = uuid;
        this.skills = new HashMap<>();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void addSkill(Skill skill){
        if(skills.containsKey(skill.getType())){
            return;
        }
        skills.put(skill.getType(), skill);
    }

    public void updateSkill(Skill skill){
        if(skills.containsKey(skill.getType())){
            skills.replace(skill.getType(), skill);
        } else {
            skills.put(skill.getType(), skill);
        }
    }

    public Map<SkillType, Skill> getSkills() {
        return skills;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Skill getSkill(SkillType type){
        for (Skill skill : skills.values()){
            if(skill.getType() == type){
                return skill;
            }
        }
        return null;
    }
}
