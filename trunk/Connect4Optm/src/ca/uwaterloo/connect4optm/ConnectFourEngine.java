package ca.uwaterloo.connect4optm;




import java.lang.Math.*; 
import java.util.*;

/*import org.apache.*;
import org.apache.commons.*;
import org.apache.commons.math3.*;*/
import org.apache.commons.math3.distribution.*;
import java.util.Arrays;
import java.util.Collections;
//import org.apache.commons.lang.*;


public class ConnectFourEngine {
	private int Board[][]; // board (row,column)
	private int TopPositions[]; // toppositions is an array same size of column number (ex:7)
	private int latest_move;
	private int copy_latest_move;
	private int NumberOfConnectedDisks;
	private int m, n;
	private int Player_Turn;
	private int CopyBoard[][];
	private int CopyTopPositions[];
	private int CopyPlayer_Turn;
	private int p1Numbers[];
	private int p2Numbers[];
	private int currentPNumbers[][];
	
	private int Constants[][];
	
	//private int currentP2Numbers[];
	
	/**
	 * 
	 * @param n_size
	 * @param m_size
	 * @param NumberOfDisks
	 */
	
	public  ConnectFourEngine(int n_size, int m_size, int NumberOfDisks) {
		n=n_size; // number of rows
		m=m_size; // number of columns
		int i;
		Board = new int[n][];
		
		for (i=0; i<n; i++){
			Board[i] = new int[m]; // why this way ??
		}
		TopPositions = new int[m];  
		//p1Numbers = new int[n][NumberOfDisks];
		//p2Numbers = new int[n][NumberOfDisks];
		
		currentPNumbers = new int[2][];
		currentPNumbers[0] = new int[NumberOfDisks];
		currentPNumbers[1] = new int[NumberOfDisks];
		
		Constants = new int[2][];
		Constants[0] = new int[NumberOfDisks];
		Constants[1] = new int[NumberOfDisks];
		
		for(i=0;i<NumberOfDisks; i++){
			Constants[0][i]=(int)Math.pow(10,(i));
			Constants[1][i]=(int)Math.pow(10,(i))-1;
		}
		
		//fill arrays up with zeros
		Arrays.fill(TopPositions, -1);
		NumberOfConnectedDisks=NumberOfDisks; // connect what !
		Player_Turn=1;
		
		CopyBoard = new int[n][]; // array declaration java ??
		for (i=0; i<n; i++){
			CopyBoard[i] = new int[m];
		}
		CopyTopPositions = new int[m];		
		

	}	


	// getting the miniumum value
	public static double getMinValue(double[] array){  
	     double minValue = array[0];  
	     for(int i=1;i<array.length;i++){  
	     if(array[i] < minValue){  
	     minValue = array[i];  
	        }  
	     }  
	    return minValue;  
	}  
	// getting the miniumum value
	public static double getSumValue(double[] array){  
	     double sumValue = array[0];  
	     for(int i=1;i<array.length;i++){  
	    	 sumValue+=array[i];
	     }  
	    return sumValue;  
	}  	
	public void PlayerMove(int Column){ 
		TopPositions[Column]++;
		Board[TopPositions[Column]][Column]=Player_Turn;
		Player_Turn= (Player_Turn==1)?2:1;
		latest_move=Column;
	}
	
	public void print_board(){
		int i,j;
		for (i=n-1; i>=0; i--){
			for (j=0; j<m; j++){
				System.out.print(Board[i][j]+ " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	public int WhosTurn(){
		return Player_Turn;
	}
	
	public void Scoringfn(int row, int co, int playernum){
		//int j=latest_move;
		int i;	
		int ConnectedHor=1;
		int ConnectedFor=1;
		int ConnectedBack=1;
		int ConnectedVer=1;
		//int Type;
		//Type=Board[row][co];
		//int Position=TopPositions[latest_move];
		//currentP1Numbers[];
		Arrays.fill(currentPNumbers[0], 0);		
		Arrays.fill(currentPNumbers[1], 0);
		
		//Vertical Below
		if (row > 0){
			for(i=1; ((i<NumberOfConnectedDisks) && ((row-i)>=0)) ; i++){
				if(Board[row-i][co] == playernum)
					ConnectedVer++;
				else
					break;
			}
		}
		currentPNumbers[playernum-1][ConnectedVer-1]++;

		//Horizontal
		//Right
		if (co+1 < m){ 
			for(i=co+1; (i<(co+NumberOfConnectedDisks) && i<m) ; i++){
				if(Board[row][i] == playernum)
					ConnectedHor++;
				else
					break;
			}
			/*
			// Needs to be improved
			for(i=co-1; i>co-NumberOfConnectedDisks+Connected; i--){
				if(Board[Position][i] == (playernum)
					Connected++;
				else
					break;
			}
			// Needs to be improved
			*/
			/*if (Connected==NumberOfConnectedDisks){
				Result=Type;
				return Result;
			}*/				
		}
		//Left
		if (co > 0){ 
			for(i=co-1; (i>(co-NumberOfConnectedDisks) && i>=0) ; i--){
				if(Board[row][i] == playernum)
					ConnectedHor++;
				else
					break;
			}		
		}
		if (ConnectedHor>= NumberOfConnectedDisks)
			ConnectedHor= NumberOfConnectedDisks;
		currentPNumbers[playernum-1][ConnectedHor-1]++;
		
		//Diag Forward
		//Diag Right Up
		if (co+1 < m){ 
			for(i=1; (i<(NumberOfConnectedDisks) && (co+i<m) && (row+i<n)) ; i++){
				if(Board[row+i][co+i] == playernum)
					ConnectedFor++;
				else
					break;
			}
		}
		//Diag Left Down
		if (co > 0){ 
			for(i=1; ((i<NumberOfConnectedDisks) && (co-i>=0) && (row-i>=0)) ; i++){
				if(Board[row-i][co-i] == playernum)
					ConnectedFor++;
				else
					break;
			}
		}
		if (ConnectedFor>= NumberOfConnectedDisks)
			ConnectedFor= NumberOfConnectedDisks;
		currentPNumbers[playernum-1][ConnectedFor-1]++;		
			
		//Diag Backward
		//Diag Right Down
		if (co+1 < m){ 
			for(i=1; ((i<NumberOfConnectedDisks) && (co+i<m) && (row-i>=0)) ; i++){
				if(Board[row-i][co+i] == playernum)
					ConnectedBack++;
				else
					break;
			}
		}
		//Diag Left Up
		if (co > 0){ 
			for(i=1; ((i<NumberOfConnectedDisks) && (co-i>=0) && (row+i<n)) ; i++){
				if(Board[row+i][co-i] == playernum)
					ConnectedBack++;
				else
					break;
			}
		}
		if (ConnectedBack>= NumberOfConnectedDisks)
			ConnectedBack= NumberOfConnectedDisks;
		currentPNumbers[playernum-1][ConnectedBack-1]++;
	}
	
	private float getScore(int position, int player){
		float Result=0;
		int i;
		int otherPlayer;
		
		otherPlayer= (player==1)?2 :1;
		
		Scoringfn(TopPositions[position]+1, position, player);
		
		Scoringfn(TopPositions[position]+1, position, otherPlayer);
		
		for(i=0; i<NumberOfConnectedDisks; i++){
			Result+=Constants[0][i]*currentPNumbers[player-1][i] + Constants[1][i]*currentPNumbers[otherPlayer-1][i];
		}
		return Result;
	}
	
	public int CheckWin(){
		int Result=0;
		int Connected=0;
		int Position=TopPositions[latest_move];
		int j=latest_move,i;
		int Type;
		
		if (Position>=0){
			Type=Board[Position][j];
			
			//RIGHT

			Connected=1;
			for(i=1; i<NumberOfConnectedDisks && (j+i<m) ; i++){
				if(Board[Position][j+i] == Type)
					Connected++;
				else
					break;
			}	

			//LEFT

			for(i=1; i<NumberOfConnectedDisks && (j-i>=0) ; i++){
				if(Board[Position][j-i] == Type)
					Connected++;
				else
					break;
			}
			
			if (Connected>=NumberOfConnectedDisks){
				Result=Type;
				return Result;
			}

			
			//Below			
			if (Position+1 >= NumberOfConnectedDisks){
				Connected=1;
				for(i=Position-1; i> (Position-NumberOfConnectedDisks) ; i--){
					if(Board[i][j] == Type)
						Connected++;
					else
						break;
				}
				if (Connected>=NumberOfConnectedDisks){
					Result=Type;
					return Result;
				}
			}
			
			// Diag-Left-Down

			Connected=1;
			for(i=1; i<NumberOfConnectedDisks && (Position-i>=0) &&(j-i>=0) ; i++){
				if(Board[Position-i][j-i] == Type)
					Connected++;
				else
					break;
			}
			
			// Diag-Right-UP
			
			for(i=1; i<NumberOfConnectedDisks && (Position+i<n) &&(j+i<m) ; i++){
				if(Board[Position+i][j+i] == Type)
					Connected++;
				else
					break;
			}		
			
			if (Connected>=NumberOfConnectedDisks){
				Result=Type;
				return Result;
			}
		
			// Diag-Right-Down
			

			Connected=1;
			for(i=1; i<NumberOfConnectedDisks && (Position-i>=0) && (j+i<m); i++){
				if(Board[Position-i][j+i] == Type)
					Connected++;
				else
					break;
			}
			
			//Diag-Left-UP	
			for(i=1; i<NumberOfConnectedDisks && (Position+i <n) && (j-i>=0); i++){
				if(Board[Position+i][j-i] == Type)
					Connected++;
				else
					break;
			}
			

			if (Connected>=NumberOfConnectedDisks){
				Result=Type;
				return Result;
			}
	
			// Diag-Right up
		}
		return Result;
	}
	
	
	private void CopyRestoreBoard(int Copy, int Restore){
		int i,j;
		if(Copy==1){
			CopyPlayer_Turn=Player_Turn;
			copy_latest_move=latest_move;
		}else{
			Player_Turn=CopyPlayer_Turn;
			latest_move=copy_latest_move;
		}
		for(j=0;j<m; j++){
			if(Copy==1)
				CopyTopPositions[j]=TopPositions[j];
			else
				TopPositions[j]=CopyTopPositions[j];
			
			for(i=0; i<n; i++){
				if(Copy==1)
					CopyBoard[i][j]=Board[i][j];
				else
					Board[i][j]=CopyBoard[i][j];
			}
		}
	}
	public boolean CheckValidMove(int Column){
		return TopPositions[Column]<=n-2;
	}
	
	public boolean CheckFullBoard(){
		int i;
		boolean Full=true;
		
		for(i=0; i<m; i++){
			if (TopPositions[i]<=n-2){
				Full=false;
				return Full;
			}
		}
		return Full;
	}
	public int NextMoveHint_MC(int NumberOfPaths){
		
		int BestMove=-1;
		float NumberOfWins[]= new float[m];
		
		int NumSteps;
		int icolumn, iTrials;
		Random rand = new Random();
		int RandMove;
		int i;
		int WinMove=-1;
		
		float Score;
		
		
		for(icolumn=0; icolumn<m; icolumn++){
			NumberOfWins[icolumn]=0;
			if (CheckValidMove(icolumn)){
				
				for(iTrials=0; iTrials<NumberOfPaths; iTrials++){
					CopyRestoreBoard(1, 0);
					NumSteps=0;
					while(CheckWin()==0){
						if(CheckFullBoard()){
							break;
						}else{
							WinMove=-1;
							for(i=0;i<m && CheckValidMove(i); i++){
								
								TopPositions[i]++;
								Board[TopPositions[i]][i]=Player_Turn;
								if(CheckWin()==Player_Turn)
								{
									WinMove=i;
									Board[TopPositions[i]][i]=0;
									TopPositions[i]--;
									break;
								}
								Board[TopPositions[i]][i]=0;
								TopPositions[i]--;
							}
							
							if(WinMove==-1)
								do{
									RandMove=rand.nextInt(m);
								}while(!CheckValidMove(RandMove));
							else
								RandMove=WinMove;
							
							Score=getScore(RandMove,Player_Turn);
							PlayerMove(RandMove);
						}
						NumSteps++;
					}
					
					if (CheckWin()==CopyPlayer_Turn){
						NumberOfWins[icolumn]+=1;///(float)NumSteps;
					}else{
						NumberOfWins[icolumn]+=-1;///(float)NumSteps;
					}
					CopyRestoreBoard(0, 1);
				}
				
				
			}else{
				NumberOfWins[icolumn]=Integer.MIN_VALUE;
			}

		}
		float MaxNumberOfWins=-100000000;
		BestMove=-1;
		for( icolumn=0; icolumn<m ; icolumn++){
			if (CheckValidMove(icolumn)){
				if(NumberOfWins[icolumn]>MaxNumberOfWins){
					BestMove=icolumn;
					MaxNumberOfWins=NumberOfWins[icolumn];
				}
			}
		}
		if(BestMove==-1){
			System.out.println("NoBestMove");
		}
		return BestMove;
	}
	public int NextMoveHint_MC_Scoring(int NumberOfPaths){
		
		int BestMove=-1;
		float NumberOfWins[]= new float[m];
		
		int NumSteps;
		int icolumn, iTrials;
		Random rand = new Random();
		int RandMove;
		int i;
		int WinMove=-1;
		double Min;
		float Score;
		int[] numsToGenerate= new int[m];
		double[] discreteProbabilities = new double[m];
		
		for(i=0; i<m; i++){
			numsToGenerate[i]=i;
		}
		
		

		
		
		for(icolumn=0; icolumn<m; icolumn++){
			NumberOfWins[icolumn]=0;
			if (CheckValidMove(icolumn)){
				
				for(iTrials=0; iTrials<NumberOfPaths; iTrials++){
					CopyRestoreBoard(1, 0);
					NumSteps=0;
					PlayerMove(icolumn);
					
					while(CheckWin()==0){
						if(CheckFullBoard()){
							break;
						}else{
							WinMove=-1;
							for(i=0;i<m ; i++){
								
								if(CheckValidMove(i)){
									int lastMoveCopy=latest_move;
									latest_move=i;
									TopPositions[i]++;
									Board[TopPositions[i]][i]=Player_Turn;
									if(CheckWin()==Player_Turn)
									{
										WinMove=i;
										Board[TopPositions[i]][i]=0;
										TopPositions[i]--;
										break;
									}
									Board[TopPositions[i]][i]=0;
									TopPositions[i]--;
									latest_move=lastMoveCopy;
								}
							}
							

							
							if(WinMove==-1)
								do{
									Arrays.fill(discreteProbabilities,0);
									for(i=0;i<m && CheckValidMove(i); i++)
										discreteProbabilities[i]=getScore(i,Player_Turn);
									
									Min= getMinValue(discreteProbabilities);
									for(i=0; i<m; i++){
										if(CheckValidMove(i))
											discreteProbabilities[i]=discreteProbabilities[i]+Min+1;
									}
									
									double Sum = getSumValue(discreteProbabilities);
									

									for(i=0; i<m && CheckValidMove(i); i++){
										if(CheckValidMove(i))
											discreteProbabilities[i]=discreteProbabilities[i]/Sum;
									}
									
									EnumeratedIntegerDistribution distribution = 
										    new EnumeratedIntegerDistribution(numsToGenerate, discreteProbabilities);
										RandMove = distribution.sample();	
								}while(!CheckValidMove(RandMove));
							else
								RandMove=WinMove;
							
							
						
							
							PlayerMove(RandMove);
						}
						NumSteps++;
					}
					
					if (CheckWin()==CopyPlayer_Turn){
						NumberOfWins[icolumn]+=1/(float)NumSteps;
					}else{
						NumberOfWins[icolumn]+=-1/(float)NumSteps;
					}
					//print_board();
					CopyRestoreBoard(0, 1);
				}
				
				
			}else{
				NumberOfWins[icolumn]=Integer.MIN_VALUE;
			}

		}
		float MaxNumberOfWins=-100000000;
		BestMove=-1;
		for( icolumn=0; icolumn<m ; icolumn++){
			if (CheckValidMove(icolumn)){
				if(NumberOfWins[icolumn]>MaxNumberOfWins){
					BestMove=icolumn;
					MaxNumberOfWins=NumberOfWins[icolumn];
				}
			}
		}
		if(BestMove==-1){
			System.out.println("NoBestMove");
		}
		return BestMove;
	}	
}


