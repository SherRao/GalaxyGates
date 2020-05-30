package tk.sherrao.bukkit.galaxygates.progress;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.utils.plugin.SherPluginFeature;
import tk.sherrao.utils.TimeUtils;

public class LeaderboardManager extends SherPluginFeature {
	
	private final class Lock {}
	private final Object lock = new Lock();
	
	protected ProgressionManager progMgr;

	protected int updateRate;
	protected long lastUpdate;
	protected Map< Gate, Map<String, Integer> > leaderboard;
	protected Map<String, Integer> temp;
	
	public LeaderboardManager( GalaxyGates pl ) {
		super(pl);
		
		this.progMgr = pl.getProgressionManager();
	
		this.updateRate = 250;
		this.leaderboard = Collections.synchronizedMap( new HashMap<>( Gate.values().length ) );
		this.temp = Collections.synchronizedMap( new HashMap<>( Bukkit.getOfflinePlayers().length ) );
		
	}
	
	public boolean tryUpdate( Gate gate ) {
		synchronized( lock ) {
			if( !TimeUtils.isTimedOut( lastUpdate, updateRate ) )
				return false;
			
			else {
				lastUpdate = System.currentTimeMillis();
				temp.clear();
				for( OfflinePlayer player : Bukkit.getOfflinePlayers() ) {
					String name = player.getName();
					int won = progMgr.getGamesWon( player, gate );
					temp.put( name, won );
					
				}

				temp = temp.entrySet().stream()
						.sorted( Entry.comparingByValue( Collections.reverseOrder() ) )
						.collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new ) );
							
				leaderboard.put( gate, temp );
				return true;
				
			}
		}
	}
	
	public Map<String, Integer> getLeadFor( Gate gate ) {
		return leaderboard.get( gate );
		
	}
	
	public Map< Gate, Map<String, Integer> > getLeaderboard() {
		return leaderboard;
		
	}
	
}