package tk.sherrao.bukkit.galaxygates.arenas;

import static org.bukkit.Bukkit.dispatchCommand;
import static org.bukkit.Bukkit.getConsoleSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.galaxygates.progress.ProgressionManager;
import tk.sherrao.bukkit.utils.ItemBuilder;
import tk.sherrao.bukkit.utils.config.SherConfiguration;
import tk.sherrao.bukkit.utils.plugin.SherPluginFeature;

public class Arena extends SherPluginFeature {

	protected SherConfiguration arenaConfig, messagesConfig, soundsConfig;
	protected ProgressionManager progMgr;
	protected BukkitTask mainTask, secondaryTask;
	
	protected String name;
	protected Gate gate;
	protected Location playerSpawn, mobSpawn;
	protected String mobSpawnLoc, playerSpawnLoc;
	protected CuboidRegion region;
	protected boolean available;
	protected long endDelay, rewardDelay;
	
	protected Player player;
	protected Location oldPlayerLoc;
	protected int round;

	protected String tempRoundName;
	protected Vector tempEntityVector;
	protected Map< String, List<String> > roundCommands;
	protected List<String> rewards;
	
	protected String winMsg, lossMsg;
	protected Sound winSound, lossSound;
	
	protected ItemStack mobLocater;
	
	public Arena( GalaxyGates pl ) {
		super(pl);
		
		this.arenaConfig = pl.getArenaConfig();
		this.messagesConfig = pl.getMessagesConfig();
		this.soundsConfig = pl.getSoundsConfig(); 
		this.progMgr = pl.getProgressionManager();
		
		this.mobLocater = new ItemBuilder( Material.COMPASS )
				.setName( ChatColor.RED + "" + ChatColor.BOLD + "Mob Locater 9000" )
				.setLore( ChatColor.GOLD + "Right click to locate the nearest mob!" )
				.toItemStack();
		
	}
	
	public void load( String name, Gate gate, Location playerSpawn, Location mobSpawn, CuboidRegion region ) {
		this.name = name;
		this.gate = gate;
		this.playerSpawn = playerSpawn.add( 0, 1, 0 );
		this.mobSpawn = mobSpawn.add( 0, 1, 0 );
		this.mobSpawnLoc = Integer.toString( mobSpawn.getBlockX() ) + " " + 
		Integer.toString( mobSpawn.getBlockY() + 1 ) + " " +
		Integer.toString( mobSpawn.getBlockZ() );
		
		this.playerSpawnLoc = Integer.toString( playerSpawn.getBlockX() ) + " " + 
				Integer.toString( playerSpawn.getBlockY() + 1 ) + " " +
				Integer.toString( playerSpawn.getBlockZ() );
		
		this.region = region;
		this.available = true;
		this.endDelay = arenaConfig.getLong( "end-delay" );
		this.rewardDelay = arenaConfig.getLong( "reward-delay" );
		
		this.tempEntityVector = new Vector();
		this.roundCommands = new HashMap<>();
		this.rewards = new ArrayList<>();

		roundCommands.put( "one", arenaConfig.getStringList( "wave-commands." + gate + ".one" ) );
		roundCommands.put( "two", arenaConfig.getStringList( "wave-commands." + gate + ".two" ) );
		roundCommands.put( "three", arenaConfig.getStringList( "wave-commands." + gate + ".three" ) );
		roundCommands.put( "four", arenaConfig.getStringList( "wave-commands." + gate + ".four" ) );
		roundCommands.put( "five", arenaConfig.getStringList( "wave-commands." + gate + ".five" ) );
		roundCommands.put( "six", arenaConfig.getStringList( "wave-commands." + gate + ".six" ) );
		roundCommands.put( "seven", arenaConfig.getStringList( "wave-commands." + gate + ".seven" ) );
		roundCommands.put( "eight", arenaConfig.getStringList( "wave-commands." + gate + ".eight" ) );
		roundCommands.put( "nine", arenaConfig.getStringList( "wave-commands." + gate + ".nine" ) );
		roundCommands.put( "ten", arenaConfig.getStringList( "wave-commands." + gate + ".ten" ) );
		rewards.addAll( arenaConfig.getStringList( "wave-commands." + gate + ".rewards" ) );
		
		this.winMsg = messagesConfig.getString( "arenas.win" );
		this.lossMsg = messagesConfig.getString( "arenas.loss" );
		this.winSound = soundsConfig.getSound( "arenas.win" );
		this.lossSound = soundsConfig.getSound( "arenas.loss" );
		
	}

	public void startGame( Player player, int timeBetweenWaves ) {
		this.available = false;

		this.player = player;
		this.oldPlayerLoc = player.getLocation();

		player.teleport( playerSpawn );
		mainTask = Bukkit.getScheduler().runTaskTimer( pl, (Runnable) () -> {
			round++;
			switch( round ) {
				case 1:
					tempRoundName = "one";
					break;
					
				case 2:
					tempRoundName = "two";
					break;
					
				case 3:
					tempRoundName = "three";
					break;
					
				case 4:
					tempRoundName = "four";
					break;
					
				case 5:
					tempRoundName = "five";
					break;
					
				case 6:
					tempRoundName = "six";
					break;
					
				case 7:
					tempRoundName = "seven";
					break;
					
				case 8:
					tempRoundName = "eight";
					break;
					
				case 9:
					tempRoundName = "nine";
					break;
					
				case 10:
					tempRoundName = "ten";
					break;
					
				default:
					tempRoundName = "end";
					break;
					
			}
			
			if( !tempRoundName.equals( "end" ) ) {
				for( String command : roundCommands.get( tempRoundName ) ) {
					String cmd = command.replace( "<player>", player.getName() )
							.replace( "<mobspawn>", mobSpawnLoc )
							.replace( "<playerspawn>", playerSpawnLoc );

					dispatchCommand( getConsoleSender(), "execute " + player.getName() + " ~ ~ ~ " + cmd );
					
				}

				if( tempRoundName.equals( "one" ) )
					player.getInventory().addItem( mobLocater );
				
			} else {
				if( mainTask != null ) 
					mainTask.cancel();

				secondaryTask = Bukkit.getScheduler().runTaskTimer( pl, (Runnable) () -> {
					for( Entity entity : playerSpawn.getWorld().getLivingEntities() ) {
						if( entity instanceof Player || entity instanceof Arrow )
							continue;
						
						tempEntityVector = tempEntityVector.setX( entity.getLocation().getX() );
						tempEntityVector = tempEntityVector.setY( entity.getLocation().getY() );
						tempEntityVector = tempEntityVector.setZ( entity.getLocation().getZ() );
						if( region.contains( tempEntityVector )  )
							return;
						
					}
					
					endGame( false );
					
				} , 0, endDelay * 20 );
				
			}
			
		}, 20 * 1, 20 * timeBetweenWaves );
	}
	
	public void endGame( boolean death ) {
		Player player = this.player;

		this.player = null;
		this.available = true;
		this.oldPlayerLoc = null;
		this.round = 0;
		this.tempRoundName = null;

		if( mainTask != null ) 
			mainTask.cancel();
			
		if( secondaryTask != null ) 
			secondaryTask.cancel();
		
		for( Entity entity : playerSpawn.getWorld().getEntities() ) {
			tempEntityVector = tempEntityVector.setX( entity.getLocation().getX() );
			tempEntityVector = tempEntityVector.setY( entity.getLocation().getY() );
			tempEntityVector = tempEntityVector.setZ( entity.getLocation().getZ() );
			if( region.contains( tempEntityVector ) && !(entity instanceof Player) )
				entity.remove();
			
			else
				continue;
			
		}
		
		if( death ) {
			player.sendMessage( lossMsg );
			pl.playSound( player, lossSound );
				
		} else {
			progMgr.addGameWon( player, gate );
			progMgr.addGameWon( player, null );
			dispatchCommand( getConsoleSender(), "spawn " + player.getName() );
			Bukkit.getScheduler().runTaskLater( pl, () -> {
				player.sendMessage( winMsg );
				pl.playSound( player, winSound );
				for( String command : rewards ) 
					dispatchCommand( getConsoleSender(), command.replace( "<player>", player.getName() )
							.replace( "<mobspawn>", mobSpawnLoc )
							.replace( "<playerspawn>", playerSpawnLoc ) );
				
			}, rewardDelay * 20 );
		}
	}
	
	public boolean available() {
		return available;
		
	}
	
	public String getName() {
		return name;
		
	}
	
	public Gate getGate() {
		return gate;
		
	}
	
	public Player getCurrentPlayer() {
		return player;
		
	}
	
	public CuboidRegion getRegion() {
		return region;
		
	}
	
}