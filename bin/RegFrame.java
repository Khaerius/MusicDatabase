import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RegPanel extends JPanel
{
	JTextField username = new JTextField(15);
	JPasswordField passwd = new JPasswordField(18);
	JTextField mail = new JTextField(30);
	JFormattedTextField date = new JFormattedTextField("__-__-____");
	JTextField phone = new JTextField(10);

	RegPanel()
	{
		setLayout(new GridLayout(5,1));
		add(new JTextArea("Nazwa użytkownika"));
		add(username);
		add(new JTextArea("Hasło"));
		add(passwd);
		add(new JTextArea("E-Mail"));
		add(mail);
		add(new JTextArea("Data urodzenia"));
		add(date);
		add(new JTextArea("Numer telefonu"));
		add(phone);
	}
}

public class RegFrame extends JFrame implements ActionListener
{

	RegPanel text = new RegPanel();
	JButton submit = new JButton("Zatwierdź");

	public RegFrame()
	{
		super( "Rejestracja" );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
		setLocation(300,300);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(text);
		add(submit);

		submit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();

		if(source==submit)
		{
			Db base = new Db();
			base.Establish();
			base.AddUser(text.username.getText(), String.valueOf(text.passwd.getPassword()), text.mail.getText(), text.date.getText(), text.phone.getText());
			new Start();
			this.dispose();

		}
	}
}