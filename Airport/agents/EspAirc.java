package agents;

import problems.AircraftProblem;
import problems.Problem;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.*;
import jade.proto.ContractNetResponder;


public class EspAirc extends Agent {

	public static int custo_b300s_hora_atraso = 600;
	public static int custo_b400s_hora_atraso = 950;
	public static int custo_b300s_sub = 1300;
	public static int custo_b400s_sub = 2000;
	
	
	public void setup()
	{
		MessageTemplate template = MessageTemplate.and(
		  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
		  		MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		
		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				
				int proposal= Cria_Solucao(cfp) ;
				
				if (proposal!= 0) {
					// We provide a proposal
					System.out.println("Agent "+getLocalName()+": Proposing "+proposal);
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					propose.setContent(String.valueOf(proposal));
					return propose;
				}
				else {
					// We refuse to provide a proposal
					System.out.println("Agent "+getLocalName()+": Refuse");
					throw new RefuseException("evaluation-failed");
				}
			}
			
			protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
				System.out.println("Agent "+getLocalName()+": Proposal accepted");
				if (true) {
					System.out.println("Agent "+getLocalName()+": Action successfully performed");
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				}
				else {
					System.out.println("Agent "+getLocalName()+": Action execution failed");
					throw new FailureException("unexpected-error");
				}	
			}
			
			protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
				System.out.println("Agent "+getLocalName()+": Proposal rejected");
			}
		} );
		
		
		

	}
	
	public int Cria_Solucao(ACLMessage message)
	{
		try {
			
			Problem prob = (Problem)message.getContentObject();
			AircraftProblem aircProb = prob.getAirProbs().get(0);
			int delay = aircProb.getMinutesDelay();
			
			/*
			 * 
			 * Adicionar atraso aos outros OU ALTERAR PARA OUTRO VOO(depend do esp)
			 * 
			 * 
			 */
			
		} catch (UnreadableException e) {
			
			e.printStackTrace();
		}
		
		return 0;
	}
}


		/*Custos:
		 * Cada esp irá adicionar custos de uma forma diferente
		 * para obter resultados diferentes. 
		 *  Por atraso de aviao:
		 *  		- Tipos de aviao tem custos diferentes.
		 *  
		 *  Por mudança de aviao:
		 *  		- Igual. Tipos diferentes. Custos diferentes.
		 *  
		 *  Para os esps darem resultados diferentes vamos ter de definir limites de tempo
		 *  diferentes, isto é, para o esp1 se o aviao atrasar tipo mais de uma
		 *  hora entao tem de se mudar de aviao. Para o esp2 será um tempo diferente.
		 *  E para o esp3 podemos dizer q ele nunca quer mudar de aviao. Apenas esperar.
		 * 
		 * 
		 * 
*/


