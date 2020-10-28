package pedroroel.coronamayhem.controllers;

import pedroroel.coronamayhem.CoronaMayhem;
import pedroroel.coronamayhem.entities.MaskDrop;

import java.util.ArrayList;
import java.util.List;

public class DropController extends Controller {
    private final List<MaskDrop> drops = new ArrayList<>();

    public DropController(CoronaMayhem world) {
        super(world);
    }

    @Override
    public void startAlarm() {

    }

    @Override
    protected void restartAlarm() {

    }

    @Override
    public void triggerAlarm(String s) {

    }
}
