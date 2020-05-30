package tk.sherrao.bukkit.galaxygates.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.arenas.Arena;
import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.utils.plugin.SherEventListener;

public class PlayerDeathListener extends SherEventListener {

	protected ArenaManager arenaMgr;
	
	public PlayerDeathListener( GalaxyGates pl ) {
		super(pl);
		
		this.arenaMgr = pl.getArenaManager();
		
	}
	
	@EventHandler( priority = EventPriority.HIGH )
	public void onPlayerDeath( PlayerDeathEvent event ) {
		Player player = event.getEntity();
		Arena arena = arenaMgr.playerDeath( player );
		if( arena != null ) {
			arena.endGame( true );
			event.getDrops().clear();
			
		} else
			return;
		
	}

}