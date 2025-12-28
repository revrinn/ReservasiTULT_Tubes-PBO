package ReservasiTULT;

public class Dosen extends Pengguna {
    private String nip;
    private String unitKerja;

    public Dosen(String nama, String nip, String unitKerja) {
        super(nama);
        this.nip = nip;
        this.unitKerja = unitKerja;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Dosen");
        System.out.println("Nama : " + nama);
        System.out.println("NIP  : " + nip);
        System.out.println("Unit : " + unitKerja);
    }

    @Override
    public boolean bolehMengajukanReservasi() {
        return true;
    }

    @Override
    public String getRole() {
        return "DOSEN";
    }

    public String getNip() {
        return nip;
    }
    
    public String getUnitKerja() {
        return unitKerja;
    }
}