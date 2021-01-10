package com.epam.jwd.core_final.context.impl;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ElementMenu implements ElementMenuProcess {
    private final List<ElementMenu> submenuList;
    private String name = "";
    private boolean isEscapeElement = true;
    ElementMenuProcess elementMenuProcess;
    private final Scanner scanner = new Scanner(System.in);

    public ElementMenu(String name, List<ElementMenu> submenuList, boolean isEscapeElement,
                       ElementMenuProcess elementMenuProcess) {
        this.name = name;
        this.submenuList = submenuList;
        this.isEscapeElement = isEscapeElement;
        this.elementMenuProcess = elementMenuProcess;
    }

    @Override
    public void process() {
        if (submenuList == null)
            elementMenuProcess.process();
        else
            selectElementSubMenu();

    }

    private void selectElementSubMenu() {
        boolean escapeMenu = false;
        do {
            for (int i = 0; i < submenuList.size(); i++) {
                System.out.println(i + " - " + submenuList.get(i).name);
            }
            String s = scanner.nextLine();
            if (Pattern.matches("[0-9]+", s))
                for (int i = 0; i < submenuList.size(); i++) {
                    if (Integer.parseInt(s) == i) {
                        submenuList.get(i).process();
                        escapeMenu = submenuList.get(i).isEscapeElement;
                        break;
                    }
                }
            else
                System.out.println("Incorrect choice. Try again.");

        } while (!escapeMenu);
    }
}
