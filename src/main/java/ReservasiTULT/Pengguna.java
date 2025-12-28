package ReservasiTULT;

public abstract class Pengguna {
    protected int id;
    protected String nama;

    public Pengguna(String nama) {
        this.nama = nama;
    }

    public abstract void tampilkanInfo();
    public abstract boolean bolehMengajukanReservasi();
    public abstract String getRole();

    public String getNama() {
        return nama;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}