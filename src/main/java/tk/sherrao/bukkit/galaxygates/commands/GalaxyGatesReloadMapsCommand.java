package tk.sherrao.bukkit.galaxygates.commands;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.utils.command.CommandBundle;
import tk.sherrao.bukkit.utils.command.SherSubCommand;
import tk.sherrao.bukkit.utils.config.SherConfiguration;

public class GalaxyGatesReloadMapsCommand extends SherSubCommand {

	protected ArenaManager arenaMgr;
	
	protected SherConfiguration messagesConfig, soundsConfig;
	protected String successfulMsg, failureMsg;
	protected Sound successSound, failureSound;
	
	public GalaxyGatesReloadMapsCommand( GalaxyGates pl ) {
		super( "reloadmaps", pl );

		this.arenaMgr = pl.getArenaManager();
		
		this.messagesConfig = pl.getMessagesConfig();
		this.soundsConfig = pl.getSoundsConfig();
		
		this.successfulMsg = messagesConfig.getString( "commands.reload-success" );
		this.failureMsg = messagesConfig.getString( "commands.reload-failure" );
		this.successSound = soundsConfig.getSound( "commands.reload-success" );
		this.failureSound = soundsConfig.getSound( "commands.reload-failure" );

	}

	@Override
	public void onExecute( CommandBundle bundle ) {
		CommandSender sender = (Player) bundle.sender;
		try {
			arenaMgr.resolveArenas();
			bundle.messageSound( successfulMsg, successSound );
			
		} catch( Exception e ) {
			String who = (sender instanceof Player ? "Player " + sender.getName() : "The console " );
			log.warning( who + " failed to reload maps, printing stacktrace...", e );
			if( sender instanceof Player )
				bundle.messageSound( failureMsg, failureSound );
			
		}
	}

}