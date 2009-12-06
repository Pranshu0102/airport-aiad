package agents;

import java.io.IOException;
import java.util.ArrayList;

import problems.AircraftProblem;
import problems.Problem;
import solutions.AircraftSolution;
import solutions.Solution;
import support.Aux;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.*;
import jade.proto.ContractNetResponder;
import airline.*;
import agents.*;

public class EspAirc2 extends Agent {

	public static int custo_b300s_hora_atraso = 600;
	public static int custo_b400s_hora_atraso = 950;
	public static int custo_b300s_sub = 7300;
	public static int custo_b400s_sub = 9000;
	int custoTotal;
	ArrayList<EscCrew> esc;
	
	public void setup()
	{
		
		MessageTemplate template = MessageTemplate.and(
		  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
		  		MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		
		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
				
				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
				
				AircraftSolution proposal= Cria_Solucao(cfp) ;
				
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
	
	public AircraftSolution Cria_Solucao(ACLMessage message)
	{
		AircraftSolution sol=null;
		try {
			
			Aux x = (Aux)message.getContentObject();
	
			Problem prob = x.getProblem();
			esc = x.getEscc();
			AircraftProblem aircProb = prob.getAirProbs().get(0);
			int delay = aircProb.getMinutesDelay();
			
			if(delay<90)
			{
				
				int num = testAircraftManager(prob,esc);
				custoTotal+= num*custo_b300s_hora_atraso;
				 sol = new AircraftSolution("Voos relacionados atrasados", 1, custoTotal);
			}else
				
				//necessario substituir aviao
				
				custoTotal+= custo_b300s_sub + (delay/60)*custo_b300s_hora_atraso;
			     sol = new AircraftSolution("Aviao Susbitituido", 1, custoTotal);
		
			
		} catch (UnreadableException e) {
			
			e.printStackTrace();
		}
		
		return sol;
	}
	
	
	private int testAircraftManager(Problem problem, ArrayList<EscCrew> escCrews) {
		int num_voos_afectado=0;
			// Vamos partir do principio que uma solução não gera novos problemas
			if (problem.getAirProbs().size() != 0) {
				System.out.println("Contem um problema de avião");
				// Chama 3 especialistas

				// Escolhe duas soluções

				// Retira o problema de aviao da lista
				//problem.setAirProbs(null);

				// Adiciona novos problemas que possam surgir com a implementaçao de
				// cada uma das soluções?
				Long delay = 15L * 60 * 1000;
				boolean found = false;
				int k = 0, i = 0;
				while(!found && i<escCrews.size())
				{
					while(!found && k<escCrews.get(i).getFlights().size())
					{
						if(problem.getFlight() == escCrews.get(i).getFlights().get(k))
						{
							escCrews.get(i).print();
							escCrews.get(i).addDelay(k,delay);
							escCrews.get(i).print();
							found = true;
							num_voos_afectado++;
						}
						k++;
					}
					i++;
					k=0;
				}

			}
			

			// Chama proximo Manager enviando os dois problemas e as duas soluções

			// DÚVIDA:
			/*
			 * Uma solução criada por este manager pode gerar outros problemas,
			 * mesmo sem ter esses problemas resolvidos envio a solução para o
			 * SupCoo???
			 */

			// testCrewManager()
			return num_voos_afectado;
		}
}



