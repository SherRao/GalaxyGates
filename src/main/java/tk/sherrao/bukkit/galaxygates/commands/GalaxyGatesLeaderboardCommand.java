package tk.sherrao.bukkit.galaxygates.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.galaxygates.progress.LeaderboardManager;
import tk.sherrao.bukkit.utils.command.CommandBundle;
import tk.sherrao.bukkit.utils.command.SherSubCommand;
import tk.sherrao.bukkit.utils.config.SherConfiguration;
import tk.sherrao.utils.strings.StringMultiJoiner;

public class GalaxyGatesLeaderboardCommand extends SherSubCommand {

	protected SherConfiguration leaderboardConfig;
	protected LeaderboardManager leadMgr;
	
	protected List<String> defaultMenu, totalMenu, menu;
	protected String entry;
	protected StringBuilder formattedMenu;
	protected int itemsPerPage;
	
	protected String wrongCmdMsg;
	protected Sound wrongCmdSound;
	
	public GalaxyGatesLeaderboardCommand( GalaxyGates pl ) {
		super( "leaderboard", pl );
		
		this.leaderboardConfig = pl.getLeaderboardConfig();
		this.leadMgr = pl.getLeaderboardManager();
		
		this.defaultMenu = leaderboardConfig.getStringList( "display.normal-menu" );
		this.totalMenu = leaderboardConfig.getStringList( "display.total-menu" );
		this.entry = leaderboardConfig.getString( "display.entry" );
		this.formattedMenu = new StringBuilder();
		this.itemsPerPage = leaderboardConfig.getInt( "display.entries-per-page" );
		
		this.wrongCmdMsg = pl.getMessagesConfig().getString( "" );
		this.wrongCmdSound = pl.getSoundsConfig().getSound( "" );
		
	}

	@Override
	public void onExecute( CommandBundle bundle ) {
		Gate g = null;
		try {
			g = Gate.valueOf( bundle.argAt(1).toUpperCase() );
		
		} catch( IndexOutOfBoundsException e ) {}
			
		if( g == null )
			menu = totalMenu;
		
		else 
			menu = defaultMenu;
		
		final Gate gate = g;
		Bukkit.getScheduler().runTaskAsynchronously( pl, () -> {
			leadMgr.tryUpdate( gate );
			formattedMenu.setLength(0);
			Map<String, Integer> top = leadMgr.getLeadFor( gate );
			for( String str : menu ) {
				if( str.contains( "[gate]" ) )
					str = str.replace( "[gate]", gate.getName() );
				
				if( str.contains( "[entries]" ) ) {
					StringMultiJoiner smj = new StringMultiJoiner( "\n" );
					for( int i = 0; i < (itemsPerPage < top.size() ? itemsPerPage : top.size() ); i++ ) {
						String player = (String) top.keySet().toArray()[i];
						int wins = top.get( player );
						smj.add( entry.replace( "[position]", Integer.toString( i + 1 ) )
								.replace( "[display-name]", player )
								.replace( "[wins]", Integer.toString( wins ) ));
						
					}
					
					str = str.replace( "[entries]", smj.toString() );
					
				}
				
				formattedMenu.append( str );
				formattedMenu.append( "\n" );
			
			}

			bundle.message( formattedMenu.toString().substring( 0, formattedMenu.lastIndexOf( "\n" ) ) );

		} );
	}
	
}