package tk.sherrao.bukkit.galaxygates.progress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.galaxygates.ItemManager;
import tk.sherrao.bukkit.utils.plugin.SherPluginFeature;

public class ProgressionManager extends SherPluginFeature {

	private final class Lock {}
	private final Object lock = new Lock();
	
	protected File dataFile;
	protected YamlConfiguration data;
	
	protected ItemStack alphaGateItem, betaGateItem, gammaGateItem, deltaGateItem, hadesGateItem, lambdaGateItem, 
		kappaGateItem, zetaGateItem, kronosGateItem;
	
	public ProgressionManager( GalaxyGates pl ) {
		super(pl);
		
		this.dataFile = pl.createFile( "data.yml" );
		this.data = YamlConfiguration.loadConfiguration( dataFile );
		
		this.alphaGateItem = ItemManager.getAlphaItem();
		this.betaGateItem = ItemManager.getBetaItem();
		this.gammaGateItem = ItemManager.getGammaItem();
		this.deltaGateItem = ItemManager.getDeltaItem();
		this.hadesGateItem = ItemManager.getHadesItem();
		this.lambdaGateItem = ItemManager.getLambdaItem();
		this.kappaGateItem = ItemManager.getKappaItem();
		this.zetaGateItem = ItemManager.getZetaItem();
		this.kronosGateItem = ItemManager.getKronosItem();
		
	}
	
	public void addGameWon( OfflinePlayer player, Gate gate ) {
		synchronized( lock ) {
			try {
				String loc = "games-won." + player.getUniqueId() + "." + (gate == null ? "total" : gate);
				if( data.contains( loc ) )
					data.set( loc, data.getInt( loc ) + 1 );
			
				else
					data.set( loc, 1 );
			
				data.save( dataFile );
			
			} catch( IOException e ) { log.warning( "Failed to save date for player: " + player.getName(), e ); }
		}
	}
	
	public void addGateProgression( OfflinePlayer player, Gate gate, int amount ) {
		synchronized( lock ) {
			try {
				String loc = "progression." + player.getUniqueId() + "." + gate;
				if( data.contains( loc ) ) 
					data.set( loc, data.getInt( loc ) + amount );
					
				else 
					data.set( loc, amount );

				data.save( dataFile );
					
			} catch( IOException e ) { log.warning( "Failed to save date for player: " + player.getName(), e ); }
		}
	}
	
	public void removeGateProgression( OfflinePlayer player, Gate gate, int amount ) {
		synchronized( lock ) {
			try {
				String loc = "progression." + player.getUniqueId() + "." + gate;
				if( !data.contains( loc ) ) 
					data.set( loc, 0 );
					
				else {
					int saved = getGateProgression( player, gate );
					if( amount <= saved )
						data.set( loc, saved - amount );
					
					else
						data.set( loc, 0 );

					data.save( dataFile );
					
				}
				
			} catch( IOException e ) { log.warning( "Failed to save date for player: " + player.getName(), e ); }
		}
	}
	
	@SuppressWarnings( "finally" )
	public int getGamesWon( OfflinePlayer player, Gate gate ) {
		synchronized( lock ) {
			int value = 0;
			try {
				String loc = "games-won." + player.getUniqueId() + "." + (gate == null ? "total" : gate);
				if( !data.contains( loc ) ) {
					data.set( loc, 0 );
					data.save( dataFile );
				
				} else
					value =  data.getInt( loc );
			
			} catch( IOException e ) { 
				log.warning( "Failed to save date for player: " + player.getName(), e ); 
				
			} finally { return value; } 
		}
	}
	
	@SuppressWarnings( "finally" )
	public int getGateProgression( OfflinePlayer player, Gate gate ) {
		synchronized( lock ) {
			int value = 0;
			try {
				String loc = "progression." + player.getUniqueId() + "." + gate;
				if( !data.contains( loc ) ){
					data.set( loc, 0 );
					data.save( dataFile );
					
				} else 
					value = data.getInt( loc );
					
			} catch( IOException e ) { 
				log.warning( "Failed to save date for player: " + player.getName(), e ); 
				
			} finally { return value; }
		}
	}
	
	public boolean processInventory( OfflinePlayer player, Gate gate ) {
		synchronized( lock ) {
			PlayerInventory inv = ( (Player) player ).getInventory();
			int index = 0;
			boolean hadItemInInv = false;
			
			switch( gate ) {
				case ALPHA:
					for( ItemStack item : inv ) {
						if( !ItemManager.itemIsEqualTo( item, alphaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;	
						break;

					}
					
					break;
					
				case BETA:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, betaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

					break;
					
				case GAMMA:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, gammaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

					break;
					
				case DELTA:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, deltaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

					break;
					
				case HADES:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, hadesGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

					break;
					
				case LAMBDA:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, lambdaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

					break;
					
				case KAPPA:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, kappaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

				case ZETA:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, zetaGateItem ) )
							continue;
						
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;

					} 

					break;
					
				case KRONOS:
					for( ItemStack item : inv.getContents() ) {
						if( !ItemManager.itemIsEqualTo( item, kronosGateItem ) )
							continue;
					
						index = inv.first( item );
						addGateProgression( player, gate, inv.getItem( index ).getAmount() );
						hadItemInInv = true;
						break;
					
					}
					
					break;
					
				default:
					throw new IllegalArgumentException( "Invalid gate: " + gate );

			}
			
			if( hadItemInInv )
				inv.clear( index );
		
			return hadItemInInv;
			
		}
	}
	
	public ItemStack processItem( OfflinePlayer player, Gate gate, ItemStack item ) {
		synchronized( lock ) {
			ItemStack it = item.clone();
			String progression = String.valueOf( getGateProgression( player, gate ) ); 
			ItemMeta im = it.getItemMeta();
			List<String> lore = new ArrayList<String>( item.getItemMeta().getLore().size() );
			for( String line : item.getItemMeta().getLore() )
				lore.add( line.replace( "[progression]", progression ) );
				
			im.setDisplayName( item.getItemMeta().getDisplayName().replace( "[progression]", progression ) );
			im.setLore( lore );
			it.setItemMeta( im );
			return it;
				
		}
	}
	
}