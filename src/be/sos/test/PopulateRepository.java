package be.sos.test;


import be.sos.enumeration.BreadType;
import be.sos.enumeration.Language;
import be.sos.enumeration.PersonRole;
import be.sos.model.*;
import be.sos.repository.*;
import be.sos.utils.CommonData;
import be.sos.utils.PasswordUtils;
import be.sos.utils.RandomUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PopulateRepository {

    @Test
    void populateFiles() {
        populateTypeSandwichRepo();
        populateSandwichRepo();
        populatePersonRepo();
        populateCourseRepo();
        populateSessionRepo();
        populateOrderRepo();
    }




    void populateTypeSandwichRepo() {
        String filename = CommonData.getPathData() + "/typeSandwiches.csv";
        try {
            Files.writeString(Path.of(filename), "");
        } catch (IOException e) {
            System.out.println("Impossible to create the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        TypeSandwichRepository typeSandwichRepository = TypeSandwichFileRepository.getInstance();

        try {
            Files.writeString(Path.of(filename), typeSandwichRepository.getHeaderFile()+"\n");
        } catch (IOException e) {
            System.out.println("Impossible to write the header of the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        List<TypeSandwich> typeSandwichList = List.of(
                new TypeSandwich(1, "Viandes", "Vlees"),
                new TypeSandwich(2, "Fromage", "Kaas"),
                new TypeSandwich(3, "Poisson", "Vis"),
                new TypeSandwich(4, "Special", "Specials"),
                new TypeSandwich(5, "Végétarien", "Vegetarische specials")
        );
        for (TypeSandwich typeSandwich : typeSandwichList) {
            typeSandwichRepository.addWithId(typeSandwich);
        }
    }


    void populateSandwichRepo() {
        String filename = CommonData.getPathData() + "/sandwiches.csv";
        try {
            Files.writeString(Path.of(filename), "");
        } catch (IOException e) {
            System.out.println("Impossible to create the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        SandwichRepository sandwichRepository = SandwichFileRepository.getInstance();
        TypeSandwichRepository typeSandwichRepository = TypeSandwichFileRepository.getInstance();

        try {
            Files.writeString(Path.of(filename), sandwichRepository.getHeaderFile()+"\n");
        } catch (IOException e) {
            System.out.println("Impossible to write the header of the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }



        List<Sandwich> sandwichList = List.of(
            new Sandwich(1, Map.of(Language.FR, "Jambon", Language.NL, "Hesp"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(2, Map.of(Language.FR, "Rosbif", Language.NL, "Rosbief"), typeSandwichRepository.findById(1).get(),4.00, true, null),
            new Sandwich(3, Map.of(Language.FR, "Jambon de Parme", Language.NL, "Parmaham"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(4, Map.of(Language.FR, "Americain préparé", Language.NL, "Americain préparé"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(5, Map.of(Language.FR, "Martino", Language.NL, "Martino"), typeSandwichRepository.findById(1).get(),3.75, true, null),
            new Sandwich(6, Map.of(Language.FR, "Salami", Language.NL, "Salami"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(7, Map.of(Language.FR, "Salade de viande", Language.NL, "Vleessla"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(8, Map.of(Language.FR, "Jambon + fromage", Language.NL, "Hesp + kaas"), typeSandwichRepository.findById(1).get(),3.75,  true, null),
            new Sandwich(9, Map.of(Language.FR, "Poulet curry", Language.NL, "Kip curry"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(10, Map.of(Language.FR, "Salade de poulet", Language.NL, "Kipsla"), typeSandwichRepository.findById(1).get(),3.50, true, null),
            new Sandwich(11, Map.of(Language.FR, "Gouda", Language.NL, "Hollandse kaas"), typeSandwichRepository.findById(2).get(),3.50, true, null),
            new Sandwich(12, Map.of(Language.FR, "Brie", Language.NL, "Brie"), typeSandwichRepository.findById(2).get(),3.75, true, null),
            new Sandwich(13, Map.of(Language.FR, "Salade de crevettes", Language.NL, "Garnaalsla"), typeSandwichRepository.findById(3).get(),4.25, true, null),
            new Sandwich(14, Map.of(Language.FR, "Salade de thon", Language.NL, "Tonijnsla"), typeSandwichRepository.findById(3).get(),3.50, true, null),
            new Sandwich(15, Map.of(Language.FR, "Salade de thon piquant", Language.NL, "Pikante tonijnsla"), typeSandwichRepository.findById(3).get(),3.50, true, null),
            new Sandwich(16, Map.of(Language.FR, "Salade de saumon", Language.NL, "Zalmsla"), typeSandwichRepository.findById(3).get(),4.50, true, null),
            new Sandwich(17, Map.of(Language.FR, "Salade de saumon fumé", Language.NL, "Gerookte zalmsla"), typeSandwichRepository.findById(3).get(),4.50, true, null),
            new Sandwich(18, Map.of(Language.FR, "Salade de crabe", Language.NL, "Krabsla"), typeSandwichRepository.findById(3).get(),4.50, true, null),
            new Sandwich(19, Map.of(Language.FR, "Pinky", Language.NL, "Pinky"), typeSandwichRepository.findById(4).get(),4.50,false,
                    List.<Ingredient>of(new Ingredient("salade","sla"), new Ingredient("tomates","tomaat"), new Ingredient("oeufs","ei"), new Ingredient("filet de hareng","haringfilet"), new Ingredient("tartare","tartaar"))),
            new Sandwich(20, Map.of(Language.FR, "Hawaiien", Language.NL, "Hawaiibroodje"), typeSandwichRepository.findById(4).get(),4.50,false,
                    List.<Ingredient>of(new Ingredient("ananas","ananas"), new Ingredient("jambon","hesp"), new Ingredient("fromage","kass"), new Ingredient("sauce de cocktail","cocktailsaus"))),
            new Sandwich(21, Map.of(Language.FR, "Florida", Language.NL, "Florida"), typeSandwichRepository.findById(4).get(),4.50,false,
                    List.<Ingredient>of(new Ingredient("salade","sla"), new Ingredient("ananas","ananas"), new Ingredient("blanc de poulet","kipfilet"), new Ingredient("sauce de cocktail","cocktailsaus"))),
            new Sandwich(22, Map.of(Language.FR, "Primus", Language.NL, "Primus"), typeSandwichRepository.findById(4).get(),4.50,false,
                    List.<Ingredient>of(new Ingredient("salade","sla"), new Ingredient("pêches","perzik"), new Ingredient("salade de thon","tonijnsla"))),
                new Sandwich(23, Map.of(Language.FR, "Milano", Language.NL, "Milano"), typeSandwichRepository.findById(4).get(),4.50,false,
                        List.<Ingredient>of(new Ingredient("salade","sla"), new Ingredient("tomates","tomaat"), new Ingredient("cornichon","augruk"), new Ingredient("salami","salami"), new Ingredient("tartare","tartaar"))),
            new Sandwich(24, Map.of(Language.FR, "Ardenne", Language.NL, "Ardenne"), typeSandwichRepository.findById(4).get(),4.50,false,
                    List.<Ingredient>of(new Ingredient("rucola","rucola"), new Ingredient("tomates","tomaat"), new Ingredient("jambon de Parme","parmaham"), new Ingredient("sauce de cocktail","cocktailsaus"))),
            new Sandwich(25, Map.of(Language.FR, "Maya", Language.NL, "Maya"), typeSandwichRepository.findById(5).get(),5.00,false,
                    List.<Ingredient>of(new Ingredient("rucola","rucola"), new Ingredient("brie","brie"), new Ingredient("miel","honing"), new Ingredient("pêche","perzik"))),
            new Sandwich(26, Map.of(Language.FR, "Fromage du chèvre", Language.NL, "Broodje geitenkaas"), typeSandwichRepository.findById(5).get(),5.00,false,
                    List.<Ingredient>of(new Ingredient("fromage de chèvre","geitenkaas"), new Ingredient("miel","honing"), new Ingredient("noix","walnoten"), new Ingredient("rucola","rucola"))),
            new Sandwich(27, Map.of(Language.FR, "Printemps", Language.NL, "Lentebroodje"), typeSandwichRepository.findById(5).get(),5.00,false,
                    List.<Ingredient>of(new Ingredient("salade celeri","seldersla"), new Ingredient("carottes","wortel"), new Ingredient("ananas","ananas"))),
            new Sandwich(28, Map.of(Language.FR, "Mozzarella", Language.NL, "Mozzarella"), typeSandwichRepository.findById(5).get(),4.75,false,
                    List.<Ingredient>of(new Ingredient("huile d'olive","olijfolie"), new Ingredient("sel","zout"), new Ingredient("poivre","peper"), new Ingredient("basilic","basilicum"), new Ingredient("tomates","tomaat"))),
            new Sandwich(29, Map.of(Language.FR, "\"Santé\"", Language.NL, "Gezond"), typeSandwichRepository.findById(5).get(),5.00,false,
                    List.<Ingredient>of(new Ingredient("salade","sla"), new Ingredient("tomates","tomaat"), new Ingredient("salade de céleri","seldersla"), new Ingredient("carottes","wortel"), new Ingredient("concombre","komkommer"), new Ingredient("oeuf","ei"), new Ingredient("mayo","mayo"))),
            new Sandwich(30, Map.of(Language.FR, "Végan", Language.NL, "Vegan  broodje"), typeSandwichRepository.findById(5).get(),4.00,false,
                    List.<Ingredient>of(new Ingredient("salade végane au curry","vegan curry"), new Ingredient("salade","sla"), new Ingredient("tomate","tomaat"), new Ingredient("carottes","wortel"), new Ingredient("concombre","komkommer"), new Ingredient("maïs","maïs"), new Ingredient("rucola","rucola"), new Ingredient("ananas","ananas")))
                );
        for (Sandwich sandwich : sandwichList) {
            sandwichRepository.addWithId(sandwich);
        }
    }

    void populatePersonRepo() {

        String filename = CommonData.getPathData() + "/persons.csv";
        try {
            Files.writeString(Path.of(filename), "");
        } catch (IOException e) {
            System.out.println("Impossible to create the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        PersonRepository personRepository = PersonFileRepository.getInstance();

        try {
            Files.writeString(Path.of(filename), personRepository.getHeaderFile()+"\n");
        } catch (IOException e) {
            System.out.println("Impossible to write the header of the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }


        List<Person> personList = List.of(
                new Person(1, PersonRole.INSTRUCTOR, "Harry", "Potter", Language.NL, "1@1.be", PasswordUtils.hashPassword("1234")),
                new Person(2, PersonRole.INSTRUCTOR, "Hermione", "Granger", Language.FR, "2@2.be", PasswordUtils.hashPassword("1234")),
                new Person(3, PersonRole.INSTRUCTOR, "Ron", "Weasley", Language.NL, "3@3.be", PasswordUtils.hashPassword("1234")),
                new Person(4, PersonRole.INSTRUCTOR, "Albus", "Dumbledore", Language.FR, "4@4.be", PasswordUtils.hashPassword("1234")),
                new Person(5, PersonRole.STUDENT, "Toto", "Snape", Language.NL, "5@5.be", PasswordUtils.hashPassword("123")),
                new Person(6, PersonRole.STUDENT, "Draco", "Malfoy", Language.FR, "6@6.be", PasswordUtils.hashPassword("123")),
                new Person(7, PersonRole.STUDENT, "Neville", "Longbottom", Language.NL, "7@7.be", PasswordUtils.hashPassword("123")),
                new Person(8, PersonRole.STUDENT, "Luna", "Lovegood", Language.FR, "8@8.be", PasswordUtils.hashPassword("123")),
                new Person(9, PersonRole.STUDENT, "Ginny", "Weasley", Language.NL, "9@9.be", PasswordUtils.hashPassword("123")),
                new Person(10, PersonRole.STUDENT, "Cedric", "Diggory", Language.FR, "10@10.be", PasswordUtils.hashPassword("123")),
                new Person(11, PersonRole.STUDENT, "Cho", "Chang", Language.NL, "11@11.be", PasswordUtils.hashPassword("123")),
                new Person(12, PersonRole.STUDENT, "Fred", "Weasley", Language.FR, "12@12.be", PasswordUtils.hashPassword("123")),
                new Person(13, PersonRole.STUDENT, "George", "Weasley", Language.NL, "13@13.be", PasswordUtils.hashPassword("123")),
                new Person(14, PersonRole.STUDENT, "Fleur", "Delacour", Language.FR, "14@14.be", PasswordUtils.hashPassword("123")),
                new Person(15, PersonRole.STUDENT, "Viktor", "Krum", Language.NL, "15@15.be", PasswordUtils.hashPassword("123")),
                new Person(16, PersonRole.STUDENT, "Minerva", "McGonagall", Language.FR, "16@16.be", PasswordUtils.hashPassword("123")),
                new Person(17, PersonRole.STUDENT, "Rubeus", "Hagrid", Language.NL, "17@17.be", PasswordUtils.hashPassword("123")),
                new Person(18, PersonRole.STUDENT, "Sirius", "Black", Language.FR, "18@18.be", PasswordUtils.hashPassword("123")),
                new Person(19, PersonRole.OFFICE_MANAGER, "Remus", "Lupin", Language.NL, "19@19.be", PasswordUtils.hashPassword("12345")),
                new Person(20, PersonRole.OFFICE_MANAGER, "Nymphadora", "Tonks", Language.FR, "20@20.be", PasswordUtils.hashPassword("12345"))
        );

        for (Person person : personList) {
            personRepository.addWithId(person);
        }
    }

    void populateCourseRepo() {
        String filename = CommonData.getPathData() + "/courses.csv";
        try {
            Files.writeString(Path.of(filename), "");
        } catch (IOException e) {
            System.out.println("Impossible to create the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }
        CourseRepository courseRepository = CourseFileRepository.getInstance();

        try {
            Files.writeString(Path.of(filename), courseRepository.getHeaderFile()+"\n");
        } catch (IOException e) {
            System.out.println("Impossible to write the header of the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        List<Course> courseList = List.of(
                new Course(1, "Java Programming"),
                new Course(2, "Web Development"),
                new Course(3, "Data Science"),
                new Course(4, "Mobile App Development"),
                new Course(5, "C++ Programming"),
                new Course(6, "VB.Net Programming"),
                new Course(7, "C# Programming"),
                new Course(8, "Python Programming"),
                new Course(9, "JS Programming"),
                new Course(10, "Ruby Programming")

        );
        for (Course course : courseList) {
            courseRepository.addWithId(course);
        }
    }


    void populateSessionRepo() {

        String filename = CommonData.getPathData() + "/sessions.csv";
        try {
            Files.writeString(Path.of(filename), "");
        } catch (IOException e) {
            System.out.println("Impossible to create the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        SessionRepository sessionpersonRepository = SessionFileRepository.getInstance();

        try {
            Files.writeString(Path.of(filename), sessionpersonRepository.getHeaderFile()+"\n");
        } catch (IOException e) {
            System.out.println("Impossible to write the header of the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }

        CourseRepository cr = CourseFileRepository.getInstance();
        PersonRepository pr = PersonFileRepository.getInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");

        //     public Session(Long id, Course course, LocalDate startDate, LocalDate endDate, String location, Person instructor, List<Person> students) {
        List<Session> sessionList = List.of(
                new Session(1L, cr.findById(1).get(), LocalDate.parse("1/12/2024", dtf), LocalDate.parse("15/12/2024", dtf), "Brussel", pr.findById(1).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(2L, cr.findById(2).get(), LocalDate.parse("1/1/2025", dtf), LocalDate.parse("15/2/2025", dtf), "Brussel", pr.findById(2).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(3L, cr.findById(5).get(), LocalDate.parse("1/5/2025", dtf), LocalDate.parse("18/5/2025", dtf), "Brussel", pr.findById(4).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(4L, cr.findById(7).get(), LocalDate.parse("1/9/2025", dtf), LocalDate.parse("15/12/2025", dtf), "Brussel", pr.findById(2).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(5L, cr.findById(1).get(), LocalDate.parse("1/12/2025", dtf), LocalDate.parse("15/12/2026", dtf), "Brussel", pr.findById(3).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(6L, cr.findById(8).get(), LocalDate.parse("1/12/2026", dtf), LocalDate.parse("15/12/2026", dtf), "Brussel", pr.findById(4).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(7L, cr.findById(6).get(), LocalDate.parse("1/5/2026", dtf), LocalDate.parse("15/6/2026", dtf), "Brussel", pr.findById(1).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(8L, cr.findById(9).get(), LocalDate.parse("1/5/2026", dtf), LocalDate.parse("15/5/2026", dtf), "Brussel", pr.findById(2).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(9L, cr.findById(3).get(), LocalDate.now().minusDays(16), LocalDate.now().minusDays(2), "Brussel", pr.findById(1).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(10L, cr.findById(5).get(), LocalDate.now().plusDays(3), LocalDate.now().plusDays(15), "Brussel", pr.findById(3).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(11L, cr.findById(5).get(), LocalDate.now().minusDays(1), LocalDate.now().plusDays(3), "Brussel", pr.findById(1).get(), pr.findByIds(List.of(6,8,12,15,16))),
                new Session(12L, cr.findById(5).get(), LocalDate.now().minusDays(3), LocalDate.now().plusDays(3), "Brussel", pr.findById(2).get(), pr.findByIds(List.of(6,8,12,15,16)))
        );

        for (Session session : sessionList) {
            sessionpersonRepository.addWithId(session);
        }
    }


    void populateOrderRepo() {

        String filename = CommonData.getPathData() + "/orders.ndjson";
        try {
            Files.writeString(Path.of(filename), "");
        } catch (IOException e) {
            System.out.println("Impossible to create the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }


        OrderRepository orderRepository = OrderFileRepository.getInstance();

        /*try {
            Files.writeString(Path.of(filename), orderRepository.getHeaderFile()+"\n");
        } catch (IOException e) {
            System.out.println("Impossible to write the header of the file \"" + filename +"\"");
            throw new RuntimeException(e);
        }*/

        SessionRepository sesRepo = SessionFileRepository.getInstance();
        SandwichRepository sandRepo = SandwichFileRepository.getInstance();
        PersonRepository pr = PersonFileRepository.getInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy");

        //     public Session(Long id, Course course, LocalDate startDate, LocalDate endDate, String location, Person instructor, List<Person> students) {
        List<Order> orderList = new ArrayList<>();

        for(long i = 1; i <= 8; i++) {
            Session session = sesRepo.findById(i).get();
            for(int j = 0 ; j < 10; j++) {
                LocalDate date = RandomUtils.randomDate(session.getStartDate(), session.getEndDate());
                Person orderer = RandomUtils.randomElement(session.getStudents());
                int nbrSandwich = RandomUtils.randomInt(1,2);
                List<SandwichWithOptions> sandwichList = new ArrayList<>();
                for(int k = 0; k < nbrSandwich; k++) {
                    sandwichList.add(new SandwichWithOptions(RandomUtils.randomElement(sandRepo.findAll()), RandomUtils.randomBoolean(),RandomUtils.randomEnum(BreadType.class)));
                }
                orderList.add(new Order(null, date, orderer, session, sandwichList, "Remark" + ((i-1) * 10 + j + 1)));
            }
        }


        for (Order order : orderList) {
            orderRepository.add(order);
        }
    }
}
