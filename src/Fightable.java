import java.util.List;

/**
 * an interface that all character who might be involved in a combat should
 * implement
 */
public interface Fightable {
    public abstract void act(RectangularRPGBoard b, List<Monster> monsters, List<Hero> heros);
}