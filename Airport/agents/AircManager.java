package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

import java.io.IOException;

import support.Auxiliar;
public class AircManager extends Agent{
	
	int numProb;
	
	public void setup()
	{
		ReceiveProblem rcvPrbl  = new ReceiveProblem();
		addBehaviour(rcvPrbl);
		
	}

	class ReceiveProblem extends OneShotBehaviour
	{
		public ReceiveProblem()
		{
	
		
		}
		
		public void action()
		{
			MessageTemplate msgProblema = MessageTemplate.and(MessageTemplate
					.MatchPerformative(ACLMessage.INFORM), MessageTemplate
					.MatchConversationId("problema")) ;
			ACLMessage problema = blockingReceive(msgProblema);
			if(problema != null)
			{
				Auxiliar x = new Auxiliar();
				try {
					x = (Auxiliar)problema.getContentObject();
				} catch (UnreadableException e) {
					
					e.printStackTrace();
				}
				ACLMessage problemaAviao = new ACLMessage(ACLMessage.CFP);
				//Posteriormente enviar mensagem para Topic para todos receberem
				problemaAviao.addReceiver(new AID("EspAirc", false));
				try {
					problemaAviao.setContentObject(x);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				problemaAviao.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
				send(problemaAviao);
				addBehaviour(new ContractNetInitiator(myAgent, problemaAviao)
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
						//Avaliar respostas
						
						//Enviar resultado para CrewManager
						
						ACLMessage problemaCrew = new ACLMessage(ACLMessage.INFORM);
						problemaCrew.addReceiver(new AID("CrewManager", false));
						problemaCrew.setConversationId("problema Crew");
						send(problemaCrew);
						
						ACLMessage problemaSolved = new ACLMessage(ACLMessage.INFORM);
						problemaSolved.addReceiver(new AID("SupCoo", false));
						problemaSolved.setConversationId("solution" + Integer.toString(numProb));
						send(problemaSolved);
						numProb++;
					}
					
					protected void handleInform(ACLMessage inform) {
						System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
					}
					
					
					
					
					
				});
				
			}
			
		}
	}
}