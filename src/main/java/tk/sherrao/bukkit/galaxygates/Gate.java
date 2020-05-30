package tk.sherrao.bukkit.galaxygates;

import org.apache.commons.lang.StringUtils;

public enum Gate {

	ALPHA( "alpha" ), BETA( "beta" ), GAMMA( "gamma" ), DELTA( "delta" ), HADES( "hades" ), LAMBDA( "lambda" ), 
	KAPPA( "kappa" ), ZETA( "zeta" ), KRONOS( "kronos" );
	
	private String name;
	
	private Gate( String name ) {
		this.name = name;
		
	}
	
	public String getName() {
		return StringUtils.capitalize( name );
		
	}
	
	@Override
	public String toString() {
		return name;
		
	}
	
	public static boolean isGate( String str ) {
		for( Gate gate : values() ) 
			if( gate.toString().equalsIgnoreCase( str ) )
				return true;
		
		return false;
		
	}
	
}