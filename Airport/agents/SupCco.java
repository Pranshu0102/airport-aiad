package agents;

import java.util.ArrayList;

import jade.core.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;





public class SupCco extends Agent{
	
	ArrayList<ACLMessage> solucoes = new ArrayList<ACLMessage>();
	int numProblema = 0;
	
	public void setup()
	{
		RecebeSolutions rcvSol = new RecebeSolutions(this, numProblema);
		addBehaviour(rcvSol);
		
	}
	
	class RecebeSolutions extends CyclicBehaviour
	{
		int problema;
		public RecebeSolutions(Agent a, int prob)
		{
			super(a);
			problema = prob;
		}
		
		public void action()
		{
			
			MessageTemplate templateSolucoes = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId("solution" + Integer.toString(problema)));
			ACLMessage solu = receive(templateSolucoes);
			if(solu != null)
			{
				solucoes.add(solu);
			}
			if(solucoes.size() == 6)
			{
				this.block();
				//daqui vai para behaviour q escolha melhor solucao
				myAgent.removeBehaviour(this);
				
			}
		}
		
	}

}