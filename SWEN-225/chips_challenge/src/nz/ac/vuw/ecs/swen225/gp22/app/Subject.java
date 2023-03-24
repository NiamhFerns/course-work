package nz.ac.vuw.ecs.swen225.gp22.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the subject in an observer pattern.
 *
 * @author niamh
 */
public class Subject {
    private final ArrayList<Observer> obs = new ArrayList<>();

    /**
     * Update all registered observers of this subject.
     *
     * @author niamh
     */
    protected final void update() {
        List.copyOf(obs).forEach(Observer::update);
    }

    /**
     * Add an observer to the list of observers to be updated.
     * @param ob observer to be added.
     *
     * @author niamh
     */
    public final void register(Observer ob) {
        obs.add(ob);
    }

    /**
     * Remove an observer from the list of observers to be updated.
     * @param ob observer to be removed.
     *
     * @author niamh
     */
    public final void unregister(Observer ob) {
        obs.remove(ob);
    }
}
