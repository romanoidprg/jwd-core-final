package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.BaseEntityAlreadyExistException;
import com.epam.jwd.core_final.exception.BaseEntityException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.NassaCrewService;
import com.epam.jwd.core_final.service.impl.NassaMissionService;
import com.epam.jwd.core_final.service.impl.NassaSpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NassaApplicationMenu implements ApplicationMenu {
    Logger logger = LoggerFactory.getLogger(NassaApplicationMenu.class);
    private final NassaContext nassaContext;
    private final Scanner scanner = handleUserInput(System.in);
    private Long id = null;
    private String name = null;
    private Rank rank = null;
    private Role role = null;
    private Long flightDistance = null;
    private boolean isReadyForNextMissions = true;
    private String s;
    Random r = new Random();
    List<CrewMember> crewMembers;
    List<Spaceship> spaceShips;
    List<FlightMission> flightMissions;

    private final File missionFile = new File(ApplicationProperties.getMissionsFileName());

    CrewMemberFactory crewMemberFactory = CrewMemberFactory.INSTANCE;
    SpaceshipFactory spaceshipFactory = SpaceshipFactory.INSTANCE;
    FlightMissionFactory flightMissionFactory = FlightMissionFactory.INSTANCE;

    private final NassaCrewService nassaCrewService;
    private final NassaSpaceshipService nassaSpaceshipService;
    private final NassaMissionService nassaMissionService;


    private final ElementMenuProcess nothingDoProcess = () -> {
    };

    private final ElementMenuProcess showAllCrewMembersProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            for (CrewMember c : nassaCrewService.findAllCrewMembers()) {
                System.out.println(c.toString());
            }
        }
    };

    private final ElementMenuProcess showAllSpaceshipsProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            for (Spaceship s : nassaSpaceshipService.findAllSpaceships()) {
                System.out.println(s.toString());
            }
        }
    };

    private final ElementMenuProcess showCrewMembersOnCriteriaProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            Criteria<CrewMember> crewMemberCriteria =
                    CrewMemberCriteria.newBuilder().<CrewMemberCriteria.Builder>whereIdIs(id)
                    .<CrewMemberCriteria.Builder>whereNameIs(name).whereRankIs(rank)
                    .whereRoleIs(role).readyForNextMissionsIs(isReadyForNextMissions).build();
            for (CrewMember cm : nassaCrewService.findAllCrewMembersByCriteria(crewMemberCriteria)) {
                System.out.println(cm.toString());
            }
            resetparameters();
        }
    };

    private final ElementMenuProcess showSpaceshipsOnCriteriaProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            Criteria<Spaceship> spaceshipCriteria =
                    SpaceshipCriteria.newBuilder().<SpaceshipCriteria.Builder>whereIdIs(id)
                    .<SpaceshipCriteria.Builder>whereNameIs(name).whereFlightDistanceIs(flightDistance)
                    .readyForNextMissionsIs(isReadyForNextMissions).build();
            for (Spaceship s : nassaSpaceshipService.findAllSpaceshipsByCriteria(spaceshipCriteria)) {
                System.out.println(s.toString());
            }
            resetparameters();
        }
    };

    private final ElementMenuProcess enterIdProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            id = Long.getLong(inputParameter(scanner, "[0-9]{1,}"));
        }
    };

    private final ElementMenuProcess enterNameProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            name = inputParameter(scanner, "[\\w -]+");
        }
    };

    private final ElementMenuProcess enterCrewMemberRoleProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            s = inputParameter(scanner, "[1-4]{1}");
            role = s == null ? null : Role.resolveRoleById(Integer.parseInt(s));
        }
    };

    private final ElementMenuProcess enterCrewMemberRankProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            s = inputParameter(scanner, "[1-4]{1}");
            rank = s == null ? null : Rank.resolveRankById(Integer.parseInt(s));
        }
    };

    private final ElementMenuProcess enterSpaceshipDistanceProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            s = inputParameter(scanner, "[0-9]+");
            flightDistance = s == null ? null : (Long.parseLong(s));
        }
    };

    private final ElementMenuProcess enterReadyProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            s = inputParameter(scanner, "true");
            isReadyForNextMissions = s != null;
        }
    };

    private final ElementMenuProcess createCrewMemberProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            System.out.println("Enter CrewMember info (pattern: Role,Name,Rank)");
            String userInput = scanner.nextLine();
            try {
                nassaCrewService.createCrewMember(crewMemberFactory.create(userInput));
            } catch (BaseEntityAlreadyExistException e) {
                logger.error(e.getMessage());
            } catch (UnknownEntityException e) {
                logger.error(e.getMessage());
            }
        }
    };

    private final ElementMenuProcess createSpaceshipProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            System.out.println("Enter Spaceship info (pattern): Name,distance,{1:count,2:count,3:count,4:count}");
            String userInput = scanner.nextLine();
            try {
                nassaSpaceshipService.createSpaceship(spaceshipFactory.create(userInput));
            } catch (BaseEntityAlreadyExistException e) {
                logger.error(e.getMessage());
            } catch (UnknownEntityException e) {
                logger.error(e.getMessage());
            }
        }
    };

    private final ElementMenuProcess WRFileMissionProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            try (FileWriter writer = new FileWriter(missionFile, false)) {
                for (FlightMission fm : flightMissions) {
                    writer.write("{\n");
                    writer.write(" \"id\":" + fm.getId() + ",\n");
                    writer.write(" \"Name\":" + fm.getName() + ",\n");
                    writer.write(" \"StartDate\":" + fm.getStartDate() + ",\n");
                    writer.write(" \"EndDate\":" + fm.getEndDate() + ",\n");
                    writer.write(" \"Distance\":" + fm.getDistance() + ",\n");
                    writer.write(" \"Mission Result\":" + fm.getMissionResult() + "\n");
                    writer.write(" \"Spaceship\":\n");
                    writer.write(" {\n");
                    writer.write("  \"id\":" + fm.getAssignedSpaceShip().getId() + ",\n");
                    writer.write("  \"Name\":" + fm.getAssignedSpaceShip().getName() + ",\n");
                    writer.write("  \"Distance\":" + fm.getAssignedSpaceShip().getFlightDistance() + ",\n");
                    writer.write("  \"Crew\":\n");
                    writer.write("  [\n");
                    for (CrewMember c : fm.getAssignedCrew()) {
                        writer.write("   {\n");
                        writer.write("    \"id\":" + c.getId() + ",\n");
                        writer.write("    \"Role\":" + c.getRole() + ",\n");
                        writer.write("    \"Name\":" + c.getName() + ",\n");
                        writer.write("    \"Rank\":" + c.getRank() + "\n");
                        writer.write("   }\n");
                    }
                    writer.write("  ]\n");
                    writer.write(" }\n");
                    writer.write("}\n");
                }
                writer.flush();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    };

    private final ElementMenuProcess createMissionProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            System.out.println("Enter mission name:");
            String name = scanner.nextLine();
            Spaceship assignedSpaceShip;
            FlightMission flightMission;
            do {
                try {
                    assignedSpaceShip = spaceShips.get(r.nextInt(spaceShips.size()));
                    nassaSpaceshipService.assignSpaceshipOnMission(assignedSpaceShip);
                    LocalDate startDate = LocalDate.of(1980 + r.nextInt(50), 1 + r.nextInt(12)
                            , 1 + r.nextInt(28));
                    LocalDate endDate = startDate.plusYears(r.nextInt(50)).plusMonths(r.nextInt(12))
                            .plusDays(r.nextInt(28));
                    Long distance = (long) (Math.random() * 10000000 + 10000);
                    List<CrewMember> assignedCrew = completeCrew(assignedSpaceShip);
                    flightMission = flightMissionFactory.create(name,
                            startDate, endDate, distance, assignedSpaceShip, assignedCrew);
                    nassaMissionService.createMission(flightMission);
                    if (endDate.isBefore(LocalDate.now()))
                        flightMission.setMissionResult(MissionResult.COMPLETED);
                } catch (BaseEntityException e) {
                    assignedSpaceShip = null;
                    logger.error(e.getMessage());
                } catch (UnknownEntityException e) {
                    assignedSpaceShip = null;
                    logger.error(e.getMessage());
                }
            } while (assignedSpaceShip == null);
        }
    };

    private final ElementMenuProcess updateMissionProcess = new ElementMenuProcess() {
        @Override
        public void process() {
            for (FlightMission fm : flightMissions) {
                int i = r.nextInt(5) + 1;
                if (fm.getMissionResult() != MissionResult.FAILED) {
                    switch (i) {
                        case 1:
                            fm.setMissionResult(MissionResult.CANCELLED);
                            break;
                        case 2:
                            if (fm.getStartDate().isAfter(LocalDate.now()))
                                fm.setMissionResult(MissionResult.PLANNED);
                            break;
                        case 3:
                            if (fm.getEndDate().isBefore(LocalDate.now()))
                                fm.setMissionResult(MissionResult.COMPLETED);
                            break;
                        case 4:
                            if (fm.getStartDate().isBefore(LocalDate.now()) && fm.getEndDate().isAfter(LocalDate.now()))
                                fm.setMissionResult(MissionResult.IN_PROGRESS);
                            break;
                        case 5:
                            if (fm.getStartDate().isBefore(LocalDate.now()) && fm.getEndDate().isAfter(LocalDate.now()))
                                fm.setMissionResult(MissionResult.FAILED);
                            break;
                    }
                }
            }
        }
    };

    private final ElementMenuProcess exitProcess = this::resetparameters;

    private final List<ElementMenu> rootMenuList = new ArrayList<>();

    private final ElementMenu rootMenu = new ElementMenu("Root", rootMenuList, true, exitProcess);

    private final List<ElementMenu> getUpdateCrewMembersMenuList = new ArrayList<>();

    private final ElementMenu getUpdateCrewMembersMenu = new ElementMenu(
            "Get/Update crewMembers.", getUpdateCrewMembersMenuList, false, nothingDoProcess);

    private final ElementMenu showAllCrewMembersMenu = new ElementMenu(
            "Show all crewMembers.", null, false, showAllCrewMembersProcess);

    private final List<ElementMenu> showCrewMembersOnCriteriaMenuList = new ArrayList<>();

    private final ElementMenu showCrewMembersOnCriteriaMenu = new ElementMenu(
            "Show crewMembers on criteria.", showCrewMembersOnCriteriaMenuList, false, nothingDoProcess);

    private final ElementMenu enterIdMenu = new ElementMenu(
            "Enter Id.", null, false, enterIdProcess);

    private final ElementMenu enterNameMenu = new ElementMenu(
            "Enter Name.", null, false, enterNameProcess);

    private final ElementMenu enterCrewMemberRoleMenu = new ElementMenu(
            "Enter Role.", null, false, enterCrewMemberRoleProcess);

    private final ElementMenu enterCrewMemberRankMenu = new ElementMenu(
            "Enter Rank.", null, false, enterCrewMemberRankProcess);

    private final ElementMenu enterReadyMenu = new ElementMenu(
            "Enter isReadyForNextMissions.", null, false, enterReadyProcess);

    private final ElementMenu printCrewMemberMenu = new ElementMenu(
            "Show filtered crewMembers and clear criteria.", null, false,
            showCrewMembersOnCriteriaProcess);

    private final ElementMenu createCrewMemberMenu = new ElementMenu(
            "Create crewMember.", null, false, createCrewMemberProcess);

    private final List<ElementMenu> getUpdateSpaceshipsMenuList = new ArrayList<>();

    private final ElementMenu getUpdateSpaceshipsMenu = new ElementMenu(
            "Get/Update spaceships.", getUpdateSpaceshipsMenuList, false, nothingDoProcess);

    private final ElementMenu showAllSpaceshipsMenu = new ElementMenu(
            "Show all spaceships.", null, false, showAllSpaceshipsProcess);

    private final List<ElementMenu> showSpaceshipsOnCriteriaMenuList = new ArrayList<>();

    private final ElementMenu showSpaceshipsOnCriteriaMenu = new ElementMenu(
            "Show spaceships on criteria.", showSpaceshipsOnCriteriaMenuList, false, nothingDoProcess);

    private final ElementMenu enterSpaceshipDistanceMenu = new ElementMenu(
            "Enter distance.", null, false, enterSpaceshipDistanceProcess);

    private final ElementMenu printSpaceshipMenu = new ElementMenu(
            "Show filtered spaceships and clear criteria.", null, false,
            showSpaceshipsOnCriteriaProcess);

    private final ElementMenu createSpaceshipMenu = new ElementMenu(
            "Create spaceship.", null, false, createSpaceshipProcess);

    private final List<ElementMenu> createUpdateMissionsMenuList = new ArrayList<>();

    private final ElementMenu createUpdateMissionsMenu = new ElementMenu(
            "Create/Update missions.", createUpdateMissionsMenuList, false, nothingDoProcess);

    private final ElementMenu createMissionMenu = new ElementMenu(
            "Create random mission", null, false, createMissionProcess);

    private final ElementMenu updateMissionMenu = new ElementMenu(
            "Update random mission", null, false, updateMissionProcess);

    private final ElementMenu WRFileMissionMenu = new ElementMenu(
            "Write mission info to file", null, false, WRFileMissionProcess);


    private final ElementMenu exitMenu = new ElementMenu(
            "Exit.", null, true, nothingDoProcess);

    public NassaApplicationMenu(NassaContext nassaContext) {
        this.nassaContext = nassaContext;
        crewMembers = (List<CrewMember>) nassaContext.retrieveBaseEntityList(CrewMember.class);
        spaceShips = (List<Spaceship>) nassaContext.retrieveBaseEntityList(Spaceship.class);
        flightMissions = (List<FlightMission>) nassaContext.retrieveBaseEntityList(FlightMission.class);
        nassaCrewService = new NassaCrewService(nassaContext);
        nassaSpaceshipService = new NassaSpaceshipService(nassaContext);
        nassaMissionService = new NassaMissionService(nassaContext);
    }

    public void mainDialog() {
        completeListsMenu();
        rootMenu.process();
    }

    private void completeListsMenu() {
        rootMenuList.add(getUpdateCrewMembersMenu);
        {
            getUpdateCrewMembersMenuList.add(showAllCrewMembersMenu);
            getUpdateCrewMembersMenuList.add(showCrewMembersOnCriteriaMenu);
            {
                showCrewMembersOnCriteriaMenuList.add(enterIdMenu);
                showCrewMembersOnCriteriaMenuList.add(enterNameMenu);
                showCrewMembersOnCriteriaMenuList.add(enterCrewMemberRoleMenu);
                showCrewMembersOnCriteriaMenuList.add(enterCrewMemberRankMenu);
                showCrewMembersOnCriteriaMenuList.add(enterReadyMenu);
                showCrewMembersOnCriteriaMenuList.add(printCrewMemberMenu);
                showCrewMembersOnCriteriaMenuList.add(exitMenu);
            }
            getUpdateCrewMembersMenuList.add(createCrewMemberMenu);
            getUpdateCrewMembersMenuList.add(exitMenu);
        }
        rootMenuList.add(getUpdateSpaceshipsMenu);
        {
            getUpdateSpaceshipsMenuList.add(showAllSpaceshipsMenu);
            getUpdateSpaceshipsMenuList.add(showSpaceshipsOnCriteriaMenu);
            {
                showSpaceshipsOnCriteriaMenuList.add(enterIdMenu);
                showSpaceshipsOnCriteriaMenuList.add(enterNameMenu);
                showSpaceshipsOnCriteriaMenuList.add(enterSpaceshipDistanceMenu);
                showSpaceshipsOnCriteriaMenuList.add(enterReadyMenu);
                showSpaceshipsOnCriteriaMenuList.add(printSpaceshipMenu);
                showSpaceshipsOnCriteriaMenuList.add(exitMenu);
            }
            getUpdateSpaceshipsMenuList.add(createSpaceshipMenu);
            getUpdateSpaceshipsMenuList.add(exitMenu);
        }
        rootMenuList.add(createUpdateMissionsMenu);
        {
            createUpdateMissionsMenuList.add(createMissionMenu);
            createUpdateMissionsMenuList.add(updateMissionMenu);
            createUpdateMissionsMenuList.add(WRFileMissionMenu);
            createUpdateMissionsMenuList.add(exitMenu);
        }
        rootMenuList.add(exitMenu);
    }

    private String inputParameter(Scanner scanner, String regex) {
        System.out.println("Enter value of parameter");
        String userInput = scanner.nextLine();
        if (Pattern.matches(regex, userInput))
            return userInput;
        else {
            System.out.println("Incorrect input. Search will be by any value of this parameter");
            return null;
        }
    }

    private void resetparameters() {
        id = null;
        name = null;
        rank = null;
        role = null;
        flightDistance = null;
        isReadyForNextMissions = true;
    }

    public List<CrewMember> completeCrew(Spaceship spaceship) {
        List<CrewMember> members;
        CrewMember crewMember;
        List<CrewMember> crew = new ArrayList<>();
        for (int j = 1; j < 5; j++) {
            for (int i = 0; i < spaceship.getCrew().get(Role.resolveRoleById(j)); i++) {
                final Role role = Role.resolveRoleById(j);
                members = crewMembers.stream().filter(c -> c.getRole() == role).collect(Collectors.toList());
                do {
                    try {
                        crewMember = members.get(r.nextInt(members.size()));
                        nassaCrewService.assignCrewMemberOnMission(crewMember);
                        crew.add(crewMember);
                    } catch (BaseEntityException e) {
                        crewMember = null;
                        logger.error(e.getMessage());
                    } catch (UnknownEntityException e) {
                        crewMember = null;
                        logger.error(e.getMessage());
                    }
                } while (crewMember == null);
            }
        }
        return crew;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return nassaContext;
    }
}
