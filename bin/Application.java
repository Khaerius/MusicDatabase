import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

class UserButtons extends JPanel
{
	 JButton profile = new JButton("Wyświetl informacje o swoim profilu");
	 JButton aAlbum = new JButton("Dodaj album do swojej kolekcji");
	 JButton lAlbums = new JButton("Wyświetl swoją kolekcję albumów");
	 JButton cPlaylist = new JButton("Utwórz nową playlistę");
	 JButton aPlaylist = new JButton("Dodaj utwór do playlisty");
	 JButton lPlaylists = new JButton("Wyświetl zapisane playlisty");

	UserButtons()
	{
		setLayout(new GridLayout(6,1));

		add(profile);
		add(aAlbum);
		add(lAlbums);
		add(cPlaylist);
		add(aPlaylist);
		add(lPlaylists);
	}
	
}

class SearchPanel extends JPanel
{
	JTextField searchband = new JTextField("Wyszukaj zespoły",50);
	JButton bButton = new JButton("Szukaj zespołu");
	JTextField searchalbum = new JTextField("Wyszukaj albumu",50);
	JButton aButton = new JButton("Szukaj albumu");
	JTextField searchsong = new JTextField("Wyszukaj piosenkę",50);
	JButton sButton = new JButton("Szukaj piosenki");

	SearchPanel()
	{
		setLayout(new GridLayout(3,1));

		add(searchband);
		add(bButton);
		add(searchalbum);
		add(aButton);
		add(searchsong);
		add(sButton);
	}
}

class MainPanel extends JPanel
{
	SearchPanel spanel = new SearchPanel();
	JTextArea ta = new JTextArea();
	JScrollPane effects = new JScrollPane(ta);

	MainPanel()
	{
		setLayout(new GridLayout(2,1));

		add(spanel);
		add(effects);
	}
}

class AdminButtons extends JPanel
{
	 JButton aMusician = new JButton("Dodaj muzyka:");
	 JButton aArtist = new JButton("Dodaj zespół/artystę");
	 JButton aAlbum = new JButton("Dodaj album:");
	 JButton aLabel = new JButton("Dodaj wytwórnię:");
	 JButton aCountry = new JButton("Dodaj kraj:");
	 JButton aGenre = new JButton("Dodaj gatunek:");
	 JTextField fGenre = new JTextField("Wpisz gatunek",25);
	 JTextField fCountry = new JTextField("Wpisz kraj",25);

	AdminButtons()
	{
		setLayout(new GridLayout(6,1));

		add(aMusician);
		add(aArtist);
		add(aAlbum);
		add(aLabel);
		add(fCountry);
		add(aCountry);
		add(fGenre);
		add(aGenre);

	}

}

class AddMusicianFrame extends JFrame implements ActionListener
{
	JTextField fname = new JTextField("Wpisz imię",25);
	JTextField lname = new JTextField("Wpisz nazwisko",35);
	JTextField kraj = new JTextField("Wpisz kraj pochodzenia",25);
	JFormattedTextField date = new JFormattedTextField("__-__-____");
	JTextField instr = new JTextField("Wpisz główny instrument",25);
	JButton submit = new JButton("Dodaj");
	Db base = new Db();

	AddMusicianFrame()
	{
		super("Dodawanie muzyka");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
		setLocation(200,200);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(fname);
		add(lname);
		add(kraj);
		add(date);
		add(instr);
		add(submit);

		submit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit)
		{
			base.AddMusician(fname.getText(),lname.getText(),kraj.getText(),date.getText(),instr.getText());
			base.Close();
			this.dispose();
		}
	}
}

class AddLabelFrame extends JFrame implements ActionListener
{
	JTextField name = new JTextField("Wpisz nazwę",40);
	JTextField country = new JTextField("Wpisz kraj",25);
	JButton submit = new JButton("Dodaj");
	Db base = new Db();

	AddLabelFrame()
	{
		super("Dodawanie wytwórni");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
		setLocation(200,200);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(name);
		add(country);
		add(submit);

		submit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit)
		{
			base.AddLabel(name.getText(),country.getText());
			base.Close();
			this.dispose();
		}
	}
}

class AddArtistFrame extends JFrame implements ActionListener
{
	JTextField name = new JTextField("Wpisz nazwę",80);
	JTextField genre = new JTextField("Wpisz gatunek",25);
	JTextField country = new JTextField("Wpisz kraj",25);
	JTextField artist = new JTextField("Do jakiego artysty",80);
	JTextField fname = new JTextField("Imię muzyka",25);
	JTextField lname = new JTextField("Nazwisko muzyka",35);
	JTextArea opis = new JTextArea("Dodaj muzyków do zespołu, muzyk i zespół muszą istnieć w bazie:");
	JButton submit1 = new JButton("Dodaj zespół");
	JButton submit2 = new JButton("Dodaj członka zespołu");
	JButton exit = new JButton("Zamknij");
	Db base = new Db();

	AddArtistFrame()
	{
		super("Dodawanie artysty");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1000,600);
		setLocation(200,200);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(name);
		add(genre);
		add(country);
		add(submit1);

		opis.setEditable(false);
		add(opis);
		add(artist);
		add(fname);
		add(lname);
		add(submit2);

		add(exit);

		submit1.addActionListener(this);
		submit2.addActionListener(this);
		exit.addActionListener(this);

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{public void windowClosing(WindowEvent winEvnt){base.Close();}}
			);
		
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit1)
		{
			base.AddArtist(name.getText(),genre.getText(),country.getText());
		}
		else if(source==submit2)
		{
			base.MusiciantoArtist(artist.getText(), fname.getText(),lname.getText());
		}
		else if(source==exit)
		{
			this.dispose();
		}
	}
}

class AddAlbumFrame extends JFrame implements ActionListener
{
	JTextField name = new JTextField("Wpisz nazwę",80);
	JTextField genre = new JTextField("Wpisz gatunek",25);
	JTextField artist = new JTextField("Zespół, który nagrał album",80);
	JTextField label = new JTextField("Wytwórnia",25);
	JFormattedTextField date = new JFormattedTextField("__-__-____");
	JTextField album = new JTextField("Tytuł albumu",80);
	JTextField title = new JTextField("Tytuł piosenki",100);
	JTextField time = new JTextField("Czas trwania w sekundach",25);
	JTextArea opis = new JTextArea("Dodaj utwory do albumu:");
	JButton submit1 = new JButton("Dodaj album");
	JButton submit2 = new JButton("Dodaj utwory do albumu");
	JButton exit = new JButton("Zamknij");
	Db base = new Db();

	AddAlbumFrame()
	{
		super("Dodawanie albumu");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1000,600);
		setLocation(200,200);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(name);
		add(genre);
		add(artist);
		add(label);
		add(date);
		add(submit1);

		opis.setEditable(false);
		add(opis);
		add(album);
		add(title);
		add(time);
		add(submit2);

		add(exit);

		submit1.addActionListener(this);
		submit2.addActionListener(this);
		exit.addActionListener(this);

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{public void windowClosing(WindowEvent winEvnt){base.Close();}}
			);
		
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit1)
		{
			base.AddAlbum(name.getText(),genre.getText(),artist.getText(),label.getText(),date.getText());
		}
		else if(source==submit2)
		{
			base.AddSong(title.getText(),album.getText(),time.getText());
		}
		else if(source==exit)
		{
			this.dispose();
		}
	}
}

class AddToCollectionFrame extends JFrame implements ActionListener
{
	JTextField name = new JTextField("Wpisz tytuł albumu",40);
	JTextField artist = new JTextField("Wpisz wykonawcę",25);
	JTextField note = new JTextField("Wpisz ocenę",10);
	JButton submit = new JButton("Dodaj");
	Db base = new Db();
	String user;

	AddToCollectionFrame(String un)
	{
		super("Dodawanie albumu do kolekcji");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
		setLocation(200,200);

		user = un;

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(name);
		add(artist);
		add(note);
		add(submit);

		submit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit)
		{
			base.AddtoCollection(user,name.getText(),artist.getText(),note.getText());
			base.Close();
			this.dispose();
		}
	}
}

class CreatePlaylistFrame extends JFrame implements ActionListener
{
	JTextField name = new JTextField("Wpisz tytuł playlisty",40);
	JButton submit = new JButton("Utwórz");
	Db base = new Db();
	String user;

	CreatePlaylistFrame(String un)
	{
		super("Tworzenie nowej playlisty");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
		setLocation(200,200);

		user = un;

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(name);
		add(submit);

		submit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit)
		{
			base.CreatePlaylist(user,name.getText());
			base.Close();
			this.dispose();
		}
	}
}

class AddToPlaylistFrame extends JFrame implements ActionListener
{
	JTextField name = new JTextField("Wpisz tytuł playlisty",40);
	JTextField song = new JTextField("Wpisz utwór, który chces dodac",100);
	JTextField album = new JTextField("Wpisz album, z którego pochodzi",80);
	JButton submit = new JButton("Dodaj");
	Db base = new Db();

	AddToPlaylistFrame()
	{
		super("Dodawanie do playlisty");
		base.Establish();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
		setLocation(200,200);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(name);
		add(song);
		add(album);
		add(submit);

		submit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if(source==submit)
		{
			base.AddtoPlaylist(name.getText(),song.getText(),album.getText());
			base.Close();
			this.dispose();
		}
	}
}

public class Application extends JFrame implements ActionListener
{
	Db base = new Db();
	UserButtons userpanel = new UserButtons();
	MainPanel panel = new MainPanel();
	AdminButtons update = new AdminButtons();
	String user = "";
	String toPanel = "";
	public Application(String un)
	{
		super("Serwis Muzyczny");
		base.Establish();
		user = un;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1080,700);
		setLocation(100,50);

		setLayout(new GridLayout(1,3));

		add(userpanel);
		add(panel);

		int f = base.GetStatus(user);
		if(f==1)
		{
			add(update);
		}
		else
		{
			add(new JTextArea("Zawartość dla administratorów"));
		}
		

		update.aCountry.addActionListener(this);
		update.aGenre.addActionListener(this);
		update.aLabel.addActionListener(this);
		update.aMusician.addActionListener(this);
		update.aArtist.addActionListener(this);
		update.aAlbum.addActionListener(this);
		
		userpanel.profile.addActionListener(this);
		userpanel.aAlbum.addActionListener(this);
		userpanel.lAlbums.addActionListener(this);
		userpanel.cPlaylist.addActionListener(this);
		userpanel.aPlaylist.addActionListener(this);
		userpanel.lPlaylists.addActionListener(this);

		panel.spanel.bButton.addActionListener(this);
		panel.spanel.aButton.addActionListener(this);
		panel.spanel.sButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e){

		Object source = e.getSource();

		if(source==update.aCountry)
		{
			base.AddCountry(update.fCountry.getText());
		}
		else if(source==update.aGenre)
		{
			base.AddGenre(update.fGenre.getText());
		}
		else if(source==update.aMusician)
		{
			new AddMusicianFrame();
		}
		else if(source==update.aLabel)
		{
			new AddLabelFrame();
		}
		else if(source==update.aArtist)
		{
			new AddArtistFrame();
		}
		else if(source==update.aAlbum)
		{
			new AddAlbumFrame();
		}
		else if(source==userpanel.profile)
		{
			panel.ta.setText("");
			toPanel = base.ViewProfile(user);
			panel.ta.setText(toPanel);
		}
		else if(source==userpanel.aAlbum)
		{
			new AddToCollectionFrame(user);
		}
		else if(source==userpanel.lAlbums)
		{
			panel.ta.setText("");
			toPanel = base.ShowCollection(user);
			panel.ta.setText(toPanel);
		}
		else if(source==userpanel.cPlaylist)
		{
			new CreatePlaylistFrame(user);
		}
		else if(source==userpanel.aPlaylist)
		{
			new AddToPlaylistFrame();
		}
		else if(source==userpanel.lPlaylists)
		{
			panel.ta.setText("");
			toPanel = base.ShowPlaylists(user);
			panel.ta.setText(toPanel);
		}
		else if(source==panel.spanel.bButton)
		{
			panel.ta.setText("");
			toPanel = base.SearchEngine(1,panel.spanel.searchband.getText());
			panel.ta.setText(toPanel);
		}
		else if(source==panel.spanel.aButton)
		{
			panel.ta.setText("");
			toPanel = base.SearchEngine(2,panel.spanel.searchalbum.getText());
			panel.ta.setText(toPanel);
		}
		else if(source==panel.spanel.sButton)
		{
			panel.ta.setText("");
			toPanel = base.SearchEngine(3,panel.spanel.searchsong.getText());
			panel.ta.setText(toPanel);
		}	
	}
}
