import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LogPanel extends JPanel
{
	JTextField username = new JTextField(15);
	JPasswordField passwd = new JPasswordField(18);

	LogPanel()
	{
		setLayout(new GridLayout(2,1));
		add(new JTextArea("Nazwa użytkownika"));
		add(username);
		add(new JTextArea("Hasło"));
		add(passwd);
	}

	String getterN()
	{
		return username.getText();
	}

	String getterP()
	{
		return String.valueOf(passwd.getPassword());
	}
}

public class LogFrame extends JFrame  implements ActionListener
{
	LogPanel text = new LogPanel();
	JButton submit = new JButton("Zaloguj");

	public LogFrame()
	{
		super( "Logowanie" );
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
			boolean flag;
			Db base = new Db();
			base.Establish();
			flag = base.LogCheck(text.getterN(),text.getterP());
			base.Close();
			if(flag==true)
			{
				new Application(text.getterN());
				this.dispose();
			}
			else
			{
				System.out.println("Nieprawidłowe dane");
			}
		}
	}
}