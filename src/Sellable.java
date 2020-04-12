/**
 * an interface that all item that can be sold in a market should implement
 */
public interface Sellable {
    public abstract int getHalfPrice();
    public abstract boolean canSoldToMarket();
}