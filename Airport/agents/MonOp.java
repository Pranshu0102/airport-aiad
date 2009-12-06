package agents;

import java.awt.Dimension;
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
	
	
	Main_Frame frame_voos;
	ParseExcel parExc = new ParseExcel();
	int num_rows_max= parExc.num_events;
	int num_rows;
	public void setup() {
		
		Init_Gui();
		DetectProblem dtcPrblm = new DetectProblem();
		addBehaviour(dtcPrblm);

	}

	public void Init_Gui()
	{
	
		frame_voos = new Main_Frame();
		frame_voos.setSize(865, 450);
		frame_voos.setPreferredSize(new Dimension(865,108));
		frame_voos.setVisible(true);
		
		
		
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

		public void onTick() {
			String type;
			int delay;
			String description;
			Flight fli;
			CrewMember crew;
			
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

			
				warning = null;
				crewProblem = null;
				airProblem = null;
				problem = null;
				paxProblem = null;
				
				
				
				
				// criar delay na an�lise de eventos
			

				// ler dados evento
				type = events.get(num_rows).getType();
				System.out.println("Type:" + type);
				delay = events.get(num_rows).getDelay();
				System.out.println("Delay: "+ delay);
				description = events.get(num_rows).getDescription();
				System.out.println("Description: " + description);
				fli = events.get(num_rows).getFlight();
				System.out.println("Num voo" +fli.getFlightNumber());
				
				
			
				if (type.equalsIgnoreCase("aircraft")) {
					if (delay < warningAircraft) {
						warning = new Warning(type, description, delay);
						frame_voos.addWarning_Panel(warning);
						EnviaWarning enviawarn = new EnviaWarning(myAgent, warning);
						myAgent.addBehaviour(enviawarn);
					} else {
						problem = new Problem();
						airProblem = new AircraftProblem(description, delay);
						frame_voos.addProblem_Panel(airProblem);
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
					//	myAgent.addBehaviour(enviawarn);
						
					} else {
						problem = new Problem();
						paxProblem = new PaxProblem("pax", delay, Integer.parseInt(description));
						problem.addPaxProbs(paxProblem);
						
						EnviaProblema enviaprob = new EnviaProblema(myAgent, problem);
					//	myAgent.addBehaviour(enviaprob);
						
					}
				}
				num_rows++;
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
			

			Set set = problems.entrySet();

			Iterator i = set.iterator();

			while (i.hasNext()) {
				Map.Entry me = (Map.Entry) i.next();
				((Problem) me.getValue()).print();
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