package tk.sherrao.bukkit.galaxygates.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.arenas.Arena;
import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.utils.plugin.SherEventListener;

public class PlayerDisconnectListener extends SherEventListener {

	protected ArenaManager arenaMgr;
	
	public PlayerDisconnectListener( GalaxyGates pl ) {
		super(pl);

		this.arenaMgr = pl.getArenaManager();
		
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onPlayerDisconnect( PlayerQuitEvent event ) {
		Player player = event.getPlayer();
		Arena arena = arenaMgr.playerDeath( player );
		if( arena != null ) {
			player.setHealth(0D);
			
		} else
			return;
		
	}
	
}
