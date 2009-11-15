package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

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
		public DetectProblem()
		{
			
		}
		
		public void action()
		{
			// Ler Planeamento. Detectar Problema.
			
		}
		
	}
	
	class EnviaProblema extends OneShotBehaviour
	{
		public EnviaProblema()
		{
			
		}
		public void action()
		{
			ACLMessage msgProb = new ACLMessage(ACLMessage.CFP);
			msgProb.addReceiver(new AID("AircManager" , false));
			msgProb.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			msgProb.setContent("Problema");
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