package com.mikedogg.ootp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.opencsv.CSVReader;

public class GetSeasonStats {

	public static void main(String[] args) throws IOException {
		
		// load app properties file
		
        try (InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("ootpconfig.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find ootpconfig.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            input.close();

            // print ootpconfig.properties to console
            PrintWriter writer = new PrintWriter(System.out);
            
            // print the list with a PrintWriter object
            prop.list(writer);

            // flush and close the stream
            writer.flush(); 
            writer.close();

            
            assembleStatsAndOutput(prop);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

		
	}
	
	
	private static void assembleStatsAndOutput(Properties prop) throws IOException {
		
		//read in owned players file
//		String ownedPlayerFile="D://baseball//2020//OOTP//RefFiles//master_players_file.csv";

	    Reader reader = Files.newBufferedReader(Paths.get(prop.getProperty("masterPlayerFile")), Charset.forName("windows-1252"));
	    CSVReader csvReader = new CSVReader(reader);
	    List<String[]> list = new ArrayList<String[]>();
	    list = csvReader.readAll();
	    reader.close();
	    csvReader.close();
	    
	    TreeMap<String, OwnedPlayers> ownedPlayers = new TreeMap<String, OwnedPlayers>();
	    int idx = 0;
	    for (String[] i:list) {
	    	if (idx == 0) {
	    		idx = 1;
	    		continue;
	    	}
	    	//input = owner, playerid, playername, playerteam
	    	// TreeMap key=ootpPlayerId value=OwnedPlayers instance
	    	ownedPlayers.put(i[1], new OwnedPlayers(i[1],i[0],i[3],i[2]));
	    }

	    /*
	    // read in owned MINORS players
		ownedPlayerFile="D://baseball//2020//OOTP//RefFiles//ootpMinorPlayers.csv";
	    reader = Files.newBufferedReader(Paths.get(ownedPlayerFile));
	    csvReader = new CSVReader(reader);
	    list = new ArrayList<String[]>();
	    list = csvReader.readAll();
	    reader.close();
	    csvReader.close();
	    
	    TreeMap<String, OotpMinorPlayers> ootpMinorPlayers = new TreeMap<String, OotpMinorPlayers>();
	    idx = 0;
	    for (String[] i:list) {
	    	if (idx == 0) {
	    		idx = 1;
	    		continue;
	    	}
	    	// input = owner, bxscname,ootpplayerid
	    	// TreeMap key=ootpPlayerId value=OotpMinorPlayer instance
	    	ootpMinorPlayers.put(i[2], new OotpMinorPlayers(i[0],i[1],i[2]));
	    }
*/	    
	    // parse batters
//		Document document = Jsoup.connect("https://www.baseball-reference.com/sim/leagues/MLB/2020-batting.shtml").get();
		Document document = Jsoup.connect(prop.getProperty("seasonBattingURL")).get();
		Element elementsTbody = document.getElementsByTag("tbody").first();
		
		// open output file for batters
	    FileWriter myWriter = new FileWriter(prop.getProperty("newMasterPlayerFile"));
//	    myWriter.write("Owner,PlayerId,PlayerName,Team,AB,Runs,Hits,HR,RBI,SB\n");
	    
		TreeMap<String,AllPlayersRef> allPlayersRef = new TreeMap<String, AllPlayersRef> ();

		idx  = 0;
		do {
			
			idx += 1;
			
			if (idx==2)  {
//				document = Jsoup.connect("https://www.baseball-reference.com/sim/leagues/MLB/2020-pitching.shtml").get();
				document = Jsoup.connect(prop.getProperty("seasonPitchingURL")).get();
				elementsTbody = document.getElementsByTag("tbody").first();
			}
		
			// loop through all rows to get stats for each player
			for (Element e:elementsTbody.children()) {
			    
				// load batting players into TreeMap as a means of keeping only unique occurrances
				String playerId = e.getElementsByAttributeValue("data-stat", "player").select("a").first().attr("href");
				String owner = "";
				if (ownedPlayers.containsKey(playerId))
					owner = ownedPlayers.get(playerId).getOwner();
//				else if (ootpMinorPlayers.containsKey(playerId))
//					owner = ootpMinorPlayers.get(playerId).getOwner();					
				String fullName = e.getElementsByAttributeValue("data-stat", "player").select("a").first().text();
				String team = e.getElementsByAttributeValue("data-stat", "team_ID").select("a").first().text();
				if (idx == 1) {
					int ab = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "AB").first().text());
					int r = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "R").first().text());
					int h = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "H").first().text());
					int hr = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "HR").first().text());
					int rbi = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "RBI").first().text());
					int sb = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SB").first().text());
					allPlayersRef.put(playerId,new AllPlayersRef(playerId,owner,fullName,team,ab,r,h,hr,rbi,sb,0,0,0,0,0,0,0));
				}
				else {
					int w = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "W").first().text());
					int sv = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SV").first().text());
					int hold = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "HOLD").first().text());
					float ip = Float.parseFloat(e.getElementsByAttributeValue("data-stat", "IP").first().text());
					int er = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "ER").first().text());
					int so = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "SO").first().text());
					int bb = Integer.parseInt(e.getElementsByAttributeValue("data-stat", "BB").first().text());
					if (!allPlayersRef.containsKey(playerId))
						allPlayersRef.put(playerId,new AllPlayersRef(playerId,owner,fullName,team,0,0,0,0,0,0,w,sv,hold,ip,er,so,bb));
					else
						allPlayersRef.replace (playerId,allPlayersRef.get(playerId).addPicherStats(w,sv,hold,ip,er,so,bb));
				}
	
			}
		} while (idx < 2);
	    FileWriter myWriterHit = new FileWriter(prop.getProperty("seasonBattingFile"));
	    FileWriter myWriterPit = new FileWriter(prop.getProperty("seasonPitchingFile"));
	    
	    myWriterHit.write("Owner,Player,Team,AB,R,H,HR,RBI,SB\n");
	    myWriterPit.write("Owner,Player,Team,WIN,SV,HOLD,IP,ER,SO,BB\n");
		
		myWriter.write("Owner,PlayerId,PlayerName,PlayerTeam\n");
		for (String player:allPlayersRef.keySet()) {
			myWriter.write(allPlayersRef.get(player).getOwner()+","
					+allPlayersRef.get(player).getPlayerId()+","
					+allPlayersRef.get(player).getFullName()+","
					+allPlayersRef.get(player).getTeam()
					+"\n"
			);
			if (allPlayersRef.get(player).getIp() > 0)
				myWriterPit.write(allPlayersRef.get(player).getOwner()+","
						+allPlayersRef.get(player).getFullName()+","
						+allPlayersRef.get(player).getTeam()+","
						+allPlayersRef.get(player).getW()+","
						+allPlayersRef.get(player).getSv()+","
						+allPlayersRef.get(player).getHold()+","
						+allPlayersRef.get(player).getIp()+","
						+allPlayersRef.get(player).getEr()+","
						+allPlayersRef.get(player).getSo()+","
						+allPlayersRef.get(player).getBb()+
						"\n"
				);
			else
				myWriterHit.write(allPlayersRef.get(player).getOwner()+","
						+allPlayersRef.get(player).getFullName()+","
						+allPlayersRef.get(player).getTeam()+","
						+allPlayersRef.get(player).getAb()+","
						+allPlayersRef.get(player).getR()+","
						+allPlayersRef.get(player).getH()+","
						+allPlayersRef.get(player).getHr()+","
						+allPlayersRef.get(player).getRbi()+","
						+allPlayersRef.get(player).getSb()+"\n"
				);
				
		}
		
		myWriterHit.close();	
		myWriterPit.close();	
		
		// print owned players not found
		for (String k: ownedPlayers.keySet()) {
			if (!allPlayersRef.containsKey(k)) 
				myWriter.write(ownedPlayers.get(k).getOwner()+","+k+","+ownedPlayers.get(k).getOotpName()+","+ownedPlayers.get(k).getTeam()+"\n");
		}
		/*
		for (String k: ootpMinorPlayers.keySet()) {
			if (!allPlayersRef.containsKey(k)) 
				myWriter.write(ootpMinorPlayers.get(k).getOwner()+","+k+","+ootpMinorPlayers.get(k).getBxscPlayer()+",???"+"\n");
		}
		*/
		myWriter.close();	
	}

}
