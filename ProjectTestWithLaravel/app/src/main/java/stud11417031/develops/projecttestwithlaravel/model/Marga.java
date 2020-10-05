package stud11417031.develops.projecttestwithlaravel.model;

public class Marga {
    private int id;
    private String nama;
    private String deskripsi;
    private String image;

    public Marga(int id, String nama, String deskripsi, String image) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getImage() {
        return image;
    }
}
