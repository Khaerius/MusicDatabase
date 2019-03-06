import java.sql.*;

class Db
{
	Connection c = null;

	public void Establish()
	{
		 System.out.println("Sprawdzenie czy sterownik jest zarejestrowany w menadzerze");
  		try {
   			Class.forName("org.postgresql.Driver");
 	    } catch (ClassNotFoundException cnfe) {
    		System.out.println("Nie znaleziono sterownika!");
    		System.out.println("Wyduk sledzenia bledu i zakonczenie.");
   			cnfe.printStackTrace();
    		System.exit(1);
 		 }
		try{
			c = DriverManager.getConnection("jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u6rojek","u6rojek","6rojek");

		}catch (SQLException se){
			System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
    		se.printStackTrace();
   	 		System.exit(1);
		}

	}

	public void Close()
	{
		try{
			c.close();

		}catch (SQLException se){
			System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
    		se.printStackTrace();
   	 		System.exit(1);
		}
	}

	public void AddUser(String username, String passwd, String mail, String date, String phone)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.uzytkownik(nazwa_uzytkownika,email,data_urodzenia,numer_telefonu,premium) VALUES (?,?,CAST(? as date),?,FALSE)");
				pst.setString(1,username);
				pst.setString(2,mail);
				pst.setString(3,date);
				pst.setString(4,phone);
				int rows;
				rows = pst.executeUpdate();
				pst.close();

				pst = c.prepareStatement("INSERT INTO projekt.haslo(uzytkownik,haslo) VALUES ((SELECT uzytkownik_id FROM projekt.uzytkownik WHERE nazwa_uzytkownika=?),MD5(?))");
				pst.setString(1,username);
				pst.setString(2,passwd);
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public boolean LogCheck(String username, String passwd)
	{
		int f=0;
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("SELECT u.nazwa_uzytkownika, h.haslo FROM projekt.uzytkownik u JOIN projekt.haslo h ON u.uzytkownik_id=h.uzytkownik WHERE u.nazwa_uzytkownika=? AND h.haslo=MD5(?)");
				pst.setString(1,username);
				pst.setString(2,passwd);
				ResultSet rs;
				rs = pst.executeQuery();
				String name="";
				while(rs.next())
				{
					name = rs.getString(1);
				}
				rs.close();
				if(name!="")
				{
					f=1;
				}
				else
				{
					f=0;
				}
			}catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
		if(f==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void AddCountry(String name)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.kraj(nazwa) VALUES (?)");
				pst.setString(1,name);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddGenre(String name)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.gatunek(nazwa) VALUES (?)");
				pst.setString(1,name);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddMusician(String fname, String lname, String country, String date, String instr)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.muzyk(imię,nazwisko,kraj,data_urodzenia,instrument) VALUES (?,?,(SELECT kraj_id from projekt.kraj WHERE nazwa=?),CAST (? as DATE),?)");
				pst.setString(1,fname);
				pst.setString(2,lname);
				pst.setString(3,country);
				pst.setString(4,date);
				pst.setString(5,instr);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddLabel(String name, String country)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.wytwornia_plytowa(nazwa,kraj) VALUES (?,(SELECT kraj_id from projekt.kraj WHERE nazwa=?))");
				pst.setString(1,name);
				pst.setString(2,country);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddArtist(String name, String genre, String country)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.artysta(nazwa,gatunek,kraj) VALUES (?,(SELECT gatunek_id from projekt.gatunek WHERE nazwa=?),(SELECT kraj_id from projekt.kraj WHERE nazwa=?))");
				pst.setString(1,name);
				pst.setString(2,genre);
				pst.setString(3,country);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void MusiciantoArtist(String artist, String fname, String lname)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.muzyk_artysta(artysta,muzyk) VALUES ((SELECT artysta_id FROM projekt.artysta WHERE nazwa=?),(SELECT muzyk_id FROM projekt.muzyk WHERE imię=? AND nazwisko=?))");
				pst.setString(1,artist);
				pst.setString(2,fname);
				pst.setString(3,lname);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddAlbum(String name, String genre, String artist, String label, String date)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.album(tytul,gatunek,tworca,wytwornia,data_wydania,liczba_utworow,srednia_ocen) VALUES (?,(SELECT gatunek_id from projekt.gatunek WHERE nazwa=?),(SELECT artysta_id from projekt.artysta WHERE nazwa=?),(SELECT wytwornia_id from projekt.wytwornia_plytowa WHERE nazwa=?),CAST (? as DATE),0,0)");
				pst.setString(1,name);
				pst.setString(2,genre);
				pst.setString(3,artist);
				pst.setString(4,label);
				pst.setString(5,date);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddSong(String title, String album, String time)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.utwor(tytul,album,czas_trwania) VALUES (?,(SELECT album_id FROM projekt.album WHERE tytul=?),CAST (? as NUMERIC))");
				pst.setString(1,title);
				pst.setString(2,album);
				pst.setString(3,time);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public int GetStatus(String un)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("SELECT premium FROM projekt.uzytkownik WHERE nazwa_uzytkownika=?");
				pst.setString(1,un);
				ResultSet rs;
				rs = pst.executeQuery();
				String pr="";
				while(rs.next())
				{
					pr = rs.getString(1);
				}
				rs.close();
				if(pr.equals("t")==true)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
		else
		{
			System.out.println("Brak połączenia z bazą");
			return 0;
		}
		return 0;
	}

	public String ViewProfile(String un)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.dane_profilu WHERE nazwa_uzytkownika=?");
				pst.setString(1,un);
				ResultSet rs;
				rs = pst.executeQuery();
				String pr = "";
				while(rs.next())
				{
					pr = "Twoja nazwa użytkownika: ";
					pr = pr+(rs.getString(1))+"\n";
					pr = pr + "E-mail: ";
					pr = pr + (rs.getString(2))+"\n";
					pr = pr + "Numer telefonu: ";
					pr = pr + (rs.getString(3))+"\n";
					pr = pr + "Data urodzenia: ";
					pr = pr + (rs.getString(4));
				}
				rs.close();
				return pr;
			}catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
		System.out.println("Brak połączenia z bazą");
		return "Błąd połączenia";
	}

	public void AddtoCollection(String un, String album, String artist, String note)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.uzytkownik_album(album,uzytkownik,ocena) VALUES ((SELECT album_id FROM projekt.album WHERE tytul=? AND tworca=(SELECT artysta_id from projekt.artysta WHERE nazwa=?)),(SELECT uzytkownik_id FROM projekt.uzytkownik WHERE nazwa_uzytkownika=?),CAST (? as NUMERIC))");
				pst.setString(1,album);
				pst.setString(2,artist);
				pst.setString(3,un);
				pst.setString(4,note);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public String ShowCollection(String un)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.kolekcja WHERE nazwa_uzytkownika=?");
				pst.setString(1,un);
				ResultSet rs;
				rs = pst.executeQuery();
				String pr = "";
				pr = pr + "Twoja kolekcja: \n";
				while(rs.next())
				{
					pr = "Nazwa albumu: ";
					pr = pr+(rs.getString(3))+"\n";
					pr = pr + "Zespół: ";
					pr = pr + (rs.getString(4))+"\n";
					pr = pr + "Oceniłeś album na: ";
					pr = pr + (rs.getString(2))+"\n";
					pr = pr + "Średnia ocen w serwisie: ";
					pr = pr + (rs.getString(5));
					pr =  pr + "\n"+"\n";
				}
				rs.close();
				return pr;
			}catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
		System.out.println("Brak połączenia z bazą");
		return "Błąd połączenia";
	}

	public void CreatePlaylist(String un, String title)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.playlista(tytul,liczba_utworow,autor) VALUES (?,0,(SELECT uzytkownik_id FROM projekt.uzytkownik WHERE nazwa_uzytkownika=?))");
				pst.setString(1,title);
				pst.setString(2,un);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public void AddtoPlaylist(String playlista, String utwor, String album)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.playlista_utwor(playlista,utwor) VALUES ((SELECT playlista_id FROM projekt.playlista WHERE tytul=?),(SELECT utwor_id FROM projekt.utwor WHERE tytul=? AND album=(SELECT album_id from projekt.album WHERE tytul=?)))");
				pst.setString(1,playlista);
				pst.setString(2,utwor);
				pst.setString(3,album);
				int rows;
				rows = pst.executeUpdate();
				pst.close();
			}
			catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
	}

	public String ShowPlaylists(String un)
	{
		if(c!=null)
		{
			try{
				PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.playlisty WHERE autor=(SELECT uzytkownik_id FROM projekt.uzytkownik WHERE nazwa_uzytkownika=?)");
				pst.setString(1,un);
				ResultSet rs;
				rs = pst.executeQuery();
				String pr = "";
				pr = pr + "Twoje playlisty: \n";
				String flag = "";
				while(rs.next())
				{
					if(flag.equals(rs.getString(2))==false)
					{
						pr = "\nNazwa playlisty: ";
						pr = pr+(rs.getString(2))+"\n";
						flag = rs.getString(2);
					}
					pr = pr + (rs.getString(3))+"  ";
					pr = pr + (rs.getString(4))+"\n";
				}
				rs.close();
				return pr;
			}catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
		System.out.println("Brak połączenia z bazą");
		return "Błąd połączenia";
	}

	public String SearchEngine(int f, String exp)
	{
		if(c!=null)
		{
			try{
				if(f==1){
				PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.zespoły WHERE nazwa LIKE ?");
				pst.setString(1,"%"+exp+"%");
				ResultSet rs;
				rs = pst.executeQuery();
				String pr = "";
				pr = pr + "Zespoły: \n";
				String flag = "";
				while(rs.next())
				{
					if(flag.equals(rs.getString(4))==false)
					{
						pr = pr+(rs.getString(4))+"\n";
						flag = rs.getString(4);
					}
					pr = pr + (rs.getString(1))+"  ";
					pr = pr + (rs.getString(2))+"  ";
					pr = pr + (rs.getString(3))+"\n";
				}
				rs = pst.executeQuery();
				flag="";
				pr = pr + "Albumy: \n";
				while(rs.next())
				{
					if(flag.equals(rs.getString(6))==false)
					{
						pr = pr+(rs.getString(6))+"\n";
						flag = rs.getString(6);
					}
				}
				rs.close();
				return pr;
				}

				if(f==2){
				PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.zespoły WHERE tytul LIKE ?");
				pst.setString(1,"%"+exp+"%");
				ResultSet rs;
				rs = pst.executeQuery();
				String pr = "";
				pr = pr + "Albumy: (zespół) | (wytwórnia) | (data wydania) | (średnia ocen)\n";
				String flag = "";
				while(rs.next())
				{
					if(flag.equals(rs.getString(6))==false)
					{
					pr = pr + (rs.getString(6))+"  ";
					pr = pr + (rs.getString(4))+"  ";
					pr = pr + (rs.getString(7))+"  ";
					pr = pr + (rs.getString(8))+"  ";
					pr = pr + (rs.getString(9))+"  \n";
					flag = rs.getString(6);
					PreparedStatement pst2 = c.prepareStatement("SELECT tytul FROM projekt.utwory WHERE album LIKE ?");
					pst2.setString(1,"%"+exp+"%");
					ResultSet rs2;
					rs2 = pst2.executeQuery();
					pr = pr + " Lista utworów:\n";
					while(rs2.next())
					{
						pr = pr + " " + (rs2.getString(1)) + "\n";
					}
					rs2.close();
					}
				}
				rs.close();
				return pr;
				}

				if(f==3){
				PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.utwory WHERE tytul LIKE ?");
				pst.setString(1,"%"+exp+"%");
				ResultSet rs;
				rs = pst.executeQuery();
				String pr = "";
				pr = pr + "Utwory: (tytuł) | (album) | (zespół)\n";
				while(rs.next())
				{
					pr = pr + (rs.getString(1))+"  ";
					pr = pr + (rs.getString(2))+"  ";
					pr = pr + (rs.getString(3))+"  \n";
				}
				rs.close();
				return pr;
				}

			}catch(SQLException e){
				System.out.println("Blad podczas przetwarzania danych:"+e) ;
			}
		}
		System.out.println("Brak połączenia z bazą");
		return "Błąd połączenia";
	}
}
