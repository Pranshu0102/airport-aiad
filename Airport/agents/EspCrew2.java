package agents;

import java.io.IOException;

import problems.CrewProblem;
import problems.Problem;

import solutions.AircraftSolution;
import solutions.CrewSolution;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;

public class EspCrew2 extends Agent{

	private static int custo_substituicao = 160;
	private static int custo_minuto_atraso = 30;
	public void setup()
	{
		MessageTemplate template = MessageTemplate.and(
		  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
		  		MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		
		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				
				CrewSolution proposal= Cria_Solucao(cfp) ;
				
				if (!proposal.equals(null)) {
					// We provide a proposal
					System.out.println("Agent "+getLocalName()+": Proposing "+proposal);
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					try {
						propose.setContentObject(proposal);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
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
	
	public CrewSolution Cria_Solucao(ACLMessage msg)
	{
		
		return null;
	}
}
