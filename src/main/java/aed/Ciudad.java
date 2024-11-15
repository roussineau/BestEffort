package aed;

public class Ciudad implements Identificable {

  public int id;
  public int ganancia;
  public int perdida;
  public int superavit;

  public Ciudad(int id) {
    this.id = id;
    this.perdida = 0;
    this.superavit = 0;
    this.ganancia = 0;
  }

  public int ganancia() {
    return ganancia;
  }

  public int superavit() {
    return superavit;
  }

  public int id() {
    return id;
  }

  @Override
  public int getId(){
    return id;
  }
}
