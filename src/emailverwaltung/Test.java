package emailverwaltung;

public class Test {

	public static void main(String[] args) {
		//Object[] possibleValues = { "AS400", "SQLite"};
		//Object selectedValue = JOptionPane.showInputDialog(null, "DB2 / SQLite", "Verbindungstyp", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
		
		//EmailKontaktDao.getConn(selectedValue.toString());
//		EmailKontakt contact = new EmailKontakt("Till","Schulze", "mail@email.de");
//		contact.setId(1);
//		EmailKontaktDao.insert(contact);
		
//		EmailKontaktDao.delete(contact);
//		EmailKontaktDao.printDB("AS400");
		EmailKontaktDao.printDB("SQLite");
//	
//		EmailKontakt cont = EmailKontaktDao.last();
//		System.out.println(cont.toString());
//		
//		EmailKontakt cont1 = EmailKontaktDao.first();
//		System.out.println(cont1.toString());
//		
//		EmailKontakt cont2 = EmailKontaktDao.next(contact);
//		System.out.println(cont2.toString());
		
		System.out.println(EmailKontaktDao.getDataCount());
		System.out.println("Done!");
		System.out.println("Super done!");


	}

}
