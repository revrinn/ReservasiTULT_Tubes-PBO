package ReservasiTULT;

public class Ruangan_Kelas {
    private int id;
    private String kode;
    private int lantai;
    private int kapasitas;
    private boolean tersedia;
    private String fasilitas;

    public Ruangan_Kelas(String kode, int lantai, int kapasitas) {
        this.kode = kode;
        this.lantai = lantai;
        this.kapasitas = kapasitas;
        this.tersedia = true;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    public String getKode() {
        return kode;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getLantai() {
        return lantai;
    }
    
    public int getKapasitas() {
        return kapasitas;
    }
    
    public String getFasilitas() {
        return fasilitas;
    }
    
    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public void tampilkanInfo() {
        System.out.print(
            kode + " | Lantai " + lantai +
            " | Kapasitas " + kapasitas +
            " | Status: "
        );

        if (tersedia) {
            System.out.println("Tersedia");
        } else {
            System.out.println("Dipakai");
        }
    }
}