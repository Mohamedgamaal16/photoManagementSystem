import business_logic.PhotoManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import business_logic.PhotoManager;
import data.Photo;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PhotoManager manager = new PhotoManager();
        System.out.println("Welcome to Photo Manager!");

        // Sample photos
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
        Photo p1 = new Photo("1", "mohamed", "Miami", today, 25.7617, -80.1918);
        Photo p2 = new Photo("2", "city.png", "New York", today, 40.7128, -74.0060);
        Photo p3 = new Photo("3", "mohamed", "Dubai", today, 25.276987, 55.296249);
        Photo p4 = new Photo("4", "beach.png", "Miami", today, 25.7617, -80.1918);
        Photo p5 = new Photo("5", "gemy", "New York", today, 40.7128, -74.0060);
        Photo p6 = new Photo("6", "mohamed", "Dubai", today, 25.276987, 55.296249);
        Photo p7 = new Photo("7", "beach.png", "Miami", today, 25.7617, -80.1918);
        Photo p8 = new Photo("8", "wael", "New York", today, 40.7128, -74.0060);
        Photo p9 = new Photo("9", "home", "Dubai", today, 25.276987, 55.296249);

        p1.addTag("Eiffel");
        p1.addTag("vacation");
        p5.addTag("vacation");p8.addTag("vacation");p6.addTag("vacation");p7.addTag("vacation");
        p2.addTag("food");

        p5.addTag("food");
        p6.addTag("food");
        p8.addTag("food");
        p3.addTag("BigBen");
        p1.addTag("BigBen");
        p2.addTag("BigBen");
        p7.addTag("BigBen");
        p5.addTag("vacation");

        manager.uploadPhoto(p1);
        manager.uploadPhoto(p2);
        manager.uploadPhoto(p3);
        manager.uploadPhoto(p4);
        manager.uploadPhoto(p5);
        manager.uploadPhoto(p6);
        manager.uploadPhoto(p7);
        manager.uploadPhoto(p8);
        manager.uploadPhoto(p9);

        while (true) {
            System.out.println("\n--- Photo Manager Menu ---");
            System.out.println("1. Upload a New Photo");
            System.out.println("2. Add Tag to Photo");
            System.out.println("3. Search by Tag");
            System.out.println("4. Search by Location");
            System.out.println("5. Search by Date");
            System.out.println("6. Search by Date Range");
            System.out.println("7. Search by Geo Range");
            System.out.println("8. Weighted Search");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Photo ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Location: ");
                    String location = scanner.nextLine();
                    Date date = inputDate(scanner, "Enter Date (yyyy-MM-dd): ");
                    System.out.print("Enter Latitude: ");
                    double lat = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Longitude: ");
                    double lon = Double.parseDouble(scanner.nextLine());


                    System.out.print("Enter Location: ");
                    String filename = scanner.nextLine();


                    Photo photo = new Photo(id, filename, location, date, lat, lon);
                    manager.uploadPhoto(photo);
                    System.out.println("Photo uploaded.");
                    break;

                case 2:
                    System.out.print("Enter Photo ID: ");
                    String pid = scanner.nextLine();
                    System.out.print("Enter Tag to Add: ");
                    String tag = scanner.nextLine();
                    manager.addTagToPhoto(pid, tag);
                    System.out.println("Tag added to photo.");
                    break;

                case 3:
                    System.out.print("Enter tag: ");
                    tag = scanner.nextLine();
                    printPhotos(manager.searchByTag(tag));
                    break;

                case 4:
                    System.out.print("Enter location: ");
                    location = scanner.nextLine();
                    printPhotos(manager.searchByLocation(location));
                    break;

                case 5:
                    date = inputDate(scanner, "Enter date (yyyy-MM-dd): ");
                    printPhotos(manager.searchByDate(date));
                    break;

                case 6:
                    Date from = inputDate(scanner, "Enter start date (yyyy-MM-dd): ");
                    Date to = inputDate(scanner, "Enter end date (yyyy-MM-dd): ");
                    printPhotos(manager.searchByDateRange(from, to));
                    break;

                case 7:
                    System.out.print("Enter min latitude: ");
                    double minLat = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter max latitude: ");
                    double maxLat = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter min longitude: ");
                    double minLon = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter max longitude: ");
                    double maxLon = Double.parseDouble(scanner.nextLine());
                    printPhotos(manager.searchByGeoRange(minLat, maxLat, minLon, maxLon));
                    break;

                case 8:
                    System.out.print("Enter tag: ");
                    String wTag = scanner.nextLine();
                    System.out.print("Enter location: ");
                    String wLoc = scanner.nextLine();
                    Date wDate = inputDate(scanner, "Enter date (yyyy-MM-dd): ");
                    printPhotos(manager.weightedSearch(wTag, wLoc, wDate));
                    break;

                case 0:
                    System.out.println("Exiting. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static Date inputDate(Scanner scanner, String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine();
                return sdf.parse(dateStr);
            } catch (Exception e) {
                System.out.println("Invalid format. Try again.");
            }
        }
    }

    private static void printPhotos(Collection<Photo> photos) {
        if (photos.isEmpty()) {
            System.out.println("No photos found.");
        } else {
            for (Photo p : photos) {
                System.out.println(p);
            }
        }
    }
}
