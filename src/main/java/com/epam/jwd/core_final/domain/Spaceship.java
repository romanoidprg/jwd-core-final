package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    //todo
    private static Long counter = 1L;
    private Map<Role, Short> crew;
    private Long flightDistance;
    private Boolean isReadyForNextMissions;


    public Spaceship(String spaceship) {
        if (Pattern.matches("[\\w ]{1,};[0-9]{1,};\\{1:[0-9]{1,},2:[0-9]{1,},3:[0-9]{1,},4:[0-9]{1,}}", spaceship)) {
            this.id = counter++;
            Scanner scanner = new Scanner(spaceship);
            scanner.useDelimiter(";");
            this.name = scanner.next();
            this.flightDistance = scanner.nextLong();
            String s = scanner.next();
            scanner = new Scanner(s.substring(1, s.length() - 1));
            scanner.useDelimiter(",");
            crew = new HashMap<>();
            for (int i = 1; i < 5; i++) {
                crew.put(Role.resolveRoleById(i), Short.valueOf(scanner.next().substring(2)));
            }
            isReadyForNextMissions = true;
        } else
            throw new UnknownEntityException(spaceship);
    }

    @Override
    public String toString() {
        return "Spaceship:_id<" + id + ">_name<" + name + ">_distance<" + flightDistance
                + ">_crew" + crew + "_readyForMission<" + isReadyForNextMissions + ">";
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }
}
