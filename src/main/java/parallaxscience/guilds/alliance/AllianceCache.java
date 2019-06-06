package parallaxscience.guilds.alliance;

import parallaxscience.guilds.guild.Guild;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllianceCache {

    private final static String fileName = "world/Guilds_AllianceCache.dat";

    private static HashMap<String, Alliance> alliances;

    @SuppressWarnings("unchecked")
    public static void initialize()
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            alliances = (HashMap<String, Alliance>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        }
        catch(Exception e)
        {
            alliances = new HashMap<>();
        }
    }

    public static void createAlliance(String alliance, String guildName)
    {
        alliances.put(alliance, new Alliance(guildName));
    }

    public static void leaveAlliance(Guild guild)
    {
        String guildName = guild.getGuildName();
        Alliance alliance = getAlliance(guildName);
        alliance.removeGuild(guild.getGuildName());
        if(alliance.getGuildCount() == 0) alliances.remove(guildName);
        guild.setAlliance(null);
    }

    public static Alliance getAlliance(String allianceName)
    {
        return alliances.get(allianceName);
    }

    public static List<String> getAllianceList()
    {
        List<String> list = new ArrayList<>();
        for(Map.Entry<String, Alliance> entry : alliances.entrySet())
        {
            list.add(entry.getKey());
        }
        return list;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void save()
    {
        File file = new File(fileName);
        try
        {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(alliances);
            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch(Exception e)
        {
            System.out.println("ERROR: IOException on alliance cache file save");
        }
    }
}
