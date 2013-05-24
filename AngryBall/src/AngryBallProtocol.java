public class AngryBallProtocol {
		
		enum State {
		    WAITING_CARD, VERIFYING_CARD, MATCHING_DATA, CHOOSING_TRANSACTION,
		    DEPOSITING_CASH,DISPLAYING_BALANCE,DISPENSING_CASH,DISPENSING_CARD, END 
		}
		
	    private State currentState;

	    public AngryBallProtocol()
		{		
			currentState = State.WAITING_CARD;
		}
		
	    public String getCurrentState()
	    {
	    	return currentState.toString();
	    }
	           
		public void nextState(String transition) 
		{
			switch (currentState)
			{
				case WAITING_CARD:
					if(transition.equalsIgnoreCase("insert_card"))
						currentState = State.VERIFYING_CARD;		
					break;
				
				case VERIFYING_CARD:
					if(transition.equalsIgnoreCase("input_pin"))
						currentState = State.MATCHING_DATA;				
					break;
					
				case MATCHING_DATA:
					if(transition.equalsIgnoreCase("correct_pin"))
						currentState = State.CHOOSING_TRANSACTION;
					else if(transition.equalsIgnoreCase("incorrect_pin"))
						currentState = State.VERIFYING_CARD;				
					break;
					
				case CHOOSING_TRANSACTION:
					if(transition.equalsIgnoreCase("check_balance"))
						currentState = State.DISPLAYING_BALANCE;
					else if(transition.equalsIgnoreCase("insert_cash"))
						currentState = State.DEPOSITING_CASH;
					else if(transition.equalsIgnoreCase("withdraw"))
						currentState = State.DISPENSING_CASH;
					else if(transition.equalsIgnoreCase("cancel"))
						currentState = State.DISPENSING_CARD;				
					break;	
					
				case DISPLAYING_BALANCE:
					if(transition.equalsIgnoreCase("another_transaction"))
						currentState = State.CHOOSING_TRANSACTION;
					else if(transition.equalsIgnoreCase("cancel"))
						currentState = State.DISPENSING_CARD;
					break;
					
				case DISPENSING_CASH:
					if(transition.equalsIgnoreCase("another_transaction"))
						currentState = State.CHOOSING_TRANSACTION;
					else if(transition.equalsIgnoreCase("cancel"))
						currentState = State.DISPENSING_CARD;

				case DEPOSITING_CASH:
					if(transition.equalsIgnoreCase("another_transaction"))
						currentState = State.CHOOSING_TRANSACTION;
					else if(transition.equalsIgnoreCase("cancel"))
						currentState = State.DISPENSING_CARD;
					break;
					
				case DISPENSING_CARD:
					if(transition.equalsIgnoreCase("take_card"))
						currentState = State.END;
					break;
			
				default:
					break;
						
			}		
		}
}
