package tk.sherrao.bukkit.galaxygates.arenas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.utils.config.SherConfiguration;
import tk.sherrao.bukkit.utils.plugin.SherPluginFeature;

public class ArenaManager extends SherPluginFeature {

	private final class Lock {}
	private final Object lock = new Lock();
	
	protected SherConfiguration arenaConfig;
	
	protected WorldEditPlugin wePlugin;
	protected WorldGuardPlugin wgPlugin;
	protected List<Arena> arenas;
	protected int timeBetweenWaves;
	
    protected World world;
    protected Material playerSpawn, mobSpawn;
	protected RegionManager regionMgr;
	
	public ArenaManager( GalaxyGates pl ) {
		super(pl);
		
		this.arenaConfig = pl.getArenaConfig();
		
        try {
        	wgPlugin = WGBukkit.getPlugin();
			wePlugin = wgPlugin.getWorldEdit();
		
        } catch ( CommandException e ) {
        	log.severe( "Couldn't load WorldEdit, check your installation!" );
        	log.severe( "Printing stacktrace...", e );
        	
        }
        
        this.arenas = Collections.synchronizedList( new ArrayList<>() );
		this.timeBetweenWaves = arenaConfig.getInt( "time-per-round" );
		
		this.world = Bukkit.getWorld( arenaConfig.getString( "world-name" ) );
		this.playerSpawn = arenaConfig.getMaterial( "player-spawn" );
		this.mobSpawn = arenaConfig.getMaterial( "mob-spawn" );
		this.regionMgr = wgPlugin.getRegionManager( world );
		
		world.setGameRuleValue( "logAdminCommands", "false" );
		world.setGameRuleValue( "sendCommandFeedback", "false" );

	}
	
	@SuppressWarnings( "deprecation" )
	public int resolveArenas() throws Exception {
		synchronized( lock ) {
			arenas.clear();
	        int loaded = 0;
			for( Entry<String, ProtectedRegion> entry : regionMgr.getRegions().entrySet() ) {
				String name = entry.getKey();
				Gate gate = null;
				for( Gate g : Gate.values() )
					if( name.startsWith( "arena_" + g ) )
						gate = g;
				
				if( gate == null )
					continue;
					
				ProtectedRegion protRegion = entry.getValue();
				CuboidRegion region = new CuboidRegion( BukkitUtil.getLocalWorld( world ), protRegion.getMinimumPoint(), protRegion.getMaximumPoint() );
				Location pSpawn = null, mSpawn = null;
				for ( BlockVector vector : region ) {
					Material mat = world.getBlockAt( vector.getBlockX(), vector.getBlockY(), vector.getBlockZ() )
							.getType();
					if ( mat == playerSpawn )
						pSpawn = new Location( world, vector.getX(), vector.getY(), vector.getZ() );

					if ( mat == mobSpawn )
						mSpawn = new Location( world, vector.getX(), vector.getY(), vector.getZ() );

				}

				if ( pSpawn == null ) {
					log.warning( "Couldn't find a player-spawn location for the arena: " + entry.getKey() );
					continue;

				} else if ( mSpawn == null ) {
					log.warning( "Couldn't find a mob-spawn location for the arena: " + entry.getKey() );
					continue;

				}

				log.info( "Found valid arena: " + entry.getKey() + " for gate-type " + gate );
				Arena arena = new Arena( (GalaxyGates) pl );
				arena.load( name, gate, pSpawn, mSpawn, region );
				arenas.add( arena );
				loaded++;

			}
			
			log.info( "Loaded arenas! (Total: " + loaded + ")" );
			return loaded;
			
		}
	}
	
	public boolean startGame( Player player, Gate gate ) {
		synchronized ( lock ) {
			for( Arena arena : arenas ) {
				if( arena.available() && arena.getGate() == gate ) {
					arena.startGame( player, timeBetweenWaves );
					return true;

				} else
					continue;

			}

			return false;
		
		}
	}
	
	public Arena playerDeath( Player player ) {
		synchronized( lock ) {
			for( Arena arena : arenas ) {
				if( !arena.available ) {
					if( arena.getCurrentPlayer().equals( player ) ) {
						return arena;
						
					} else
						continue;
					
				} else
					continue;
				
			}
			
			return null;
			
		}
	}
	
	public List<Arena> getArenas() {
		synchronized( lock ) {
			return arenas;
			
		}
	}
	
}