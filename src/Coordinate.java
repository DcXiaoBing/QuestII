import java.util.Random;

/**
 * a class represent a x,y coordinate in 2D rectangular board
 */
public class Coordinate{
    private int x, y;


    public Coordinate(){
        x = y = 0;
    }

    public Coordinate(int _x, int _y){
        x = _x;
        y = _y;
    }

    public void setX(int _x){x = _x;}
    public int getX(){return x;}
    public void setY(int _y){y = _y;}
    public int getY(){return y;}

    public static Coordinate generateRandomCoordinate(int xBound, int yBound){
        Coordinate res = new Coordinate();
        Random ran = new Random();

        res.setX(ran.nextInt(xBound));
        res.setY(ran.nextInt(yBound));
        return res;
    }

    @Override
    public boolean equals(Object o){
        boolean res = false;
        if(this == o){
            res = true;
        }else if(o instanceof Coordinate){
            Coordinate c = (Coordinate)o;
            if(c.getX() == this.x && c.getY() == this.y){
                res = true;
            }
        }

        return res;
    }

    @Override
    public int hashCode(){
        Integer tempX = x;
        Integer tempY = y;
        return tempX.hashCode() * tempY.hashCode();
    }
}