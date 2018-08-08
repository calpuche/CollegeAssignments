package bowling;

public class SinglePlayerBowlingScoreboardImpl_Alpuche implements SinglePlayerBowlingScoreboard, AssignmentMetaData
{
	private static final int MAXIMUM_ROLLS = 21;	//Maximum rolls in a one player game
	private String playerName;
	private int[] pinsKnockedDownArray = new int[MAXIMUM_ROLLS];
	private int rollCount = 0;
	
	public SinglePlayerBowlingScoreboardImpl_Alpuche(String playerName)
	{
		assert playerName != null : "playerName is null!";
		this.playerName = playerName;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public int getCurrentFrame() 
	{
		assert !isGameOver() : "Game is over!";

		return getCurrentFrameThroughIncrementCount();

	}
	
	private int getCurrentFrameThroughIncrementCount() {
		
		int currentFrameScan = 1;
		int arrayIndex=0;
		int numberOfRolls=0;
		while (numberOfRolls<rollCount && currentFrameScan<10) {
			if (pinsKnockedDownArray[arrayIndex] == 10) {
				arrayIndex++;
				currentFrameScan ++;
				numberOfRolls++;
			}
			else if ((numberOfRolls+2)<=rollCount && currentFrameScan<10) {
				arrayIndex= arrayIndex+2;
				numberOfRolls = numberOfRolls +2;
				currentFrameScan ++;
			}
			else if ((numberOfRolls+1)<=rollCount && currentFrameScan<10){
				arrayIndex= arrayIndex+2;
				numberOfRolls = numberOfRolls +2;
			}
		}
		return currentFrameScan;
	}

	public Mark getMark(int frameNumber, int boxIndex) 
	{	
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 10 : "frameNumber = " + frameNumber + " > 10!";
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 3 : "boxIndex = " + boxIndex + " > 3!";
		// new pre:  frameNumber <= getCurrentFrame()
		if (!isGameOver()) {
			assert frameNumber <= getCurrentFrame() : "frameNumber = " + frameNumber + "<= " + "getCurrentFrame= " + getCurrentFrame();


			// new pre: (frameNumber != getCurrentFrame()) || (getCurrentBall() > boxIndex)
			assert (frameNumber != getCurrentFrame()) || (getCurrentBall() > boxIndex) : "frameNumber=" + frameNumber + "getCurrentBall = " + getCurrentBall() + "getCurrentFrame()=" + getCurrentFrame() +  "boxIndex=" +  boxIndex;
		}
		//part of pre: frameNumber = 10 ==> isGameOver()
		if (frameNumber==10 && !isGameOver()) {
			//return Mark.EMPTY;
			assert getCurrentBall() > boxIndex : "getCurrentBall() = " + getCurrentBall() +" < boxIndex=" + boxIndex;
		}
		if (frameNumber ==10) {
			assert (boxIndex == 1 ||boxIndex == 2 || boxIndex == 3) : "frameNumber == 10 and boxIndex=="+ boxIndex;
		}
		if (frameNumber < 10 && frameNumber >= 1) {
			assert (boxIndex <=2 && boxIndex >= 1) : "boxIndex = " + boxIndex + "> 2 when 1<= frameNumber < 3";
		}
		if (boxIndex == 3) {
			assert (frameNumber == 10) : "frame number =" + frameNumber + "when boxIndex=3";
		}

		//End of assertions from previous 
		
		Mark mark = null;
		if(frameNumber < 10) mark = getMarkSingleDigitFrameNumber(frameNumber, boxIndex);
		else mark = getMarkTenthFrame(boxIndex);
		assert mark != null : "mark is null!";
		return mark;
	}
		
	private Mark getMarkSingleDigitFrameNumber(int frameNumber, int boxIndex)
	{
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 9 : "frameNumber = " + frameNumber + " > 9!";
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 2 : "boxIndex = " + boxIndex + " > 2!";
		int currentFrameScan = 1;
		int arrayIndex=0;
		while (currentFrameScan<frameNumber) {
			if (pinsKnockedDownArray[arrayIndex] == 10) {
				arrayIndex++;
				currentFrameScan ++;
			}
			else {
				arrayIndex= arrayIndex+2;
				currentFrameScan ++;
			}	
		}
		if (pinsKnockedDownArray[arrayIndex]==10) {
			if (boxIndex==1) {
				return Mark.EMPTY;
			}
			else {
				return Mark.STRIKE;
			}
		}
		
		if (boxIndex==2) {
			if (pinsKnockedDownArray[arrayIndex+boxIndex-1]+pinsKnockedDownArray[arrayIndex+boxIndex-2]==10) {
				return Mark.SPARE;
			}
		}
		return Mark.translate(pinsKnockedDownArray[arrayIndex+boxIndex-1]);	
	}
	
	private Mark getMarkTenthFrame(int boxIndex)
	{
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 3 : "boxIndex = " + boxIndex + " > 3!";
		
		int arrayIndex = 0;
		int frame=1;
		while (frame<10) {
			if (pinsKnockedDownArray[arrayIndex] == 10) {
				frame ++;
				arrayIndex ++;
			}
			else {
				arrayIndex = arrayIndex+2;
				frame ++;
			}
			
		}
		//Can only be a Spare if Previous Box had a Roll of 0 Pins Knocked Down
		boolean isSelectedBoxIndexRollStrikeOrSpare = (pinsKnockedDownArray[arrayIndex+boxIndex-1] == 10);
		boolean isFirstRollOnTenthFrameAStrike = (pinsKnockedDownArray[arrayIndex+boxIndex-2]==10);
		boolean isPreviousAndCurrentBoxEqualToTen = (pinsKnockedDownArray[arrayIndex+boxIndex-2] + pinsKnockedDownArray[arrayIndex+boxIndex-1] == 10);
		
		if (boxIndex==2  && !isFirstRollOnTenthFrameAStrike && isPreviousAndCurrentBoxEqualToTen) {
			return Mark.SPARE;
		}
		if (boxIndex==2 && isFirstRollOnTenthFrameAStrike && isSelectedBoxIndexRollStrikeOrSpare) {
			return Mark.STRIKE;
		}
		if (boxIndex==3 && isPreviousAndCurrentBoxEqualToTen && pinsKnockedDownArray[arrayIndex+boxIndex-2] != 10) {
			return Mark.SPARE;
		}
		
		
		
		if (pinsKnockedDownArray[arrayIndex+boxIndex-1]!=10){
		return Mark.translate(pinsKnockedDownArray[arrayIndex+boxIndex-1]);
		}
		else {
			return Mark.STRIKE;
		}
	}

	public int getScore(int frameNumber) {
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 10 : "frameNumber = " + frameNumber + " > 10!";

		if (!isGameOver()) {
			assert getCurrentFrame() > frameNumber : "getCurrentFrame() = " + getCurrentFrame() + " < frameNumber = " + frameNumber;
			if (getCurrentFrame()==(frameNumber+2)) {
				assert !(Mark.STRIKE.equals(getMark(frameNumber, 2)) && Mark.STRIKE.equals(getMark(frameNumber+1, 2))) : " getCurrentFrame=" + getCurrentFrame() + "frameNumber=" + frameNumber + " getMark(frameNumber,2)==" + getMark(frameNumber,2) + " getMark(frameNumber+1, 2)==" + getMark(frameNumber+1, 2) + "pinsKnockArray" + pinsKnockedDownArray[0] + pinsKnockedDownArray[1] + pinsKnockedDownArray[2]+pinsKnockedDownArray[3]+pinsKnockedDownArray[4] + pinsKnockedDownArray[5] + pinsKnockedDownArray[6]; 
			}
			if (getCurrentFrame() == frameNumber + 1) {
				assert !(Mark.STRIKE.equals(getMark(frameNumber,2))): "frameNumber=" + frameNumber + " getMark(frameNumber,2)=" + getMark(frameNumber,2);
			}
			if (getCurrentFrame() == frameNumber+1 && getCurrentBall()==1) {
				assert !(Mark.SPARE.equals(getMark(frameNumber,2))) : "getMark(frameNumber,2)=" + getMark(frameNumber,2);
			}
		}

		if (frameNumber == 10) {
			assert isGameOver() : "Game is not over!";
		}

		int score=0;
		int currentFrame = 0;
		for (int i=0;  i<rollCount; i++) {
			if (currentFrame<frameNumber && currentFrame<10) {
				if (pinsKnockedDownArray[i]==10) {
					score += (pinsKnockedDownArray[i] + pinsKnockedDownArray[i+1] + pinsKnockedDownArray[i+2]);
					currentFrame++;
				}
				else if (pinsKnockedDownArray[i] + pinsKnockedDownArray[i+1] ==10) {
					score += (pinsKnockedDownArray[i] + pinsKnockedDownArray[i+1] + pinsKnockedDownArray[i+2]);
					i++;
					currentFrame++;
				}
				else {
					score += pinsKnockedDownArray[i];
					score += pinsKnockedDownArray[i+1];
					i++;
					currentFrame++;
				}
			}
			else if(currentFrame==11) {
				score += (pinsKnockedDownArray[i] + pinsKnockedDownArray[i+1] + pinsKnockedDownArray[i+2]);
			}
		}
		return score;
	}

	public boolean isGameOver() {
	
		int currentFrameScan = 1;
		int arrayIndex=0;
		int numberOfStrikesRolled = 0;
		int ballsRolledWithoutStrike = 0;
		while (currentFrameScan<10) {
			if (pinsKnockedDownArray[arrayIndex] == 10) {
				currentFrameScan ++;
				numberOfStrikesRolled ++;
				arrayIndex++;
			}
			else {
				arrayIndex= arrayIndex+2;
				currentFrameScan ++;
				ballsRolledWithoutStrike = ballsRolledWithoutStrike+2;
			}	
		}
		if (arrayIndex>=rollCount) {
			return false;
		}
		

		int numberOfRollsPerformed = numberOfStrikesRolled + ballsRolledWithoutStrike;
		
		if (pinsKnockedDownArray[arrayIndex]==10 && numberOfRollsPerformed+3 == rollCount) {
			return true;
		}
		else if ((pinsKnockedDownArray[arrayIndex] + pinsKnockedDownArray[arrayIndex+1])==10 && numberOfRollsPerformed+3 == rollCount && pinsKnockedDownArray[arrayIndex]!=10) {
			return true;
		}
		else if (pinsKnockedDownArray[arrayIndex] + pinsKnockedDownArray[arrayIndex+1] != 10 && numberOfRollsPerformed+2==rollCount && pinsKnockedDownArray[arrayIndex]!=10){
			return true;
		}
		
		return false;

		
	}

	public void recordRoll(int pinsKnockedDown) 
	{
		assert 0 <= pinsKnockedDown : "pinsKnockedDown = " + pinsKnockedDown + " < 0!";
		assert pinsKnockedDown <= 10 : "pinsKnockedDown = " + pinsKnockedDown + " > 10!";
		assert (getCurrentBall() == 1) || 
				((getCurrentBall() == 2) && (((getCurrentFrame() == 10) && isStrikeOrSpare(getMark(10, 1))) || ((pinsKnockedDownArray[rollCount - 1] + pinsKnockedDown) <= 10))) || 
				((getCurrentBall() == 3) && (((getCurrentFrame() == 10) && isStrikeOrSpare(getMark(10, 2))) || ((pinsKnockedDownArray[rollCount - 1] + pinsKnockedDown) <= 10)));
		assert !isGameOver() : "Game is over!";

		pinsKnockedDownArray[rollCount] = pinsKnockedDown;
		rollCount++;
	}

	public int getCurrentBall() {
		
		assert !isGameOver() : "Game is over!";
		int currentBall = 1;
		if (getCurrentFrame()!= 10) {
			int currentFrameScan = 1;
			int numberOfRolls = 0;
			int arrayIndex=0;
			while (numberOfRolls<rollCount && currentFrameScan<10) {
				if (pinsKnockedDownArray[arrayIndex] == 10) {
					arrayIndex++;
					currentFrameScan ++;
					numberOfRolls++;
					currentBall=1;
				}
				else if ((numberOfRolls+2)<=rollCount && currentFrameScan<10) {
					arrayIndex= arrayIndex+2;
					numberOfRolls = numberOfRolls +2;
					currentFrameScan ++;
					currentBall=1;
				}
				else if ((numberOfRolls+1)<=rollCount && currentFrameScan<10){
					arrayIndex= arrayIndex+2;
					numberOfRolls = numberOfRolls +2;
					currentBall=2;
				}
			}
		}
		else {
			int arrayIndex = 0;
			int frame=1;
			while (frame<10) {
				if (pinsKnockedDownArray[arrayIndex] == 10) {
					frame ++;
					arrayIndex ++;
				}
				else {
					arrayIndex = arrayIndex+2;
					frame ++;
				}
				
			}
			while (arrayIndex<rollCount){
				arrayIndex++;
				currentBall++;
			}
		}
		return currentBall;
	}

	private static final String VERTICAL_SEPARATOR = "#";
	private static final String HORIZONTAL_SEPARATOR = "#";
	private static final String LEFT_EDGE_OF_SMALL_SQUARE = "[";
	private static final String RIGHT_EDGE_OF_SMALL_SQUARE = "]";
	private String getScoreboardDisplay()
	{
		StringBuffer frameNumberLineBuffer = new StringBuffer();
		StringBuffer markLineBuffer = new StringBuffer();
		StringBuffer horizontalRuleBuffer = new StringBuffer();
		StringBuffer scoreLineBuffer = new StringBuffer();
		frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
		
		markLineBuffer.append(VERTICAL_SEPARATOR);
		horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
		scoreLineBuffer.append(VERTICAL_SEPARATOR);
		int theCurrentFrame = 0;
		if (isGameOver()) {
			theCurrentFrame=10;
		}
		else {
			theCurrentFrame=getCurrentFrame();
		}
		
		
		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{

			frameNumberLineBuffer.append("  " + frameNumber + "  ");
			markLineBuffer.append(" ");
			
			if (frameNumber < theCurrentFrame) {
			markLineBuffer.append(getMark(frameNumber, 1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(frameNumber, 2));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			}	
			else if (frameNumber == theCurrentFrame && getCurrentBall() >1) {
				markLineBuffer.append(getMark(frameNumber, 1));
				markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
				markLineBuffer.append(" ");
				markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			}
			else {
				markLineBuffer.append(" ");
				markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
				markLineBuffer.append(" ");
				markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			}
			final int CHARACTER_WIDTH_SCORE_AREA = 5;
			for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) horizontalRuleBuffer.append(HORIZONTAL_SEPARATOR);
			if(isGameOver() || frameNumber < theCurrentFrame)
			{
				int score = getScore(frameNumber);
				final int PADDING_NEEDED_BEHIND_SCORE = 1;
				final int PADDING_NEEDED_IN_FRONT_OF_SCORE = CHARACTER_WIDTH_SCORE_AREA - ("" + score).length() - PADDING_NEEDED_BEHIND_SCORE;
				for(int i = 0; i < PADDING_NEEDED_IN_FRONT_OF_SCORE; i++) scoreLineBuffer.append(" ");
				scoreLineBuffer.append(score);
				for(int i = 0; i < PADDING_NEEDED_BEHIND_SCORE; i++) scoreLineBuffer.append(" ");
			}
			else
			{
				for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) scoreLineBuffer.append(" ");
			}
			
			frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
			markLineBuffer.append(VERTICAL_SEPARATOR);
			horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
			scoreLineBuffer.append(VERTICAL_SEPARATOR);
		}
		//Frame 10:
		{
			final String THREE_SPACES = "   ";
			frameNumberLineBuffer.append(THREE_SPACES + 10 + THREE_SPACES);
			
			markLineBuffer.append(" ");
			if (isGameOver() && theCurrentFrame == 10) {
			markLineBuffer.append(getMark(10, 1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(10, 2));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(10, 3));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			}
			
			else if (getCurrentBall() == 2 && theCurrentFrame==10) {
			markLineBuffer.append(getMark(10,1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(" ");
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(" ");
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			}
			else if (getCurrentBall() == 3 && theCurrentFrame==10) {
				markLineBuffer.append(getMark(10,1));
				markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
				markLineBuffer.append(getMark(10, 2));
				markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
				markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
				markLineBuffer.append(" ");
				markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
				}
			else {
			markLineBuffer.append(" ");
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(" ");
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(" ");
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
					
			}
			
			final int CHARACTER_WIDTH_SCORE_AREA = 8;
			for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) horizontalRuleBuffer.append(HORIZONTAL_SEPARATOR);
			if(isGameOver())
			{
				int score = getScore(10);
				final int PADDING_NEEDED_BEHIND_SCORE = 1;
				final int PADDING_NEEDED_IN_FRONT_OF_SCORE = CHARACTER_WIDTH_SCORE_AREA - ("" + score).length() - PADDING_NEEDED_BEHIND_SCORE;
				for(int i = 0; i < PADDING_NEEDED_IN_FRONT_OF_SCORE; i++) scoreLineBuffer.append(" ");
				scoreLineBuffer.append(score);
				for(int i = 0; i < PADDING_NEEDED_BEHIND_SCORE; i++) scoreLineBuffer.append(" ");
			}
			else
			{
				for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) scoreLineBuffer.append(" ");
			}
			
			frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
			markLineBuffer.append(VERTICAL_SEPARATOR);
			horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
			scoreLineBuffer.append(VERTICAL_SEPARATOR);
		}
			
		return 	getPlayerName() + "\n" +
				horizontalRuleBuffer.toString() + "\n" +
				frameNumberLineBuffer.toString() + "\n" +
				horizontalRuleBuffer.toString() + "\n" +
				markLineBuffer.toString() + "\n" +
				scoreLineBuffer.toString() + "\n" +
				horizontalRuleBuffer.toString();
	}
	
	public String toString()
	{
		return getScoreboardDisplay();
	}

	private boolean isStrikeOrSpare(Mark mark)
	{
		return ((mark == Mark.STRIKE) || (mark == Mark.SPARE));
	}
	public String getFirstNameOfSubmitter() 
	{
		return "Carlos";
	}

	public String getLastNameOfSubmitter() 
	{
		return "Alpuche";
	}
	
	public double getHoursSpentWorkingOnThisAssignment()
	{
		return 14;
	}
	
	public int getScoreAgainstTestCasesSubset()
	{
		return 125;
	}
}