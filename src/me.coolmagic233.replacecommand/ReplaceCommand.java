package me.coolmagic233.replacecommand;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.ConfigSection;

import java.util.ArrayList;
import java.util.List;

public class ReplaceCommand extends PluginBase implements Listener {
    private List<String> commands = new ArrayList<>();
    @Override
    public void onEnable(){
        saveDefaultConfig();
        if(ModeCommand.valueOf(getConfig().getString("mode")) == ModeCommand.replace) {
            loadReplaceCommands();
        }
        if(ModeCommand.valueOf(getConfig().getString("mode")) == ModeCommand.world) {
            loadWorldsCommands();
        }
        getServer().getPluginManager().registerEvents(this,this);
        getLogger().info("插件启动!");
    }
    public void loadReplaceCommands(){
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

    public void loadWorldsCommands(){
        commands = getConfig().getStringList("world.cmd");
        for (String command : commands) {
            getServer().getCommandMap().register("", new Command(command) {
                @Override
                public boolean execute(CommandSender commandSender, String s, String[] strings) {
                    if(commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        if(getConfig().getString("world."+player.getLevel().getName()).length() != 0){
                            getServer().dispatchCommand(commandSender,getConfig().getString("world." + player.getLevel().getName()));
                        }
                    }
                    return false;
                }
            });
        }
    }


}