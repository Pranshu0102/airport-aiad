package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import problems.AircraftProblem;
import problems.CrewProblem;
import problems.Event;
import problems.PaxProblem;
import problems.Problem;
import problems.Warning;
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

	Map<Flight, Problem> problems;
	Map<Flight, Warning> warnings;

	public static void main(String args[]) {
		test main = new test();
		main.parseFlights();
		main.parseEvents();
		main.analiseEvents();
	}

	private void testAircraftManager(Problem problem) {

		// Vamos partir do principio que uma solução não gera novos problemas
		if (problem.getAirProbs().size() != 0) {
			System.out.println("Contem um problema de avião");
			// Chama 3 especialistas

			// Escolhe duas soluções

			// Retira o problema de aviao da lista
			//problem.setAirProbs(null);

			// Adiciona novos problemas que possam surgir com a implementaçao de
			// cada uma das soluções?
			Long delay = 15L * 60 * 1000;
			boolean found = false;
			int k = 0, i = 0;
			while(!found && i<escCrews.size())
			{
				while(!found && k<escCrews.get(i).getFlights().size())
				{
					if(problem.getFlight() == escCrews.get(i).getFlights().get(k))
					{
						escCrews.get(i).print();
						escCrews.get(i).addDelay(k,delay);
						escCrews.get(i).print();
						found = true;
					}
					k++;
				}
				i++;
				k=0;
			}

		}
		

		// Chama proximo Manager enviando os dois problemas e as duas soluções

		// DÚVIDA:
		/*
		 * Uma solução criada por este manager pode gerar outros problemas,
		 * mesmo sem ter esses problemas resolvidos envio a solução para o
		 * SupCoo???
		 */

		// testCrewManager()
	}

	@SuppressWarnings("unchecked")
	private void analiseEvents() {
		String type;
		int delay;
		String description;

		int warningAircraft = 50;
		int warningCrew = 25;
		int warningPax = 35;

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

					if (problems.get(flight)!=null) {
						problem = problems.get(flight);
						problems.remove(flight);
						problem.addAirProbs(airProblem);
					} else {
						problem = new Problem(flight);
						problem.addAirProbs(airProblem);
					}
					testAircraftManager(problem);
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
					paxProblem = new PaxProblem(description, delay);

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
			// if (warning != null)
			// {
			// warning.print();
			// }
			// else if (problem!= null)
			// {
			// problem.print();
			// testAircraftManager(problem);
			// }
		}

		Set set = problems.entrySet();

		Iterator i = set.iterator();

		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			((Problem) me.getValue()).print();
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
