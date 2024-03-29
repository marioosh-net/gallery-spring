package net.marioosh.gallery.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

public class UndefinedUtils {

	private final static SecureRandom r = new SecureRandom();
	
	private static Logger log = Logger.getLogger(UndefinedUtils.class);
	
	/**
	 * wygeneruj hash wykorzystujac podany string
	 * @param seed
	 * @return
	 */
	public static String getHash(String seed) {
		String random = new BigInteger(130, r).toString(32);
		return random;
	}
	
	public static String nextRandomString() {
		String random = new BigInteger(130, r).toString(32);
		return random.substring(0, 10);
	}
	
	public static String randomNumber(final int length) {
	    String alphabet = "0123456789";
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
	    }
	    return sb.toString();
	}
	
	public static String randomString(final int length) {
	    String alphabet = "abcdefghijklmnopqrstuvwxyz";
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
	    }
	    return sb.toString();
	}
	
	public static String randomWord() {
	
		String[] x =
			{"a","able","about","absolute","accept","account","achieve","across","act",
				"active","actual","add","address","admit","advertise","affect","afford",
				"after","afternoon","again","against","age","agent","ago","agree","air",
				"all","allow","almost","along","already","alright","also","although","always",
				"america","amount","and","another","answer","any","apart","apparent","appear",
				"apply","appoint","approach","appropriate","area","argue","arm","around","arrange",
				"art","as","ask","associate","assume","at","attend","authority","available","aware",
				"away","awful","baby","back","bad","bag","balance","ball","bank","bar","base",
				"basis","be","bear","beat","beauty","because","become","bed","before","begin",
				"behind","believe","benefit","best","bet","between","big","bill","birth","bit",
				"black","bloke","blood","blow","blue","board","boat","body","book","both",
				"bother","bottle","bottom","box","boy","break","brief","brilliant","bring",
				"britain","brother","budget","build","bus","business","busy","but","buy",
				"by","cake","call","can","car","card","care","carry","case","cat","catch",
				"cause","cent","centre","certain","chair","chairman","chance","change","chap",
				"character","charge","cheap","check","child","choice","choose","Christ",
				"Christmas","church","city","claim","class","clean","clear","client","clock",
				"close","closes","clothe","club","coffee","cold","colleague","collect","college",
				"colour","come","comment","commit","committee","common","community","company","compare",
				"complete","compute","concern","condition","confer","consider","consult","contact",
				"continue","contract","control","converse","cook","copy","corner","correct","cost",
				"could","council","count","country","county","couple","course","court","cover",
				"create","cross","cup","current","cut","dad","danger","date","day","dead","deal",
				"dear","debate","decide","decision","deep","definite","degree","department",
				"depend","describe","design","detail","develop","die","difference","difficult",
				"dinner","direct","discuss","district","divide","do","doctor","document","dog",
				"door","double","doubt","down","draw","dress","drink","drive","drop","dry","due",
				"during","each","early","east","easy","eat","economy","educate","effect","egg",
				"eight","either","elect","electric","eleven","else","employ","encourage","end",
				"engine","english","enjoy","enough","enter","environment","equal","especial",
				"europe","even","evening","ever","every","evidence","exact","example","except",
				"excuse","exercise","exist","expect","expense","experience","explain","express",
				"extra","eye","face","fact","fair","fall","family","far","farm","fast","father",
				"favour","feed","feel","few","field","fight","figure","file","fill","film","final",
				"finance","find","fine","finish","fire","first","fish","fit","five","flat","floor",
				"fly","follow","food","foot","for","force","forget","form","fortune","forward",
				"four","france","free","friday","friend","from","front","full","fun","function",
				"fund","further","future","game","garden","gas","general","germany","get","girl",
				"give","glass","go","god","good","goodbye","govern","grand","grant","great",
				"green","ground","group","grow","guess","guy","hair","half","hall","hand",
				"hang","happen","happy","hard","hate","have","he","head","health","hear","heart",
				"heat","heavy","hell","help","here","high","history","hit","hold","holiday",
				"home","honest","hope","horse","hospital","hot","hour","house","how",
				"however","hullo","hundred","husband","idea","identify","if","imagine","important",
				"improve","in","include","income","increase","indeed","individual","industry",
				"inform","inside","instead","insure","interest","into","introduce","invest",
				"involve","issue","it","item","jesus","job","join","judge","jump","just","keep",
				"key","kid","kill","kind","king","kitchen","knock","know","labour","lad","lady",
				"land","language","large","last","late","laugh","law","lay","lead","learn","leave",
				"left","leg","less","let","letter","level","lie","life","light","like","likely",
				"limit","line","link","list","listen","little","live","load","local","lock",
				"london","long","look","lord","lose","lot","love","low","luck","lunch","machine",
				"main","major","make","man","manage","many","mark","market","marry","match",
				"matter","may","maybe","mean","meaning","measure","meet","member","mention","middle",
				"might","mile","milk","million","mind","minister","minus","minute","miss","mister",
				"moment","monday","money","month","more","morning","most","mother","motion","move",
				"mrs","much","music","must","name","nation","nature","near","necessary","need",
				"never","new","news","next","nice","night","nine","no","non","none","normal",
				"north","not","note","notice","now","number","obvious","occasion","odd","of","off",
				"offer","office","often","okay","old","on","once","one","only","open","operate",
				"opportunity","oppose","or","order","organize","original","other","otherwise","ought",
				"out","over","own","pack","page","paint","pair","paper","paragraph","pardon","parent",
				"park","part","particular","party","pass","past","pay","pence","pension","people",
				"per","percent","perfect","perhaps","period","person","photograph","pick","picture",
				"piece","place","plan","play","please","plus","point","police","policy","politic",
				"poor","position","positive","possible","post","pound","power","practise","prepare",
				"present","press","pressure","presume","pretty","previous","price","print","private",
				"probable","problem","proceed","process","produce","product","programme","project",
				"proper","propose","protect","provide","public","pull","purpose","push","put",
				"quality","quarter","question","quick","quid","quiet","quite","radio","rail",
				"raise","range","rate","rather","read","ready","real","realise","really","reason",
				"receive","recent","reckon","recognize","recommend","record","red","reduce","refer",
				"regard","region","relation","remember","report","represent","require","research",
				"resource","respect","responsible","rest","result","return","rid","right","ring",
				"rise","road","role","roll","room","round","rule","run","","safe","sale","same",
				"saturday","save","say","scheme","school","science","score","scotland","seat",
				"second","secretary","section","secure","see","seem","self","sell","send","sense",
				"separate","serious","serve","service","set","settle","seven","sex","shall","share",
				"she","sheet","shoe","shoot","shop","short","should","show","shut","sick","side",
				"sign","similar","simple","since","sing","single","sir","sister","sit","site",
				"situate","six","size","sleep","slight","slow","small","smoke","so","social",
				"society","some","son","soon","sorry","sort","sound","south","space","speak",
				"special","specific","speed","spell","spend","square","staff","stage","stairs",
				"stand","standard","start","state","station","stay","step","stick","still","stop",
				"story","straight","strategy","street","strike","strong","structure","student","study",
				"stuff","stupid","subject","succeed","such","sudden","suggest","suit","summer","sun",
				"sunday","supply","support","suppose","sure","surprise","switch","system","","table",
				"take","talk","tape","tax","tea","teach","team","telephone","television","tell","ten",
				"tend","term","terrible","test","than","thank","the","then","there","therefore","they",
				"thing","think","thirteen","thirty","this","thou","though","thousand","three","through",
				"throw","thursday","tie","time","to","today","together","tomorrow","tonight","too","top",
				"total","touch","toward","town","trade","traffic","train","transport","travel","treat",
				"tree","trouble","true","trust","try","tuesday","turn","twelve","twenty","two","type",
				"under","understand","union","unit","unite","university","unless","until","up","upon",
				"use","usual","value","various","very","video","view","village","visit","vote","wage",
				"wait","walk","wall","want","war","warm","wash","waste","watch","water","way","we",
				"wear","wednesday","wee","week","weigh","welcome","well","west","what","when","where",
				"whether","which","while","white","who","whole","why","wide","wife","will","win","wind",
				"window","wish","with","within","without","woman","wonder","wood","word","work","world",
				"worry","worse","worth","would","write","wrong"};
		Integer random = (int) (Math.random() * x.length);
		return x[random];
	}

	public static String randomWords(int maxcount) {
		int c = (int)(Math.random() * maxcount) + 1;
		String text = randomWord();
		while(--c > 0) {
			text += " " + randomWord();
		}
		return text;
	}

	public static int[][] pages(int count, int perPage) {
		int pages = count / perPage + (count % perPage == 0 ? 0 : 1);
		int[][] p = new int[pages][3];
		for(int i = 0; i < p.length; i++) {
			p[i][0] = i + 1; 
			p[i][1] = i * perPage; 
			p[i][2] = ((i+1) * perPage) - 1;
			// log.debug(i + "," + p[i][0] + "," +p[i][1] + "," + p[i][2] );
		}
		return p;
	}

	public static String relativePath(File base, File path) {
		return base.toURI().relativize(path.toURI()).getPath();
	}
	public static String relativePath(String base, String path) {
		return new File(base).toURI().relativize(new File(path).toURI()).getPath();
	}
	
	public static String absolutePath(String basePath, String path) {
		try {
			File f = new File(basePath, path);
			return f.getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the
		 * Reader.read(char[] buffer) method. We iterate until the
		 * Reader return -1 which means there's no more data to
		 * read. We use the StringWriter class to produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}	
}
