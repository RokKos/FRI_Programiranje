import java.util.*;

public class Naloga5 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// Branje podatkov
		// Grid
		int height = in.nextInt();
		int width = in.nextInt();
		boolean[][] map = new boolean[height][width];

		for (int y = height - 1; y >= 0; --y) {
			for (int x = 0; x < width; ++x) {
				int value = in.nextInt();
				map[y][x] = value == 1 ? true : false;
			}
		}

		// Funtions
		int numFunctions = in.nextInt();
		FunctionCall[] allFunctions = new FunctionCall[numFunctions];
		for (int i = 0; i < numFunctions; ++i) {
			int numOperations = in.nextInt();

			String[] array_fu = new String[numOperations];
			for (int j = 0; j < numOperations; ++j) {
				String name = in.next();
				if (name.equals("FUN")) {
					int id = in.nextInt();
					array_fu[j] = name + Integer.toString(id);
				} else {
					array_fu[j] = name;
				}

			}

			FunctionCall fc = new FunctionCall("FUN" + i, array_fu);

			allFunctions[i] = fc;
			/*
			Stack functionStack = new Stack();
			for (int j = 0; j < numOperations; ++j) {
				String name = in.next();
				Call newCall = new Call(name);
				functionStack.push(newCall);
			}
			FunctionCall function = new FunctionCall(FUNCTIONNAME + (i+1), functionStack);
			allFunctions[i] = function;
			*/
		}

		Stack MainStack = new Stack();
		Queue MainQueue = new Queue();
		//int program_id = 0;
		//FunctionCall startingFunction = new FunctionCall(allFunctions[0], program_id);
		StackTrace firstStackTrace = new StackTrace(0, 0);

		MainStack.push(firstStackTrace);

		// Cordinates and orientation
		int y = in.nextInt();
		int x = in.nextInt();
		int orientation = in.nextInt();
		Robot MrRobot = new Robot(x, y, orientation, map, width, height);

		// actions
		int numActions = in.nextInt();

		while (numActions > 0) {

			StackTrace currentStackCall = (StackTrace) MainStack.top();
			//System.out.println(currentStackCall.Name + " " + currentStackCall.FunctionIndex);
			//MainStack.PrintStack();
			//MrRobot.LocationRotation();
			//MrRobot.PrintMap();
			FunctionCall currentFunctionCall = allFunctions[currentStackCall.Name];
			boolean isEndOfThisCall = currentFunctionCall.FunctionsToExecute.length == currentStackCall.FunctionIndex;
			if (isEndOfThisCall) {
				MainStack.pop();
				if (MainStack.top() == null) {
					break;
				}
				continue;
			}

			boolean pushNewFunctionOnStack = false;
			boolean functionFailed = false;
			for (int i = currentStackCall.FunctionIndex; i < currentFunctionCall.FunctionsToExecute.length; ++i) {
				String function = currentFunctionCall.FunctionsToExecute[i];

				if (function.substring(0, 3).equals("FUN")) {
					int newFunctionIndex = Integer.parseInt(function.substring(3, function.length()));

					// Update current function
					MainStack.pop();
					currentStackCall.FunctionIndex = i + 1;
					MainStack.push(currentStackCall);


					// Push new function
					StackTrace newStackTrace = new StackTrace(newFunctionIndex - 1, 0);
					MainStack.push(newStackTrace);


					pushNewFunctionOnStack = true;
					break;
				} else if (function.equals("SETJMP")) {
					// Update before pushing to queue
					MainStack.pop();
					currentStackCall.FunctionIndex = i + 1;
					MainStack.push(currentStackCall);

					Stack deepFucker = DeepFuckingCopy(MainStack);

					//System.out.println("set");
					MainQueue.enqueue(deepFucker);

				} else if (function.equals("JMP")) {
					if (MainQueue.empty()) {
						functionFailed = true;
						break;
					}
					Stack newMainStack = (Stack) MainQueue.front();
					MainQueue.dequeue();

					MainStack = newMainStack;
					//System.out.println("jump");
					pushNewFunctionOnStack = true;

				} else {
					if (function.equals("FWD")) {
						if (!MrRobot.MoveForward()) {
							functionFailed = true;
						}
						//System.out.println("FWD");
					} else if (function.equals("RGT")) {
						MrRobot.RotateRight();
						//System.out.println("RGT");
					} else if (function.equals("LFT")) {
						MrRobot.RotateLeft();
						//System.out.println("LFT");
					}
					numActions--;
					if (functionFailed || numActions == 0) {break;}
				}
			}

			if (!pushNewFunctionOnStack || functionFailed) {
				MainStack.pop();
				if (MainStack.top() == null) {
					break;
				}
				continue;
			}
		}

		MrRobot.LocationRotation();

		// test program for robot
		/*
		MrRobot.PrintMap();
		// Movement
		for (int i =0; i < 3; ++i) {
			MrRobot.MoveForward();
		}
		MrRobot.RotateRight();
		for (int i =0; i < 3; ++i) {
			MrRobot.MoveForward();
		}

		MrRobot.RotateLeft();
		MrRobot.RotateLeft();
		for (int i =0; i < 3; ++i) {
			MrRobot.MoveForward();
		}
		*/
	}

	static Stack DeepFuckingCopy (Stack stack) {
		Stack deepMotherFucker = new Stack();
		Stack arrayToProperPush = new Stack();
		StackElement first = stack.top;  // Get firs element (I changed to public)

		while (first != null) {
			StackTrace deepAsOcean = (StackTrace) first.element;

			StackTrace dontGiveAShit = new StackTrace(deepAsOcean.Name, deepAsOcean.FunctionIndex);
			arrayToProperPush.push(dontGiveAShit);

			first = first.next;

		}

		first = arrayToProperPush.top;
		while (first != null) {
			StackTrace deepAsOcean = (StackTrace) first.element;

			StackTrace dontGiveAShit = new StackTrace(deepAsOcean.Name, deepAsOcean.FunctionIndex);
			deepMotherFucker.push(dontGiveAShit);

			first = first.next;

		}

		return deepMotherFucker;

	}

	static boolean Match3Characters (String a, String b) {
		for (int i = 0; i < 3; ++i) {
			if (a.charAt(i) != b.charAt(i)) { return false; }
		}
		return true;
	}

	static final String FUNCTIONNAME = "FUN";
}



enum Direction {NORTH, EAST, SOUTH, WEST};



class Robot {
	private int x;
	private int y;
	private final int WIDTH;
	private final int HEIGHT;
	private Direction dir;


	static int[][] DirectionChange = new int[][]{
		{1, 0},  // y,x
		{0, 1},
		{-1, 0},
		{0, -1},
	};

	boolean[][] Map;

	public Robot (int _x, int _y, int _dir, boolean[][] _map, int _width, int _height) {
		x = _x;
		y = _y;
		dir = Direction.values()[_dir];
		Map = _map;

		WIDTH = _width;
		HEIGHT = _height;
	}

	public boolean MoveForward () {
		int[] changeDir = DirectionChange[dir.ordinal()];
		int dx = changeDir[1];
		int dy = changeDir[0];

		//System.out.println("old position: x: " + x + " y: " + y + "->");

		boolean isRobotInBounds = x + dx >= 0 && x + dx < WIDTH && y + dy >= 0 && y + dy < HEIGHT;
		if (!isRobotInBounds) { return false; }

		boolean isRobotPathClear = Map[dy + y][dx + x];
		if (isRobotPathClear) { return false; }

		x += dx;
		y += dy;
		//System.out.println("new position: x: " + x + " y: " + y);
		return true;

	}

	public void RotateRight () {
		dir = Direction.values()[(dir.ordinal() + 1) % 4];
		//System.out.println(dir);
	}

	public void RotateLeft () {
		int newDir = dir.ordinal() - 1;
		if (newDir == -1) { newDir = 3; }
		dir = Direction.values()[newDir];
		//System.out.println(dir);
	}

	public void PrintMap() {
		for (int _y = HEIGHT - 1; _y >= 0; --_y) {
			for (int _x = 0; _x < WIDTH; ++_x) {
				if (x == _x && y == _y) {
					System.out.print("X ");
				} else {
					System.out.print((Map[_y][_x] ? 1 : 0) + " ");
				}
			}
			System.out.println();
		}
	}

	public void LocationRotation() {
		System.out.println(y + " " + x + " " + dir.ordinal());
	}
}

class StackElement
{
	Object element;
	StackElement next;

	StackElement()
	{
		element = null;
		next = null;
	}
}

class Stack
{
	//StackElement -> StackElement -> StackElement -> ... -> StackElement
	//     ^
	//     |
	//    top
	//
	// elemente vedno dodajamo in brisemo na zacetku seznama (kazalec top)


	public StackElement top;

	public Stack()
	{
		makenull();
	}

	public void makenull()
	{
		top = null;
	}

	public boolean empty()
	{
		return (top == null);
	}

	public Object top()
	{
		// Funkcija vrne vrhnji element sklada (nanj kaze kazalec top).
		// Elementa NE ODSTRANIMO z vrha sklada!
		if (top == null) { return null; }
		return top.element;
	}

	public void push(Object obj)
	{
		//System.out.println("here");
		// Funkcija vstavi nov element na vrh sklada (oznacuje ga kazalec top)
		StackElement prevTop = top;
		StackElement newElement = new StackElement();
		newElement.element = obj;
		newElement.next = prevTop;
		top = newElement;

	}

	public void pop()
	{
		//System.out.println("hey");
		// Funkcija odstrani element z vrha sklada (oznacuje ga kazalec top)
		top = top.next;
	}

	public void PrintStack() {
		StackElement first = top;  // Get firs element (I changed to public)

		while (first != null) {
			StackTrace st = (StackTrace) first.element;
			System.out.print("FUN" + st.Name + " " + st.FunctionIndex + " ");

			first = first.next;
		}

		System.out.println();
	}
}

class QueueElement
{
	Object element;
	QueueElement next;

	QueueElement()
	{
		element = null;
		next = null;
	}
}

class Queue
{
	//QueueElement -> QueueElement -> QueueElement -> ... -> QueueElement
	//     ^                                                       ^
	//     |                                                       |
	//    front                                                   rear
	//
	// nove elemente dodajamo na konec vrste (kazalec rear)
	// elemente brisemo na zacetku vrste (kazalec front)

	private QueueElement front;
	private QueueElement rear;

	public Queue()
	{
		makenull();
	}

	public void makenull()
	{
		front = null;
		rear = null;
	}

	public boolean empty()
	{
		return (front == null);
	}

	public Object front()
	{
		// funkcija vrne zacetni element vrste (nanj kaze kazalec front).
		// Elementa NE ODSTRANIMO iz vrste!
		if (front == null) { return null; }

		return front.element;
	}

	public void enqueue(Object obj)
	{
		// funkcija doda element na konec vrste (nanj kaze kazalec rear)
		QueueElement newElement = new QueueElement();
		newElement.element = obj;
		if (front == null) {front = newElement; }
		else {
			if (rear == null) {
				rear = newElement;
				front.next = rear;
			} else {
				QueueElement currRear = rear;
				rear = newElement;
				currRear.next = rear;
			}
		}
	}

	public void dequeue()
	{
		// funkcija odstrani zacetni element vrste (nanj kaze kazalec front)
		front = front.next;
		if (front == null) { rear = null;}

	}
}

class StackTrace {
	public int Name;
	public int FunctionIndex;

	StackTrace (int _name, int _functionIndex) {
		Name = _name;
		FunctionIndex = _functionIndex;
	}
}

class FunctionCall {
	public String Name;
	public String[] FunctionsToExecute;

	FunctionCall(String _name, String[] _functionsToExecute){
		Name = _name;
		FunctionsToExecute = _functionsToExecute;
	}
}
/*
class Call {
	public String Name;
	public int UniqueId;

	Call (String _name) {
		Name = _name;
		UniqueId = -1;
	}

	Call (String _name, int _uniqueId) {
		Name = _name;
		UniqueId = _uniqueId;
	}

}

class FunctionCall extends Call {
	public Stack CallsToLoad;

	FunctionCall (String _ime, Stack _callsToLoad) {
		super(_ime);
		CallsToLoad = _callsToLoad;
	}

	FunctionCall (String _ime, Stack _callsToLoad, int _uniqueId) {
		super(_ime, _uniqueId);
		CallsToLoad = _callsToLoad;
	}

	FunctionCall(FunctionCall fun, int _uniqueId) {
		super(fun.Name, _uniqueId);
		CallsToLoad = fun.CallsToLoad;
	}
}
*/