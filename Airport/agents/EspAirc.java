package agents;

import java.io.IOException;
import java.util.ArrayList;

import problems.AircraftProblem;
import problems.Problem;
import solutions.AircraftSolution;
import solutions.Solution;
import support.Auxiliar;
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

public class EspAirc extends Agent {

	public static int custo_small_hora_atraso = 600;
	public static int custo_big_hora_atraso = 950;
	public static int custo_smal_sub = 7300;
	public static int custo_big_sub = 9000;
	int custoTotal;
	ArrayList<EscCrew> esc;

	public void setup() {

		MessageTemplate template = MessageTemplate
				.and(
						MessageTemplate
								.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
						MessageTemplate.MatchPerformative(ACLMessage.CFP));

		addBehaviour(new ContractNetResponder(this, template) {
			protected ACLMessage prepareResponse(ACLMessage cfp)
					throws NotUnderstoodException, RefuseException {

				System.out.println("Agent " + getLocalName()
						+ ": CFP received from " + cfp.getSender().getName()
						+ ". Action is " + cfp.getContent());

				AircraftSolution proposal = Cria_Solucao(cfp);

				if (!proposal.equals(null)) {
					// We provide a proposal
					System.out.println("Agent " + getLocalName()
							+ ": Proposing " + proposal);
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					try {
						propose.setContentObject(proposal);
					} catch (IOException e) {

						e.printStackTrace();
					}
					return propose;
				} else {
					// We refuse to provide a proposal
					System.out.println("Agent " + getLocalName() + ": Refuse");
					throw new RefuseException("evaluation-failed");
				}
			}

			protected ACLMessage prepareResultNotification(ACLMessage cfp,
					ACLMessage propose, ACLMessage accept)
					throws FailureException {
				System.out.println("Agent " + getLocalName()
						+ ": Proposal accepted");
				if (true) {
					System.out.println("Agent " + getLocalName()
							+ ": Action successfully performed");
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				} else {
					System.out.println("Agent " + getLocalName()
							+ ": Action execution failed");
					throw new FailureException("unexpected-error");
				}
			}

			protected void handleRejectProposal(ACLMessage cfp,
					ACLMessage propose, ACLMessage reject) {
				System.out.println("Agent " + getLocalName()
						+ ": Proposal rejected");
			}
		});

	}

	public AircraftSolution Cria_Solucao(ACLMessage message) {
		AircraftSolution sol = null;
		try {

			Auxiliar x = (Auxiliar) message.getContentObject();

			Problem prob = x.getProblem();
			esc = x.getEscc();
			
			Long totalDelayTime = getTotalDelay(prob, esc);
			int custDelay = 0, custSub=0;
			
			String model = prob.getFlight().getAircraft().getModel().getModel();
			
			if(model.equalsIgnoreCase("319") || model.equalsIgnoreCase("320") || model.equalsIgnoreCase("321"))
			{
				custDelay = custo_small_hora_atraso;
				custSub = custo_smal_sub;
			}
			else
			{
				custDelay = custo_big_hora_atraso;
				custSub = custo_big_sub;
			}
			
			

			if (totalDelayTime < 60) {

				custoTotal = (int) (totalDelayTime * custDelay/60); 
				sol = new AircraftSolution("Voos relacionados atrasados", 1,
							custoTotal);
			} else

				// necessario substituir aviao

				custoTotal += custSub + prob.getAirProbs().get(0).getMinutesDelay()
						* custDelay/60;
			
			sol = new AircraftSolution("Aviao Susbitituido", 1, custoTotal);

		} catch (UnreadableException e) {

			e.printStackTrace();
		}

		return sol;
	}

	private Long getTotalDelay(Problem problem, ArrayList<EscCrew> escCrews) {
		// Vamos partir do principio que uma solução não gera novos problemas
		Long totalDelay = 0L;
		Long delay = 0L;
		if (problem.getAirProbs().size() != 0) {
			System.out.println("Contem um problema de avi�o");
			
			for(int i= 0; i!= problem.getAirProbs().size(); i++)
				if(problem.getAirProbs().get(i).getMinutesDelay() > totalDelay)
						delay = problem.getAirProbs().get(0).getMinutesDelay() * 60 * 1000L;
			
			boolean found = false;
			int k = 0, i = 0;
			while (!found && i < escCrews.size()) {
				while (!found && k < escCrews.get(i).getFlights().size()) {
					if (problem.getFlight() == escCrews.get(i).getFlights()
							.get(k)) {
						totalDelay = escCrews.get(i).getHowManyFlightsLeft(k)*delay;
						found = true;
					}
					k++;
				}
				i++;
				k = 0;
			}
	
		}
		
		return totalDelay;
	}
}
