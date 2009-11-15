package agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;

public class PaxManager extends Agent{
int numProb;
	
	public void setup()
	{
		RecebeProbPax rcvPax = new RecebeProbPax(this);
		addBehaviour(rcvPax);
		
	}
	
	class RecebeProbPax extends OneShotBehaviour
	{
	public RecebeProbPax(Agent a)
	{
		super(a);
	}
	public void action()
	{
		MessageTemplate templat = MessageTemplate.and(MessageTemplate
				.MatchConversationId("problema Pax"), MessageTemplate
				.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage problemaPax = blockingReceive(templat);
		if(problemaPax != null)
		{
			ACLMessage problema_Pax = new ACLMessage(ACLMessage.CFP);
			problema_Pax.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			problema_Pax.addReceiver(new AID("EspCrew", false));
			problema_Pax.setContent("problema crew.");
			problema_Pax.setConversationId("problema Crew");
			send(problema_Pax);
			addBehaviour(new ContractNetInitiator(myAgent, problema_Pax)
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
					
					ACLMessage problemaSolved = new ACLMessage(ACLMessage.INFORM);
					problemaSolved.addReceiver(new AID("SupCoo", false));
					problemaSolved.setConversationId("solution"+Integer.toString(numProb));
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