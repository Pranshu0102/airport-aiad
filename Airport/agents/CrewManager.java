package agents;
import jade.core.*;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;

public class CrewManager extends Agent {

	int numProb;
	public void setup()
	{
		RecebeProblema recvProb = new RecebeProblema(this);
		addBehaviour(recvProb);
		
	}
	
	class RecebeProblema extends OneShotBehaviour
	{
		public RecebeProblema(Agent a)
		{
			super(a);
		}
		
		public void action()
		{
			MessageTemplate tmplt = MessageTemplate.and(MessageTemplate
					.MatchConversationId("problema Crew"), MessageTemplate
					.MatchPerformative(ACLMessage.INFORM));
			
			ACLMessage prob = blockingReceive(tmplt);
			if(prob != null)
			{
				//LE PROBLEMA
				
				//ENVIA problema para especialistas
				
				ACLMessage problemaCrew = new ACLMessage(ACLMessage.CFP);
				problemaCrew.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
				problemaCrew.addReceiver(new AID("EspCrew", false));
				problemaCrew.setContent("problema crew.");
				problemaCrew.setConversationId("problema Crew");
				send(problemaCrew);
				addBehaviour(new ContractNetInitiator(myAgent, problemaCrew)
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
						problemaCrew.addReceiver(new AID("PaxManager", false));
						problemaCrew.setConversationId("problema Passageiros");
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