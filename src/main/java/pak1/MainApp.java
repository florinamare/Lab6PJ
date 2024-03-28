package pak1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainApp {
    public static void writeFile(List<Angajat> list) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            File file = new File("src/main/resources/angajati.json");
            mapper.writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Angajat> readFile() {
        try {
            File file = new File("src/main/resources/angajati.json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(file, new TypeReference<List<Angajat>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printEmployees() {
        angajatiList.forEach(System.out::println);
    }

    public static void printEmployeesWithWageGreaterThan2500() {
        angajatiList
                .stream()
                .filter((angajat -> angajat.getSalariu() > 2500f))
                .forEach(System.out::println);
    }

    public static List<Angajat> printDirectorsEmployedLastYear() {
        List<Angajat> angajati = angajatiList.stream().filter(angajat -> (LocalDate.now().getYear() - angajat.getDataAngajarii().getYear()) == 1)
                .filter(angajat -> angajat.getDataAngajarii().getMonthValue() == 4)
                .filter(angajat -> angajat.getPost().contains("Director")|| angajat.getPost().contains("Sef")).toList();
        return angajati;
    }

    public static void printNormalEmployees() {
        angajatiList.stream().filter(angajat -> !angajat.getPost().contains("Director"))
                .sorted(Comparator.comparing(Angajat::getSalariu).reversed())
                .forEach(System.out::println);
    }

    public static List<String> getEmployeesNames() {
        return angajatiList.stream().map(angajat -> angajat.getNume().toUpperCase()).collect(Collectors.toList());
    }

    public static void printWagesSmallerThan3000() {
        //angajatiList.stream().filter((angajat -> angajat.getSalariu() < 3000f)).forEach(angajat -> System.out.println(angajat.getSalariu()));
        angajatiList.stream()
                .filter((angajat -> angajat.getSalariu() < 3000f))
                .map(a->a.getSalariu())
                .forEach(s -> System.out.println(s));
    }

    public static void printFirstEmployeeEver() {
//        Optional<Angajat> angajat = angajatiList.stream().min(Comparator.comparing(Angajat::getDataAngajarii)).ifPresentOrElse(angajat1 -> angajat1.get().toString());//ifpresesntorelse
//        System.out.println(angajat.isPresent() ? angajat.get().toString() : "Nu exista angajati!");
        angajatiList.stream()
                .min((Comparator.comparing(Angajat::getDataAngajarii)))
                .ifPresentOrElse(
                        angajat -> System.out.println(angajat.toString()),
                        () -> System.out.println(" idk")
                );


    }

    public static void printStatisticsAboutWages() {
        System.out.println(angajatiList.stream().collect(Collectors.summarizingDouble(Angajat::getSalariu)));
    }

    public static void printIfIonInList() {
        angajatiList.stream()
                .filter(angajat -> angajat.getNume().contains("Ion"))
                .findAny()
                .ifPresentOrElse(
                        angajat -> System.out.println("Exista Ion"),
                        () -> System.out.println("Nu exista Ion")
                );
    }

    public static long printEmployeesFromLastSummer() {
        LocalDate ultimaZi_De_Primavara = LocalDate.of(2022,6,1);
        LocalDate primaZi_De_Toamna = LocalDate.of(2022,9,1);
        return angajatiList.stream()
                .filter(angajat -> angajat.getDataAngajarii().isAfter(ultimaZi_De_Primavara)&&angajat.getDataAngajarii().isBefore(primaZi_De_Toamna)).count();

    }

    public static void printNumberOfEmployeesFromLastSummer() {
        System.out.println("Numarul de angjati de vara trecuta: "+ angajatiList.stream().filter(angajat -> (LocalDate.now().getYear() - angajat.getDataAngajarii().getYear()) == 1)
                .filter(angajat -> angajat.getDataAngajarii().getMonthValue() == 7 || angajat.getDataAngajarii().getMonthValue() == 8
                        || angajat.getDataAngajarii().getMonthValue() == 9).count()+"\nNominal:\n");
        angajatiList.stream().filter(angajat -> (LocalDate.now().getYear() - angajat.getDataAngajarii().getYear()) == 1)
                .filter(angajat -> angajat.getDataAngajarii().getMonthValue() == 7 || angajat.getDataAngajarii().getMonthValue() == 8
                        || angajat.getDataAngajarii().getMonthValue() == 9).forEach(System.out::println);
    }
    public static void main(String[] args) {
       angajatiList.add(new Angajat("Andrei", "Director", LocalDate.of(2022, Month.APRIL,2),9400));
       angajatiList.add(new Angajat("David", "Manager", LocalDate.of(2019, Month.JANUARY,1),7500));
      angajatiList.add(new Angajat("Ion", "Muncitor", LocalDate.of(2021, Month.FEBRUARY,16),2900));
       angajatiList.add(new Angajat("Sergiu", "Muncitor", LocalDate.of(2022, Month.JULY,4),1800));
       angajatiList.add(new Angajat("Vasilica", "Sef", LocalDate.of(2022, Month.APRIL,7),10780));

      writeFile(angajatiList);
        angajatiList = readFile();
        System.out.println("1. Afisarea listei de angajati folosind referinte la metode");
        printEmployees();
        System.out.println("2. Afișarea angajaților care au salariul peste 2500 RON");
        printEmployeesWithWageGreaterThan2500();
        System.out.println();
        System.out.println("3. Crearea unei liste cu angajații din luna aprilie, a anului trecut, care au funcție de conducere");
        printDirectorsEmployedLastYear().forEach(System.out::println);
        System.out.println("4. Afisarea angajatilor care nu au functie de conducere");
        printNormalEmployees();
        System.out.println("5. Extragerea din lista de angajati a unei liste de String-uri care contine numele angajatilor scrise cu majuscule");
        getEmployeesNames().forEach(System.out::println);
        System.out.println("6. Afisarea salariilor mai mici de 3000 de RON");
        printWagesSmallerThan3000();
        System.out.println();
        System.out.println("7. Afisarea datelor primului angajat al firmei");
        printFirstEmployeeEver();
        System.out.println("8. Afisarea de statistici referitoare la salariul angajatilor");
        printStatisticsAboutWages();
        System.out.println("9. Afisarea unor mesaje care indica daca printre angajati exista cel putin un Ion");
        printIfIonInList();
        System.out.println();
        System.out.println("10. Afisarea numarului de persoane care s-au angajat in vara anului precedent");
        // printNumberOfEmployeesFromLastSummer();
        System.out.println(printEmployeesFromLastSummer());
    }

    public static List<Angajat> angajatiList = new ArrayList<>();
}