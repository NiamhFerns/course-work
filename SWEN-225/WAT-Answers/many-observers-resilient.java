/*
 The answer must have balanced parentheses and can not use
 "null","while","if","for"

 Use Observer Pattern to solve this exercise.
 The Observer Pattern allows for an event to be handled
 while keeping the source of the event unaware of the 
 handlers specific implementation.
 In this exercise, we are implementing the pattern
 when many Observers must be able to coexist.
*/
import java.util.*;
import java.util.stream.Stream;
import java.lang.reflect.Modifier;

interface Observer{void update();}

abstract class Subject{
    // --------------------- //
    // MY ANSWER STARTS HERE //
    // --------------------- //
    private Set<Observer> obs = new LinkedHashSet<>();
    protected final void onChange() {
        obs.forEach(o -> o.update());
    }

    public final void attach(Observer o) {
        obs.add(o);
    }

    public final void deattachAll() {
        obs.clear();
    }
    // ------------------- //
    // MY ANSWER ENDS HERE //
    // ------------------- //
}

class Bob extends Subject{//omitted, note: it is always the same code
  /*omitted*/
  }
public class Exercise {
  public static void main(String[]a) {
    assert Stream.of(Subject.class.getDeclaredFields())
      .allMatch(f->Modifier.isPrivate(f.getModifiers()));
    Bob bob = new Bob();
    int[] tot1 = {0};
    int[] tot2 = {0};
    bob.eat(1);
    bob.attach(()->tot1[0]+=1);
    assert tot1[0]==0:"Is this possible?";
    bob.eat(1);
    assert tot1[0]==1:"first action";
    bob.attach(()->{bob.attach(()->{}); tot2[0]+=1;});//change here!
    bob.eat(2); bob.eat(5);
    assert tot1[0]==3:"first 3 actions";
    assert tot2[0]==2:"first 2 actions for the second observer";
    bob.deattachAll();
    bob.eat(100);
    assert tot1[0]==3:"last action is not recorded obs1";
    assert tot2[0]==2:"last action is not recorded obs2";
  }
}
//[ Check code snipplet inserted here ]
