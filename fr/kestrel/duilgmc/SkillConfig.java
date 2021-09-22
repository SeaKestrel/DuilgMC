package fr.kestrel.duilgmc;

import fr.kestrel.duilgmc.skills.Skill;
import fr.kestrel.duilgmc.skills.SkillProfile;
import fr.kestrel.duilgmc.skills.SkillType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SkillConfig {

    public static File file;
    public static FileConfiguration config;

    public SkillConfig(){
        file = new File("plugins/Maxicraft", "skills.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void setup(){
        config.options().copyDefaults(false);
        if(!file.exists()){
            config.createSection("players");
        } else {
            if(config.getConfigurationSection("players") == null ) config.createSection("players");
            for(String id : config.getConfigurationSection("players").getKeys(false)){
                SkillProfile profile = new SkillProfile(UUID.fromString(id));
                for(String s : config.getConfigurationSection("players."+id.toString()+".skills").getKeys(false)){
                    if(s.equals("alchemist")) continue;
                    double exp = config.getDouble("players."+id.toString()+".skills."+s+".exp");
                    int level = config.getInt("players."+id.toString()+".skills."+s+".level");
                    Skill skill = new Skill(SkillType.valueOf(s.toUpperCase()), level, exp);
                    profile.addSkill(skill);
                }
                Main.addSkillProfile(UUID.fromString(id), profile);
            }
        }
    }

    public void init(){
        if(!file.exists()){
            config.createSection("players");
            try{
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            for(String id : config.getConfigurationSection("players").getKeys(false)){
                SkillProfile profile = new SkillProfile(UUID.fromString(id));
                for(String s : config.getConfigurationSection("players."+id.toString()+".skills").getKeys(false)){
                    double exp = config.getDouble("players."+id.toString()+".skills."+s+".exp");
                    int level = config.getInt("players."+id.toString()+".skills."+s+".level");
                    Skill skill = new Skill(SkillType.valueOf(s.toUpperCase()), level, exp);
                    profile.addSkill(skill);
                }
                Main.addSkillProfile(UUID.fromString(id), profile);
            }
        }
    }

    public void save(){
        if(file.exists()){
            try {
                for(SkillProfile profile : Main.getSkillProfiles().values()){
                    UUID id = profile.getUuid();
                    for (Skill skill : profile.getSkills().values()) {
                        config.set("players." + id.toString() + ".skills." + skill.getType().getId() + ".exp", skill.getExp());
                        config.set("players." + id.toString() + ".skills." + skill.getType().getId() + ".level", skill.getLevel());
                    }
                }
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.init();
        }
    }

}