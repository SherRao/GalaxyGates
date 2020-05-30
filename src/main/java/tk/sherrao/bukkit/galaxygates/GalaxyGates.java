package tk.sherrao.bukkit.galaxygates;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.galaxygates.commands.GalaxyGatesCommand;
import tk.sherrao.bukkit.galaxygates.listeners.PlayerCompassRightClickListener;
import tk.sherrao.bukkit.galaxygates.listeners.PlayerDeathListener;
import tk.sherrao.bukkit.galaxygates.listeners.PlayerDisconnectListener;
import tk.sherrao.bukkit.galaxygates.progress.LeaderboardManager;
import tk.sherrao.bukkit.galaxygates.progress.ProgressionManager;
import tk.sherrao.bukkit.utils.config.SherConfiguration;
import tk.sherrao.bukkit.utils.plugin.SherPlugin;

public class GalaxyGates extends SherPlugin {

	protected SherConfiguration arenaConfig, guiConfig, itemsConfig, messagesConfig, soundsConfig, leaderboardConfig;
		
	protected ProgressionManager progMgr;
	protected ArenaManager arenaMgr;
	protected LeaderboardManager leadMgr;
	
	public GalaxyGates() {
		super();
		
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		
	}
	
	@Override
	public void onEnable() {
		super.onEnable();

		super.saveResource( "arenas.yml", false );
		super.saveResource( "gui.yml", false );
		super.saveResource( "items.yml", false );
		super.saveResource( "messages.yml", false );
		super.saveResource( "sounds.yml", false );
		super.saveResource( "leaderboard.yml", false );
		
		arenaConfig = new SherConfiguration( this, new File( super.getDataFolder(), "arenas.yml" ) );
		guiConfig = new SherConfiguration( this, new File( super.getDataFolder(), "gui.yml" ) );
		itemsConfig = new SherConfiguration( this, new File( super.getDataFolder(), "items.yml" ) );
		messagesConfig = new SherConfiguration( this, new File( super.getDataFolder(), "messages.yml" ) );
		soundsConfig = new SherConfiguration( this, new File( super.getDataFolder(), "sounds.yml" ) );
		leaderboardConfig = new SherConfiguration( this, new File( super.getDataFolder(), "leaderboard.yml" ) );

		ItemManager.load( this );

		try {
			progMgr = new ProgressionManager( this );
			arenaMgr = new ArenaManager( this );
			arenaMgr.resolveArenas();
			leadMgr = new LeaderboardManager( this );
			
		} catch ( Exception e ) { e.printStackTrace(); }
		
		super.registerCommand( "galaxygates", new GalaxyGatesCommand( this ) );
		super.registerEventListener( new PlayerDeathListener( this ) );
		super.registerEventListener( new PlayerDisconnectListener( this ) );
		super.registerEventListener( new PlayerCompassRightClickListener( this ) );
		super.complete();
		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
	}
	
	@SuppressWarnings( "unused" )
	private void io() {
		File folder = new File( super.getDataFolder(), "/data/" );
		if( !folder.exists() ) {
			folder.mkdir();
			
			File arenaFile = new File( folder, "arenas.yml" );
			if( !arenaFile.exists() ) {
				super.saveResource( "arenas.yml", false );
				try {
					Files.move( new File( super.getDataFolder(), "arenas.yml" ), new File( folder, "arenas.yml" ) );
				} catch ( IOException e ) { e.printStackTrace(); }
				
			}
		}
	}
	
	public SherConfiguration getArenaConfig() {
		return arenaConfig;
		
	}
	
	public SherConfiguration getGuiConfig() {
		return guiConfig;
		
	}
	
	public SherConfiguration getItemConfig() {
		return itemsConfig;
		
	}
	
	public SherConfiguration getMessagesConfig() {
		return messagesConfig;
		
	}
	
	public SherConfiguration getSoundsConfig() {
		return soundsConfig;
		
	}
	
	public SherConfiguration getLeaderboardConfig() {
		return leaderboardConfig;
		
	}
	
	public ProgressionManager getProgressionManager() {
		return progMgr;
		
	}
	
	public ArenaManager getArenaManager() {
		return arenaMgr;
		
	}
	
	public LeaderboardManager getLeaderboardManager() {
		return leadMgr;
		
	}
	
}