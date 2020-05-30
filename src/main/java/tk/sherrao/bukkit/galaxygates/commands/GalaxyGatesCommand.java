package tk.sherrao.bukkit.galaxygates.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tk.sherrao.bukkit.galaxygates.GalaxyGates;
import tk.sherrao.bukkit.galaxygates.Gate;
import tk.sherrao.bukkit.galaxygates.ItemManager;
import tk.sherrao.bukkit.galaxygates.arenas.ArenaManager;
import tk.sherrao.bukkit.galaxygates.progress.ProgressionManager;
import tk.sherrao.bukkit.utils.ItemBuilder;
import tk.sherrao.bukkit.utils.SherGUI;
import tk.sherrao.bukkit.utils.command.CommandBundle;
import tk.sherrao.bukkit.utils.command.SherCommand;
import tk.sherrao.bukkit.utils.command.SherSubCommand;
import tk.sherrao.bukkit.utils.config.SherConfiguration;

public class GalaxyGatesCommand extends SherCommand {

	protected SherConfiguration guiConfig;
	protected ProgressionManager progMgr;
	protected ArenaManager arenaMgr;
	protected SherSubCommand giveCmd, reloadMapsCmd, topCmd;
	protected List<String> onTab;
	
	protected SherGUI gui;
	protected ItemStack alphaGateDefaultItem, betaGateDefaultItem, gammaGateDefaultItem, deltaGateDefaultItem, hadesGateDefaultItem, lambdaGateDefaultItem, 
		kappaGateDefaultItem, zetaGateDefaultItem, kronosGateDefaultItem,
						alphaGateFinishedItem, betaGateFinishedItem, gammaGateFinishedItem, deltaGateFinishedItem, hadesGateFinishedItem, lambdaGateFinishedItem, 
		kappaGateFinishedItem, zetaGateFinishedItem, kronosGateFinishedItem;
		
	protected int alphaGateItemSlot, betaGateItemSlot, gammaGateItemSlot, deltaGateItemSlot, hadesGateItemSlot, lambdaGateItemSlot,
		kappaGateItemSlot, zetaGateItemSlot, kronosGateItemSlot;
	
	protected SherConfiguration messagesConfig, soundsConfig;
	protected String gateReceivedMsg, gateItemRedeemedMsg, gateItemNotInInvMsg, tpToArenaMsg, noArenaMsg, invNotEmptyMsg, senderConsoleMsg;
	protected Sound gateReceivedSound,gateItemRedeemedSound, gateItemNotInInvSound, tpToArenaSound, noArenaSound, invNotEmptySound;
	
	public GalaxyGatesCommand( GalaxyGates pl ) {
		super( "galaxygates", pl );
		
		this.guiConfig = pl.getGuiConfig();
		this.progMgr = pl.getProgressionManager();
		this.arenaMgr = pl.getArenaManager();
		this.giveCmd = new GalaxyGatesGiveCommand(pl);
		this.reloadMapsCmd = new GalaxyGatesReloadMapsCommand(pl);
		this.topCmd = new GalaxyGatesLeaderboardCommand(pl);
		this.onTab = super.asList( "give", "reloadmaps" );
		
		this.gui = new SherGUI( pl, guiConfig.getString( "title" ), guiConfig.getInt( "rows" ) * 9 );
		gui.setClickableItems( false );
		gui.setOpenSound( guiConfig.getSound( "open-sound" ) );

		this.alphaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.alpha.progressing.item" ) )
				.setName( guiConfig.getString( "gates.alpha.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.alpha.progressing.lore" ) )
				.toItemStack();
		
		this.alphaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.alpha.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.alpha.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.alpha.full-progression.lore" ) )
				.toItemStack();		
		
		
		this.betaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.beta.progressing.item" ) )
				.setName( guiConfig.getString( "gates.beta.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.beta.progressing.lore" ) )
				.toItemStack();
		
		this.betaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.beta.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.beta.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.beta.full-progression.lore" ) )
				.toItemStack();
		
		
		this.gammaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.gamma.progressing.item" ) )
				.setName( guiConfig.getString( "gates.gamma.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.gamma.progressing.lore" ) )
				.toItemStack();
		
		this.gammaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.gamma.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.gamma.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.gamma.full-progression.lore" ) )
				.toItemStack();
		
		
		this.deltaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.delta.progressing.item" ) )
				.setName( guiConfig.getString( "gates.delta.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.delta.progressing.lore" ) )
				.toItemStack();
		
		this.deltaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.delta.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.delta.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.delta.full-progression.lore" ) )
				.toItemStack();
		
		
		this.hadesGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.hades.progressing.item" ) )
				.setName( guiConfig.getString( "gates.hades.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.hades.progressing.lore" ) )
				.toItemStack();
		
		this.hadesGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.hades.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.hades.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.hades.full-progression.lore" ) )
				.toItemStack();		
		
		
		this.lambdaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.lambda.progressing.item" ) )
				.setName( guiConfig.getString( "gates.lambda.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.lambda.progressing.lore" ) )
				.toItemStack();
		
		this.lambdaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.lambda.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.lambda.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.lambda.full-progression.lore" ) )
				.toItemStack();		
		
		
		this.kappaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.kappa.progressing.item" ) )
				.setName( guiConfig.getString( "gates.kappa.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.kappa.progressing.lore" ) )
				.toItemStack();
		
		this.kappaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.kappa.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.kappa.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.kappa.full-progression.lore" ) )
				.toItemStack();	
		
		
		this.zetaGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.zeta.progressing.item" ) )
				.setName( guiConfig.getString( "gates.zeta.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.zeta.progressing.lore" ) )
				.toItemStack();

		this.zetaGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.zeta.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.zeta.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.zeta.full-progression.lore" ) )
				.toItemStack();	
		
		this.kronosGateDefaultItem = new ItemBuilder( guiConfig.getMaterial( "gates.kronos.progressing.item" ) )
				.setName( guiConfig.getString( "gates.kronos.progressing.title" ) )
				.setLore( guiConfig.getStringList( "gates.kronos.progressing.lore" ) )
				.toItemStack();

		this.kronosGateFinishedItem = new ItemBuilder( guiConfig.getMaterial( "gates.kronos.full-progression.item" ) )
				.setName( guiConfig.getString( "gates.kronos.full-progression.title" ) )
				.setLore( guiConfig.getStringList( "gates.kronos.full-progression.lore" ) )
				.toItemStack();	
		
		this.alphaGateItemSlot = guiConfig.getInt( "gates.alpha.slot" );  
		this.betaGateItemSlot = guiConfig.getInt( "gates.beta.slot" );    
		this.gammaGateItemSlot = guiConfig.getInt( "gates.gamma.slot" );  
		this.deltaGateItemSlot = guiConfig.getInt( "gates.delta.slot" ); 
		this.hadesGateItemSlot = guiConfig.getInt( "gates.hades.slot" ); 
		this.lambdaGateItemSlot = guiConfig.getInt( "gates.lambda.slot" );
		this.kappaGateItemSlot = guiConfig.getInt( "gates.kappa.slot" );  
		this.zetaGateItemSlot = guiConfig.getInt( "gates.zeta.slot" );    
		this.kronosGateItemSlot = guiConfig.getInt( "gates.kronos.slot" );
		
		this.messagesConfig = pl.getMessagesConfig();
		this.soundsConfig = pl.getSoundsConfig();
		
		this.gateReceivedMsg = messagesConfig.getString( "commands.gate-received" );
		this.gateItemRedeemedMsg = messagesConfig.getString( "gui.gate-redeemed" );
		this.gateItemNotInInvMsg = messagesConfig.getString( "gui.gate-not-in-inventory" );
		this.tpToArenaMsg = messagesConfig.getString( "arenas.teleporting-to-arena" );
		this.noArenaMsg = messagesConfig.getString( "arenas.no-arena-available" );
		this.invNotEmptyMsg = messagesConfig.getString( "arenas.inventory-not-empty" );
		this.senderConsoleMsg = messagesConfig.getString( "commands.not-a-player" );

		this.gateReceivedSound = soundsConfig.getSound( "commands.gate-received" );
		this.gateItemRedeemedSound = soundsConfig.getSound( "gui.gate-redeemed" );
		this.gateItemNotInInvSound = soundsConfig.getSound( "gui.gate-not-in-inventory" );
		this.tpToArenaSound = soundsConfig.getSound( "arenas.teleporting-to-arena" );
		this.noArenaSound = soundsConfig.getSound( "arenas.no-arena-available" );
		this.invNotEmptySound = soundsConfig.getSound( "arenas.inventory-not-empty" );
		
	}

	@Override
	protected void onExecute( CommandBundle bundle ) {
		if( bundle.hasArgs() ) {
			String arg = bundle.argAt(0);
			if( arg.equals( "leaderboard" ) || arg.equals( "lead" ) || arg.equals( "top" ) )
				topCmd.onExecute( bundle );
					
			else if( bundle.hasOpPermission( "galaxygates.op" ) ) {
				if( arg.equals( "give" ) )
					giveCmd.onExecute( bundle );
			
				else if( arg.equals( "reload" ) || arg.equals( "restart" ) ) 
					reloadMapsCmd.onExecute( bundle );
				
				else
					gui( bundle );
					
			} else 
				gui( bundle );
				
		} else
			gui( bundle );

	}
	
	@Override
	public List<String> onTab( CommandBundle bundle ) {
		if( bundle.hasOpPermission( "galaxygates.op" ) ) {
			if( bundle.argsEqualTo(0) )
				return onTab;
			
			else if( bundle.argsMoreThan(0) && bundle.argAt(0).startsWith( "g" ) )
				return giveCmd.onTab( bundle );
		
			else 
				return CommandBundle.EMPTY_LIST;
			
		} else
			return CommandBundle.EMPTY_LIST;

	}
	
	private void gui( CommandBundle bundle ) {
		if( !bundle.isPlayer() ) {
			bundle.message( senderConsoleMsg );
			return;
			
		}
		
		Player player = (Player) bundle.sender;
		gui.clearItems();
		if( progMgr.getGateProgression( player, Gate.ALPHA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.ALPHA, alphaGateDefaultItem ), alphaGateItemSlot );
			gui.doOnClickOf( alphaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.ALPHA ) )  {
					try {
						player.sendMessage( gateItemRedeemedMsg.replace( "[gate]", Gate.ALPHA.toString() ) );
						pl.playSound( player, gateItemRedeemedSound );
						
						gui.removeItem( alphaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.ALPHA, alphaGateDefaultItem ), alphaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else {
					player.sendMessage( gateItemNotInInvMsg.replace( "[gate]", Gate.ALPHA.toString() ) );
					pl.playSound( player, gateItemNotInInvSound );
					
				}
				
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.ALPHA, alphaGateFinishedItem ), alphaGateItemSlot );
			gui.doOnClickOf( alphaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.ALPHA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.ALPHA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.ALPHA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.BETA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.BETA, betaGateDefaultItem ), betaGateItemSlot );
			gui.doOnClickOf( betaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.BETA ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.BETA.toString() ), gateItemRedeemedSound );
						gui.removeItem( betaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.BETA, betaGateDefaultItem ), betaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.BETA.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.BETA, betaGateFinishedItem ), betaGateItemSlot );
			gui.doOnClickOf( betaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.BETA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.BETA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.BETA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.GAMMA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.GAMMA, gammaGateDefaultItem ), gammaGateItemSlot );
			gui.doOnClickOf( gammaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.GAMMA ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.GAMMA.toString() ), gateItemRedeemedSound );
						gui.removeItem( gammaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.GAMMA, gammaGateDefaultItem ), gammaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.GAMMA.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.GAMMA, gammaGateFinishedItem ), gammaGateItemSlot );
			gui.doOnClickOf( gammaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.GAMMA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.GAMMA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.GAMMA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.DELTA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.DELTA, deltaGateDefaultItem ), deltaGateItemSlot );
			gui.doOnClickOf( deltaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.DELTA ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.DELTA.toString() ), gateItemRedeemedSound );
						gui.removeItem( deltaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.DELTA, deltaGateDefaultItem ), deltaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.DELTA.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.DELTA, deltaGateFinishedItem ), deltaGateItemSlot );
			gui.doOnClickOf( deltaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.DELTA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.DELTA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.DELTA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.HADES ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.HADES, hadesGateDefaultItem ), hadesGateItemSlot );
			gui.doOnClickOf( hadesGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.HADES ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.HADES.toString() ), gateItemRedeemedSound );
						gui.removeItem( hadesGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.HADES, hadesGateDefaultItem ), hadesGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.HADES.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.HADES, hadesGateFinishedItem ), hadesGateItemSlot );
			gui.doOnClickOf( hadesGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.HADES ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.HADES.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.HADES, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.LAMBDA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.LAMBDA, lambdaGateDefaultItem ), lambdaGateItemSlot );
			gui.doOnClickOf( lambdaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.LAMBDA ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.LAMBDA.toString() ), gateItemRedeemedSound );
						gui.removeItem( lambdaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.LAMBDA, lambdaGateDefaultItem ), lambdaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.LAMBDA.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.LAMBDA, lambdaGateFinishedItem ), lambdaGateItemSlot );
			gui.doOnClickOf( lambdaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.LAMBDA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.LAMBDA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.LAMBDA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.KAPPA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.KAPPA, kappaGateDefaultItem ), kappaGateItemSlot );
			gui.doOnClickOf( kappaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.KAPPA ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.KAPPA.toString() ), gateItemRedeemedSound );
						gui.removeItem( kappaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.KAPPA, kappaGateDefaultItem ), kappaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.KAPPA.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.KAPPA, kappaGateFinishedItem ), kappaGateItemSlot );
			gui.doOnClickOf( kappaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.KAPPA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.KAPPA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.KAPPA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.ZETA ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.ZETA, zetaGateDefaultItem ), zetaGateItemSlot );
			gui.doOnClickOf( zetaGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.ZETA ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.ZETA.toString() ), gateItemRedeemedSound );
						gui.removeItem( zetaGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.ZETA, zetaGateDefaultItem ), zetaGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.ZETA.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.ZETA, zetaGateFinishedItem ), zetaGateItemSlot );
			gui.doOnClickOf( zetaGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.ZETA ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.ZETA.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.ZETA, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}
		
		
		if( progMgr.getGateProgression( player, Gate.KRONOS ) < 9 ) {
			gui.addItem( progMgr.processItem( player, Gate.KRONOS, kronosGateDefaultItem ), kronosGateItemSlot );
			gui.doOnClickOf( kronosGateItemSlot, (event) -> { 
				if( progMgr.processInventory( player, Gate.KRONOS ) )  {
					try {
						bundle.messageSound( gateItemRedeemedMsg.replace( "[gate]", Gate.KRONOS.toString() ), gateItemRedeemedSound );
						gui.removeItem( kronosGateItemSlot );
						gui.addItem( progMgr.processItem( player, Gate.KRONOS, kronosGateDefaultItem ), kronosGateItemSlot );
						player.closeInventory();
						Thread.sleep( 100 );
						gui( bundle );

					} catch ( InterruptedException e ) { e.printStackTrace(); }
					
				} else 
					bundle.messageSound( gateItemNotInInvMsg.replace( "[gate]", Gate.KRONOS.toString() ), gateItemNotInInvSound );
					
			} ); 
			
		} else {
			gui.addItem( progMgr.processItem( player, Gate.KRONOS, kronosGateFinishedItem ), kronosGateItemSlot );
			gui.doOnClickOf( kronosGateItemSlot, (event) -> { 
				if( !inventoryEmpty( player ) ) 
					bundle.messageSound( invNotEmptyMsg, invNotEmptySound );
					
				else if( arenaMgr.startGame( player, Gate.KRONOS ) ) {
					bundle.messageSound( tpToArenaMsg.replace( "[gate]",Gate.KRONOS.toString() ), tpToArenaSound );
					progMgr.removeGateProgression( player, Gate.KRONOS, 9 );
					
				} else  
					bundle.messageSound( noArenaMsg, noArenaSound );
				
			} ); 

		}

		gui.open( player );
		
	}

	private boolean inventoryEmpty( Player player ) {
		for( ItemStack item : player.getInventory().getArmorContents() ) {
			if( (item != null) && ( item.getType() != Material.AIR ) ) {
		        return false;

			} else {
				for( ItemStack gate : ItemManager.items() )
					if( ItemManager.itemIsEqualTo( item, gate ) )
						return false;
				
			}
		    
		}
		
		for( int i = 0; i < player.getInventory().getSize(); i++ ) {
			ItemStack item = player.getInventory().getItem(i);
		    if ( (item != null) && ( item.getType() != Material.AIR ) ) {
		        return false;
		      
		    }
		    
		}
		
		return true;		
		
	}
	
}