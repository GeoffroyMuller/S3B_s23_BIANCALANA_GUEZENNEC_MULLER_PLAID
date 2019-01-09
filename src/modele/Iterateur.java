package modele;

public interface Iterateur {
    public boolean hasNext();
    public boolean hasPrevious();
    public boolean hasNext(int pas);
    public boolean hasPrevious(int pas);
    public boolean hasUp();
    public boolean hasDown();
    public Object next();
    public Object previous();
    public Object next(int pas);
    public Object previous(int pas);
    public Object up();
    public Object down();
    public Object actual();
    public void reset();
    public int getCoordI();
    public int getCoordY();
}
