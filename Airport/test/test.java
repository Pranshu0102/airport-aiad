package test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import problems.AircraftProblem;
import problems.CrewProblem;
import problems.Event;
import problems.PaxProblem;
import problems.Problem;
import problems.Warning;
import solutions.AircraftSolution;
import solutions.CrewSolution;
import support.ParseExcel;
import airline.Aircraft;
import airline.AircraftModel;
import airline.Airport;
import airline.CrewMember;
import airline.EscCrew;
import airline.Flight;
import airline.Rank;

public class test {
	Map<String, Aircraft> aircrft;
	Map<String, Rank> rank;
	ArrayList<CrewMember> crewmember;
	Map<String, Airport> airport;
	Map<String, AircraftModel> airModel;
	ArrayList<Flight> flight;
	ArrayList<EscCrew> escCrews;
	ArrayList<Event> events;

	public HashMap<String, Integer> landing = new HashMap<String, Integer>();
	public HashMap<String, Integer> fuel = new HashMap<String, Integer>();
	public HashMap<String, Integer> services = new HashMap<String, Integer>();
	public static int delayPerMinPerPax = 15;

	int affectedFlights = 0;
	int paxAffected = 0;

	Map<Flight, Problem> problems;
	Map<Flight, Warning> warnings;

	public static void main(String args[]) {
		test main = new test();
		main.initializeCosts();
		main.parseFlights();
		main.parseEvents();
		main.analiseEvents();

	}

	private void initializeCosts() {
		landing.put("319", 319);
		landing.put("320", 337);
		landing.put("321", 373);
		landing.put("332", 1137);
		landing.put("343", 1169);

		fuel.put("319", 89);
		fuel.put("320", 95);
		fuel.put("321", 98);
		fuel.put("332", 109);
		fuel.put("343", 150);

		services.put("319", 200);
		services.put("320", 220);
		services.put("321", 235);
		services.put("332", 301);
		services.put("343", 410);
	}

	private int getTotalDelayAircraft(Problem problem) {

		int totalDelay = 0;
		int delay = 0;
		if (problem.getAirProbs().size() != 0) {

			for (int i = 0; i != problem.getAirProbs().size(); i++)
				if (problem.getAirProbs().get(i).getMinutesDelay() > totalDelay)
					delay = problem.getAirProbs().get(0).getMinutesDelay();

			boolean found = false;
			int k = 0, i = 0;
			while (!found && i < escCrews.size()) {
				while (!found && k < escCrews.get(i).getFlights().size()) {
					if (problem.getFlight() == escCrews.get(i).getFlights()
							.get(k)) {
						affectedFlights = escCrews.get(i)
								.getHowManyFlightsLeft(k);
						paxAffected = escCrews.get(i).getPaxAffected(k);
						totalDelay = affectedFlights * delay;

						found = true;
					}
					k++;
				}
				i++;
				k = 0;
			}

		}

		return totalDelay + delay;
	}

	public void Cria_Solucao_Aircraft(Problem prob) {
		int totalCost;
		if (prob.getAirProbs().size() != 0) {
			AircraftSolution sol = null;

			int totalDelayTime = getTotalDelayAircraft(prob);

			int costFuel = 0, costServices = 0, costLanding = 0;

			String model = prob.getFlight().getAircraft().getModel().getModel();
			String newModel;

			// procurar EscCrew disponível:
			Airport airport = prob.getFlight().getDepartureAirport();
			Timestamp departureTime = prob.getFlight().getDepartureTime();
			Long tripDuration = prob.getFlight().getArrivalTime().getTime()
					- prob.getFlight().getDepartureTime().getTime();

			EscCrew escCrewReplace;
			boolean found = false;
			int i = 0;

			while (!found && i < escCrews.size())

			{

				if (escCrews.get(i).getDisponibilityToFly(departureTime,
						tripDuration, airport)) {

					found = true;
					escCrewReplace = escCrews.get(i);

					newModel = escCrews.get(i).getFlights().get(0)
							.getAircraft().getModel().getModel();

					costFuel = (2 * fuel.get(newModel)) - fuel.get(model);
					costLanding = (2 * landing.get(newModel))
							- landing.get(model);
					costServices = (2 * services.get(newModel))
							- services.get(model);
					totalCost = costFuel + costLanding + costServices;

					// Calcular custos incluindo, a crew que foi escolhida
					// tempo de viagem etc

					sol = new AircraftSolution("Aviao Susbitituido", 1,
							totalCost, escCrewReplace);

				}

				i++;
			}

			if (!found) {
				paxAffected += prob.getFlight().getBusActlSeats()
						+ prob.getFlight().getEconActlSeats();
				totalCost = (int) (delayPerMinPerPax * paxAffected * totalDelayTime);
				sol = new AircraftSolution("Voos relacionados atrasados",
						affectedFlights, totalCost);
			}

			System.out.println(sol.toString());
			System.out.println();
		}

		Cria_Solucao_CrewMember(prob);
	}

	private int getTotalDelayCrewMember(Problem problem, CrewProblem crewProblem) {
		int totalDelay = 0;
		int delay = crewProblem.getMinutesDelay();

		boolean found = false;
		int k = 0, i = 0;
		while (!found && i < escCrews.size()) {
			while (!found && k < escCrews.get(i).getFlights().size()) {
				if (problem.getFlight() == escCrews.get(i).getFlights().get(k)) {
					affectedFlights = escCrews.get(i).getHowManyFlightsLeft(k);
					paxAffected = escCrews.get(i).getPaxAffected(k);
					totalDelay = affectedFlights * delay;

					found = true;
				}
				k++;
			}
			i++;
			k = 0;
		}

		return totalDelay + delay;

	}

	private void Cria_Solucao_CrewMember(Problem prob) {

		int totalCost = 0;
		CrewMember crewMember;
		CrewMember newCrewMember;
		Airport airport;
		Timestamp departureTime;
		CrewSolution crewSolution = null;
		int delay;

		for (int j = 0; j != prob.getCrewProbs().size(); j++) {

			newCrewMember = null;
			crewMember = prob.getCrewProbs().get(j).getCrewMember();
			airport = prob.getFlight().getDepartureAirport();
			departureTime = prob.getFlight().getDepartureTime();
			delay = prob.getCrewProbs().get(j).getMinutesDelay();
			boolean found = false;
			int i = 0;

			while (!found && i < escCrews.size()) {
				newCrewMember = escCrews.get(i)
						.getDisponibilityToCrewMemberFly(departureTime, delay,
								airport, crewMember);

				if (newCrewMember != null) {
					found = true;					
					
					// criar objecto solução
					crewSolution = new CrewSolution(prob.getCrewProbs().get(j),
							"CrewMember substituido", totalCost, newCrewMember,
							escCrews.get(i));
				}
				i++;
			}

			if (!found) {
				paxAffected += prob.getFlight().getBusActlSeats()
						+ prob.getFlight().getEconActlSeats();
				int totalDelayTime = getTotalDelayCrewMember(prob,prob
						.getCrewProbs().get(j));
				totalCost = (int) (delayPerMinPerPax * paxAffected * totalDelayTime);

				crewSolution = new CrewSolution(prob.getCrewProbs().get(j), "Delay espalhado",
						totalCost);
			}

			System.out.println(crewSolution.toString());
			System.out.println();
		}
		Cria_Solucao_Pax(prob);

	}

	private void Cria_Solucao_Pax(Problem prob) {
		
	}

	@SuppressWarnings("unchecked")
	private void analiseEvents() {
		String type;
		int delay;
		String description;

		int warningAircraft = 5;
		int warningCrew = 5;
		int warningPax = 10;

		Warning warning;
		CrewProblem crewProblem;
		AircraftProblem airProblem;
		PaxProblem paxProblem;
		Problem problem;
		Flight flight;

		problems = new HashMap<Flight, Problem>();
		warnings = new HashMap<Flight, Warning>();

		for (int i = 0; i != events.size(); i++) {
			warning = null;
			crewProblem = null;
			airProblem = null;
			problem = null;
			paxProblem = null;

			// ler dados evento
			type = events.get(i).getType();
			delay = events.get(i).getDelay();
			description = events.get(i).getDescription();
			flight = events.get(i).getFlight();

			if (type.equalsIgnoreCase("aircraft")) {
				if (delay < warningAircraft) {

					if (problems.get(flight) == null) {

						warning = new Warning(flight, type, description, delay);

						if (warnings.get(flight) != null) {
							if (warnings.get(flight).getMinutesDelay() < delay) {
								warnings.remove(flight);
								warnings.put(flight, warning);
							}
						} else {
							warnings.put(flight, warning);
						}

					}
				} else {
					airProblem = new AircraftProblem(description, delay);

					if (warnings.get(flight) != null)
						warnings.remove(flight);

					if (problems.get(flight) != null) {
						problem = problems.get(flight);
						problems.remove(flight);
						problem.addAirProbs(airProblem);
					} else {
						problem = new Problem(flight);
						problem.addAirProbs(airProblem);
					}
					problems.put(flight, problem);
				}

			} else if (type.equalsIgnoreCase("crewmember")) {
				if (delay < warningCrew) {
					if (problems.get(flight) == null) {
						warning = new Warning(flight, type, description, delay);

						if (warnings.get(flight) != null) {
							if (warnings.get(flight).getMinutesDelay() < delay) {
								warnings.remove(flight);
								warnings.put(flight, warning);
							}
						} else {
							warnings.put(flight, warning);
						}
					}
				} else {
					crewProblem = new CrewProblem(
							events.get(i).getCrewMember(), description, delay);

					if (warnings.get(flight) != null)
						warnings.remove(flight);

					if (problems.get(flight) != null) {
						problem = problems.get(flight);
						problems.remove(flight);
						problem.addCrewProbs(crewProblem);
					} else {
						problem = new Problem(flight);
						problem.addCrewProbs(crewProblem);
					}

					problems.put(flight, problem);
				}

			} else {
				if (delay < warningPax) {
					if (problems.get(flight) == null) {
						warning = new Warning(flight, type, description, delay);

						if (warnings.get(flight) != null) {
							if (warnings.get(flight).getMinutesDelay() < delay) {
								warnings.remove(flight);
								warnings.put(flight, warning);
							}
						} else {
							warnings.put(flight, warning);
						}
					}
				} else {
					paxProblem = new PaxProblem(description, delay, events.get(
							i).getNumPax());

					if (warnings.get(flight) != null)
						warnings.remove(flight);

					if (problems.get(flight) != null) {
						problem = problems.get(flight);
						problems.remove(flight);
						problem.addPaxProbs(paxProblem);
					} else {
						problem = new Problem(flight);
						problem.addPaxProbs(paxProblem);
					}

					problems.put(flight, problem);
				}
			}

		}

		Set set = problems.entrySet();

		Iterator i = set.iterator();

		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			((Problem) me.getValue()).print();
			Cria_Solucao_Aircraft((Problem) me.getValue());
		}

	}

	private void parseEvents() {
		ParseExcel parExc = new ParseExcel();
		parExc.openFile("EVENTS.xls");

		events = parExc.getEvents(parExc.getFile().getSheet(0), flight,
				escCrews);

		parExc.closeFile();
	}

	public void parseFlights() {
		// Ler Planeamento.
		ParseExcel parExc = new ParseExcel();
		parExc.openFile("FLIGHTS_2009_09.xls");
		airModel = parExc.getAircraftModels(parExc.getFile().getSheet(2));
		airport = parExc.getAirports(parExc.getFile().getSheet(3));
		aircrft = parExc.getAircrafts(parExc.getFile().getSheet(1), airModel);
		rank = parExc.getRanks(parExc.getFile().getSheet(5), airModel);
		crewmember = parExc.getCrewMembers(parExc.getFile().getSheet(4), rank);

		flight = parExc.getFlights(parExc.getFile().getSheet(0), airport,
				aircrft);
		escCrews = parExc.getEscCrews(flight, crewmember, rank);
		parExc.closeFile();

		for (int i = 0; i != escCrews.size(); i++) {
			escCrews.get(i).print();
			System.out.println("----------------------------------------");
		}
		System.out.println("----------------------------------------");
	}
}
