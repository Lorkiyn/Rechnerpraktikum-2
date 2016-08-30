package emailverwaltung;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class JFrameEmailverwaltung extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelId;
	private JLabel labelFirstName;
	private JLabel labelName;
	private JLabel labelEmail;
	private static JTextField textFieldId;
	private static JTextField textFieldFirstName;
	private static JTextField textFieldLastName;
	private static JTextField textFieldEmail;
	private JButton buttonSeach;
	private JButton buttonToStart;
	private JButton buttonBack;
	private JButton buttonNew;
	private JButton buttonEdit;
	private JButton buttonDelete;
	private JButton buttonNext;
	private JButton buttonToEnd;
	private int lastSelectedIndex = 0;
	private boolean editClicked = false;
	private boolean newClicked = false;
	private JTable tableData;
	private JScrollPane scrollPane;
	private JComboBox<Object> comboBoxDatabase;
	private TableModel model = new TableModel(EmailKontaktDao.getTableData());
	private boolean cancel = false; 
	private JPopupMenu popupMenu;
	private JMenuItem itemNew;
	private JMenuItem itemEdit;
	private JMenuItem itemDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			
					JFrameEmailverwaltung frame = new JFrameEmailverwaltung();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameEmailverwaltung() {
		setResizable(false);
		setTitle("E-Mail Verwaltung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 221);
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

		labelId = new JLabel("ID:");
		labelId.setBounds(10, 11, 100, 20);
		contentPane.add(labelId);

		labelFirstName = new JLabel("Vorname:");
		labelFirstName.setBounds(10, 42, 100, 20);
		contentPane.add(labelFirstName);

		labelName = new JLabel("Nachname:");
		labelName.setBounds(10, 73, 100, 20);
		contentPane.add(labelName);

		labelEmail = new JLabel("E-Mail:");
		labelEmail.setBounds(10, 104, 100, 20);
		contentPane.add(labelEmail);

		textFieldId = new JTextField();
		textFieldId.setBounds(120, 11, 390, 20);
		textFieldId.setEditable(false);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);

		textFieldFirstName = new JTextField();
		textFieldFirstName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				checkFields();

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFields();

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				checkFields();

			}

		});
		textFieldFirstName.setBounds(120, 42, 390, 20);
		textFieldFirstName.setEditable(false);
		textFieldFirstName.setColumns(10);
		contentPane.add(textFieldFirstName);

		textFieldLastName = new JTextField();
		textFieldLastName.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				checkFields();

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFields();

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				checkFields();

			}

		});
		textFieldLastName.setBounds(120, 73, 390, 20);
		textFieldLastName.setEditable(false);
		textFieldLastName.setColumns(10);
		contentPane.add(textFieldLastName);

		textFieldEmail = new JTextField();
		textFieldEmail.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				checkFields();

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				checkFields();

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				checkFields();

			}

		});
		textFieldEmail.setBounds(120, 104, 390, 20);
		textFieldEmail.setEditable(false);
		textFieldEmail.setColumns(10);
		contentPane.add(textFieldEmail);

		buttonSeach = new JButton("Suchen");
		buttonSeach.setBounds(250, 166, 130, 20);
		buttonSeach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrameSuchen frame = new JFrameSuchen();
				frame.setVisible(true);
				checkStatus(); 
				selectInTable(guiToContact());

			}

		});
		contentPane.add(buttonSeach);

		buttonToStart = new JButton("|<");
		buttonToStart.setBounds(120, 166, 60, 20);
		buttonToStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEditablefalse();
				EmailKontakt contact = EmailKontaktDao.first();
				fillFields(contact);
				checkStatus();
				selectInTable(guiToContact());

			}

		});
		contentPane.add(buttonToStart);

		buttonBack = new JButton("<<");
		buttonBack.setBounds(180, 166, 60, 20);
		buttonBack.setEnabled(false);
		buttonBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEditablefalse();
				EmailKontakt contact = EmailKontaktDao.previous(guiToContact());
				fillFields(contact);
				checkStatus();
				selectInTable(guiToContact());

			}

		});
		contentPane.add(buttonBack);

		buttonNew = new JButton("Neu");
		buttonNew.setBounds(250, 135, 130, 20);
		buttonNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newEntry();
				
			}

		});
		contentPane.add(buttonNew);

		buttonEdit = new JButton("Edit");
		buttonEdit.setBounds(390, 135, 60, 20);
		buttonEdit.setFont(new Font("Tahoma", Font.PLAIN, 10));
		buttonEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editEntry();
			}

		});
		contentPane.add(buttonEdit);

		buttonDelete = new JButton("X");
		buttonDelete.setBounds(450, 135, 60, 20);
		buttonDelete.setFont(new Font("Tahoma", Font.PLAIN, 10));
		buttonDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteEntry();
				
			}

		});
		contentPane.add(buttonDelete);

		buttonNext = new JButton(">>");
		buttonNext.setBounds(390, 166, 60, 20);
		buttonNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEditablefalse();
				EmailKontakt contact = EmailKontaktDao.next(guiToContact());
				fillFields(contact);
				checkStatus();
				selectInTable(guiToContact());

			}

		});
		contentPane.add(buttonNext);

		buttonToEnd = new JButton(">|");
		buttonToEnd.setBounds(450, 166, 60, 20);
		buttonToEnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEditablefalse();
				EmailKontakt contact = EmailKontaktDao.last();
				fillFields(contact);
				checkStatus();
				selectInTable(guiToContact());

			}

		});
		contentPane.add(buttonToEnd);

		comboBoxDatabase = new JComboBox<Object>(comboBoxData());
		comboBoxDatabase.setBounds(120, 135, 120, 20);
		comboBoxDatabase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(lastSelectedIndex != comboBoxDatabase.getSelectedIndex()) {
					if(comboBoxDatabase.getSelectedIndex() == 0) {
						EmailKontaktDao.setConnTypeSQLite();
						System.out.println("[MSG] [JFR] Changing to SQLite");

					} else if(comboBoxDatabase.getSelectedIndex() == 1) {
						EmailKontaktDao.setConnTypeAS400();
						System.out.println("[MSG] [JFR] Changing to AS400");

					}
					clear();
					buttonBack.setEnabled(false);
					lastSelectedIndex = comboBoxDatabase.getSelectedIndex();

				}

			}
		});
		contentPane.add(comboBoxDatabase);

		JLabel lblDatenbank = new JLabel("Datenbank:");
		lblDatenbank.setBounds(10, 135, 100, 20);
		contentPane.add(lblDatenbank);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(520, 11, 314, 176);
		contentPane.add(scrollPane);

		popupMenu = new JPopupMenu();
//		addPopup(tableData, popupMenu);
		popupMenu.setBounds(0, 0, 200, 50);
		
		itemNew = new JMenuItem("Zeile Hinzuf\u00FCgen");
		itemNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newEntry();
				
			}
		});
		popupMenu.add(itemNew);
		
		itemDelete = new JMenuItem("Zeile L\u00F6schen");
		itemDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteEntry();
				
			}
		});
		popupMenu.add(itemDelete);
		
		itemEdit = new JMenuItem("Zeile Editieren");
		itemEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editEntry();
				
			}
		});
		popupMenu.add(itemEdit);
		
		tableData = new JTable(model);
		tableData.setShowHorizontalLines(false);
		scrollPane.setViewportView(tableData);
		tableData.setRowSelectionAllowed(true);
		tableData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableData.getColumnModel().getColumn(0).setPreferredWidth(0);
		tableData.getColumnModel().getColumn(1).setPreferredWidth(40);
		tableData.getColumnModel().getColumn(2).setPreferredWidth(40);
		tableData.getColumnModel().getColumn(3).setPreferredWidth(100);
		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if(changesMade()) {
					EmailKontaktDao.update(tableToContact());
					fillFields(tableToContact());
					
				}

			}
		});
		tableData.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableData.getSelectedRow();
				if(row == -1) {
					return;
				}
				textFieldId.setText(tableData.getModel().getValueAt(row, 0).toString());
				textFieldFirstName.setText(tableData.getModel().getValueAt(row, 1).toString());
				textFieldLastName.setText(tableData.getModel().getValueAt(row, 2).toString());
				textFieldEmail.setText(tableData.getModel().getValueAt(row, 3).toString());

			}
		});
	}
	
	private void selectInTable(EmailKontakt contact) {
		for(int i = 0; i < model.getRowCount(); i++) {
			if(model.getValueAt(i, 0).toString().equals(textFieldId.getText())) {
				tableData.setRowSelectionInterval(i, i);
				
			}
			
		}
		
	}
	
	private void selectInTable(int id) {
		for(int i = 0; i < model.getRowCount(); i++) {
			if(Integer.parseInt(model.getValueAt(i, 0).toString()) == id) {
				tableData.setRowSelectionInterval(i, i);
				
			}
			
		}
		
	}


	private void editEntry() {
		if(textFieldId.getText().toString().equals("")) {
			return;
		}

		if(!editClicked) {
			setEditableNew();
			buttonEdit.setText("Save");
			disableButtonsExcept(buttonEdit);
			cancel = true;

		} else {
			EmailKontaktDao.update(guiToContact());
			buttonEdit.setText("Edit");
			setEditablefalse();
			updateTableModel();
			enableButtons();
			checkStatus();
			cancel = false;

		}

		editClicked = !editClicked;

	}
	
	private void deleteEntry() {
		if(cancel) {
			enableButtons();
			setEditablefalse();
			cancel = false;
			editClicked = false;
			newClicked = false;
			buttonEdit.setText("Edit");
			buttonNew.setText("Neu");
			clear();

		} else if(textFieldId.getText().toString().equals("")) {
			return;

		} else if(!cancel) {
			EmailKontaktDao.delete(guiToContact());
			fillFields(EmailKontaktDao.previous(guiToContact()));			
			updateTableModel();
			selectInTable(guiToContact());
			checkStatus();

		}

	}

	private void newEntry() {
		if(!newClicked) {
			clear();
			setEditableNew();
			buttonNew.setText("Save");
			disableButtonsExcept(buttonNew);
			buttonNew.setEnabled(false);
			cancel = true;

		} else {
			buttonNew.setEnabled(true);
			buttonNew.setText("Neu");
			setEditablefalse();
			
			EmailKontakt guiContact = guiToContact();
			String id = String.valueOf(EmailKontaktDao.insert(guiContact));
			int intId = Integer.parseInt(id);
			guiContact.setId(intId);
			textFieldId.setText(id);
			
			updateTableModel();
			enableButtons();
			fillFields(guiContact);
			selectInTable(intId);
			checkStatus();
			
			cancel = false;

		}

		newClicked = !newClicked;

	}
	
	private boolean changesMade() {
		EmailKontakt sqlContact = EmailKontaktDao.select(guiToContact().getId());
		EmailKontakt guiContact = tableToContact();
		
		try {
			if(sqlContact.getFirstName().equals(guiContact.getFirstName())
					&& sqlContact.getName().equals(guiContact.getName()) 
					&& sqlContact.getEmail().equals(guiContact.getEmail())) {
				
				return false;
				
			} else {
				return true;
				
			}
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	private EmailKontakt tableToContact() {
		int row;
		EmailKontakt contact = null;
		try {
			row = tableData.getSelectedRow();
			int id = Integer.parseInt(tableData.getModel().getValueAt(row, 0).toString());
			String firstName = tableData.getModel().getValueAt(row, 1).toString();
			String lastName = tableData.getModel().getValueAt(row, 2).toString();
			String email = tableData.getModel().getValueAt(row, 3).toString();
			contact = new EmailKontakt(firstName, lastName, email);
			contact.setId(id);

		} catch (NullPointerException e) {
			System.out.println("[ERR] [JFR] No row selected");

		}

		return contact;

	}

	private void checkFields() {
		if(textFieldFirstName.getText().length() > 0
				&& textFieldLastName.getText().length() > 0
				&& textFieldEmail.getText().length() > 0) {

			if(newClicked) {
				buttonNew.setEnabled(true);

			} else if (editClicked){
				buttonEdit.setEnabled(true);

			}

		} else {
			buttonNew.setEnabled(false);
			buttonEdit.setEnabled(false);

		}

	}

	private void updateTableModel() {
		model.fireUpdate();

	}

	public static void fillFields(EmailKontakt contact) {
		try {
			textFieldId.setText(String.valueOf(contact.getId()));
			textFieldFirstName.setText(contact.getFirstName());
			textFieldLastName.setText(contact.getName());
			textFieldEmail.setText(contact.getEmail());

		} catch (NullPointerException e) {
			System.out.println("[ERR] [JFR] No data found!");
		}

	}

	private EmailKontakt guiToContact() {
		EmailKontakt contact = new EmailKontakt(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldEmail.getText());

		try {	
			contact.setId(Integer.parseInt(textFieldId.getText()));

		} catch(Exception e) {

		}

		return contact; 

	}

	private void enableButtons() {
		buttonBack.setEnabled(true);
		buttonDelete.setEnabled(true);
		buttonEdit.setEnabled(true);
		buttonNew.setEnabled(true);
		buttonNext.setEnabled(true);
		buttonSeach.setEnabled(true);
		buttonToEnd.setEnabled(true);
		buttonToStart.setEnabled(true);
		comboBoxDatabase.setEnabled(true);

	}

	private void disableButtonsExcept(JButton button) {
		buttonBack.setEnabled(false);
		buttonEdit.setEnabled(false);
		buttonNew.setEnabled(false);
		buttonNext.setEnabled(false);
		buttonSeach.setEnabled(false);
		buttonToEnd.setEnabled(false);
		buttonToStart.setEnabled(false);
		comboBoxDatabase.setEnabled(false);

		button.setEnabled(true);

	}

	public static void clear() {
		textFieldEmail.setText("");
		textFieldFirstName.setText("");
		textFieldId.setText("");
		textFieldLastName.setText("");

	}

	private void setEditableNew() {
		textFieldEmail.setEditable(true);
		textFieldFirstName.setEditable(true);
		textFieldId.setEditable(false);
		textFieldLastName.setEditable(true);

	}

	private void setEditablefalse() {
		textFieldEmail.setEditable(false);
		textFieldFirstName.setEditable(false);
		textFieldId.setEditable(false);
		textFieldLastName.setEditable(false);

	}

	private Object[] comboBoxData() {
		return new Object[] {"SQLite", "AS400"};

	}

	private void checkStatus() {
		try {
			int min = EmailKontaktDao.first().getId();
			int max = EmailKontaktDao.last().getId();
			int id = 0;

			try {
				id = Integer.parseInt(textFieldId.getText().toString());

			} catch (NumberFormatException e) {


			}

			if(id == min) {
				buttonBack.setEnabled(false);

			} else {
				buttonBack.setEnabled(true);

			}

			if(id == max) {
				buttonNext.setEnabled(false);

			} else {
				buttonNext.setEnabled(true);

			}

		} catch (NullPointerException e) {

		}
		
	}
//	private static void addPopup(Component component, final JPopupMenu popup) {
//		component.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			public void mouseReleased(MouseEvent e) {
//				if (e.isPopupTrigger()) {
//					showMenu(e);
//				}
//			}
//			private void showMenu(MouseEvent e) {
//				popup.show(e.getComponent(), e.getX(), e.getY());
//			}
//		});
//	}
}
