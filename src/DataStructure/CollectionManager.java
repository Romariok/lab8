package DataStructure;


import Database.HumanbeingTableManager;
import Database.TableManager;
import Data.*;
import server.FileManagment.ParserfromBD;

import java.io.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionManager {
    /**
     * LinkedList collection that contains HumanBeing objects
     */
    private LinkedList<HumanBeing> humans = new LinkedList<>();
    private CopyOnWriteArrayList<HumanBeing> concurrentHumans = new CopyOnWriteArrayList<>();

    /**
     * Value for initialized time
     */
    private final ZonedDateTime indate;
    /**
     * ParserXML used for execute and write data to file
     *
     * @see ParserfromBD
     */
    private ParserfromBD parserfromBD;
    /**
     * Initialize {@code Comparator}
     */
    Comparator<HumanBeing> comparator = (o1, o2) -> o2.compareTo(o1);


    private HumanbeingTableManager hbManager = new HumanbeingTableManager();

    public static String bdColumns = "(id, name, x, y, creationDate, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, carCool, login)";
    public static String bdSetColumns = "(name, x, y, realHero, hasToothpick, impactSpeed, soundtrackName, weaponType, mood, carCool)";

    public CollectionManager() {
        indate = ZonedDateTime.now();
        this.parserfromBD = new ParserfromBD(this);
        load();
    }

    public ZonedDateTime getIndate() {
        return indate;
    }

    public Comparator<HumanBeing> getComparator() {
        return comparator;
    }

    public CopyOnWriteArrayList<HumanBeing> getConcurrentCollection() {
        return concurrentHumans;
    }


    public void setConcurrentCollection(CopyOnWriteArrayList<HumanBeing> ls) {
        this.concurrentHumans = ls;
    }

    /**
     * Used to load data from file to collection
     */
    private void load() {
        try {
            parserfromBD.parseData();
        } catch (Exception ex) {
            System.err.println("Возникла непредвиденная ошибка! SELECT * не сработал(");
            System.exit(1);
        }
        System.out.println("Файл успешно загружен в коллекцию!");
    }

    public HumanbeingTableManager getDBManager() {
        return this.hbManager;
    }

    public String getValues(HumanBeing h, boolean id, boolean set) {
        StringBuilder result = new StringBuilder("(");
        if (!set) {
            if (id) {
                result.append(h.getId()).append(",'");
            } else {
                result.append("default,'");
            }
        }
        result.append(h.getName()).append("',").append(h.getCoordinates().getX()).append(",").append(h.getCoordinates().getY()).append(",");
        result.append(h.isRealHero()).append(",").append(h.getHasToothpick()).append(",").append(h.getImpactSpeed()).append(",'");
        result.append(h.getSoundtrackName()).append("','").append(h.getWeaponType()).append("','").append(h.getMood()).append("',").append(h.getCar().toString()).append(")");
        return result.toString();
    }
}
