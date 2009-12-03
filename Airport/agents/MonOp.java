package agents;

import java.io.IOException;
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

import support.ParseExcel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import airline.*;

/*
 * Le problema. Envia problema para AircManager.
 * */

public class MonOp extends Agent {

	public void setup() {
		DetectProblem dtcPrblm = new DetectProblem();
		addBehaviour(dtcPrblm);

	}

	class DetectProblem extends OneShotBehaviour {
		Map<String, Aircraft> aircrft;
		Map<String, Rank> rank;
		ArrayList<CrewMember> crewmember;
		Map<String, Airport> airport;
		Map<String, AircraftModel> airModel;
		ArrayList<Flight> flight;
		ArrayList<EscCrew> escCrews;
		ArrayList<Event> events;

		public DetectProblem() {

		}

		public void action() {
			// Ler Planeamento.
			ParseExcel parExc = new ParseExcel();
			parExc.openFile("FLIGHTS_2009_09.xls");
			airModel = parExc.getAircraftModels(parExc.getFile().getSheet(2));
			airport = parExc.getAirports(parExc.getFile().getSheet(3));
			aircrft = parExc.getAircrafts(parExc.getFile().getSheet(1),
					airModel);
			rank = parExc.getRanks(parExc.getFile().getSheet(5), airModel);
			crewmember = parExc.getCrewMembers(parExc.getFile().getSheet(4),
					rank);

			flight = parExc.getFlights(parExc.getFile().getSheet(0), airport,
					aircrft);
			escCrews = parExc.getEscCrews(flight, crewmember, rank);
			parExc.closeFile();

			// Ler eventos
			parExc = new ParseExcel();
			parExc.openFile("EVENTS.xls");
			events = parExc.getEvents(parExc.getFile().getSheet(0), flight,
					escCrews);
			parExc.closeFile();

			// Analisar eventos
			LeProblema leprob = new LeProblema(myAgent, events);
			myAgent.addBehaviour(leprob);
		}

	}

	class LeProblema extends TickerBehaviour {
		List<Event> events;
		Map<Flight, Problem> problems;
		Map<Flight, Warning> warnings;
		
		public LeProblema(Agent a, List<Event> events) {
			super(a, 15000);
			this.events = events;
		}

		public void generateCenas() {
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
							warning = new Warning(flight, type, description,
									delay);
							warnings.put(flight, warning);
						}
					} else {
						airProblem = new AircraftProblem(description, delay);

						if (warnings.get(flight) != null)
							warnings.remove(flight);

						if (problems.get(flight) != null) {
							problem = problems.get(flight);
							problems.remove(flight);
							problem.addAirProbs(airProblem);
							// problem.print();
						} else {
							problem = new Problem(flight);
							problem.addAirProbs(airProblem);
						}

						problems.put(flight, problem);
					}

				} else if (type.equalsIgnoreCase("crewmember")) {
					if (delay < warningCrew) {
						if (problems.get(flight) == null) {
							warning = new Warning(flight, type, description,
									delay);
							warnings.put(flight, warning);
						}
					} else {
						crewProblem = new CrewProblem(events.get(i)
								.getCrewMember(), description, delay);

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
							warning = new Warning(flight, type, description,
									delay);
							warnings.put(flight, warning);
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
			}

			Set set = problems.entrySet();

			Iterator i = set.iterator();

			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				((Problem) me.getValue()).print();
			}

		}

		public void onTick() {

		}

	}

	class EnviaWarning extends OneShotBehaviour {
		Warning warning;

		public EnviaWarning(Agent a, Warning w) {
			super(a);
			warning = w;

		}

		@Override
		public void action() {
			// TODO Auto-generated method stub

		}
	}

	class EnviaProblema extends OneShotBehaviour {
		Problem problem;

		public EnviaProblema(Agent a, Problem p) {
			super(a);
			problem = p;

		}

		public void action() {
			ACLMessage msgProb = new ACLMessage(ACLMessage.CFP);
			msgProb.addReceiver(new AID("AircManager", false));
			msgProb
					.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			try {
				msgProb.setContentObject(problem);
			} catch (IOException e) {

				e.printStackTrace();
			}
			send(msgProb);

			addBehaviour(new ContractNetInitiator(myAgent, msgProb) {
				protected void handlePropose(ACLMessage propose) {
					System.out.println("Agent " + propose.getSender().getName()
							+ " proposed " + propose.getContent());
				}

				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent " + refuse.getSender().getName()
							+ " refused");
				}

				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the
						// receiver
						// does not exist
						System.out.println("Responder does not exist");
					} else {
						System.out.println("Agent "
								+ failure.getSender().getName() + " failed");
					}
					// Immediate failure --> we will not receive a response from
					// this agent

				}

				protected void handleAllResponses() {

				}

				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent " + inform.getSender().getName()
							+ " successfully performed the requested action");
				}
			});
		}

	}

}