interface Charger { void charge(); }

class OldCharger {
    void oldCharge() { System.out.println("Charging with Old Charger"); }
}

class ChargerAdapter implements Charger {
    OldCharger old;
    ChargerAdapter(OldCharger old) { this.old = old; }
    public void charge() { old.oldCharge(); }
}

public class AdapterDemo {
    public static void main(String[] args) {
        Charger c = new ChargerAdapter(new OldCharger());
        c.charge();
    }
}
