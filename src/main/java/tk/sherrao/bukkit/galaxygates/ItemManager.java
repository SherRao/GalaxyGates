package tk.sherrao.bukkit.galaxygates;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import tk.sherrao.bukkit.utils.ItemBuilder;
import tk.sherrao.bukkit.utils.config.SherConfiguration;

public class ItemManager {

	protected static SherConfiguration itemConfig;
	protected static ItemStack alpha, beta, gamma, delta, hades, lambda, kappa, zeta, kronos;
	protected static List<ItemStack> items;
	 
	public static void load( GalaxyGates pl ) {
		itemConfig = pl.getItemConfig();
		alpha = new ItemBuilder( itemConfig.getMaterial( "gates.alpha.item" ) )
				.setName( itemConfig.getString( "gates.alpha.title" ) )
				.setLore( itemConfig.getStringList( "gates.alpha.lore" ) )
				.toItemStack();
		
		beta = new ItemBuilder( itemConfig.getMaterial( "gates.beta.item" ) )
				.setName( itemConfig.getString( "gates.beta.title" ) )
				.setLore( itemConfig.getStringList( "gates.beta.lore" ) )
				.toItemStack();

		gamma = new ItemBuilder( itemConfig.getMaterial( "gates.gamma.item" ) )
				.setName( itemConfig.getString( "gates.gamma.title" ) )
				.setLore( itemConfig.getStringList( "gates.gamma.lore" ) )
				.toItemStack();
		
		delta = new ItemBuilder( itemConfig.getMaterial( "gates.delta.item" ) )
				.setName( itemConfig.getString( "gates.delta.title" ) )
				.setLore( itemConfig.getStringList( "gates.delta.lore" ) )
				.toItemStack();
		
		hades = new ItemBuilder( itemConfig.getMaterial( "gates.hades.item" ) )
				.setName( itemConfig.getString( "gates.hades.title" ) )
				.setLore( itemConfig.getStringList( "gates.hades.lore" ) )
				.toItemStack();
		
		lambda = new ItemBuilder( itemConfig.getMaterial( "gates.lambda.item" ) )
				.setName( itemConfig.getString( "gates.lambda.title" ) )
				.setLore( itemConfig.getStringList( "gates.lambda.lore" ) )
				.toItemStack();
		
		kappa = new ItemBuilder( itemConfig.getMaterial( "gates.kappa.item" ) )
				.setName( itemConfig.getString( "gates.kappa.title" ) )
				.setLore( itemConfig.getStringList( "gates.kappa.lore" ) )
				.toItemStack();
		
		zeta = new ItemBuilder( itemConfig.getMaterial( "gates.zeta.item" ) )
				.setName( itemConfig.getString( "gates.zeta.title" ) )
				.setLore( itemConfig.getStringList( "gates.zeta.lore" ) )
				.toItemStack();
		
		kronos = new ItemBuilder( itemConfig.getMaterial( "gates.kronos.item" ) )
				.setName( itemConfig.getString( "gates.kronos.title" ) )
				.setLore( itemConfig.getStringList( "gates.kronos.lore" ) )
				.toItemStack();
		
		items = Arrays.asList( alpha, beta, gamma, lambda, hades, delta, kronos, zeta, kappa );
		
	} 
	
	public static boolean itemIsEqualTo( ItemStack first, ItemStack second ) {
		return first != null && second != null & 
				first.getType() == second.getType() && 
				first.getItemMeta().getDisplayName().equals( second.getItemMeta().getDisplayName() ) &&
				first.getItemMeta().getLore().equals( second.getItemMeta().getLore() );
			
	}
	
	public static ItemStack getAlphaItem() {
		return alpha;
		
	}

	public static ItemStack getBetaItem() {
		return beta;
		
	}
	
	public static ItemStack getGammaItem() {
		return gamma;
		
	}
	
	public static ItemStack getDeltaItem() {
		return delta;
		
	}
	
	public static ItemStack getHadesItem() {
		return hades;
		
	}
	
	public static ItemStack getLambdaItem() {
		return lambda;
		
	}
	
	public static ItemStack getKappaItem() {
		return kappa;
		
	}
	
	public static ItemStack getZetaItem() {
		return zeta;

	}
	
	public static ItemStack getKronosItem() {
		return kronos;
		
	}
	
	public static List<ItemStack> items() {
		return items;
		
	}
	
}