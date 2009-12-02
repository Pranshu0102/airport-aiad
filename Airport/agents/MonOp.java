package agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import problems.AircraftProblem;
import problems.CrewProblem;
import problems.Event;
import problems.PaxProblem;
import problems.Problem;
import problems.Warning;

import support.ParseExcel;
import test.test;
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
			
			
			parExc = new ParseExcel();
			parExc.openFile("EVENTS.xls");
			events = parExc.getEvents(parExc.getFile().getSheet(0), flight,
					escCrews);
			parExc.closeFile();
			
			LeProblema leprob = new LeProblema(myAgent, events);
			myAgent.addBehaviour(leprob);
		}

	}

	class LeProblema extends TickerBehaviour {
		List<Event> events;
		
		public LeProblema(Agent a, List<Event> events) {
			super(a, 15000);
			this.events = events;
		}

		public void onTick() {
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
			List<Warning> warnings = new ArrayList<Warning>();
			List<Problem> problems = new ArrayList<Problem>();

			for (int i = 0; i != events.size(); i++) {
				warning = null;
				crewProblem = null;
				airProblem = null;
				problem = null;
				paxProblem = null;

				// criar delay na análise de eventos
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// ler dados evento
				type = events.get(i).getType();
				delay = events.get(i).getDelay();
				description = events.get(i).getDescription();

				if (type.equalsIgnoreCase("aircraft")) {
					if (delay < warningAircraft) {
						warning = new Warning(type, description, delay);
						
						EnviaWarning enviawarn = new EnviaWarning(myAgent, warning);
						myAgent.addBehaviour(enviawarn);
					} else {
						problem = new Problem();
						airProblem = new AircraftProblem(description, delay);
						problem.addAirProbs(airProblem);
						
						EnviaProblema enviaprob = new EnviaProblema(myAgent, problem);
						myAgent.addBehaviour(enviaprob);
					}

				} else if (type.equalsIgnoreCase("crewmember")) {
					if (delay < warningCrew) {
						warning = new Warning(type, description, delay);
						
						EnviaWarning enviawarn = new EnviaWarning(myAgent, warning);
						myAgent.addBehaviour(enviawarn);
					} else {
						problem = new Problem();
						crewProblem = new CrewProblem(description, delay);
						problem.addCrewProbs(crewProblem);
						
						EnviaProblema enviaprob = new EnviaProblema(myAgent, problem);
						myAgent.addBehaviour(enviaprob);
					}

				} else {
					if (delay < warningPax) {
						warning = new Warning(type, description, delay);
						
						EnviaWarning enviawarn = new EnviaWarning(myAgent, warning);
						myAgent.addBehaviour(enviawarn);
						
					} else {
						problem = new Problem();
						paxProblem = new PaxProblem(description, delay);
						problem.addPaxProbs(paxProblem);
						
						EnviaProblema enviaprob = new EnviaProblema(myAgent, problem);
						myAgent.addBehaviour(enviaprob);
					}
				}
//				if (warning != null)
//				{
//					warnings.add(warning);
//					warning.print();
//				}
//				else if (problem!= null)
//				{
//					problems.add(problem);
//					problem.print();
//				}
			}

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