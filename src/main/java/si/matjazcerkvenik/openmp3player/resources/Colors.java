package si.matjazcerkvenik.openmp3player.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.matjazcerkvenik.openmp3player.backend.OContext;

/*
 * http://www.w3schools.com/cssref/css_colornames.asp
 */
public class Colors {
	
	private static Logger logger = LoggerFactory.getLogger(Colors.class);
	
	public static final String AliceBlue = "AliceBlue"; //#F0F8FF
	public static final String AntiqueWhite = "AntiqueWhite"; //#FAEBD7
	public static final String Aqua = "Aqua"; //#00FFFF
	public static final String Aquamarine = "Aquamarine"; //#7FFFD4
	public static final String Azure = "Azure"; //#F0FFFF
	public static final String Beige = "Beige"; //#F5F5DC
	public static final String Bisque = "Bisque"; //#FFE4C4
	public static final String Black = "Black"; //#000000
	public static final String BlanchedAlmond = "BlanchedAlmond"; //#FFEBCD
	public static final String Blue = "Blue"; //#0000FF
	public static final String BlueViolet = "BlueViolet"; //#8A2BE2
	public static final String Brown = "Brown"; //#A52A2A
	public static final String BurlyWood = "BurlyWood"; //#DEB887
	public static final String CadetBlue = "CadetBlue"; //#5F9EA0
	public static final String Chartreuse = "Chartreuse"; //#7FFF00
	public static final String Chocolate = "Chocolate"; //#D2691E
	public static final String Coral = "Coral"; //#FF7F50
	public static final String CornflowerBlue = "CornflowerBlue"; //#6495ED
	public static final String Cornsilk = "Cornsilk"; //#FFF8DC
	public static final String Crimson = "Crimson"; //#DC143C
	public static final String Cyan = "Cyan"; //#00FFFF
	public static final String DarkBlue = "DarkBlue"; //#00008B
	public static final String DarkCyan = "DarkCyan"; //#008B8B
	public static final String DarkGoldenRod = "DarkGoldenRod"; //#B8860B
	public static final String DarkGray = "DarkGray"; //#A9A9A9
	public static final String DarkGreen = "DarkGreen"; //#006400
	public static final String DarkKhaki = "DarkKhaki"; //#BDB76B
	public static final String DarkMagenta = "DarkMagenta"; //#8B008B
	public static final String DarkOliveGreen = "DarkOliveGreen"; //#556B2F
	public static final String DarkOrange = "DarkOrange"; //#FF8C00
	public static final String DarkOrchid = "DarkOrchid"; //#9932CC
	public static final String DarkRed = "DarkRed"; //#8B0000
	public static final String DarkSalmon = "DarkSalmon"; //#E9967A
	public static final String DarkSeaGreen = "DarkSeaGreen"; //#8FBC8F
	public static final String DarkSlateBlue = "DarkSlateBlue"; //#483D8B
	public static final String DarkSlateGray = "DarkSlateGray"; //#2F4F4F
	public static final String DarkTurquoise = "DarkTurquoise"; //#00CED1
	public static final String DarkViolet = "DarkViolet"; //#9400D3
	public static final String DeepPink = "DeepPink"; //#FF1493
	public static final String DeepSkyBlue = "DeepSkyBlue"; //#00BFFF
	public static final String DimGray = "DimGray"; //#696969
	public static final String DodgerBlue = "DodgerBlue"; //#1E90FF
	public static final String FireBrick = "FireBrick"; //#B22222
	public static final String FloralWhite = "FloralWhite"; //#FFFAF0
	public static final String ForestGreen = "ForestGreen"; //#228B22
	public static final String Fuchsia = "Fuchsia"; //#FF00FF
	public static final String Gainsboro = "Gainsboro"; //#DCDCDC
	public static final String GhostWhite = "GhostWhite"; //#F8F8FF
	public static final String Gold = "Gold"; //#FFD700
	public static final String GoldenRod = "GoldenRod"; //#DAA520
	public static final String Gray = "Gray"; //#808080
	public static final String Green = "Green"; //#008000
	public static final String GreenYellow = "GreenYellow"; //#ADFF2F
	public static final String HoneyDew = "HoneyDew"; //#F0FFF0
	public static final String HotPink = "HotPink"; //#FF69B4
	public static final String IndianRed = "IndianRed"; //#CD5C5C
	public static final String Indigo = "Indigo"; //#4B0082
	public static final String Ivory = "Ivory"; //#FFFFF0
	public static final String Khaki = "Khaki"; //#F0E68C
	public static final String Lavender = "Lavender"; //#E6E6FA
	public static final String LavenderBlush = "LavenderBlush"; //#FFF0F5
	public static final String LawnGreen = "LawnGreen"; //#7CFC00
	public static final String LemonChiffon = "LemonChiffon"; //#FFFACD
	public static final String LightBlue = "LightBlue"; //#ADD8E6
	public static final String LightCoral = "LightCoral"; //#F08080
	public static final String LightCyan = "LightCyan"; //#E0FFFF
	public static final String LightGoldenRodYellow = "LightGoldenRodYellow"; //#FAFAD2
	public static final String LightGray = "LightGray"; //#D3D3D3
	public static final String LightGreen = "LightGreen"; //#90EE90
	public static final String LightPink = "LightPink"; //#FFB6C1
	public static final String LightSalmon = "LightSalmon"; //#FFA07A
	public static final String LightSeaGreen = "LightSeaGreen"; //#20B2AA
	public static final String LightSkyBlue = "LightSkyBlue"; //#87CEFA
	public static final String LightSlateGray = "LightSlateGray"; //#778899
	public static final String LightSteelBlue = "LightSteelBlue"; //#B0C4DE
	public static final String LightYellow = "LightYellow"; //#FFFFE0
	public static final String Lime = "Lime"; //#00FF00
	public static final String LimeGreen = "LimeGreen"; //#32CD32
	public static final String Linen = "Linen"; //#FAF0E6
	public static final String Magenta = "Magenta"; //#FF00FF
	public static final String Maroon = "Maroon"; //#800000
	public static final String MediumAquaMarine = "MediumAquaMarine"; //#66CDAA
	public static final String MediumBlue = "MediumBlue"; //#0000CD
	public static final String MediumOrchid = "MediumOrchid"; //#BA55D3
	public static final String MediumPurple = "MediumPurple"; //#9370DB
	public static final String MediumSeaGreen = "MediumSeaGreen"; //#3CB371
	public static final String MediumSlateBlue = "MediumSlateBlue"; //#7B68EE
	public static final String MediumSpringGreen = "MediumSpringGreen"; //#00FA9A
	public static final String MediumTurquoise = "MediumTurquoise"; //#48D1CC
	public static final String MediumVioletRed = "MediumVioletRed"; //#C71585
	public static final String MidnightBlue = "MidnightBlue"; //#191970
	public static final String MintCream = "MintCream"; //#F5FFFA
	public static final String MistyRose = "MistyRose"; //#FFE4E1
	public static final String Moccasin = "Moccasin"; //#FFE4B5
	public static final String NavajoWhite = "NavajoWhite"; //#FFDEAD
	public static final String Navy = "Navy"; //#000080
	public static final String OldLace = "OldLace"; //#FDF5E6
	public static final String Olive = "Olive"; //#808000
	public static final String OliveDrab = "OliveDrab"; //#6B8E23
	public static final String Orange = "Orange"; //#FFA500
	public static final String OrangeRed = "OrangeRed"; //#FF4500
	public static final String Orchid = "Orchid"; //#DA70D6
	public static final String PaleGoldenRod = "PaleGoldenRod"; //#EEE8AA
	public static final String PaleGreen = "PaleGreen"; //#98FB98
	public static final String PaleTurquoise = "PaleTurquoise"; //#AFEEEE
	public static final String PaleVioletRed = "PaleVioletRed"; //#DB7093
	public static final String PapayaWhip = "PapayaWhip"; //#FFEFD5
	public static final String PeachPuff = "PeachPuff"; //#FFDAB9
	public static final String Peru = "Peru"; //#CD853F
	public static final String Pink = "Pink"; //#FFC0CB
	public static final String Plum = "Plum"; //#DDA0DD
	public static final String PowderBlue = "PowderBlue"; //#B0E0E6
	public static final String Purple = "Purple"; //#800080
	public static final String Red = "Red"; //#FF0000
	public static final String RosyBrown = "RosyBrown"; //#BC8F8F
	public static final String RoyalBlue = "RoyalBlue"; //#4169E1
	public static final String SaddleBrown = "SaddleBrown"; //#8B4513
	public static final String Salmon = "Salmon"; //#FA8072
	public static final String SandyBrown = "SandyBrown"; //#F4A460
	public static final String SeaGreen = "SeaGreen"; //#2E8B57
	public static final String SeaShell = "SeaShell"; //#FFF5EE
	public static final String Sienna = "Sienna"; //#A0522D
	public static final String Silver = "Silver"; //#C0C0C0
	public static final String SkyBlue = "SkyBlue"; //#87CEEB
	public static final String SlateBlue = "SlateBlue"; //#6A5ACD
	public static final String SlateGray = "SlateGray"; //#708090
	public static final String Snow = "Snow"; //#FFFAFA
	public static final String SpringGreen = "SpringGreen"; //#00FF7F
	public static final String SteelBlue = "SteelBlue"; //#4682B4
	public static final String Tan = "Tan"; //#D2B48C
	public static final String Teal = "Teal"; //#008080
	public static final String Thistle = "Thistle"; //#D8BFD8
	public static final String Tomato = "Tomato"; //#FF6347
	public static final String Turquoise = "Turquoise"; //#40E0D0
	public static final String Violet = "Violet"; //#EE82EE
	public static final String Wheat = "Wheat"; //#F5DEB3
	public static final String White = "White"; //#FFFFFF
	public static final String WhiteSmoke = "WhiteSmoke"; //#F5F5F5
	public static final String Yellow = "Yellow"; //#FFFF00
	public static final String YellowGreen = "YellowGreen"; //#9ACD32
	
	public static Map<String, String> tagColors = null;
	public static Map<String, String> bgColors = null;
	
	
	
	
	
	/* TAG COLORS */
	
	
	
	
	/**
	 * Return map of default tag colors
	 * @return colors
	 */
	private static Map<String, String> getDefaultTagColors() {
		
		Map<String, String> colors = new HashMap<String, String>();
		
		colors.put(Aqua, "#00FFFF");
		colors.put(Blue, "#0000FF");
		colors.put(Chartreuse, "#7FFF00");
		colors.put(Crimson, "#DC143C");
		colors.put(DarkBlue, "#00008B");
		colors.put(DarkOrange, "#FF8C00");
		colors.put(DarkRed, "#8B0000");
		colors.put(DarkViolet, "#9400D3");
		colors.put(DeepPink, "#FF1493");
		colors.put(DeepSkyBlue, "#00BFFF");
		colors.put(FireBrick, "#B22222");
		colors.put(Green, "#008000");
		colors.put(Lime, "#00FF00");
		colors.put(Magenta, "#FF00FF");
		colors.put(MediumSpringGreen, "#00FA9A");
		colors.put(OliveDrab, "#6B8E23");
		colors.put(OrangeRed, "#FF4500");
		colors.put(Peru, "#CD853F");
		colors.put(Red, "#FF0000");
		colors.put(SaddleBrown, "#8B4513");
		colors.put(Teal, "#008080");
		colors.put(Tomato, "#FF6347");
		
		return colors;
		
	}
	
	
	
	
	/**
	 * Load tagColors.properties. If file doesn't exist, create new one and write default color set.
	 * @return props
	 */
	private static Properties loadTagColorsProperties() {
		
		File f = new File(OContext.CFG_DIR + "/tagColors.properties");
		Properties props = new Properties();
		
		if (f.exists()) {
			
			try {
				props.load(new FileInputStream(OContext.CFG_DIR + "/tagColors.properties"));
			} catch (FileNotFoundException e) {
				logger.error("Colors:loadTagColorsProperties(): error writing properties", e);
			} catch (IOException e) {
				logger.error("Colors:loadTagColorsProperties(): error writing properties", e);
			}
			
		} else {
			
			// create default properties file if it doesn't exist
			props.putAll(getDefaultTagColors());
			try {
			    props.store(new FileOutputStream(OContext.CFG_DIR + "/tagColors.properties"), null);
			} catch (IOException e) {
				logger.error("Colors:loadTagColorsProperties(): error writing properties", e);
			}
			
		}
		
		return props;
		
	}
	
	
	/**
	 * Get map of background colors.<br>
	 * @return tagColors
	 */
	public static Map<String, String> getTagColors() {
		
		if (tagColors != null) {
			return tagColors;
		}
		
		tagColors = new HashMap<String, String>();
		
		Properties p = loadTagColorsProperties();
		
		Enumeration<?> e = p.propertyNames();
		while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      tagColors.put(key, p.getProperty(key));
		}
		
		return tagColors;
	}
	
	
	
	
	
	
	
	/* BACKGROUND COLORS */
	
	
	
	/**
	 * Return map of default background colors
	 * @return colors
	 */
	private static Map<String, String> getDefaultBackgroundColors() {
		
		Map<String, String> colors = new HashMap<String, String>();
		
		colors.put(LightPink, "#FFB6C1");
		colors.put(LightSkyBlue, "#87CEFA");
		colors.put(Moccasin, "#FFE4B5");
		colors.put(PaleGreen, "#98FB98");
		colors.put(PaleTurquoise, "#AFEEEE");
		colors.put(Pink, "#FFC0CB");
		colors.put(Plum, "#DDA0DD");
		colors.put("MYellow", "#FFFFAA");
		colors.put("MOrange", "#FFCC99");
		colors.put("MGreen", "#CCFF99");
		colors.put("MBlue", "#CCFFFF");
		colors.put("MViolet", "#FFCCFF");
		
		return colors;
		
	}
	
	
	/**
	 * Load bgColors.properties. If file doesn't exist, create new one and write default color set.
	 * @return props
	 */
	private static Properties loadBgColorsProperties() {
		
		File f = new File(OContext.CFG_DIR + "/bgColors.properties");
		Properties props = new Properties();
		
		if (f.exists()) {
			
			try {
				props.load(new FileInputStream(OContext.CFG_DIR + "/bgColors.properties"));
			} catch (FileNotFoundException e) {
				logger.error("Colors:loadBgColorsProperties(): error writing properties", e);
			} catch (IOException e) {
				logger.error("Colors:loadBgColorsProperties(): error writing properties", e);
			}
			
		} else {
			
			// create default properties file if it doesn't exist
			props.putAll(getDefaultBackgroundColors());
			try {
			    props.store(new FileOutputStream(OContext.CFG_DIR + "/bgColors.properties"), null);
			} catch (IOException e) {
				logger.error("Colors:loadBgColorsProperties(): error writing properties", e);
			}
			
		}
		
		return props;
		
	}
	
	
	/**
	 * Get map of background colors.<br>
	 * Keep in mind, that for every background color there must be the same bgColor-* class in css.
	 * @return bgColors
	 */
	public static Map<String, String> getBgColors() {
		
		if (bgColors != null) {
			return bgColors;
		}
		
		bgColors = new HashMap<String, String>();
		
		Properties p = loadBgColorsProperties();
		
		Enumeration<?> e = p.propertyNames();
		while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      bgColors.put(key, p.getProperty(key));
		}
		
		return bgColors;
	}
	
	
}
