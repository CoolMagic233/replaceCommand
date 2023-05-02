package me.coolmagic233.replacecommand;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;
import java.util.List;

public class ReplaceCommand extends PluginBase implements Listener {
    private List<String> commands = new ArrayList<>();
    @Override
    public void onEnable(){
        saveDefaultConfig();
        loadCommands();
        getServer().getPluginManager().registerEvents(this,this);
        getLogger().info("插件启动!");
    }
    public void loadCommands(){
        commands = getConfig().getStringList("player_replace");
        for (String command : commands) {
            getServer().getCommandMap().register("", new Command(command.split("\\|")[0]) {
                @Override
                public boolean execute(CommandSender commandSender, String s, String[] strings) {
                    getServer().dispatchCommand(commandSender,command.split("\\|")[1]);
                    return false;
                }
            });
        }
    }

}