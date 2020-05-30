package tk.sherrao.bukkit.galaxygates.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.galaxygates.ItemManager;
import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.utils.command.CommandBundle;
import tk.sherrao.bukkit.utils.command.SherSubCommand;
import tk.sherrao.bukkit.utils.config.SherConfiguration;

public class GalaxyGatesGiveCommand extends SherSubCommand {

	protected ArenaManager arenaMgr;
	protected SherConfiguration messagesConfig, soundsConfig;
	
	protected String invalidCmdUsageMsg, invalidPlayerMsg, invalidGateMsg,
		gateSentMsg, gateReceivedMsg;
	
	protected Sound invalidCmdUsageSound, invalidPlayerSound, invalidGateSound,
		gateSentSound, gateReceivedSound;
	
	public GalaxyGatesGiveCommand( GalaxyGates pl ) {
		super( "give", pl );
		
		this.arenaMgr = pl.getArenaManager();
		this.messagesConfig = pl.getMessagesConfig();
		this.soundsConfig = pl.getSoundsConfig();
		
		this.invalidCmdUsageMsg = messagesConfig.getString( "commands.invalid-usage" );
		this.invalidPlayerMsg = messagesConfig.getString( "commands.invalid-player" );
		this.invalidGateMsg = messagesConfig.getString( "commands.invalid-gate" );
		this.gateSentMsg = messagesConfig.getString( "commands.gate-sent" );
		this.gateReceivedMsg = messagesConfig.getString( "commands.gate-received" );
		
		this.invalidCmdUsageSound = soundsConfig.getSound( "commands.invalid-usage" );
		this.invalidPlayerSound = soundsConfig.getSound( "commands.invalid-player" );
		this.invalidGateSound = soundsConfig.getSound( "commands.invalid-gate" );
		this.gateSentSound = soundsConfig.getSound( "commands.gate-sent" );
		this.gateReceivedSound = soundsConfig.getSound( "commands.gate-received" );
		
	}

	@Override
	public void onExecute( CommandBundle bundle ) {
		if( bundle.argsMoreThan(2) ) {
			String playerName = bundle.argAt(1);
			String gateName = bundle.argAt(2).toLowerCase();
			Player player = Bukkit.getPlayerExact( playerName );
			if( Gate.isGate( gateName ) ) {
				if( player != null ) {
					bundle.messageSound( gateSentMsg.replace( "[player]", player.getName() )
							.replace( "[gate]", gateName ), gateSentSound );
					player.sendMessage( gateReceivedMsg
							.replace( "[gate]", gateName ) );
					player.playSound( player.getLocation(), gateReceivedSound, 1F, 1F );
					ItemStack it = null;
					int amount = 1;
					if( bundle.argsMoreThan(3) ) {
						try {
							amount = Integer.valueOf( bundle.argAt(3) );
							
						} catch( NumberFormatException | IndexOutOfBoundsException e ) { amount = 1; }
						
					} 
					
					switch( gateName ) {
						case "alpha":
							it = ItemManager.getAlphaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;
							
						case "beta":
							it = ItemManager.getBetaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;
						
						case "gamma":
							it = ItemManager.getGammaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;

						case "delta":
							it = ItemManager.getDeltaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;

						case "hades":
							it = ItemManager.getHadesItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;

						case "lambda":
							it = ItemManager.getLambdaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;

						case "kappa":
							it = ItemManager.getKappaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;

						case "zeta":
							it = ItemManager.getZetaItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;

						case "kronos":
							it = ItemManager.getKronosItem();
							it.setAmount( amount );
							player.getInventory().addItem(it);
							break;
							
						default:
							break;
						
					}
					
				} else
					bundle.messageSound( invalidPlayerMsg.replace( "[input]", playerName ), invalidPlayerSound );
			
			} else
				bundle.messageSound( invalidGateMsg.replace( "[input]", gateName ), invalidGateSound );
				
		} else 
			bundle.messageSound( invalidCmdUsageMsg.replace( "[cmd]", bundle.alias ), invalidCmdUsageSound );
		
	}
	
}