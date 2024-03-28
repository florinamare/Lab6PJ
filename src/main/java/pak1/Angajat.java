package pak1;


import java.time.LocalDate;

public class Angajat
{
    private String nume;
    private String post;
    private LocalDate data_angajarii;
    private float salariu;

    public Angajat() {}

    public Angajat(final String nume, final String post,
                   final LocalDate dataAngajarii, final float salariu)
    {
        this.nume = nume;
        this.post = post;
        data_angajarii = dataAngajarii;
        this.salariu = salariu;
    }

    public float getSalariu()
    {
        return salariu;
    }

    public LocalDate getDataAngajarii()
    {
        return data_angajarii;
    }

    public String getPost()
    {
        return post;
    }

    public String getNume()
    {
        return nume;
    }

    public void setDataAngajarii(LocalDate dataAngajarii)
    {
        data_angajarii = dataAngajarii;
    }

    public void setPost(String post)
    {
        this.post = post;
    }

    public void setNume(String nume)
    {
        this.nume = nume;
    }

    public void setSalariu(float salariu)
    {
        this.salariu = salariu;
    }

    @Override
    public String toString()
    {
        return "Nume: " + nume + "\n" +
                "Post: " + post + "\n" +
                "Data angajarii: " + data_angajarii + "\n" +
                "Salariu: " + salariu + "\n";
    }


    public int compareTo(Angajat b)
    {
        if (salariu < b.salariu)
        {
            return -1;
        }
        if (salariu == b.salariu)
        {
            return 0;
        }
        return 1;
    }
}