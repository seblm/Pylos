package pylos.game.internal;

import pylos.game.BallPosition;
import pylos.game.Pattern;
import pylos.game.Pylos;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PylosBuilder {
    private final Pylos pylos;

    public PylosBuilder(Pylos pylos) {
        this.pylos = pylos;
    }

    public HashMap<String, BallPosition> createBallPositions() {
        final LinkedHashMap<String, BallPosition> registry = new LinkedHashMap<>();

        // creates ball positions
        registry.put("a1", new BallPosition("a1", 1));
        registry.put("a2", new BallPosition("a2", 1));
        registry.put("a3", new BallPosition("a3", 1));
        registry.put("a4", new BallPosition("a4", 1));
        registry.put("b1", new BallPosition("b1", 1));
        registry.put("b2", new BallPosition("b2", 1));
        registry.put("b3", new BallPosition("b3", 1));
        registry.put("b4", new BallPosition("b4", 1));
        registry.put("c1", new BallPosition("c1", 1));
        registry.put("c2", new BallPosition("c2", 1));
        registry.put("c3", new BallPosition("c3", 1));
        registry.put("c4", new BallPosition("c4", 1));
        registry.put("d1", new BallPosition("d1", 1));
        registry.put("d2", new BallPosition("d2", 1));
        registry.put("d3", new BallPosition("d3", 1));
        registry.put("d4", new BallPosition("d4", 1));
        registry.put("e1", new BallPosition("e1", 2));
        registry.put("e2", new BallPosition("e2", 2));
        registry.put("e3", new BallPosition("e3", 2));
        registry.put("f1", new BallPosition("f1", 2));
        registry.put("f2", new BallPosition("f2", 2));
        registry.put("f3", new BallPosition("f3", 2));
        registry.put("g1", new BallPosition("g1", 2));
        registry.put("g2", new BallPosition("g2", 2));
        registry.put("g3", new BallPosition("g3", 2));
        registry.put("h1", new BallPosition("h1", 3));
        registry.put("h2", new BallPosition("h2", 3));
        registry.put("i1", new BallPosition("i1", 3));
        registry.put("i2", new BallPosition("i2", 3));
        registry.put("j1", new BallPosition("j1", 4));

        // level 2
        registry.get("e1")
                .addBallPositionAtTheBottomOfMyself(registry.get("a1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("a2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b2"), pylos);

        registry.get("e2")
                .addBallPositionAtTheBottomOfMyself(registry.get("a2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("a3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b3"), pylos);

        registry.get("e3")
                .addBallPositionAtTheBottomOfMyself(registry.get("a3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("a4"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b4"), pylos);

        registry.get("f1")
                .addBallPositionAtTheBottomOfMyself(registry.get("b1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c2"), pylos);

        registry.get("f2")
                .addBallPositionAtTheBottomOfMyself(registry.get("b2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c3"), pylos);

        registry.get("f3")
                .addBallPositionAtTheBottomOfMyself(registry.get("b3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("b4"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c4"), pylos);

        registry.get("g1")
                .addBallPositionAtTheBottomOfMyself(registry.get("c1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("d1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("d2"), pylos);

        registry.get("g2")
                .addBallPositionAtTheBottomOfMyself(registry.get("c2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("d2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("d3"), pylos);

        registry.get("g3")
                .addBallPositionAtTheBottomOfMyself(registry.get("c3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("d3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("c4"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("d4"), pylos);

        // level 3
        registry.get("h1")
                .addBallPositionAtTheBottomOfMyself(registry.get("e1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("f1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("e2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("f2"), pylos);

        registry.get("h2")
                .addBallPositionAtTheBottomOfMyself(registry.get("e2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("f2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("e3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("f3"), pylos);

        registry.get("i1")
                .addBallPositionAtTheBottomOfMyself(registry.get("f1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("g1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("f2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("g2"), pylos);

        registry.get("i2")
                .addBallPositionAtTheBottomOfMyself(registry.get("f2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("g2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("f3"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("g3"), pylos);

        // level 4
        registry.get("j1")
                .addBallPositionAtTheBottomOfMyself(registry.get("h1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("i1"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("h2"), pylos)
                .addBallPositionAtTheBottomOfMyself(registry.get("i2"), pylos);

        // level 1
        new Pattern(pylos, registry.get("a1"), registry.get("a2"), registry.get("a3"), registry.get("a4"));
        new Pattern(pylos, registry.get("b1"), registry.get("b2"), registry.get("b3"), registry.get("b4"));
        new Pattern(pylos, registry.get("c1"), registry.get("c2"), registry.get("c3"), registry.get("c4"));
        new Pattern(pylos, registry.get("d1"), registry.get("d2"), registry.get("d3"), registry.get("d4"));
        new Pattern(pylos, registry.get("a1"), registry.get("b1"), registry.get("c1"), registry.get("d1"));
        new Pattern(pylos, registry.get("a2"), registry.get("b2"), registry.get("c2"), registry.get("d2"));
        new Pattern(pylos, registry.get("a3"), registry.get("b3"), registry.get("c3"), registry.get("d3"));
        new Pattern(pylos, registry.get("a4"), registry.get("b4"), registry.get("c4"), registry.get("d4"));

        // level 2
        new Pattern(pylos, registry.get("e1"), registry.get("e2"), registry.get("e3"));
        new Pattern(pylos, registry.get("f1"), registry.get("f2"), registry.get("f3"));
        new Pattern(pylos, registry.get("g1"), registry.get("g2"), registry.get("g3"));
        new Pattern(pylos, registry.get("e1"), registry.get("f1"), registry.get("g1"));
        new Pattern(pylos, registry.get("e2"), registry.get("f2"), registry.get("g2"));
        new Pattern(pylos, registry.get("e3"), registry.get("f3"), registry.get("g3"));

        return registry;
    }
}
