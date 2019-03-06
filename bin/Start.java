import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class StartButtons extends JPanel implements ActionListener
{
	private JButton log;
	private JButton reg;
	private Start p;

	public StartButtons(Start parent)
	{
		p = parent;
		log = new JButton("Logowanie");
		reg = new JButton("Rejestracja");

		log.addActionListener(this);
		reg.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(log);
		add(reg);
	}

	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();

		if(source==log)
		{
			new LogFrame();
			p.dispose();
		}

		else if(source==reg)
		{
			new RegFrame();
			p.dispose();
		}
	}
}

class Start extends JFrame
{
	public Start()
	{
		super( "Serwis Muzyczny" );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(400,300);
		setLocation(600,600);
		
		JPanel buttons = new StartButtons(this);
		add(buttons);
		
	}
}