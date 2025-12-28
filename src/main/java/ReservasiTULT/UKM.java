/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReservasiTULT;

public class UKM {
    private String kodeUkm;
    private String namaUnit;
    private Dosen pembina;

    public UKM(String kodeUkm, String namaUnit, Dosen pembina) {
        this.kodeUkm = kodeUkm;
        this.namaUnit = namaUnit;
        this.pembina = pembina;
    }

    public String getNamaUnit() {
        return namaUnit;
    }

    public void tampilkanInfo() {
        System.out.println("UKM : " + namaUnit);
        if (pembina != null) {
            System.out.println("Pembina : " + pembina.getNama());
        } else {
            System.out.println("Pembina : Belum ditetapkan");
        }
    }
    
}