package tk.sherrao.bukkit.galaxygates.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.Vector;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.arenas.Arena;
import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.utils.plugin.SherEventListener;

public class PlayerCompassRightClickListener extends SherEventListener {
	
	protected ArenaManager arenaMgr;
	
	protected String itemName;
	protected List<String> itemLore;
	
	public PlayerCompassRightClickListener( GalaxyGates pl ) {
		super(pl);
		
		this.arenaMgr = pl.getArenaManager();
		
		this.itemName = ChatColor.RED + "" + ChatColor.BOLD + "Mob Locater 9000";
		this.itemLore = Arrays.asList( ChatColor.GOLD + "Right click to locate the nearest mob!" );
		
	}
	
	@EventHandler( priority = EventPriority.HIGHEST )
	public void onPlayerCompassRightClick( PlayerInteractEvent event ) {
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if( item == null || item.getType() == Material.AIR )
			return;
			
		else if( item.getType() == Material.COMPASS && 
				item.getItemMeta().getDisplayName().equals( itemName ) && 
				item.getItemMeta().getLore().equals( itemLore ) ) {

			Vector vector = new Vector();
			for( Arena arena : arenaMgr.getArenas() ) {
				if( arena.getCurrentPlayer() != null &&  arena.getCurrentPlayer().equals( player ) ) {
					for( Entity entity : player.getWorld().getEntities() ) {
						vector = vector.setX( entity.getLocation().getX() );
						vector = vector.setY( entity.getLocation().getY() );
						vector = vector.setZ( entity.getLocation().getZ() );
						if( arena.getRegion().contains( vector ) && !(entity instanceof Player) ) {
							player.setCompassTarget( entity.getLocation() );
							return;
							
						} else
							continue;
					}
				}				
			}
			
		} else
			return;
			
	}

}
