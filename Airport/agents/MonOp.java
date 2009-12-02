package agents;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import problems.Problem;

import test.ParseExcel;
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

public class MonOp extends Agent{
	
	public void setup()
	{
		DetectProblem dtcPrblm = new DetectProblem();
		addBehaviour(dtcPrblm);
		
	}
	
	
	class DetectProblem extends OneShotBehaviour
	{
		Map<String, Aircraft> aircrft;
		Map<String, Rank> rank;
		ArrayList<CrewMember> crewmember;
		Map<String, Airport> airport ;
		Map<String, AircraftModel> airModel;
		ArrayList<Flight> flight;
		ArrayList<EscCrew> escCrews ;
		
		
		public DetectProblem()
		{
			
		}
		
		public void action()
		{
			// Ler Planeamento.
			ParseExcel parExc = new ParseExcel();
			
			airModel = parExc.getAircraftModels(parExc.getFile().getSheet(2));
			airport = parExc.getAirports(parExc.getFile().getSheet(3));
			aircrft = parExc.getAircrafts(parExc.getFile().getSheet(1), airModel);
			rank = parExc.getRanks(parExc.getFile().getSheet(5), airModel);
			crewmember = parExc.getCrewMembers(parExc.getFile().getSheet(4), rank);
			
			
			flight = parExc.getFlights(parExc.getFile().getSheet(0), airport, aircrft);
			escCrews = parExc.getEscCrews(flight, crewmember, rank);
			
			for(int i = 0 ; i!= escCrews.size(); i++)
				escCrews.get(i).print();
			
			//Ver problema
			test x = new test();
			Problem prob ;
			prob = x.analiseEvents();
		}
		
	}
	
	class EnviaProblema extends OneShotBehaviour
	{
		Problem problem;
		public EnviaProblema(Agent a, Problem p)
		{
			super(a);
			problem = p;
			
		}
		public void action()
		{
			ACLMessage msgProb = new ACLMessage(ACLMessage.CFP);
			msgProb.addReceiver(new AID("AircManager" , false));
			msgProb.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			try {
				msgProb.setContentObject(problem);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			send(msgProb);
			
			addBehaviour(new ContractNetInitiator(myAgent, msgProb)
			{
				protected void handlePropose(ACLMessage propose) {
					System.out.println("Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+refuse.getSender().getName()+" refused");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the receiver
						// does not exist
						System.out.println("Responder does not exist");
					}
					else {
						System.out.println("Agent "+failure.getSender().getName()+" failed");
					}
					// Immediate failure --> we will not receive a response from this agent
					
				}
				
				protected void handleAllResponses() {
				
				}
				
				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
				}
			});
		}
		
	}
	
	
}