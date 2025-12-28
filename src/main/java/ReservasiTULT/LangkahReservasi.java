/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReservasiTULT;

public interface LangkahReservasi {
    boolean cekKetersediaan();
    void ajukanReservasi();
    void batalkanReservasi();
    String cetakStruk();
    void tampilkanDetail();
}

