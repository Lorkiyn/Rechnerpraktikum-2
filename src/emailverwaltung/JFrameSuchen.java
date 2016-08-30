package emailverwaltung;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class JFrameSuchen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldId;
	private JTextField textFieldEmail;
	private JTable table;
	private TableModel model = new TableModel(new Object[][] {});
	private static EmailKontakt searchContact = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameSuchen frame = new JFrameSuchen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static EmailKontakt search() {
		JFrameSuchen frame = new JFrameSuchen();
		frame.setVisible(true);
		
		return searchContact;
	}
	
	
	private JFrameSuchen() {
		setResizable(false);
		setTitle("Eintrag Suchen");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 206);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(4, 8, 200, 167);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel labelId = new JLabel("ID:");
		labelId.setBounds(10, 16, 60, 20);
		panel.add(labelId);
		
		JLabel labelFirstName = new JLabel("Vorname:");
		labelFirstName.setBounds(10, 47, 60, 20);
		panel.add(labelFirstName);
		
		JLabel labelLastName = new JLabel("Nachname:");
		labelLastName.setBounds(10, 78, 60, 20);
		panel.add(labelLastName);
		
		JLabel labelEmail = new JLabel("Email:");
		labelEmail.setBounds(10, 109, 60, 20);
		panel.add(labelEmail);
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(76, 47, 114, 20);
		panel.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(76, 78, 114, 20);
		panel.add(textFieldLastName);
		textFieldLastName.setColumns(10);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(76, 16, 114, 20);
		panel.add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(76, 109, 114, 20);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JButton buttonSearch = new JButton("Suchen");
		buttonSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setData(EmailKontaktDao.select(textFieldId.getText(), textFieldFirstName.getText(), textFieldLastName.getText(), textFieldEmail.getText()));
				
			}
		});
		buttonSearch.setBounds(10, 136, 180, 20);
		panel.add(buttonSearch);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "JPanel title", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(214, 8, 270, 167);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton buttonGo = new JButton("Einf\u00FCgen");
		buttonGo.setBounds(10, 136, 250, 20);
		panel_1.add(buttonGo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 16, 250, 114);
		panel_1.add(scrollPane);
		
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(55);
		table.getColumnModel().getColumn(2).setPreferredWidth(55);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		scrollPane.setViewportView(table);
	}
}
