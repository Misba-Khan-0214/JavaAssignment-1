import java.util.*;

public class Helper_Class {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===== PHONE BOOK MENU =====");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 1 -> Contact.addContacts();
                case 2 -> Contact.searchContact();
                case 3 -> Contact.deleteContact();
                case 4 -> Contact.displayContacts();
                case 5 -> {
                    System.out.println("Exiting Phone Book... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

class Contact{
    private String name;
    private long phno;
    private ContactType type;
    public enum ContactType {
        HOME, OFFICE, OTHER
    }

    public long getPhno() {
        return phno;
    }

    public ContactType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phno=" + phno +
                ", type=" + type +
                '}';
    }

    private static final List<Contact> contacts = new ArrayList<Contact>() ;
    private static final Scanner sc = new Scanner(System.in);

    public Contact(String name, long phno, ContactType type){
        this.name=name;
        this.phno=phno;
        this.type=type;
    }
    public static void addContacts() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        long phno = Long.parseLong(sc.nextLine());


        System.out.print("Enter Type (HOME/OFFICE/OTHER): ");
        ContactType type = ContactType.valueOf(sc.nextLine().trim().toUpperCase());

        contacts.add(new Contact(name, phno, type));

        System.out.println("Contact added successfully!");
    }

    public static void searchContact() {
        System.out.println("Enter the name you want to search: ");
        String search = sc.nextLine();
        boolean found=false;

        for(Contact c: contacts) {
            if (search.equalsIgnoreCase(c.getName())) {
                System.out.println("Found: " + c);
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("No contact found with name "+search);
        }
    }

    public static void deleteContact() {
        System.out.println("Enter the name you want to delete: ");
        String delete = sc.nextLine();
        boolean removed = false;
        Iterator<Contact> it = contacts.iterator();
        while (it.hasNext()) {
            Contact c = it.next();
            if (delete.equalsIgnoreCase(c.getName())) {
                it.remove();
                System.out.println("Removed successfully!");
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("No contact found with name " + delete);
        }
    }

    public static void displayContacts() {
        System.out.println("------ ALL CONTACTS ------");
        if (contacts.isEmpty()) {
            System.out.println("No contacts available!");
            return;
        }
        contacts.forEach(System.out::println);
    }
}
